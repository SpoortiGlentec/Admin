package com.exmp;



import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Equipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "eq_id")  // Explicitly map the field to the "eq_id" column in the database
    private int eqId;  // Corresponds to "eq_id" in the database

    @Column(name = "equipment_name")  // Explicitly map the field to the "equipment_name" column in the database
    private String equipmentName;

    @ManyToOne
    @JoinColumn(name = "line_id", referencedColumnName = "lineId")
    private Line line;  // Linking to the Line entity
    @Column(name = "status", nullable = false, columnDefinition = "VARCHAR(10) DEFAULT 'active'")  // Added status column
    private String status; 
    @Column(name = "image_name") // New column for storing selected image name
    private String imageName;
    @Column(name = "equipment_type") // New column for storing
    private String equipment_type;
    
    

	// Default constructor
    public Equipment() {}

    // Constructor to initialize the equipment with values
    public Equipment(String equipmentName, Line line,String imageName,String equipment_type) {
        this.equipmentName = equipmentName;
        this.status="active";
        this.imageName=imageName;
        this.line = line;
        this.equipment_type=equipment_type;
    }

    // Getter and Setter for Equipment ID
    public int getEqId() {
        return eqId;
    }

    public void setEqId(int eqId) {
        this.eqId = eqId;
    }

    // Getter and Setter for Equipment Name
    public String getEquipmentName() {
        return equipmentName;
    }

    public void setEquipmentName(String equipmentName) {
        this.equipmentName = equipmentName;
    }

    // Getter and Setter for Line
    public Line getLine() {
        return line;
    }

    public void setLine(Line line) {
        this.line = line;
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
    public String getEquipment_type() {
		return equipment_type;
	}

	public void setEquipment_type(String equipment_type) {
		this.equipment_type = equipment_type;
	}
    
}

