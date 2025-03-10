package com.exmp;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PartTypeRepository extends JpaRepository<PartType, Long> {
    List<PartType> findByEquipmentTypeId(Long equipmentTypeId);
}
