package com.devcomol.em.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import com.devcomol.em.gui.bones.EnumDivision;
import com.devcomol.em.gui.bones.EnumGender;

@Component("employeeDAO")
public class EmployeeDAO {
	private List<Employee> employees;
	private NamedParameterJdbcTemplate jdbc;

	@Autowired
	// @Qualifier("poolingDataSource")
	public void setDataSource(DataSource jdbc) {
		this.jdbc = new NamedParameterJdbcTemplate(jdbc);
	}

	public EmployeeDAO() {
		employees = new LinkedList<Employee>();
	}

	public void addEmployee(Employee employee) {
		employees.add(employee);
	}

	public Employee getEmployee(int rowIndex) {
		return employees.get(rowIndex);
	}

	public List<Employee> getEmployees() {
		return Collections.unmodifiableList(employees);
	}

	public void saveToFile(File file) throws IOException {
		try (FileOutputStream fos = new FileOutputStream(file); ObjectOutputStream oos = new ObjectOutputStream(fos);) {
			Employee[] allEmployee = employees.toArray(new Employee[employees.size()]);
			oos.writeObject(allEmployee);
		} catch (Exception e) {
			throw new IOException();
		}
	}

	public void loadFromFile(File file) throws IOException, ClassNotFoundException {
		try (FileInputStream fis = new FileInputStream(file); ObjectInputStream ois = new ObjectInputStream(fis);) {
			Employee[] allEmployee = (Employee[]) ois.readObject();
			employees.clear();
			employees.addAll(Arrays.asList(allEmployee));
		} catch (IOException e1) {
			throw e1;
		} catch (ClassNotFoundException e2) {
			throw e2;
		}

	}

	public void deleteEmployee(int rowIndex) {
		employees.remove(rowIndex);
	}

	public void clear() {
		printEmployees();
		employees.clear();
	}

	// **************** Derby Database *********************

	// Load Data from database
	public void loadFromDatabase() throws SQLException {
		MapSqlParameterSource params = new MapSqlParameterSource();

		List<Employee> allEmployee = jdbc.query("Select * from employees", params, new RowMapper<Employee>() {

			public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {
				Employee employee = new Employee();

				employee.seteID(rs.getInt("eid"));
				employee.setFirstName(rs.getString("firstname"));
				employee.setLastName(rs.getString("lastname"));
				employee.setDivision(EnumDivision.valueOf(rs.getString("division")));
				employee.setDepartment(rs.getString("department"));
				employee.setAuCitizen(rs.getBoolean("citizen"));
				employee.setTfn(rs.getInt("tfn"));
				employee.setGender(EnumGender.valueOf(rs.getString("gender")));
				employee.setDateOfBirth(rs.getDate("dateofbirth"));
				employee.setResponsibility(rs.getString("responsibility"));

				return employee;
			}

		});

		employees.clear();
		employees.addAll(allEmployee);

	}
	
	// Load Data for single department
	public List<Employee> loadFromDatabase(String department) throws SQLException{
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("department", department);

		List<Employee> deptEmployee = jdbc.query("Select * from employees where department=:department", params, new RowMapper<Employee>() {

			public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {
				Employee employee = new Employee();

				employee.seteID(rs.getInt("eid"));
				employee.setFirstName(rs.getString("firstname"));
				employee.setLastName(rs.getString("lastname"));
				employee.setDivision(EnumDivision.valueOf(rs.getString("division")));
				employee.setDepartment(rs.getString("department"));
				employee.setAuCitizen(rs.getBoolean("citizen"));
				employee.setTfn(rs.getInt("tfn"));
				employee.setGender(EnumGender.valueOf(rs.getString("gender")));
				employee.setDateOfBirth(rs.getDate("dateofbirth"));
				employee.setResponsibility(rs.getString("responsibility"));

				return employee;
			}

		});
		
		return deptEmployee;
	}

