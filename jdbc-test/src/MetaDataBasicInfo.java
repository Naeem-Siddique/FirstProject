import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

public class MetaDataBasicInfo {
	public static void main (String[] args) {
		Connection con=null;
		JdbcConnection connection=new JdbcConnection();
		try {
			con=connection.getConnection();
			DatabaseMetaData databaseMetaData=con.getMetaData();
			// --------Display Information about Database--------
			System.out.println("Data Base Name :"+databaseMetaData.getDatabaseProductName());
			System.out.println("Data Base Version :"+databaseMetaData.getDatabaseProductVersion());
			
			//---------Display information about JDBC---------------
			System.out.println("JDBC Driver Name :"+databaseMetaData.getDriverName());
			System.out.println("JDBC Driver version :"+databaseMetaData.getDriverVersion());
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

}
