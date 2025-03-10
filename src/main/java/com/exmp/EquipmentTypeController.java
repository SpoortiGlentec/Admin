package com.exmp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class EquipmentTypeController {
	@Autowired
    private EquipmentTypeService equipmentTypeService;

    @GetMapping("/add-equipment")
    public String showAddEquipmentForm(Model model) {
        List<EquipmentType> equipmentTypeList = equipmentTypeService.getAllEquipmentTypes();
        model.addAttribute("equipmentTypeList", equipmentTypeList);
        return "add-equipment";  // Make sure this matches your Thymeleaf template
    }
}
