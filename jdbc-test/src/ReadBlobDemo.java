import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class ReadBlobDemo {

	public static void main(String[] args) {
		Connection con = null;
		ResultSet rs=null;
		InputStream input=null;
		FileOutputStream fos=null;
		JdbcConnection connection = new JdbcConnection();
		try {
			con = connection.getConnection();
			Statement st=con.createStatement();
			String sql="select resume from employees where email='john.doe@foo.com'";
			rs=st.executeQuery(sql);
			File file=new File("resume_from_db.pdf");
			fos=new FileOutputStream(file);
			if(rs.next()) {
				input=rs.getBinaryStream("resume");
				byte [] buffer=new byte[1024];
				while(input.read(buffer)>0)
					fos.write(buffer);
				System.out.println("Save to File :"+file.getAbsolutePath());
				
			}
			
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
