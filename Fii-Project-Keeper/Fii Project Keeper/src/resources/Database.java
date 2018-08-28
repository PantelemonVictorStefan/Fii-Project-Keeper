package resources;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.text.SimpleDateFormat;
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
	
	
	public static boolean login(UserLoginView acc)
	{
		makeConnection();
		Statement stmt;
		try {
			stmt = connection.createStatement();
		
	        ResultSet rs=stmt.executeQuery("select an,tip from conturi where username='"+acc.getUsername()+"' and password="+acc.getPassword().hashCode());
	        while(rs.next())
	        { 
	        	acc.setYear(rs.getString(1));
	        	acc.setType(rs.getString(2));
	        	return true;
	        	
	        }        
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public static ArrayList<String> getLimbaje()
	{
		makeConnection();
		
		ArrayList<String> limbaje=new ArrayList<String>();
		Statement stmt;
		
		try {
			stmt = connection.createStatement();
		
	        ResultSet rs=stmt.executeQuery("select limbaj from limbaje order by limbaj");
	        while(rs.next())
	        { 
	        	limbaje.add(rs.getString(1));
	        }        
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return limbaje;
	}
	
	public static ArrayList<String> getMaterii()
	{
		makeConnection();
		
		ArrayList<String> materii=new ArrayList<String>();
		Statement stmt;
		
		try {
			stmt = connection.createStatement();
		
	        ResultSet rs=stmt.executeQuery("select materie from materii order by materie");
	        while(rs.next())
	        { 
	        	materii.add(rs.getString(1));
	        }        
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return materii;
	}
	
	public static ArrayList<String> getNumeStudenti()
	{
		makeConnection();
		
		ArrayList<String> studenti=new ArrayList<String>();
		Statement stmt;
		
		try {
			stmt = connection.createStatement();
		
	        ResultSet rs=stmt.executeQuery("select username from conturi where tip='Student' order by an,username");
	        while(rs.next())
	        { 
	        	studenti.add(rs.getString(1));
	        }        
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return studenti;
	}
	
	
	public static boolean addProject(Proiect proiect)
	{
		
		
		Statement stmt;
		ResultSet rs;
		int id=-1;
		String query;
		
		makeConnection();
		try {
			stmt = connection.createStatement();
			  System.out.println("DB version: 4");
	       if(stmt.execute("insert into proiecte (materie,nume_proiect,deadline,ora,cod_ani,limita_uploaduri,detalii) values('"+proiect.getMaterie()+"','"+proiect.getNumeRepository()+"','"+proiect.getData()+"','"+proiect.getOra()+"','"+proiect.getAudienta()+"',"+proiect.getIncarcariPermise()+",'"+proiect.getDetalii()+"')"))
	       {
	        	return false;
	       }
	        if(proiect.getStudentiSelectati().length>0)
	        {
	        rs=stmt.executeQuery("select id from proiecte where materie='"+proiect.getMaterie()+"' and nume_proiect='"+proiect.getNumeRepository()+"'");
	        if(rs.next())
	        	id=rs.getInt(1);
	        query="insert into permisiuni values";
	        for(int i=0;i<proiect.getStudentiSelectati().length-1;i++)
	        	query=query+"("+id+",'"+proiect.getStudentiSelectati()[i]+"'),";
	        query=query+"("+id+",'"+proiect.getStudentiSelectati()[proiect.getStudentiSelectati().length-1]+"')";
		        System.out.println(query);
	        	if(stmt.execute(query))
	        	{
	        	return false;
	        	}
		        
	        }
	        
	        if(proiect.getLimbajeSelectate().length>0)
	        {
	        	query="insert into limbaje values";
   		        for(int i=0;i<proiect.getLimbajeSelectate().length-1;i++)
   		        	query=query+"("+id+",'"+proiect.getLimbajeSelectate()[i]+"'),";
	    		query=query+"("+id+",'"+proiect.getLimbajeSelectate()[proiect.getLimbajeSelectate().length-1]+"')";
	    		System.out.println(query);
	    	    if(stmt.execute(query))
	    	    {
	    	    	return false;
	    	    }
	        }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return false;
	}
	
	
}
