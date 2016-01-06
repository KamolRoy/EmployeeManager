package com.devcomol.em.controller;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.devcomol.em.dao.ActivityModel;
import com.devcomol.em.dao.Employee;
import com.devcomol.em.dao.EmployeeDAO;
import com.devcomol.em.gui.bones.EnumActivity;
import com.devcomol.em.gui.bones.EnumDivision;
import com.devcomol.em.gui.bones.EnumGender;
import com.devcomol.em.gui.bones.FormActionEvent;

@Component("controller")
public class Controller {
	private int eID;
	@Autowired
	private EmployeeDAO employeeDAO;
	private Employee employee;
	private List<ActivityModel> activityLog;
	private JFrame parentFrame;

	public void setParentFrame(JFrame parentFrame) {
		this.parentFrame = parentFrame;
	}

	public Controller() {
	}

	public void addEmployee(FormActionEvent formActionEvent) {
		employee = new Employee();
		// Embedded database can't initialize instantly. Thus, getLastPK() need
		// to invoke later time
		seteID(getLastPK());
		increaseEID();
		employee.seteID(geteID());
		String firstName = formActionEvent.getFirstName();
		String lastName = formActionEvent.getLastName();

		if (firstName.length() > 0) {
			employee.setFirstName(firstName);
		} else {
			JOptionPane.showMessageDialog(null, "First Name can not be empty", "Form Input Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		if (lastName.length() > 0) {
			employee.setLastName(lastName);
		} else {
			JOptionPane.showMessageDialog(null, "Last Name can not be empty", "Form Input Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		for (ActivityModel activity : activityLog) {
			if (activity.getEnumActivity() == EnumActivity.INSERT) {
				Employee checkEmployee = activity.getEmployee();
				if (checkEmployee.getFirstName().equals(firstName) && checkEmployee.getLastName().equals(lastName)) {
					JOptionPane.showMessageDialog(null, "Duplicate employee name: " + firstName + " " + lastName,
							"Form Input Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
			}
		}

		employee.setDivision(GetSpecificType.getDivision(formActionEvent.getDivisionID()));
		employee.setDepartment(formActionEvent.getDepartment());
		employee.setAuCitizen(formActionEvent.isAuCitizen());

		if (formActionEvent.isHasTfn()) {

			if (formActionEvent.getTfn().length() <= 9) {
				employee.setTfn(Integer.parseInt(formActionEvent.getTfn()));
			} else {
				JOptionPane.showMessageDialog(null, "TFN no can not be more than 9 digit", "Form Input Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
		} else {
			employee.setTfn(0);
		}
		employee.setGender(GetSpecificType.getGender(formActionEvent.getGender()));
		employee.setDateOfBirth(formActionEvent.getDateOfBirth());
		employee.setResponsibility(formActionEvent.getResponsibility());

		if (!dataExist(employee)) {
			employeeDAO.addEmployee(employee);
			activityLog.add(new ActivityModel(EnumActivity.INSERT, employee));
		}

		/*
		 * try { insertToDatabase(employee); } catch (DuplicateKeyException e) {
		 * e.getMessage(); }
		 */
	}

	public int geteID() {
		return eID;
	}

	public void seteID(int eID) {
		this.eID = eID;
	}

	public void increaseEID() {
		eID++;
	}

	public List<Employee> getEmployees() {
		return employeeDAO.getEmployees();
	}

	public void saveToFile(File file) throws IOException {
		employeeDAO.saveToFile(file);
	}

	public void loadFromFile(File file) throws ClassNotFoundException, IOException {
		employeeDAO.loadFromFile(file);
	}

	public void deleteEmployee(int eid, int rowIndex) {
		Employee employee = employeeDAO.getEmployee(rowIndex);
		activityLog.add(new ActivityModel(EnumActivity.DELETE, employee));
		employeeDAO.deleteEmployee(rowIndex);
	}

	public void clear() {
		employeeDAO.clear();
		activityLog.clear();
	}

	public void saveChange() {
		StringBuilder sb = new StringBuilder();
		if (activityLog.size() == 0) {
			JOptionPane.showMessageDialog(parentFrame, "Nothing to change", "Information",
					JOptionPane.INFORMATION_MESSAGE);
			System.out.println("Nothing to change");
			return;
		} else {
			sb.append("Change Log: \n");
		}
		for (ActivityModel actLog : activityLog) {
			EnumActivity activity = actLog.getEnumActivity();
			Employee employee = actLog.getEmployee();
			switch (activity) {
			case INSERT:
				insertToDatabase(employee);
				sb.append("Insert Request: " + employee.getFirstName() + "\n");
				System.out.println("Insert Request: " + employee);
				break;
			case UPDATE:
				employeeDAO.updateDatabase(employee);
				sb.append("Update Request: " + employee.getFirstName() + "\n");
				System.out.println("Update Request: " + employee);
				break;
			case DELETE:
				employeeDAO.deleteFromDatabase(employee);
				sb.append("Delete Request: " + employee.getFirstName() + "\n");
				System.out.println("Delete Request: " + employee);
				break;

			default:
				System.out.println("Nothing to change");
				break;
			}

		}
		activityLog.clear();
		JOptionPane.showMessageDialog(parentFrame, sb, "Information", JOptionPane.INFORMATION_MESSAGE);
	}

	// ************* Derby Database query **********************

	public void loadFromDatabase() throws SQLException {
		employeeDAO.loadFromDatabase();
		activityLog.clear();
	}

	public List<Employee> loadFromDatabase(String department) throws SQLException {
		return employeeDAO.loadFromDatabase(department);
	}

	private boolean dataExist(Employee employee) {
		if (employeeDAO.dataExist(employee.geteID())) {
			JOptionPane.showMessageDialog(null, "Duplicate Employee ID", "Insert Error", JOptionPane.ERROR_MESSAGE);
			return true;
		} else if (employeeDAO.dataExist(employee.getFirstName(), employee.getLastName())) {
			JOptionPane.showMessageDialog(null, "Duplicate Employee Name", "Insert Error", JOptionPane.ERROR_MESSAGE);
			return true;
		} else {
			return false;
		}

	}

	private int insertToDatabase(Employee employee) {
		int generatedPK = 0;
		try {
			generatedPK = employeeDAO.insertToDataBase(employee);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Something wrong!! Couldn't insert data with EID: " + employee.geteID(),
					"Insert Error", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
		return generatedPK;
	}

	public int getLastPK() {
		int lastPK = 0;
		try {
			lastPK = employeeDAO.getLastPK();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Could not retrieved last primary key", "Retrieve Error",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
		return lastPK;
	}

	public void setActivityLog(List<ActivityModel> activityLog) {
		this.activityLog = activityLog;
	}

}

class GetSpecificType {
	static EnumDivision division = null;
	static EnumGender gender = null;

	public static EnumDivision getDivision(int divisionID) {
		switch (divisionID) {
		case 0:
			division = EnumDivision.AdminAndPolicy;
			break;
		case 1:
			division = EnumDivision.FieldOperation;
			break;
		case 2:
			division = EnumDivision.TechnicalService;
			break;
		case 3:
			division = EnumDivision.CustomerService;
			break;
		case 4:
			division = EnumDivision.BusinessOperation;
			break;
		default:
			division = EnumDivision.NoDivision;
			break;
		}
		return division;
	}

	public static EnumGender getGender(String gend) {
		switch (gend) {
		case "male":
			gender = EnumGender.MALE;
			break;
		case "female":
			gender = EnumGender.FEMALE;
			break;
		default:
			gender = EnumGender.MALE;
		}
		return gender;
	}

}
