import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.Reader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ReadClobDemo {

	public static void main(String[] args) {
		Connection con = null;
		ResultSet rs=null;
		Reader input=null;
		FileWriter fw=null;
	
		JdbcConnection connection = new JdbcConnection();
		try {
			con = connection.getConnection();
			Statement st=con.createStatement();
			String sql="select resume from employees where email='john.doe@foo.com'";
			rs=st.executeQuery(sql);
			File file=new File("resume_from_db.txt");
			fw=new FileWriter(file);
			if(rs.next()) { 
				input=rs.getCharacterStream("resume");
				int theChar;
			//	System.out.println("Size :"+input.read());
				while((theChar=input.read())>0) {
					fw.write(theChar);
				}
				System.out.println("Save to File :"+file.getAbsolutePath());
				
			}
			fw.close();;
	}catch(Exception e) {
		e.printStackTrace();
	}

	}
}
