package com.exmp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChecklistService {
	@Autowired
    private ChecklistRepository checklistRepository;  // Repository to interact with the database

    // Fetch all checklists from the database
    public List<Checklist> getAllChecklists() {
        return checklistRepository.findAll();
    }

    // Delete a checklist by its ID
    public void deleteChecklistById(Integer checklistId) {
        checklistRepository.deleteById(checklistId);  // Assuming the repository has a deleteById method
    }
    public Checklist getChecklistById(int checklistId) {
        // Return the checklist with the given ID from the repository
        return checklistRepository.findById(checklistId).orElse(null);  // Use findById which returns an Optional
    }
}
