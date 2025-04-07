package com.exmp;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller

public class ChecklistController {

	@Autowired

	private ChecklistRepository checklistRepository;

	@Autowired

	private ChecklistService checklistService;

	@Autowired

	private PartService partService;

	// Show the checklist form and populate the dropdown with active parts

	@GetMapping("/checklist")

	public String showChecklistForm(Model model) {

		List<Part> activePartList = partService.getAllActivePart();

		model.addAttribute("activePartList", activePartList);

		List<Checklist> checklistList = checklistService.getAllChecklists();

		model.addAttribute("checklistList", checklistList);

		return "checklist";

	}

	// Show form to add a new checklist

	@GetMapping("/add-checklist")

	public String showAddChecklistForm(Model model) {

		List<Part> activePartList = partService.getAllActivePart();

		model.addAttribute("activePartList", activePartList);

		return "add-checklist";

	}

	// Handle form submission to save checklist data

	@PostMapping("/add-checklist")

	public String addChecklist(@RequestParam("partId") int partId,

			@RequestParam("parameterName") String parameterName,

			@RequestParam("inspectionCondition") String inspectionCondition,

			@RequestParam(value = "parameterValueType", required = false) String parameterValueType,

			@RequestParam(value = "standardValue", required = false) Float standardValue,

			@RequestParam(value = "unit", required = false) String unit,

			@RequestParam(value = "Tolorence", required = false) Integer Tolorence,

			@RequestParam(value = "max_Tolorence", required = false) Float max_Tolorence,

			@RequestParam(value = "min_Tolorence", required = false) Float min_Tolorence) {

		Part part = partService.getPartById(partId);

		if (part == null) {

			return "redirect:/error";

		}

		Checklist checklist = new Checklist();

		checklist.setPart(part);

		checklist.setParameterName(parameterName);

		checklist.setInspectionCondition(inspectionCondition);

		if ("with_criteria".equals(inspectionCondition)) {

			checklist.setParameterValueType(parameterValueType);

			checklist.setStandardValue(standardValue);

			checklist.setUnit(unit);

			checklist.setTolorence(Tolorence);

			checklist.setMax_Tolorence(max_Tolorence);

			checklist.setMin_Tolorence(min_Tolorence);

		}

		checklist.setMeasuredValue(null);

		checklist.setAfterMaintenanceValue(null);

		checklist.setMaterialRequired(null);

		checklist.setCapturedImageAfterInspection(null);

		checklist.setCapturedImageDuringInspection(null);

		checklist.setRemark(null);

		checklist.setRemarksByEngineer(null);

		checklist.setRemarksBySectionIncharge(null);

		checklist.setRootCause(null);

		checklistRepository.save(checklist);

		return "redirect:/checklist";

	}

	// Method to handle the delete request for checklist

	@PostMapping("/delete-checklist")

	public String deleteChecklist(@RequestParam("checklistId") int checklistId) {

		checklistService.deleteChecklistById(checklistId);

		return "redirect:/checklist";

	}

// Handle form submission to edit checklist

	@PostMapping("/edit-checklist")

	public String editChecklist(@RequestParam("checklistId") int checklistId,

			@RequestParam("partId") int partId,

			@RequestParam("parameterName") String parameterName,

			@RequestParam("inspectionCondition") String inspectionCondition,

			@RequestParam(value = "parameterValueType", required = false) String parameterValueType,

			@RequestParam(value = "standardValue", required = false) Float standardValue,

			@RequestParam(value = "unit", required = false) String unit,

			@RequestParam(value = "Tolorence", required = false) Integer Tolorence,
			@RequestParam(value = "max_Tolorence", required = false) Float max_Tolorence,

			@RequestParam(value = "min_Tolorence", required = false) Float min_Tolorence, Model model)

	{

		// Fetch the checklist by ID

		Checklist checklist = checklistService.getChecklistById(checklistId);

		if (checklist == null) {

			return "redirect:/error"; // Redirect to error page if checklist not found

		}

		// Update the checklist fields

		checklist.setParameterName(parameterName);

		checklist.setInspectionCondition(inspectionCondition);

		// Handle the case where inspectionCondition is "with_criteria"

		if ("with_criteria".equals(inspectionCondition)) {

			checklist.setParameterValueType(parameterValueType);

			checklist.setStandardValue(standardValue);

			checklist.setUnit(unit);

			checklist.setTolorence(Tolorence);

			checklist.setMax_Tolorence(max_Tolorence);

			checklist.setMin_Tolorence(min_Tolorence);

		} else if ("without_criteria".equals(inspectionCondition)) {

			// Reset the fields to NA when the condition is "without_criteria"

			checklist.setParameterValueType("NA");

			checklist.setStandardValue(0f);

			checklist.setUnit("NA");

			checklist.setTolorence(0);

			checklist.setMax_Tolorence(0f);

			checklist.setMin_Tolorence(0f);

		}

		// Save the updated checklist

		checklistRepository.save(checklist);

		return "redirect:/checklist"; // Redirect back to the checklist list page

	}

}
