package com.exmp;

import jakarta.persistence.*;

@Entity
@Table(name = "role_assignment") // Explicitly mapping to the database table
public class RoleAssignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "unit_name") // Explicitly mapping column
    private String unitName;

    @Column(name = "date")
    private String date;

    @Column(name = "inspector")
    private String inspector;

    @Column(name = "approver1")
    private String approver1;

    @Column(name = "approver2")
    private String approver2;
    
    @Column(name = "approver3", nullable = true)
    private String approver3;


    @Column(name = "approver4")
    private String approver4;
    @Column(name = "approver5")
    private String approver5;
    
    @Column(name = "manager")
    private String manager;

    
   @Column(name = "inspector2")
    private String inspector2;
	public String getInspector2() {
	return inspector2;
}

public void setInspector2(String inspector2) {
	this.inspector2 = inspector2;
}

	public RoleAssignment() {
		super();
		// TODO Auto-generated constructor stub
	}

	

	public RoleAssignment(Long id, String unitName, String inspector, String approver1, String approver2,
			String approver3, String approver4, String approver5, String manager, String inspector2) {
		super();
		this.id = id;
		this.unitName = unitName;
		this.inspector = inspector;
		this.approver1 = approver1;
		this.approver2 = approver2;
		this.approver3 = approver3;
		this.approver4 = approver4;
		this.approver5 = approver5;
		this.manager = manager;
		this.inspector2 = inspector2;
	}

	public String getManager() {
		return manager;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	

	public String getInspector() {
		return inspector;
	}

	public void setInspector(String inspector) {
		this.inspector = inspector;
	}

	public String getApprover1() {
		return approver1;
	}

	public void setApprover1(String approver1) {
		this.approver1 = approver1;
	}

	public String getApprover2() {
		return approver2;
	}

	public void setApprover2(String approver2) {
		this.approver2 = approver2;
	}

	public String getApprover3() {
		return approver3;
	}

	public void setApprover3(String approver3) {
		this.approver3 = approver3;
	}

	public String getApprover4() {
		return approver4;
	}

	public void setApprover4(String approver4) {
		this.approver4 = approver4;
	}

	public String getApprover5() {
		return approver5;
	}

	public void setApprover5(String approver5) {
		this.approver5 = approver5;
	}

    
	
    
    
}

