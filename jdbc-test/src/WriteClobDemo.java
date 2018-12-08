import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class WriteClobDemo {

	public static void main(String[] args) {
		Connection con = null;
		PreparedStatement pstatement = null;
		FileReader input;
		JdbcConnection connection = new JdbcConnection();
		try {
			con = connection.getConnection();
			String sql="update employees set resume=? where email='john.doe@foo.com'";
			
			//System.out.println(theFile.getAbsolutePath());
			pstatement=con.prepareStatement(sql);
			File theFile=new File("abc.txt");
			System.out.println("File :"+theFile);
			input=new FileReader(theFile);
			pstatement.setCharacterStream(1,input);
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
