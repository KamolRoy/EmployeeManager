package com.devcomol.em.dao;

public class DivisionModel {
	int dID;
	String dName;

	public DivisionModel(int did, String dname) {
		this.dID = did;
		this.dName = dname;
	}

	public int getDid() {
		return dID;
	}

	public void setDid(int did) {
		this.dID = did;
	}

	public String getDname() {
		return dName;
	}

	public void setDname(String dname) {
		this.dName = dname;
	}

	@Override
	public String toString() {
		return dName;
	}

	

}
