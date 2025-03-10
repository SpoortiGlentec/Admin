package com.exmp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PartTypeService {

    private final PartTypeRepository partTypeRepository;

    public PartTypeService(PartTypeRepository partTypeRepository) {
        this.partTypeRepository = partTypeRepository;
    }

    public List<PartType> getPartsByEquipmentType(Long equipmentTypeId) {
        return partTypeRepository.findByEquipmentTypeId(equipmentTypeId);
    }
}
