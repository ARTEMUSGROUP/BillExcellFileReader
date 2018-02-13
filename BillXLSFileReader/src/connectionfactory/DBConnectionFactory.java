/**
 * 
 */
package connectionfactory;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnectionFactory {
	Connection con=null;
	public Connection getConnection() throws Exception{
		 Class.forName("com.mysql.jdbc.Driver");;
		  String url="jdbc:mysql://localhost:3306/artemus";
		  //String url="jdbc:mysql://localhost:3306/artemus";
		 con = DriverManager.getConnection(url,"root","@ss123");
		return con; 
	}
}
