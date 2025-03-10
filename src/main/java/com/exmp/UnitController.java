package com.exmp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/unit")
public class UnitController {

    private static final String IMAGE_FOLDER_PATH = "src/main/resources/static/images/";

    @Autowired
    private UnitService unitService;
    
    @Autowired
    private LineService lineService;
    @Autowired
    private EquipmentService equipmentService;
    @Autowired
    private PartService partService;
//    @Autowired
//   // private ChecklistService checklistService;

    @GetMapping
    public String viewUnitList(Model model) {
        List<Line> lineList = lineService.getAllLines();
        List<Equipment> equipmentList = equipmentService.getAllEquipment();
        List<Unit> unitList = unitService.getAllUnits();
        List<Unit> activeUnitList = unitService.getAllActiveUnits();
        List<Unit> inactiveUnitList = unitService.getAllInactiveUnits();
        List<Line> activeLineList = lineService.getAllActiveLine();
        List<Line> inactiveLineList = lineService.getAllInactiveLine();
        List<Equipment> activeEquipmentList = equipmentService.getAllActiveEquipment();
        List<Equipment> inactiveEquipmentList = equipmentService.getAllInactiveEquipment();
        List<Part> partList = partService.getAllParts();
        List<Part> activePartList = partService.getAllActivePart();
        List<Part> inactivePartList = partService.getAllInactivePart();
      //  List<Checklist> checklistList = checklistService.getAllChecklistParameters();

        // Fetch uploaded image names
        List<String> imageNames = getImageNames();

      //  model.addAttribute("checklistList", checklistList);
        model.addAttribute("activeUnitList", activeUnitList);
        model.addAttribute("inactiveUnitList", inactiveUnitList);
        model.addAttribute("activeLineList", activeLineList);
        model.addAttribute("inactiveLineList", inactiveLineList);
        model.addAttribute("activeEquipmentList", activeEquipmentList);
        model.addAttribute("inactiveEquipmentList", inactiveEquipmentList);
        model.addAttribute("activePartList", activePartList);
        model.addAttribute("inactivePartList", inactivePartList);
        model.addAttribute("lineList", lineList);
        model.addAttribute("equipmentList", equipmentList);
        model.addAttribute("unitList", unitList);
        model.addAttribute("partList", partList);
        model.addAttribute("imageNames", imageNames);

        return "unit"; 
    }

    @PostMapping("/add-unit")
    public String addUnit(@RequestParam("name") String name, 
                          @RequestParam("imageFile") MultipartFile imageFile) {
        try {
            String fileName = null;

            // Check if the file is of a valid type (only .jpg and .jpeg)
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

            // Store unit with image name in DB
            Unit unit = new Unit(name, fileName);
            unitService.addUnit(unit);

        } catch (IOException e) {
            e.printStackTrace();
            return "redirect:/unit?error=UploadFailed";
        }

        return "redirect:/unit"; 
    }


    @GetMapping("/images")
    @ResponseBody
    public List<String> getImageNames() {
        List<String> imageNames = new ArrayList<>();
        File folder = new File(IMAGE_FOLDER_PATH);

        if (folder.exists() && folder.isDirectory()) {
            File[] files = folder.listFiles((dir, name) -> 
                name.endsWith(".jpg") || name.endsWith(".jpeg") // Filter for .jpg and .jpeg files only
            );
            if (files != null) {
                for (File file : files) {
                    imageNames.add(file.getName());
                }
            }
        }
        return imageNames;
    }

    @GetMapping("/edit-unit/{unitId}")
    public String editUnit(@PathVariable("unitId") int unitId, Model model) {
        Optional<Unit> unit = unitService.getUnitById(unitId);
        if (unit != null) {
            model.addAttribute("unit", unit);
            return "editUnit"; 
        }
        return "redirect:/unit"; 
    }

    @PostMapping("/update-unit")
    public String updateUnit(@RequestParam("unitId") int unitId,
                             @RequestParam("unitName") String unitName,
                             @RequestParam("imageFile") MultipartFile imageFile) {

        Optional<Unit> optionalUnit = unitService.getUnitById(unitId);
        
        if (optionalUnit.isPresent()) {
            Unit unit = optionalUnit.get();
            
            // Update the unit name
            unit.setUnitName(unitName);

            // Handling image upload
            if (!imageFile.isEmpty()) {
                // Validate the file type (only .jpg and .jpeg)
                String fileExtension = imageFile.getOriginalFilename().toLowerCase();
                if (!fileExtension.endsWith(".jpg") && !fileExtension.endsWith(".jpeg")) {
                    return "redirect:/unit?error=InvalidFileType";  // Redirect with an error message
                }

                try {
                    // Define the path where the image will be saved
                    String uploadDir = "src/main/resources/static/images"; // Fixed directory path
                    String fileName = imageFile.getOriginalFilename(); // Save only the file name
                    Path targetPath = Path.of(uploadDir, fileName);

                    // Save the file to the target location
                    Files.copy(imageFile.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);

                    // Store the file name (not the full path) in the unit object
                    unit.setImageName(fileName); // Store only the file name (e.g., "back.jpg")

                    // Update the unit object in the database
                    unitService.updateUnit(unit); // Save the updated unit

                } catch (IOException e) {
                    e.printStackTrace();
                    // Handle error (e.g., return an error message or redirect to a failure page)
                }
            }

            // Update the unit with the new name and image
            unitService.updateUnit(unit); 
        }

        return "redirect:/unit"; // Redirect to the unit list page after updating
    }


    @PostMapping("/disable-unit")
    public String disableUnit(@RequestParam("unitId") int unitId) {
        unitService.getUnitById(unitId).ifPresent(unit -> {
            unit.setStatus("inactive");
            unitService.updateUnit(unit);
        });
        return "redirect:/unit";
    }


    @PostMapping("/delete-unit")
    public String deleteUnit(@RequestParam("unitId") int unitId) {
        unitService.deleteUnit(unitId);
        return "redirect:/unit";
    }

    @PostMapping("/activate-unit")
    public String activateUnit(@RequestParam("unitId") int unitId) {
        unitService.activateUnit(unitId);
        return "redirect:/unit";
    }
}
