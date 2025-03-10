package com.exmp;



import jakarta.persistence.*;

@Entity
public class Part {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "part_id")
    private int partId;

    @Column(name = "part_name")
    private String partName;

    @ManyToOne
    @JoinColumn(name = "equipment_id", referencedColumnName = "eq_id")
    private Equipment equipment;  // Each part is associated with an Equipment
   
    @Column(name = "status", nullable = false, columnDefinition = "VARCHAR(10) DEFAULT 'active'")  // Added status column
    private String status; 
    @Column(name = "image_name") // New column for storing selected image name
    private String imageName;
    @Column(name = "part_type") // New column for storing selected image name
    private String part_type;
    @Column(name="equipment_type")
    private String equipment_type;
    public Part() {}

    public Part(String partName, Equipment equipment,String imageName,String part_type,String equipment_type) {
        this.partName = partName;
        this.status="active";
        this.equipment = equipment;
        this.imageName=imageName;
        this.part_type=part_type;
        this.equipment_type=equipment_type;
        
    }

    
    public String getEquipment_type() {
		return equipment_type;
	}

	public void setEquipment_type(String equipment_type) {
		this.equipment_type = equipment_type;
	}

	public int getPartId() {
        return partId;
    }

    public void setPartId(int partId) {
        this.partId = partId;
    }

    public String getPartName() {
        return partName;
    }

    public void setPartName(String partName) {
        this.partName = partName;
    }

    public Equipment getEquipment() {
        return equipment;
    }

    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
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

	public String getPart_type() {
		return part_type;
	}

	public void setPart_type(String part_type) {
		this.part_type = part_type;
	}

	
    
  
}
