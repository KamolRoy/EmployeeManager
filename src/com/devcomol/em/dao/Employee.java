package com.devcomol.em.dao;

import java.io.Serializable;
import java.util.Date;

import org.springframework.stereotype.Component;

import com.devcomol.em.gui.bones.EnumDivision;
import com.devcomol.em.gui.bones.EnumGender;

@Component
public class Employee implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int eID;
	private String firstName;
	private String lastName;
	private EnumDivision division;
	private String department;
	private boolean auCitizen;
	private int tfn;
	private EnumGender gender;
	private Date dateOfBirth;
	private String responsibility;

	public Employee() {
	}

	public Employee(int eID, String firstName, String lastName, EnumDivision division, String department,
			boolean auCitizen, int tfn, EnumGender gender, Date dateOfBirth, String responsibility) {
		super();
		this.eID = eID;
		this.firstName = firstName;
		this.lastName = lastName;
		this.division = division;
		this.department = department;
		this.auCitizen = auCitizen;
		this.tfn = tfn;
		this.gender = gender;
		this.dateOfBirth = dateOfBirth;
		this.responsibility = responsibility;
	}

	public int geteID() {
		return eID;
	}

	public void seteID(int eID) {
		this.eID = eID;
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

	public EnumDivision getDivision() {
		return division;
	}

	public void setDivision(EnumDivision division) {
		this.division = division;
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

	public int getTfn() {
		return tfn;
	}

	public void setTfn(int tfn) {
		this.tfn = tfn;
	}

	public EnumGender getGender() {
		return gender;
	}

	public void setGender(EnumGender gender) {
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

	@Override
	public String toString() {
		return "Employee [eID=" + eID + ", firstName=" + firstName + ", lastName=" + lastName + ", division=" + division
				+ ", department=" + department + ", auCitizen=" + auCitizen + ", tfn=" + tfn + ", gender=" + gender
				+ ", dateOfBirth=" + dateOfBirth + ", responsibility=" + responsibility + "]";
	}

}
