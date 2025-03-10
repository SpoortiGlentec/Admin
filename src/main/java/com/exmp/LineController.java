package com.exmp;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class LineController {
	private static final String IMAGE_FOLDER_PATH = "src/main/resources/static/images/";


    @Autowired
    private LineService lineService;

    @Autowired
    private UnitService unitService;
    
    @Autowired
    private LineRepository lineRepository;
    @GetMapping("/line")
    public String showLinePage(Model model) {
        // Fetching active and inactive lines
        List<Line> activeLineList = lineService.getAllActiveLine();
        List<Line> inactiveLineList = lineService.getAllInactiveLine();

        // Fetching the unit list to display in the dropdown
        List<Unit> activeUnitList = unitService.getAllActiveUnits();  // Assuming you have this method in your UnitService

        // Adding data to the model
        model.addAttribute("activeLineList", activeLineList);
        model.addAttribute("inactiveLineList", inactiveLineList);
        model.addAttribute("activeUnitList", activeUnitList);  // Pass the unit list to the view

        return "line";  // This refers to the 'line.html' template
    }



    // Mapping to handle POST request for adding a line
    @PostMapping("/add-line")
    public String addLine(@RequestParam("unitId") int unitId,
                          @RequestParam("lineName") String lineName,
                          @RequestParam("imageFile") MultipartFile imageFile) {
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

            // Fetch the Unit object from the database using unitId
            Optional<Unit> optionalUnit = unitService.getUnitById(unitId);
            if (optionalUnit.isEmpty()) {
                return "redirect:/unit?error=UnitNotFound"; // Handle the case where unit is not found
            }
            Unit unit = optionalUnit.get();

            // Store line with image name in DB
            Line line = new Line(lineName, unit, fileName); // Use fileName instead of undefined imageName
            lineService.addLine(line);

        } catch (IOException e) {
            e.printStackTrace();
            return "redirect:/unit?error=UploadFailed";
        }

        // Redirect back to the form or another page
        return "redirect:/line";
    }

    @GetMapping("/images")
    @ResponseBody
    public List<String> getImageNames() {
        List<String> imageNames = new ArrayList<>();
        File folder = new File(IMAGE_FOLDER_PATH);

        if (folder.exists() && folder.isDirectory()) {
            File[] files = folder.listFiles((dir, name) -> name.endsWith(".jpg") || name.endsWith(".png") || name.endsWith(".jpeg"));
            if (files != null) {
                for (File file : files) {
                    imageNames.add(file.getName());
                }
            }
        }
        return imageNames;
    }

   

        @GetMapping("/lines")
        public String showLineList(Model model) {
            // Fetching all lines from the database
            List<Line> lineList = lineRepository.findAll();
            // Adding the lineList to the model to be accessible in the view
            model.addAttribute("lineList", lineList);
            return "lineList";  // Name of the Thymeleaf template (lineList.html)
        }
        @GetMapping("/edit-line/{lineId}")
        public String editLine(@PathVariable("lineId") int lineId, Model model) {
            Line line = lineService.getLineById(lineId);
            if (line != null) {
                model.addAttribute("line", line);
                return "editLine"; // This will show the form to edit the unit
            }
            return "redirect:/line"; // Redirect to the unit list if unit is not found
        }
        @PostMapping("/update-line")
        public String updateLine(@RequestParam("lineId") int lineId,
                                 @RequestParam("lineName") String lineName,@RequestParam("imageFile") MultipartFile imageFile) {
            Line line = lineService.getLineById(lineId);
            if (line != null) {
                line.setLineName(lineName);
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
                        line.setImageName(fileName); // Store only the file name (e.g., "back.jpg")

                lineService.updateLine(line); // Save updated unit

                    } catch (IOException e) {
                        e.printStackTrace();
                        // Handle error (e.g., return an error message or redirect to a failure page)
                    }}lineService.updateLine(line);}
            return "redirect:/line"; // Redirect back to the unit list after updating
        }
        @PostMapping("/disable-line")
        public String disableLine(@RequestParam("lineId") int lineId) {
            Line line = lineService.getLineById(lineId);
            if (line != null) {
                line.setStatus("inactive");
                lineService.updateLine(line);
            }
            return "redirect:/line";
        }

        @PostMapping("/delete-line")
        public String deleteLine(@RequestParam("lineId") int lineId) {
            lineService.deleteLine(lineId);
            return "redirect:/line";
        }
        
        @PostMapping("/activate-line")
        public String activateLine(@RequestParam("lineId") int lineId) {
            lineService.activateLine(lineId);
            return "redirect:/line";
        }}