package com.exmp;





import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;

@Entity
public class Line {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Auto-generate ID for the line
    private int lineId;  // Corresponds to "line_id" in the database
    @Column(name = "line_name")
    private String lineName;  // Corresponds to "line_name" in the database

  
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "unit_id")// Explicitly map to the "unit_id" column in the database
    private Unit unit;  // The unit to which this line belongs
    
    @Column(name = "status", nullable = false, columnDefinition = "VARCHAR(10) DEFAULT 'active'")  // Added status column
    private String status; 
    @Column(name = "image_name") // New column for storing selected image name
    private String imageName;
    
    
    // Default constructor
    public Line() {}

    // Constructor to initialize the line with values
    public Line(String lineName, Unit unit,String imageName) {
        this.lineName = lineName;
        this.status="active";
        this.unit = unit;
        this.imageName = imageName;
    }

    // Getter and Setter for Line ID
    public int getLineId() {
        return lineId;
    }

    public void setLineId(int lineId) {
        this.lineId = lineId;
    }

    // Getter and Setter for Line Name
    public String getLineName() {
        return lineName;
    }

    public void setLineName(String lineName) {
        this.lineName = lineName;
    }

    // Getter and Setter for Unit
    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }
}
