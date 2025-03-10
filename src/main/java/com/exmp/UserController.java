package com.exmp;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.ui.Model;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    // Route for the user page where new users can be added manually or from Excel
    @GetMapping("/user")
    public String showUserPage(Model model) {
        List<User> users = userRepository.findAll(); // Fetch all users from DB
        model.addAttribute("userList", users); // Add users to the model
        return "user";  // This should correspond to user.html
    }

    // Handle form submission for adding a new user manually
    @PostMapping("/submitUser")
    public String handleUserSubmission(@RequestParam String username, 
                                       @RequestParam String password, 
                                       @RequestParam String email) {  // Added email field
        // Create and save the user to the database
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(password);
        newUser.setEmail(email);  // Save email

        userRepository.save(newUser);
        return "redirect:/user";  // Redirect back to user page
    }

    // Upload and process an Excel file
    @PostMapping("/uploadExcel")
    public String uploadExcelFile(@RequestParam("excelFile") MultipartFile file, Model model) throws IOException {
        Workbook workbook = new XSSFWorkbook(file.getInputStream());
        Sheet sheet = workbook.getSheetAt(0);  // Assuming the first sheet
        Iterator<Row> rowIterator = sheet.iterator();

        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            
            // Read values from the Excel sheet
            String username = getCellValueAsString(row.getCell(0)); // Column 0
            String password = getCellValueAsString(row.getCell(1)); // Column 1
            String email = getCellValueAsString(row.getCell(2));    // Column 2 (New Email Column)

            // Save user data
            User user = new User();
            user.setUsername(username);
            user.setPassword(password);
            user.setEmail(email); // Save email from Excel

            userRepository.save(user);
        }
        workbook.close();

        model.addAttribute("message", "Excel file uploaded and users added successfully!");
        return "user";  
    }

    private String getCellValueAsString(Cell cell) {
        if (cell == null) {
            return "";
        }
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue().toString();
                } else {
                    double numericValue = cell.getNumericCellValue();
                    return (numericValue == (long) numericValue) ? 
                           String.valueOf((long) numericValue) : 
                           String.valueOf(numericValue);
                }
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            default:
                return "";
        }
    }

    // Delete a user by username
    @PostMapping("/delete-user")
    public String deleteUser(@RequestParam("username") String username) {
        userRepository.deleteById(username);
        return "redirect:/user";  
    }

    // Update user details (password & email)
    @PostMapping("/edit-user")
    public String updateUser(@RequestParam("username") String username, 
                             @RequestParam("newPassword") String newPassword,
                             @RequestParam("newEmail") String newEmail) {  // Update to use new password and email
        User user = userRepository.findById(username).orElse(null);
        if (user != null) {
            // Update only the password and email fields
            user.setPassword(newPassword);
            user.setEmail(newEmail);  // Update email
            userRepository.save(user); // Save updated user
        }
        return "redirect:/user"; // Redirect to user list page
    }

    // Show the edit user form
    @GetMapping("/edit-user/{username}")
    public String showEditUserForm(@PathVariable("username") String username, Model model) {
        User user = userRepository.findById(username).orElse(null);
        if (user != null) {
            model.addAttribute("user", user);  // Pass user object to the form
            return "edit-user"; // Thymeleaf template (edit-user.html)
        }
        return "redirect:/user"; // Redirect if user not found
    }
}
