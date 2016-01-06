package com.devcomol.em.gui.bones;

import java.util.Date;
import java.util.EventObject;

public class FormActionEvent extends EventObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String firstName;
	private String lastName;
	private int divisionID;
	private String department;
	private boolean auCitizen;
	private boolean hasTfn;
	private String tfn;
	private String gender;
	private Date dateOfBirth;
	private String responsibility;

	public FormActionEvent(Object source) {
		super(source);
	}

	public FormActionEvent(Object source, String firstName, String lastName, int divisionID, String department,
			boolean auCitizen, boolean hasTfn, String tfn, String gender, Date dateOfBirth, String responsibility) {
		super(source);
		this.firstName = firstName;
		this.lastName = lastName;
		this.divisionID = divisionID;
		this.department = department;
		this.auCitizen = auCitizen;
		this.hasTfn = hasTfn;
		this.tfn = tfn;
		this.gender = gender;
		this.dateOfBirth = dateOfBirth;
		this.responsibility = responsibility;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public int getDivisionID() {
		return divisionID;
	}

	public void setDivisionID(int divisionID) {
		this.divisionID = divisionID;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public boolean isAuCitizen() {
		return auCitizen;
	}

	public void setAuCitizen(boolean auCitizen) {
		this.auCitizen = auCitizen;
	}

	public boolean isHasTfn() {
		return hasTfn;
	}

	public void setHasTfn(boolean hasTfn) {
		this.hasTfn = hasTfn;
	}

	public String getTfn() {
		return tfn;
	}

	public void setTfn(String tfn) {
		this.tfn = tfn;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getResponsibility() {
		return responsibility;
	}

	public void setResponsibility(String responsibility) {
		this.responsibility = responsibility;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "FormActionEvent [firstName=" + firstName + ", lastName=" + lastName + ", divisionID=" + divisionID
				+ ", department=" + department + ", auCitizen=" + auCitizen + ", hasTfn=" + hasTfn + ", tfn=" + tfn
				+ ", gender=" + gender + ", dateOfBirth=" + dateOfBirth + ", responsibility=" + responsibility + "]";
	}

}
