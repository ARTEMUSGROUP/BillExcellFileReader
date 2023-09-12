/**
 * 
 */
package connectionfactory;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnectionFactory {
	Connection con=null;
	public Connection getConnection() throws Exception{
		 Class.forName("com.mysql.cj.jdbc.Driver");
		 String url="jdbc:mysql://localhost:3306/artemus";
		 con = DriverManager.getConnection(url,"root","@ss123");
		 
		
		 //Class.forName("com.mysql.cj.jdbc.Driver");
		 //String url="jdbc:mysql://artemus.cmmiskakg2dm.us-east-1.rds.amazonaws.com:3306/artemus";
		 //con = DriverManager.getConnection(url,"admin","3HSSbVcj6PMPNN9arGTC");
		 
		 //Class.forName("com.mysql.cj.jdbc.Driver");
	     //final String url = "jdbc:mysql://artemus.cmmiskakg2dm.us-east-1.rds.amazonaws.com:3306/artemus_dev";
	     //con = DriverManager.getConnection(url, "admin", "3HSSbVcj6PMPNN9arGTC");
		 
		return con; 
	}
}
