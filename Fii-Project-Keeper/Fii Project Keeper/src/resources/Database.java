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
    private static int transaction=0;
    private static ArrayList<Integer> transactions=new ArrayList<Integer>();
	
	
	
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
	
	synchronized private static int startTransaction()
	{
		transaction++;
		transactions.add(transaction);
		return transaction;
	}
	
	synchronized private static void finishTransaction(int id)
	{
		transactions.remove((Object)id);
	}
	
	private static void rollback(int id)
	{
		makeConnection();
		Statement stmt;
		
		try {
			stmt=connection.createStatement();
			stmt.execute("delete from proiecte where tranzactie="+id);
			stmt.execute("delete from limbaje_utilizate where tranzactie="+id);
			stmt.execute("delete from permisiuni where tranzactie="+id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finishTransaction(id);
		
	}
	
	private static void commit(int id)
	{
		makeConnection();
		Statement stmt;
		
		try {
			stmt=connection.createStatement();
			stmt.execute("update proiecte set tranzactie=0 where tranzactie="+id);
			stmt.execute("update limbaje_utilizate set tranzactie=0 where tranzactie="+id);
			stmt.execute("update permisiuni set tranzactie=0 where tranzactie="+id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finishTransaction(id);
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
		int tranzactie=startTransaction();
		try {
			
			stmt = connection.createStatement();
	       if(stmt.execute("insert into proiecte (materie,nume_proiect,deadline,ora,cod_ani,limita_uploaduri,detalii,tranzactie) values('"+proiect.getMaterie()+"','"+proiect.getNumeRepository()+"','"+proiect.getData()+"','"+proiect.getOra()+"','"+proiect.getAudienta()+"',"+proiect.getIncarcariPermise()+",'"+proiect.getDetalii()+"',"+tranzactie+")"))
	       {
	    	   System.out.println("Failed to add the project");
	    	   rollback(tranzactie);
	        	return false;
	       }
	        if(proiect.getStudentiSelectati().length>0)
	        {
		        rs=stmt.executeQuery("select id from proiecte where tranzactie="+tranzactie);
		        if(rs.next())
		        	id=rs.getInt(1);
		        query="insert into permisiuni values";
		        for(int i=0;i<proiect.getStudentiSelectati().length-1;i++)
		        	query=query+"("+id+",'"+proiect.getStudentiSelectati()[i]+"',"+tranzactie+"),";
		        query=query+"("+id+",'"+proiect.getStudentiSelectati()[proiect.getStudentiSelectati().length-1]+"' ,"+tranzactie+")";
		        	if(stmt.execute(query))
		        	{
		        		System.out.println("Failed to add the selected students");
		        		rollback(tranzactie);
		        		return false;
		        	}
		        
	        }
	        
	        if(proiect.getLimbajeSelectate().length>0)
	        {
	        	query="insert into limbaje_utilizate values";
   		        for(int i=0;i<proiect.getLimbajeSelectate().length-1;i++)
   		        	query=query+"("+id+",'"+proiect.getLimbajeSelectate()[i]+"', "+tranzactie+"),";
	    		query=query+"("+id+",'"+proiect.getLimbajeSelectate()[proiect.getLimbajeSelectate().length-1]+"' ,"+tranzactie+")";
	    	    if(stmt.execute(query))
	    	    {
	    	    	System.out.println("Failed to add the selected languages");
	    	    	rollback(tranzactie);
	    	    	return false;
	    	    }
	    	    commit(tranzactie);
	        }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			rollback(tranzactie);
			return false;
		}
		
		
		return true;
	}
	
	
}
