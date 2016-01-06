package com.devcomol.em.gui.bones;

public enum EnumGender {
	MALE("male"), FEMALE("female");

	String text;

	private EnumGender(String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return text;
	}
}
