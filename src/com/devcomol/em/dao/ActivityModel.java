package com.devcomol.em.dao;

import com.devcomol.em.gui.bones.EnumActivity;

public class ActivityModel {
	EnumActivity enumActivity;
	Employee employee;
	public ActivityModel(EnumActivity enumActivity, Employee employee) {
		this.enumActivity = enumActivity;
		this.employee = employee;
	}
	public EnumActivity getEnumActivity() {
		return enumActivity;
	}
	public void setEnumActivity(EnumActivity enumActivity) {
		this.enumActivity = enumActivity;
	}
	public Employee getEmployee() {
		return employee;
	}
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	@Override
	public String toString() {
		return "ActivityModel [enumActivity=" + enumActivity + ", employee=" + employee + "]";
	}
	
	
}
