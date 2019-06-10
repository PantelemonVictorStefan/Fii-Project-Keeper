package DataAccess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;



import resources.Database;
import resources.Proiect;
import resources.RepositoryCardView;
import resources.User;

public class DataAccessAPI {
	
	public DataAccessAPI()
	{
		Database.makeConnection();
	}

	public List<An> getYears()
	{
		List<An>ani=new LinkedList<An>();
		
		ResultSet rs=Database.executeQuery("select * from years");
		if(rs==null)
			return ani;
		try {
			while(rs.next())
			{
				An an=new An();
				an.setId(rs.getInt(1));
				an.setDenumire(rs.getString(2));
				
				ani.add(an);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return ani;
	}
	
	public List<Limbaj> getLanguages()
	{
		List<Limbaj>languages=new LinkedList<Limbaj>();
		
		ResultSet rs=Database.executeQuery("select * from programminglanguages");
		try {
			while(rs.next())
			{
				Limbaj language=new Limbaj();
				language.setId(rs.getInt(1));
				language.setDenumire(rs.getString(2));
				
				languages.add(language);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return languages;
	}
	
	public boolean addProject(Proiect project)
	{
		PreparedStatement prepStmt = Database.getPreparedStatement("insert into Repositories(subject,project_name,deadline,details,active) values(?,?,?,?,?)");
		
		
		try {
			prepStmt.setString(1, project.getMaterie());
			prepStmt.setString(2, project.getNumeRepository());
			prepStmt.setString(3, project.getData());
			prepStmt.setString(4, project.getDetalii());
			prepStmt.setBoolean(5, project.isActiv());
			prepStmt.execute();
			prepStmt.close();
			
			ResultSet rs=Database.executeQuery("select id from repositories where project_name='"+project.getNumeRepository()+"'");
			
			int id=0;
			if(rs.next())
				id=rs.getInt(1);
			if(id==0)
				return false;
			
			for(Limbaj limbaj:project.getLimbaje())
			{
				prepStmt = Database.getPreparedStatement("insert into repositories_programminglanguages (repository_id,language_id) values(?,?)");
				prepStmt.setInt(1, id);
				prepStmt.setInt(2, limbaj.getId());
				
				prepStmt.execute();
			}
			
			for(An an:project.getAni())
			{
				prepStmt = Database.getPreparedStatement("insert into repositories_years (repository_id,year_id) values(?,?)");
				prepStmt.setInt(1, id);
				prepStmt.setInt(2, an.getId());
				
				prepStmt.execute();
			}
			
			return true;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}
	
	
	public Proiect getProjectById(int id)
	{
		Proiect project=new Proiect();
PreparedStatement prepStmt = Database.getPreparedStatement("select * from repositories where id=? ");
		
		ResultSet rs;
		try {
			prepStmt.setInt(1, id);
			rs = prepStmt.executeQuery();
			
			if(rs.next())
			{
				project.setId(rs.getInt(1));
				project.setMaterie(rs.getString(2));
				project.setNumeRepository(rs.getString(3));
				project.setData(rs.getString(4));
				project.setDetalii(rs.getString(5));
				project.setActiv(rs.getBoolean(6));
				
				return project;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	public User getUser(String username,int password)
	{
		
		User user=new User();
		PreparedStatement prepStmt = Database.getPreparedStatement("select year,role from users where username=? and password=?");
		
		ResultSet rs;
		try {
			prepStmt.setString(1, username);
			prepStmt.setInt(2, password);
			rs = prepStmt.executeQuery();
			
			if(rs.next())
			{
				user.setUsername(username);
				user.setFirstName();
				user.setYear(rs.getString(1));
				user.setType(rs.getString(2));
				
				return user;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public List<RepositoryCardView> getRepositories(String year)
	{
		List<RepositoryCardView> repositories=new LinkedList<RepositoryCardView>();
		PreparedStatement prepStmt = Database.getPreparedStatement("select repositories.id,project_name,deadline,subject from repositories_years INNER JOIN repositories on repositories.id=repositories_years.repository_id INNER JOIN years on years.id=repositories_years.year_id where name=? and active=true");
		
		ResultSet rs;
		try {
			prepStmt.setString(1, year);
			rs = prepStmt.executeQuery();
			
			while(rs.next())
			{
				RepositoryCardView repo=new RepositoryCardView();
				
				repo.setId(rs.getInt(1));
				repo.setTitle(rs.getString(2));
				repo.setData(rs.getString(3));
				repo.setSubject(rs.getString(4));
				
				repositories.add(repo);
			}
			return repositories;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
	
}