	// Insert a single employee to database
	public int insertToDataBase(Employee employee) throws SQLException {
		EnumDivision enumDivision = employee.getDivision();
		EnumGender enumGender = employee.getGender();
		MapSqlParameterSource paramMap = new MapSqlParameterSource();
		paramMap.addValue("firstname", employee.getFirstName());
		paramMap.addValue("lastname", employee.getLastName());
		paramMap.addValue("division", enumDivision.name());
		paramMap.addValue("department", employee.getDepartment());
		paramMap.addValue("citizen", employee.isAuCitizen());
		paramMap.addValue("tfn", employee.getTfn());
		paramMap.addValue("gender", enumGender.name());
		paramMap.addValue("dateofbirth", employee.getDateOfBirth());
		paramMap.addValue("responsibility", employee.getResponsibility());

		KeyHolder keyHolder = new GeneratedKeyHolder();
		int status = jdbc.update(
				"insert into employees(firstname, lastname, division, department, citizen, tfn, gender, dateofbirth, responsibility) values"
						+ " (:firstname, :lastname, :division, :department, :citizen, :tfn, :gender, :dateofbirth, :responsibility)",
				paramMap, keyHolder);
		if (status > 0) {
			System.out.println("Data insertion successfull with name= " + employee.getFirstName());
			int generatedKey = keyHolder.getKey().intValue();
			employee.seteID(generatedKey);
			System.out.println(employees.indexOf(employee));
			return generatedKey;
		}
		return 0;
	}

	// Update single employee info to database
	public boolean updateDatabase(Employee employee) {
		EnumDivision enumDivision = employee.getDivision();
		EnumGender enumGender = employee.getGender();
		MapSqlParameterSource paramMap = new MapSqlParameterSource();
		paramMap.addValue("eid", employee.geteID());
		paramMap.addValue("firstname", employee.getFirstName());
		paramMap.addValue("lastname", employee.getLastName());
		paramMap.addValue("division", enumDivision.name());
		paramMap.addValue("department", employee.getDepartment());
		paramMap.addValue("citizen", employee.isAuCitizen());
		paramMap.addValue("tfn", employee.getTfn());
		paramMap.addValue("gender", enumGender.name());
		paramMap.addValue("dateofbirth", employee.getDateOfBirth());
		paramMap.addValue("responsibility", employee.getResponsibility());

		return jdbc.update(
				"Update employees set firstname=:firstname, lastname=:lastname, division=:division, department=:department,"
						+ " citizen=:citizen, tfn=:tfn, gender=:gender, dateofbirth=:dateofbirth, responsibility=:responsibility"
						+ " where eid=:eid",
				paramMap) == 1;

	}

	// Delete a single employee
	public boolean deleteFromDatabase(Employee employee) {
		int eid = employee.geteID();
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("eid", eid);

		return jdbc.update("Delete from employees where eid= :eid", params) == 1;
	}

	// Check for Duplicate Data with primary key in database
	public boolean dataExist(int eid) throws DuplicateKeyException {
		MapSqlParameterSource paramMap = new MapSqlParameterSource();
		paramMap.addValue("eid", eid);

		return jdbc.queryForObject("Select count(*) from employees where eid=:eid", paramMap, Integer.class) > 0;
	}

	// Check for Duplicate Data with firstname and lastname in database
	public boolean dataExist(String firstname, String lastname) throws DuplicateKeyException {
		MapSqlParameterSource paramMap = new MapSqlParameterSource();
		paramMap.addValue("firstname", firstname);
		paramMap.addValue("lastname", lastname);

		return jdbc.queryForObject("Select count(*) from employees where firstname=:firstname and lastname=:lastname",
				paramMap, Integer.class) > 0;
	}

	public int getLastPK() throws SQLException {
		String lastPKSql = "SELECT MAX(eid) FROM employees";
		int lastPK;
		MapSqlParameterSource params = new MapSqlParameterSource();
		try{
			lastPK = jdbc.queryForObject(lastPKSql, params, Integer.class);
		}catch(Exception e){
			lastPK=0;
		}
		
		return lastPK;
	}

	private void printEmployees() {
		for (Employee employee : employees) {
			System.out.println(employee);
		}
	}

	

}
