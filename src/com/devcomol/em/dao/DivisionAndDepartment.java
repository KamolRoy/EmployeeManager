package com.devcomol.em.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.DefaultListModel;
import javax.swing.ListModel;

import com.devcomol.em.gui.bones.EnumDivision;

public class DivisionAndDepartment {
	public static List<DivisionModel> getDivision(){
		List<DivisionModel> division=new ArrayList<>();
		
		division.add(new DivisionModel(0, EnumDivision.AdminAndPolicy.toString()));
		division.add(new DivisionModel(1, EnumDivision.FieldOperation.toString()));
		division.add(new DivisionModel(2, EnumDivision.TechnicalService.toString()));
		division.add(new DivisionModel(3, EnumDivision.CustomerService.toString()));
		division.add(new DivisionModel(4, EnumDivision.BusinessOperation.toString()));
		
		return division;
	}
	
	public static Map<Integer, ListModel<String>> getDepartment(){
		Map<Integer, ListModel<String>> departments = new HashMap<Integer, ListModel<String>>();
		DefaultListModel<String> dList;

		// -----------------------------------------
		dList = new DefaultListModel<String>();

		dList.addElement("Government Relation");
		dList.addElement("Administration");

		departments.put(0, dList);
		// -----------------------------------------
		dList = new DefaultListModel<String>();
		dList.addElement("BTS Maintenance");
		dList.addElement("Transmission Maintenance");
		dList.addElement("Core and IN Maintenance");
		dList.addElement("Power Management");

		departments.put(1, dList);
		// ------------------------------------------
		dList = new DefaultListModel<String>();
		dList.addElement("Operaton System Support");
		dList.addElement("Surverllance Operation Center");
		dList.addElement("Radio Access Network");
		dList.addElement("IP Transmission System");
		dList.addElement("Core and IN Operation");

		departments.put(2, dList);
		// ------------------------------------------
		dList = new DefaultListModel<String>();
		dList.addElement("Customer Representative");
		dList.addElement("Complain Management");
		dList.addElement("Billing");

		departments.put(3, dList);
		// ------------------------------------------
		dList = new DefaultListModel<String>();
		dList.addElement("Accounting");
		dList.addElement("Treasury");
		dList.addElement("Purchasing");

		departments.put(4, dList);
		
		return departments;
	}
	
	public static Map<Integer, List<String>> getListDepartment(){
		Map<Integer, List<String>> departments = new HashMap<Integer, List<String>>();
		List<String> dList;

		// -----------------------------------------
		dList = new ArrayList<>();

		dList.add("Government Relation");
		dList.add("Administration");

		departments.put(0, dList);
		// -----------------------------------------
		dList = new ArrayList<String>();
		dList.add("BTS Maintenance");
		dList.add("Transmission Maintenance");
		dList.add("Core and IN Maintenance");
		dList.add("Power Management");

		departments.put(1, dList);
		// ------------------------------------------
		dList = new ArrayList<String>();
		dList.add("Operaton System Support");
		dList.add("Surverllance Operation Center");
		dList.add("Radio Access Network");
		dList.add("IP Transmission System");
		dList.add("Core and IN Operation");

		departments.put(2, dList);
		// ------------------------------------------
		dList = new ArrayList<String>();
		dList.add("Customer Representative");
		dList.add("Complain Management");
		dList.add("Billing");

		departments.put(3, dList);
		// ------------------------------------------
		dList = new ArrayList<String>();
		dList.add("Accounting");
		dList.add("Treasury");
		dList.add("Purchasing");

		departments.put(4, dList);
		
		return departments;
	}
}
