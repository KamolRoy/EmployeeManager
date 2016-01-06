package com.devcomol.em.gui.sub;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.devcomol.em.dao.ActivityModel;
import com.devcomol.em.dao.Employee;
import com.devcomol.em.gui.bones.EnumActivity;
import com.devcomol.em.gui.bones.EnumGender;

public class TableModelEmployees extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Employee> employees;
	private List<ActivityModel> activityLog;
	private String[] columnName = { "EID", "Name", "Division", "Department", "*Citizen", "TFN", "*Gender", "DOB",
			"*Responsibility" };

	public TableModelEmployees() {
	}

	@Override
	public int getRowCount() {
		return employees.size();
	}

	@Override
	public int getColumnCount() {
		return 9;
	}

	@Override
	public String getColumnName(int col) {
		return columnName[col].toString();
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		switch (columnIndex) {
		case 8:
			return true;
		case 6:
			return true;
		case 4:
			return true;
		default:
			return false;
		}
	}

	@Override
	public Object getValueAt(int row, int col) {

		Employee employee = employees.get(row);

		switch (col) {
		case 0:
			return employee.geteID();
		case 1:
			return (employee.getFirstName() + " " + employee.getLastName());
		case 2:
			return employee.getDivision();
		case 3:
			return employee.getDepartment();
		case 4:
			return employee.isAuCitizen();
		case 5:
			return employee.getTfn();
		case 6:
			return employee.getGender();
		case 7:
			SimpleDateFormat df = new SimpleDateFormat("yyyy-mm-dd");
			return df.format(employee.getDateOfBirth());
		case 8:
			return employee.getResponsibility();
		}

		return "NA";
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		if (employees == null)
			return;
		Employee employee = employees.get(rowIndex);
		switch (columnIndex) {
		case 8:
			employee.setResponsibility((String) aValue);
			activityLog.add(new ActivityModel(EnumActivity.UPDATE, employee));
			break;
		case 6:
			employee.setGender((EnumGender) aValue);
			activityLog.add(new ActivityModel(EnumActivity.UPDATE, employee));
			break;
		case 4:
			employee.setAuCitizen((boolean) aValue);
			activityLog.add(new ActivityModel(EnumActivity.UPDATE, employee));
		default:
			break;
		}
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		Employee employee = employees.get(0);
		Object obj = null;
		switch (columnIndex) {
		case 0:
			obj = employee.geteID();
			return obj.getClass();
		case 1:
			obj = employee.getFirstName();
			return obj.getClass();
		case 2:
			obj = employee.getDivision();
			return obj.getClass();
		case 3:
			obj = employee.getDepartment();
			return obj.getClass();
		case 4:
			obj = employee.isAuCitizen();
			return obj.getClass();
		case 5:
			obj = employee.getTfn();
			return obj.getClass();
		case 6:
			obj = employee.getGender();
			return obj.getClass();
		case 7:
			obj = employee.getDateOfBirth().toString();
			return obj.getClass();
		case 8:
			obj = employee.getResponsibility();
			return obj.getClass();
		default:
			return null;
		}
	}

	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}

	public void setActivityLog(List<ActivityModel> activityLog) {
		this.activityLog = activityLog;

	}

}
