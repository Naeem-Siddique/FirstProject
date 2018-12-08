import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class JdbcConnection {
	Connection connection=null;

	public JdbcConnection() {
		
	}

	public Connection getConnection()throws Exception {
		if(connection==null || connection.isClosed())
			createConnection();
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}
	public void close() {
		try {
			if(connection!=null )
				connection.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private void createConnection() {
		Properties props = new Properties();
		try {
			props.load(new FileInputStream("demo.properties"));

			String url = props.getProperty("url");
			String user = props.getProperty("user");
			String password = props.getProperty("password");
			System.out.println("URL :" + url + "User :" + user + "Password :" + password);
			connection = DriverManager.getConnection(url, user, password);
			System.out.println("Database has been Connected SuccessFully");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	

}
