import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class WriteBlobDemo {
	public static void main(String [] args) {
		Connection con = null; 
		PreparedStatement pstatement = null;
		FileInputStream input;
		JdbcConnection connection = new JdbcConnection();
		try {
			con = connection.getConnection();
			String sql="update employees set resume=? where email='john.doe@foo.com'";
			
			//System.out.println(theFile.getAbsolutePath());
			pstatement=con.prepareStatement(sql);
			File theFile=new File("abc.pdf");
			System.out.println("File :"+theFile);
			input=new FileInputStream(theFile);
			pstatement.setBinaryStream(1,input);
			System.out.println("Storing the File in database :"+theFile.getAbsolutePath());
			System.out.println(sql);
			pstatement.executeUpdate();
			System.out.println("---------------Complete SuccessFully-------------------");
			
			
			
			

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
