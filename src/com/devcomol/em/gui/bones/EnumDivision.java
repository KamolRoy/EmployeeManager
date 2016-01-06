package com.devcomol.em.gui.bones;

public enum EnumDivision {
	AdminAndPolicy("Admin & Policy"),
	FieldOperation("Field Operation"),
	TechnicalService("Technical Service"),
	CustomerService("Customer Service"),
	BusinessOperation("Business Operation"),
	NoDivision("No Division");
	
	String text;
	
	private EnumDivision(String text){
		this.text=text;
	}
	
	@Override
	public String toString() {
		return text;
	}
}
