package com.exmp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class EquipmentTypeService {
	@Autowired
    private EquipmentTypeRepository equipmentTypeRepository;
	@Autowired
	private PartTypeRepository partTypeRepository;

    public List<EquipmentType> getAllEquipmentTypes() {
        return equipmentTypeRepository.findAll();
    }

    public EquipmentType getEquipmentTypeById(long equipmentTypeId) {
        return equipmentTypeRepository.findById(equipmentTypeId)
                                       .orElse(null);
    }
    


 
}
