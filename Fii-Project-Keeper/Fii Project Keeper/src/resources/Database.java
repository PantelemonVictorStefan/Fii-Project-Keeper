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
	
	
	public static User login(String username,String password)
	{
		makeConnection();
		Statement stmt;
		try {
			stmt = connection.createStatement();
		
	        ResultSet rs=stmt.executeQuery("select an,tip from conturi where username='"+username+"' and password="+password.hashCode());
	        while(rs.next())
	        { 
	        	
	        	return new User(username,rs.getString(1),rs.getString(2));
	        	
	        }        
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
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
	
	private static int getProjectIdByTransaction(int transaction) throws SQLException
	{
		ResultSet rs;
		Statement stmt;
		
		stmt = connection.createStatement();
		rs=stmt.executeQuery("select id from proiecte where tranzactie="+transaction);
        if(rs.next())
        	return rs.getInt(1);
        return -1;
	}
	
	private static boolean addSelectedStudents(int projectId,int transaction,String[]selectedStudents) throws SQLException
	{
		Statement stmt;
		String query;
		
		stmt = connection.createStatement();
        query="insert into permisiuni values";
        for(int i=0;i<selectedStudents.length-1;i++)
        	query=query+"("+projectId+",'"+selectedStudents[i]+"',"+transaction+"),";
        query=query+"("+projectId+",'"+selectedStudents[selectedStudents.length-1]+"' ,"+transaction+")";
        	if(stmt.execute(query))
        	{
        		return false;
        	}
        	return true;
	}
	
	private static boolean addSelectedLanguages(int projectId, int transaction,String[]selectedLanguages) throws SQLException
	{
		Statement stmt;
		String query;
		
		stmt = connection.createStatement();
		query="insert into limbaje_utilizate values";
	        for(int i=0;i<selectedLanguages.length-1;i++)
	        	query=query+"("+projectId+",'"+selectedLanguages[i]+"', "+transaction+"),";
		query=query+"("+projectId+",'"+selectedLanguages[selectedLanguages.length-1]+"' ,"+transaction+")";
	    if(stmt.execute(query))
	    {
	    	return false;
	    }
	    return true;
	}
	
	public static boolean addProject(Proiect proiect)
	{
		Statement stmt;
		int projectId;
		
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
	       
	       projectId=getProjectIdByTransaction(tranzactie);
	       if(projectId==-1)
	       {
	    	   System.out.println("Could not find the project id");
	    	   rollback(tranzactie);
	    	   return false;
	       }
	        if(proiect.getStudentiSelectati().length>0)
	        {
		        if(!addSelectedStudents(projectId,tranzactie,proiect.getStudentiSelectati()))
		        {
		        	System.out.println("Failed to add the selected students");
		        	rollback(tranzactie);
		        }
		        
	        }
	        
	        if(proiect.getLimbajeSelectate().length>0)
	        {
	        	
	    	    if(!addSelectedLanguages(projectId,tranzactie,proiect.getLimbajeSelectate()))
	    	    {
	    	    	System.out.println("Failed to add the selected languages");
	    	    	rollback(tranzactie);
	    	    	return false;
	    	    }
	        }
	        commit(tranzactie);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			rollback(tranzactie);
			return false;
		}
		
		
		return true;
	}
	
	public static ArrayList<RepositoryDTO> getProjects(String name,String year)
	{
		Statement stmt;
		ArrayList<RepositoryDTO> proiecte=new ArrayList<RepositoryDTO>();
		try {
			stmt = connection.createStatement();
			//select * from proiecte join permisiuni on id_proiect=id where ((((student='victor.pantelemon')or (substring(cod_ani,3)='3'))  and (TO_DATE(deadline,'MM/DD/YYYY') > current_date)) or (activ=true)) and proiecte.tranzactie=0;

	        ResultSet rs=stmt.executeQuery("select * from proiecte join permisiuni on id_proiect=id where ((((student='"+name+"')or (substring(cod_ani,"+year+")='1'))  and ((TO_DATE(deadline,'MM/DD/YYYY') > current_date) or deadline='')) or (activ=true)) and proiecte.tranzactie=0");
	        while(rs.next())
	        { 
	        	//System.out.println(rs.getInt(1)); //id
	        	//proiecte.add(rs.getString(1));
	        	proiecte.add(new RepositoryDTO(rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getInt(7),rs.getString(8)));
	        	//proiecte.add(new Proiect(rs.getString(2),rs.getString(3),rs.getString(4)),rs.getString(5),rs.getString(6),rs.getInt(7),rs.getString(8),rs.getBoolean(9));
	        	//Proiect(String materie,String numeRepository,String data,String ora, String audienta,String[] studentiSelectati,boolean limitat,int incarcariPermise,String detalii,String[] limbajeSelectate)
	        }
	        return proiecte;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
}
