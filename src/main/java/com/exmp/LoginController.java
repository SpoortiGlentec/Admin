package com.exmp;





import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

@Controller
public class LoginController {

    // Show login page (GET request)
    @GetMapping("/")
    public String showLoginPage() {
        return "index";  // Return the login page (login.html)
    }

    // Handle login form submission (POST request)
    @PostMapping("/")
    public String handleLogin(@RequestParam("username") String username,
                               @RequestParam("password") String password,
                               Model model) {
        // Add logic to check if the username and password are correct
        if ("admin".equals(username) && "123".equals(password)) {
            return "redirect:/dashboard";  // Redirect to the units page after successful login
        } else {
            model.addAttribute("error", "Invalid username or password.");
            return "index";  // Return to the login page with an error message
        }
    }
}
