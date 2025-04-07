package com.exmp;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.ui.Model;
@Controller
public class PartController {
	@Value("${image.folder.path}")
    private String IMAGE_FOLDER_PATH;


    @Autowired
    private PartService partService;
    @Autowired
    private EquipmentTypeService equipmenttype;

    @Autowired
    private EquipmentService equipmentService;
    @Autowired
    private PartTypeService partTypeService;

    @GetMapping("/part")
    public String showPartPage(Model model) {
    	List<Part> activePartList=partService.getAllActivePart();
    	List<Part> inactivePartList=partService.getAllInactivePart();
    	List <EquipmentType>  eqtype=equipmenttype.getAllEquipmentTypes();
    	List <Equipment> activeEquipmentList=equipmentService.getAllActiveEquipment();
    	 model.addAttribute("partTypeList", new ArrayList<PartType>());
        List<Part> partList = partService.getAllParts();  // Fetch all parts from the database
        model.addAttribute("partList", partList);  // Add parts to the model
        model.addAttribute("activePartList", activePartList); 
        model.addAttribute("inactivePartList", inactivePartList);
        model.addAttribute("activeEquipmentList",activeEquipmentList);
        model.addAttribute("eqtype",eqtype);
        // Add equipment to the model
        return "part";  // Return the correct view (index.html)
    }
    @GetMapping("/get-equipment-type")
    @ResponseBody
    public String getEquipmentType(@RequestParam int equipmentId) {
        System.out.println("Received equipmentId: " + equipmentId);  // Log the equipmentId for debugging

        Equipment equipment = equipmentService.getEquipmentById(equipmentId);
        if (equipment != null) {
            System.out.println("Equipment Type: " + equipment.getEquipment_type());  // Log the equipment type
            return equipment.getEquipment_type();  // Returns the equipment type
        } else {
            return "";  // Return empty if no equipment found
        }
    }
    
    

//    @GetMapping("/get-part-types")
//    @ResponseBody
//    public List<PartType> getPartTypes(@RequestParam long equipmentType) {
//        System.out.println("Received request to get part types for equipment type ID: " + equipmentType);
//
//        // Fetch the EquipmentType object by ID
//        EquipmentType eqType = equipmenttype.getEquipmentTypeById(equipmentType);
//        
//        if (eqType != null) {
//            System.out.println("Found EquipmentType: " + eqType.getName());
//            
//            // Fetch part types based on the equipment type
//            List<PartType> partTypes = partTypeService.getPartsByEquipmentType(eqType);
//            System.out.println("Number of part types found: " + partTypes.size());
//            
//            return partTypes;
//        } else {
//            System.out.println("No EquipmentType found for ID: " + equipmentType);
//            return new ArrayList<>();  // Return an empty list if no equipment type is found
//        }
//    }
    @GetMapping("/get-part-types")
    @ResponseBody
    public List<PartTypeResponse> getPartTypes(@RequestParam("equipment_type") Long equipmentTypeId) {
        // Log the received parameter
        System.out.println("Received equipmentTypeId: " + equipmentTypeId);

        // Fetch part types using the service method
        List<PartType> partTypes = partTypeService.getPartsByEquipmentType(equipmentTypeId);

        // Convert PartType entities to PartTypeResponse for API response
        return partTypes.stream()
                .map(partType -> new PartTypeResponse(partType.getId(), partType.getName()))
                .collect(Collectors.toList());
    }
    @PostMapping("/add-part")
    public String addPart(@RequestParam("partName") String partName, @RequestParam("equipmentId") int equipmentId,@RequestParam("imageFile") MultipartFile imageFile,@RequestParam("part_type")String part_type,@RequestParam("equipment_type")String equipment_type) {
        Equipment equipment = equipmentService.getEquipmentById(equipmentId);  // Fetch the equipment by ID
        try {
            String fileName = null;
            String fileExtension = imageFile.getOriginalFilename().toLowerCase();
            if (!fileExtension.endsWith(".jpg") && !fileExtension.endsWith(".jpeg")) {
                return "redirect:/unit?error=InvalidFileType";  // Redirect with an error message
            }

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

            Part part = new Part(partName, equipment,fileName,part_type,equipment_type);  // Create a new part associated with the equipment
            partService.addPart(part);
        } catch (IOException e) {
            e.printStackTrace();
            return "redirect:/unit?error=UploadFailed";
        }// Fetch the line using the lineId
        // Save the new equipment to the database
        return "redirect:/part";  // R
    }
   
   
        
    @GetMapping("/edit-part/{partId}")
    public String editPart(@PathVariable("partId") int partId, Model model) {
        Part part = partService.getPartById(partId);
        if (part != null) {
            model.addAttribute("part", part); // Add the part to the model to populate the form
            return "editPart"; // Display the edit form
        }
        return "redirect:/"; // If part not found, redirect to the part list
    }

    @PostMapping("/update-part")
    public String updatePart(@RequestParam("partId") int partId, 
                             @RequestParam("partName") String partName, @RequestParam("imageFile") MultipartFile imageFile) {
        Part part = partService.getPartById(partId);
        if (part != null) {
            part.setPartName(partName); // Update the part's name
            // Handling image upload
            if (!imageFile.isEmpty()) {
                try {
                    // Define the path where the image will be saved
                    String uploadDir = "src/main/resources/static/images"; // Fixed directory path
                    String fileName = imageFile.getOriginalFilename(); // Save only the file name
                    Path targetPath = Path.of(uploadDir, fileName);

                    // Save the file to the target location
                    Files.copy(imageFile.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);

                    // Store the file name (not the full path) in the unit object
                    part.setImageName(fileName); // Store only the file name (e.g., "back.jpg")

                    // Update the unit object in the database
                    partService.updatePart(part); // Save the updated unit

                } catch (IOException e) {
                    e.printStackTrace();
                    // Handle error (e.g., return an error message or redirect to a failure page)
                }
            }

            partService.addPart(part); // Save the updated part to the database
        }
        return "redirect:/part"; // Redirect to the part list after updating
    }
    @PostMapping("/disable-part")
    public String disablePart(@RequestParam("partId") int partId) {
    	Part part = partService.getPartById(partId);
        if (part != null) {
        	part.setStatus("inactive");
        	partService.updatePart(part);
        }
        return "redirect:/part";
    }
    @PostMapping("/delete-part")
    public String deletePart(@RequestParam("partId") int partId) {
    	partService.deletePart(partId);
        return "redirect:/part";
    }
    
    
    @PostMapping("/activate-part")
    public String activatePart(@RequestParam("partId") int partId) {
    	partService.activatePart(partId);
        return "redirect:/part";
    }


}