package com.devcomol.em.extras;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(locations = "classpath:com/devcomol/em/configxml/appbeans.xml")
public class CreateDB {

	private static final String JDBC_URL = "jdbc:derby:employeedb;create=true";

	// private NamedParameterJdbcTemplate jdbc;

	public static void main(String[] args) {

//		createDB();
		 queryDB();
//		 deleteRow();
		// dropTable();
	}

	/*private static void createDB() {
		try {
			Connection connection = DriverManager.getConnection(JDBC_URL);
			String createDB = "CREATE TABLE employees (eid INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 3025, INCREMENT BY 1),"
					+ " firstname VARCHAR(100) NOT NULL," + " lastname VARCHAR(100),"
					+ " division VARCHAR(150) NOT NULL," + " department VARCHAR(150) NOT NULL," + " citizen boolean,"
					+ " tfn INTEGER," + " gender VARCHAR(10) NOT NULL," + " dateofbirth DATE,"
					+ " responsibility VARCHAR(500)," + " CONSTRAINT primary_key PRIMARY KEY (eid))";

			// System.out.println(createDB); //
			connection.createStatement().execute(createDB);
			connection.createStatement().execute(
					"INSERT INTO employees (firstname, lastname, division, department, citizen, tfn, gender, dateofbirth, responsibility)"
							+ " VALUES ('Kamol', 'Roy', 'FieldOperation', 'Site Operation', false, 3412, 'MALE', '1982-06-11', 'Nothing yet')");
			

			System.out.println("Data Inserted");
			connection.close();
		} catch (SQLException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/

	/*private static void deleteRow() {
		String SQL_STATEMENT = "Delete from employees where firstname='Smita'";
		try {
			Connection connection = DriverManager.getConnection(JDBC_URL);
			connection.createStatement().execute(SQL_STATEMENT);
			System.out.println("Delete successfull");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}*/

	private static void queryDB() {

		try {
//			String SQL_STATEMENT = "Select * from employees";
			String SQL_STATEMENT = "Select department,count(*) from employees group by (department)";
			
			Connection connection = DriverManager.getConnection(JDBC_URL);
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(SQL_STATEMENT);
			ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
			int colCount = resultSetMetaData.getColumnCount();

			while (resultSet.next()) {
				System.out.println("");
				for (int x = 1; x <= colCount; x++) {
					System.out.format("%20s", resultSet.getObject(x).toString().trim() + " | ");
				}
			}

			if (statement != null)
				statement.close();
			if (connection != null)
				connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
