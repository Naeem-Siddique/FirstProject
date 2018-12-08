import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.io.FileInputStream;
import java.sql.CallableStatement;
import java.sql.Statement;
import java.sql.Types;
import java.util.Properties;
import java.util.Scanner;
import java.util.concurrent.Callable;

public class JdbcTest {
	JdbcConnection conn = new JdbcConnection();
	Statement statement = null;
	PreparedStatement preStatement;
	CallableStatement calAbleStatement;
	ResultSet resultSet = null;

	public JdbcTest() {

	}

	public static void main(String[] args) {

		JdbcTest j = new JdbcTest();
		//j.checkConnection();
		
		// System.out.println(j.askUserOkSave());
		// j.perormTransaction();
		       j.getEmployeeForDepartment("Engineering");
		      j.greetDepartment("Engineering");

		// j.CreateEmployee();
		// System.out.println("Before Update data");
		// j.showAllEmployees();
		// System.out.println("After Increasing Salary");
		// j.increaseSalayForDepartment();
		// j.showAllEmployees();
//		System.out.println("After Delete data");
//		j.CreateEmployee();
//		j.showAllEmployees();

	}
	public void checkConnection() {
		try {
			Connection con=conn.getConnection();
			con.close();
			if(con==null)
				System.out.println("Connection is Closed");
			else
				System.out.println("Connection is not Closed");
			if(con.isClosed())
				System.out.println("C---------- Closed-------------");
			else
				System.out.println("C---------- NOT Closed-------------");
				
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void CreateEmployee() {
		try {

			statement = conn.getConnection().createStatement();
//			int rowAffected=statement.executeUpdate(
//					"INSERT into employees"+
//			"(last_name,first_name,email,department,salary)"+
//			"values"+
//			"('Wright','Eric','Eric.Wright@foo.com','HR',3300.00)"
//					
//			);
//			int rowAffected=statement.executeUpdate(
//					"UPDATE employees "+
//			 "SET email='HelloWorld@world.com'"+
//			" WHERE first_name='Lisa' and last_name='Johnson'"
//					);
			int rowAffected = statement
					.executeUpdate("DELete from employees " + " WHERE first_name='Eric' and last_name='Wright'");

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (resultSet != null)
				try {
					resultSet.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}

	}

	public void showAllEmployees() {
		try {
			int id;
			String firstName;
			String lastName;
			String email;
			String department;
			double salary;

			preStatement = conn.getConnection()
					.prepareStatement("selecT * from employees where salary > ? and department=?");
			preStatement.setDouble(1, 100);
			preStatement.setString(2, "Engineering");
//			statement=connection.createStatement();
			resultSet = preStatement.executeQuery();
			while (resultSet.next()) {
				id = resultSet.getInt("id");
				firstName = resultSet.getString("first_name");
				lastName = resultSet.getString("last_name");
				email = resultSet.getString("email");
				salary = resultSet.getDouble("salary");
				department = resultSet.getString("department");
				System.out.println("ID   : " + id + "     : " + firstName + "    :" + lastName + "  " + department
						+ "   : " + salary);

			}

		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			if (resultSet != null)
				try {
					resultSet.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}

	}

	public void increaseSalayForDepartment() {
		try {

			String department = "Engineering";
			int theIncreaseAmount = 10000;
			calAbleStatement = conn.getConnection().prepareCall("{call increase_salaries_for_department(?,?)}");
			calAbleStatement.setString(1, department);
			calAbleStatement.setDouble(2, theIncreaseAmount);
			calAbleStatement.executeQuery();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}

	}

	public void greetDepartment(String department) {
		try {

			Connection con = null;
			con = conn.getConnection();

			CallableStatement st = con.prepareCall("{call greet_the_department(?)}");
			st.registerOutParameter(1, Types.VARCHAR);
			st.setString(1, department);
			st.execute();
			String result = st.getString(1);
			System.out.println("Result of INOUT Parameter Procedur :" + result);
		//	st.close();
			con.close();
			 System.out.println("-----------------------Greet Department----------------: "+ st.isClosed());

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
//			if(conn.getConnection()!=null)
//			conn.close();
		}
	}

	public void countForDepartment(String department) {
		try {
			Connection con123 = conn.getConnection();
			System.out.println("Connected");
			CallableStatement st = con123.prepareCall("{call get_count_for_department(?,?)}");
			st.setString(1, department);
			st.registerOutParameter(2, Types.INTEGER);
			st.execute();
			int count = st.getInt(2);
			System.out.println("Total Employees in" + department + " are : " + count);
			st.close();
			// con123.close();

		} catch (Exception e) {
			System.out.println("In Exception Block");
			e.printStackTrace();
		} finally {

		}

	}

	public void getEmployeeForDepartment(String department) {
		try {
			Connection con = conn.getConnection();
			CallableStatement st = con.prepareCall("{call get_employees_for_department(?)}");
			st.setString(1, department);
			st.execute();
			ResultSet rs = st.getResultSet();
			while (rs.next()) {
				int id = rs.getInt("id");
				String firstName = rs.getString("first_name");
				String lastName = rs.getString("last_name");
				String email = rs.getString("email");
				double salary = rs.getDouble("salary");
				String dep = rs.getString("department");
				System.out.println(
						"ID   : " + id + "     : " + firstName + "    :" + lastName + "  " + dep + "   : " + salary);

			} 
			
			
			con.close();
			 System.out.println("-----------------------Greet Department----------------: "+ rs.isClosed());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void perormTransaction() {
		try {
			Connection con = conn.getConnection();
			con.setAutoCommit(false);
			System.out.println("Salary Before Increment");
			showEmployees(con);
			Statement st = con.createStatement();
			st.executeUpdate("DELETE FROM employees where department='HR' ");
			st.executeUpdate("UPDATE employees set salary=3000000 where department='Engineering'");
			boolean ok = askUserOkSave();
			if (ok) {
				con.commit();
				System.out.println("---------------Transaction commited------------------------");
			} else {
				con.rollback();
				System.out.println("---------------Transaction Rollback------------------------");
			}

			showEmployees(con);

		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
	}

	public void showEmployees(Connection con) throws Exception {
		System.out.println(con.getAutoCommit());
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery("SELECT * from employees");
		while (rs.next()) {
			int id = rs.getInt("id");
			String firstName = rs.getString("first_name");
			String lastName = rs.getString("last_name");
			String email = rs.getString("email");
			double salary = rs.getDouble("salary");
			String dep = rs.getString("department");
			System.out.println(
					"ID   : " + id + "     : " + firstName + "    :" + lastName + "  " + dep + "   : " + salary);

		}

	}

	boolean askUserOkSave() {
		;
		System.out.println("It save to commit transaction (yes,no)");
		Scanner sc = new Scanner(System.in);
		String option = sc.nextLine();
		if (option.equals("yes"))
			return true;
		else
			return false;

	}
}
