package com.exmp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class PartType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "part_type_id") // Mapping the part_type_id column as the primary key
    private Long id;

    @ManyToOne
    @JoinColumn(name = "eqtype_id") // Foreign key mapping to the EquipmentType entity
    private EquipmentType equipmentType;

    @Column(name = "parttype_name", nullable = false) // Ensuring parttype_name cannot be null
    private String name;

    @Column(name = "equipment_id") // Optional field; nullable is set in the DB schema
    private Integer equipmentId;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EquipmentType getEquipmentType() {
        return equipmentType;
    }

    public void setEquipmentType(EquipmentType equipmentType) {
        this.equipmentType = equipmentType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(Integer equipmentId) {
        this.equipmentId = equipmentId;
    }
}
