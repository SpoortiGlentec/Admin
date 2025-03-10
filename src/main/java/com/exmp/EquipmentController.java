package com.exmp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Controller
public class EquipmentController {
	private static final String IMAGE_FOLDER_PATH = "src/main/resources/static/images/";

    @Autowired
    private EquipmentService equipmentService;
    @Autowired
    private EquipmentTypeService equipmentTypeService;

    @Autowired
    private LineService lineService;

    @GetMapping("/eq")
    public String showEquipmentPage(Model model) {
        List<Equipment> equipmentList = equipmentService.getAllEquipment();
        List <Equipment> inactiveEquipmentList=equipmentService.getAllInactiveEquipment();
        List<Equipment> activeEquipmentList=equipmentService.getAllActiveEquipment();
        List<EquipmentType> equipmentTypeList = equipmentTypeService.getAllEquipmentTypes();
        model.addAttribute("equipmentTypeList", equipmentTypeList);
        List <Line> activeLineList=lineService.getAllActiveLine();
        model.addAttribute("activeLineList",  activeLineList);
        model.addAttribute("activeEquipmentList", activeEquipmentList);
        model.addAttribute("inactiveEquipmentList", inactiveEquipmentList);
       
        
        return "eq";  // Return the correct view (equipmentList.html)
    }




    @PostMapping("/add-equipment")
    public String addEquipment(@RequestParam("equipmentName") String equipmentName, @RequestParam("lineId") int lineId,@RequestParam("imageFile") MultipartFile imageFile,@RequestParam("equipment_type") String equipment_type) {
        Line line = lineService.getLineById(lineId);
        try {
            String fileName = null;

            // Save the uploaded file
            if (!imageFile.isEmpty()) {
                File uploadDir = new File(IMAGE_FOLDER_PATH);
                if (!uploadDir.exists()) {
                    uploadDir.mkdirs();
                }

                fileName = imageFile.getOriginalFilename();
                Path filePath = Paths.get(IMAGE_FOLDER_PATH + fileName);
                Files.write(filePath, imageFile.getBytes());
            }

            // Store unit with image name in DB
            Equipment equipment = new Equipment(equipmentName, line,fileName,equipment_type);
            equipmentService.addEquipment(equipment);

        } catch (IOException e) {
            e.printStackTrace();
            return "redirect:/unit?error=UploadFailed";
        }// Fetch the line using the lineId
        // Save the new equipment to the database
        return "redirect:/eq";  // Redirect to the list page to show the updated list
    }
    @GetMapping("/edit-equipment/{eqId}")
    public String editEquipment(@PathVariable("eqId") int eqId, Model model) {
        Equipment equipment = equipmentService.getEquipmentById(eqId);
        if (equipment != null) {
            model.addAttribute("equipment", equipment); // Add the equipment to the model
            
            return "equipment"; // Display the edit form
        }
        return "redirect:/eq"; // If equipment not found, redirect to the list
    }

    @PostMapping("/update-equipment")
    public String updateEquipment(@RequestParam("eqId") int eqId, 
                                  @RequestParam("equipmentName") String equipmentName  ,@RequestParam("imageFile") MultipartFile imageFile) {

                
        Equipment equipment = equipmentService.getEquipmentById(eqId);
        if (equipment != null) {
            equipment.setEquipmentName(equipmentName); // Update the equipment's name
          
            if (!imageFile.isEmpty()) {
                try {
                    // Define the path where the image will be saved
                    String uploadDir = "src/main/resources/static/images"; // Fixed directory path
                    String fileName = imageFile.getOriginalFilename(); // Save only the file name
                    Path targetPath = Path.of(uploadDir, fileName);

                    // Save the file to the target location
                    Files.copy(imageFile.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);

                    // Store the file name (not the full path) in the unit object
                    equipment.setImageName(fileName); // Store only the file name (e.g., "back.jpg")

                    // Update the unit object in the database
                    equipmentService.updateEquipment(equipment); // Save the updated unit

                } catch (IOException e) {
                    e.printStackTrace();
                    // Handle error (e.g., return an error message or redirect to a failure page)
                }
            }
            equipmentService.addEquipment(equipment); // Save the updated equipment
        }
        return "redirect:/eq"; // Redirect to the equipment list after updating
    }
   
    @PostMapping("/disable-equipment")
    public String disableEquipment(@RequestParam("eqId") int eqId) {
    	Equipment 	equipment = equipmentService.getEquipmentById(eqId);
        if (equipment != null) {
        	equipment.setStatus("inactive");
        	equipmentService.updateEquipment(equipment);
        }
        return "redirect:/eq";
    }
    @PostMapping("/delete-equipment")
    public String deleteEquipment(@RequestParam("eqId") int eqId) {
    	equipmentService.deleteEquipment(eqId);
        return "redirect:/eq";
    }
    
    
    @PostMapping("/activate-equipment")
    public String activateEquipment(@RequestParam("eqId") int eqId) {
    	equipmentService.activateEquipment(eqId);
        return "redirect:/eq";
    }
        
}