package com.exmp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class PartService {

    @Autowired
    private PartRepository partRepository;

    // Fetch all parts from the database
    public List<Part> getAllParts() {
        return partRepository.findAll();
    }

    // Save a new part to the database
    public void addPart(Part part) {
        partRepository.save(part);
    }

    // Fetch a part by its ID
    public Part getPartById(int partId) {
        return partRepository.findById(partId).orElse(null);
    }
   
    
    public void updatePart(Part part) {
        partRepository.save(part); // Save the updated part to the database
    }
    public List<Part> getAllInactivePart() {
        return partRepository.findByStatus("inactive");
    }
    public List<Part> getAllActivePart() {
	    return partRepository.findByStatus("active"); // Fetch only active units
	}
    public void activatePart(int partId) {
		 Part part = partRepository.findById(partId).orElse(null);
	        if (part != null) {
	            part.setStatus("active");  // Change the status to "active"
	            partRepository.save(part);  // Save the updated unit
	        }
	    }
    public void deletePart(int partId) {
    	partRepository.deleteById(partId); 
		
	}
    @Value("${image.folder.path}")
    private String imageFolderPath;
 public List<String> getAllImageNames() {
        File folder = new File(imageFolderPath);
        if (!folder.exists() || !folder.isDirectory()) {
            return List.of();
        }
        return Arrays.stream(Optional.ofNullable(folder.listFiles((dir, name) -> name.matches(".*\\.(jpg|png|jpeg)$")))
                .orElse(new File[0]))
                .map(File::getName)
                .collect(Collectors.toList());
    }
}