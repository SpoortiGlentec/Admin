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
public class LineService {

    @Autowired
    private LineRepository lineRepository;
    @Value("${image.folder.path}")
    private String imageFolderPath;

    public List<Line> getAllLines() {
        return lineRepository.findAll();
    }

    public void addLine(Line line) {
        lineRepository.save(line);  // Save the line to the database
    }

	public Line getLineById(int lineId) {
		 return lineRepository.findById(lineId).orElse(null);
	}

	public void updateLine(Line line) {
		lineRepository.save(line);
		
	}
	// Fetch all inactive units
    public List<Line> getAllInactiveLine() {
        return lineRepository.findByStatus("inactive");
    }
    public void deleteLine(int lineId) {
		  lineRepository.deleteById(lineId); 
		
	}
	public List<Line> getAllActiveLine() {
	    return lineRepository.findByStatus("active"); // Fetch only active units
	}
	
	 public void activateLine(int lineId) {
	        Line line = lineRepository.findById(lineId).orElse(null);
	        if (line != null) {
	            line.setStatus("active");  // Change the status to "active"
	            lineRepository.save(line);  // Save the updated unit
	        }
	    }
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
	 public Optional<Line> getUnitById(int lineId) {
	        return lineRepository.findById(lineId);
	    }
} 