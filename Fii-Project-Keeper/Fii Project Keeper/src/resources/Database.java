package resources;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;


public class Database {
   
    private static Connection connection = null;
	
	
	
	private Database()
	{
	}
	
	static void makeConnection()
	{
		if(connection==null)
			createConnection();
	}
	
	static void createConnection()
    {
    	try {
    		try {
				Class.forName("org.postgresql.Driver");
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			    connection=DriverManager.getConnection(  "jdbc:postgresql://127.0.0.1:5432/FII_Project_Keeper", "postgres",  "sql");
			//connection = DriverManager.getConnection(  "jdbc:postgresql://127.0.0.1:5432/test", "postgres", "sql");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	    	
    }
	
	
	public static boolean login(String username,int password,UserLoginView acc)
	{
		if(!Validator.validateSqlInjection(username))
			return false;
		makeConnection();
		Statement stmt;
		String year;
		int type;
		try {
			stmt = connection.createStatement();
		
	        ResultSet rs=stmt.executeQuery("select year,type from accounts where username='"+username+"' and password="+password);
	        while(rs.next())
	        { 
	        	year=rs.getString(1);
	        	type=rs.getInt(2);
	        	acc.setYear(year);
	        	acc.setType(type);
	        	return true;
	        	
	        }        
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	
}
