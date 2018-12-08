import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class SchemaInfo {
	public static void main(String[] args) {
		Connection con = null;
		ResultSet rs = null;
		JdbcConnection connection = new JdbcConnection();
		try {
			con = connection.getConnection();
			Statement st=con.createStatement();
			rs=st.executeQuery("SELECT * from employees");
			
			ResultSetMetaData resultSetMetaData=rs.getMetaData();
			int columnCount=resultSetMetaData.getColumnCount();
			for(int column=1;column<=columnCount;column++) {
				System.out.print("Column Name :"+resultSetMetaData.getColumnName(column));
				System.out.print("    Column Type Name:"+resultSetMetaData.getColumnTypeName(column));
				System.out.print("    Is Nullable :"+resultSetMetaData.isNullable(column));
				System.out.println("   Is Auto Increment :"+resultSetMetaData.isAutoIncrement(column));
				
			}
			
			
			

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
}
