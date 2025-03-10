package com.exmp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RoleController {

    @Autowired
    private RoleAssignmentRepository roleAssignmentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UnitRepository unitRepository;

    @GetMapping("/role")
    public String showRolePage(Model model) {
        List<User> users = userRepository.findAll();
        List<Unit> units = unitRepository.findByStatus("Active");

        model.addAttribute("users", users);
        model.addAttribute("units", units);

        return "role";
    }

    @PostMapping("/submit-role")
    public String submitRoleSelection(
            @RequestParam("unit") String unitName,
            @RequestParam("inspector") String inspector,
            @RequestParam("approver1") String approver1,
            @RequestParam("approver2") String approver2,
            @RequestParam("approver3") String approver3,
            @RequestParam("approver4") String approver4,
            @RequestParam("approver5") String approver5,
            Model model) {

        RoleAssignment roleAssignment = new RoleAssignment();
        roleAssignment.setUnitName(unitName);
        roleAssignment.setInspector(inspector);
        roleAssignment.setApprover1(approver1);
        roleAssignment.setApprover2(approver2);
        roleAssignment.setApprover3(approver3);
        roleAssignment.setApprover4(approver4);
        roleAssignment.setApprover5(approver5);

        roleAssignmentRepository.save(roleAssignment); // Save to DB

        // Add the saved values to the model
        model.addAttribute("unit", roleAssignment.getUnitName());
        model.addAttribute("inspector", roleAssignment.getInspector());
        model.addAttribute("approver1", roleAssignment.getApprover1());
        model.addAttribute("approver2", roleAssignment.getApprover2());
        model.addAttribute("approver3", roleAssignment.getApprover3());
        model.addAttribute("approver4", roleAssignment.getApprover4());
        model.addAttribute("approver5", roleAssignment.getApprover5());
        
        model.addAttribute("message", "Role assigned successfully!");

        return "confirmation";
    }

}

