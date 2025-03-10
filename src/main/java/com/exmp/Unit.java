package com.exmp;

import jakarta.persistence.Column;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;


@Entity
public class Unit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Auto-generate ID for the unit
    @Column(name = "unit_id")
    private int unitId;  

    @Column(name = "unit_name")
    private String unitName;  

    @Column(name = "status", nullable = false, columnDefinition = "VARCHAR(20) DEFAULT 'active'")
    private String status; 

    @Column(name = "image_name") // New column for storing selected image name
    private String imageName;

    // Default constructor
    public Unit() {}

    // Constructor to initialize unit with name and image name
    public Unit(String unitName, String imageName) {
        this.unitName = unitName;
        this.imageName = imageName;
        this.status = "active"; // Default status
    }

    // Getters and Setters
    public int getUnitId() {
        return unitId;
    }

    public void setUnitId(int unitId) {
        this.unitId = unitId;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
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
