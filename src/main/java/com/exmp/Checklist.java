package com.exmp;
 
import jakarta.persistence.Entity;

import jakarta.persistence.FetchType;

import jakarta.persistence.GeneratedValue;

import jakarta.persistence.GenerationType;

import jakarta.persistence.Id;

import jakarta.persistence.JoinColumn;

import jakarta.persistence.ManyToOne;

import jakarta.persistence.Table;
 
@Entity

@Table(name="checklist1")

public class Checklist {
 
    @Id

    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private int checklistId;
 
    @ManyToOne(fetch = FetchType.LAZY)

    @JoinColumn(name = "part_id", nullable = false)

    private Part part;
 
    private String parameterName="NA";

    private String parameterValueType="NA";

    private Float standardValue=0f;

    public Float getStandardValue() {

		return standardValue;

	}

	public void setStandardValue(Float standardValue) {

		this.standardValue = standardValue;

	}

	private String unit="NA";

    private Float measuredValue=0f;

    private Float afterMaintenanceValue=0f;

    private String materialRequired="NA";

    private String inspectionCondition;

    private String capturedImageAfterInspection;

    private String capturedImageDuringInspection;

    private String remark;

    private String remarksByEngineer;

    private String remarksBySectionIncharge;

    private String rootCause;

    private Integer Tolorence=0;


	private Float max_Tolorence;

    private Float min_Tolorence;
 
 
	public Float getMeasuredValue() {

		return measuredValue;

	}

	public void setMeasuredValue(Float measuredValue) {

		this.measuredValue = measuredValue;

	}

	public Float getAfterMaintenanceValue() {

		return afterMaintenanceValue;

	}

	public void setAfterMaintenanceValue(Float afterMaintenanceValue) {

		this.afterMaintenanceValue = afterMaintenanceValue;

	}

	public Integer getTolorence() {

		return Tolorence;

	}

	public void setTolorence(Integer tolorence) {

		Tolorence = tolorence;

	}

	public Float getMax_Tolorence() {

		return max_Tolorence;

	}

	public void setMax_Tolorence(Float max_Tolorence) {

		this.max_Tolorence = max_Tolorence;

	}

	public Float getMin_Tolorence() {

		return min_Tolorence;

	}

	public void setMin_Tolorence(Float min_Tolorence) {

		this.min_Tolorence = min_Tolorence;

	}

	public int getChecklistId() {

		return checklistId;

	}

	public void setChecklistId(int checklistId) {

		this.checklistId = checklistId;

	}

	public Part getPart() {

        return part;

    }
 
    public void setPart(Part part) {

        this.part = part;

    }

	public String getParameterName() {

		return parameterName;

	}

	public void setParameterName(String parameterName) {

		this.parameterName = parameterName;

	}

	public String getParameterValueType() {

		return parameterValueType;

	}

	public void setParameterValueType(String parameterValueType) {

		this.parameterValueType = parameterValueType;

	}


	public String getUnit() {

		return unit;

	}

	public void setUnit(String unit) {

		this.unit = unit;

	}


	public String getMaterialRequired() {

		return materialRequired;

	}

	public void setMaterialRequired(String materialRequired) {

		this.materialRequired = materialRequired;

	}

	public String getInspectionCondition() {

		return inspectionCondition;

	}

	public void setInspectionCondition(String inspectionCondition) {

		this.inspectionCondition = inspectionCondition;

	}

	public String getCapturedImageAfterInspection() {

		return capturedImageAfterInspection;

	}

	public void setCapturedImageAfterInspection(String capturedImageAfterInspection) {

		this.capturedImageAfterInspection = capturedImageAfterInspection;

	}

	public String getCapturedImageDuringInspection() {

		return capturedImageDuringInspection;

	}

	public void setCapturedImageDuringInspection(String capturedImageDuringInspection) {

		this.capturedImageDuringInspection = capturedImageDuringInspection;

	}

	public String getRemark() {

		return remark;

	}

	public void setRemark(String remark) {

		this.remark = remark;

	}

	public String getRemarksByEngineer() {

		return remarksByEngineer;

	}

	public void setRemarksByEngineer(String remarksByEngineer) {

		this.remarksByEngineer = remarksByEngineer;

	}

	public String getRemarksBySectionIncharge() {

		return remarksBySectionIncharge;

	}

	public void setRemarksBySectionIncharge(String remarksBySectionIncharge) {

		this.remarksBySectionIncharge = remarksBySectionIncharge;

	}

	public String getRootCause() {

		return rootCause;

	}

	public void setRootCause(String rootCause) {

		this.rootCause = rootCause;

	}
 
   
 
}

 