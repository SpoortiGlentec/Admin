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
public class EquipmentService {

    @Autowired
    private EquipmentRepository equipmentRepository;

    public List<Equipment> getAllEquipment() {
        return equipmentRepository.findAll();  // Fetch all equipment from the database
    }

    public void addEquipment(Equipment equipment) {
        equipmentRepository.save(equipment);  // Save the equipment to the database
    }

    public Equipment getEquipmentById(int equipmentId) {
       
        return equipmentRepository.findById(equipmentId).orElse(null);  
    }
    public void updateEquipment(Equipment equipment) {
        equipmentRepository.save(equipment);  // This will update the equipment if it exists
    }

	
	// Fetch all inactive units
    public List<Equipment> getAllInactiveEquipment() {
        return equipmentRepository.findByStatus("inactive");
    }
    public void deleteEquipment(int eqId) {
    	equipmentRepository.deleteById(eqId); 
		
	}
	public List<Equipment> getAllActiveEquipment() {
	    return equipmentRepository.findByStatus("active"); // Fetch only active units
	}
	
	 public void activateEquipment(int eqId) {
		 Equipment equipment = equipmentRepository.findById(eqId).orElse(null);
	        if (equipment != null) {
	            equipment.setStatus("active");  // Change the status to "active"
	            equipmentRepository.save(equipment);  // Save the updated unit
	        }
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

