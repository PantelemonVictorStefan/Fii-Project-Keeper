package DataAccess;

import java.io.IOException;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import resources.Database;
import resources.Repository;
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
	
	public List<Limbaj> getLanguagesOfRepository(int repositoryId)
	{
		List<Limbaj>languages=new LinkedList<Limbaj>();
		
		
		PreparedStatement prepStmt = Database.getPreparedStatement("select * from programminglanguages inner join repositories_programminglanguages on programminglanguages.id=repositories_programminglanguages.language_id where repository_id=?");
		try {
			prepStmt.setInt(1, repositoryId);
			ResultSet rs=prepStmt.executeQuery();
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
	
	public List<An> getYearsOfRepository(int repositoryId)
	{
		List<An>ani=new LinkedList<An>();
		
		PreparedStatement prepStmt = Database.getPreparedStatement("select * from years inner join repositories_years on years.id=repositories_years.year_id where repository_id=?");
		
		try {
			prepStmt.setInt(1, repositoryId);
			ResultSet rs=prepStmt.executeQuery();
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
	
	public boolean addRepository(Repository repository)
	{
		PreparedStatement prepStmt = Database.getPreparedStatement("insert into Repositories(subject,project_name,deadline,details,active) values(?,?,?,?,?)");
		
		
		try {
			prepStmt.setString(1, repository.getMaterie());
			prepStmt.setString(2, repository.getNumeRepository());
			prepStmt.setString(3, repository.getData());
			prepStmt.setString(4, repository.getDetalii());
			prepStmt.setBoolean(5, repository.isActiv());
			prepStmt.execute();
			prepStmt.close();
			
			ResultSet rs=Database.executeQuery("select id from repositories where project_name='"+repository.getNumeRepository()+"'");
			
			int id=0;
			if(rs.next())
				id=rs.getInt(1);
			if(id==0)
				return false;
			
			for(Limbaj limbaj:repository.getLimbaje())
			{
				prepStmt = Database.getPreparedStatement("insert into repositories_programminglanguages (repository_id,language_id) values(?,?)");
				prepStmt.setInt(1, id);
				prepStmt.setInt(2, limbaj.getId());
				
				prepStmt.execute();
			}
			
			for(An an:repository.getAni())
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
	
	
	public Repository getRepositoryById(int id)
	{
		Repository repository=new Repository();
PreparedStatement prepStmt = Database.getPreparedStatement("select * from repositories where id=? ");
		
		ResultSet rs;
		try {
			prepStmt.setInt(1, id);
			rs = prepStmt.executeQuery();
			
			if(rs.next())
			{
				repository.setId(rs.getInt(1));
				repository.setMaterie(rs.getString(2));
				repository.setNumeRepository(rs.getString(3));
				repository.setData(rs.getString(4));
				repository.setDetalii(rs.getString(5));
				repository.setActiv(rs.getBoolean(6));
				
				repository.setAni(getYearsOfRepository(id));
				repository.setLimbaje(getLanguagesOfRepository(id));
				
				return repository;
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
		PreparedStatement prepStmt = Database.getPreparedStatement("select id,year,role from users where username=? and password=?");
		
		ResultSet rs;
		try {
			prepStmt.setString(1, username);
			prepStmt.setInt(2, password);
			rs = prepStmt.executeQuery();
			
			if(rs.next())
			{
				user.setUsername(username);
				user.setFirstName();
				user.setId(rs.getInt(1));
				user.setYear(rs.getString(2));
				user.setType(rs.getString(3));
				
				return user;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public List<RepositoryCardView> getRepositoriesByYear(String year)
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
	
	public List<RepositoryCardView> getRepositories()
	{
		List<RepositoryCardView> repositories=new LinkedList<RepositoryCardView>();
		ResultSet rs;
		try {
			rs=Database.executeQuery("select repositories.id,project_name,deadline,subject from repositories_years INNER JOIN repositories on repositories.id=repositories_years.repository_id INNER JOIN years on years.id=repositories_years.year_id order by active");
			
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
	
	private int addFile(_File file)
	{
		PreparedStatement prepStmt= Database.getPreparedStatement("insert into files(filename,size) values(?,?) returning id");
		ResultSet rs;
		int id=-1;
		
		try {
			prepStmt.setString(1, file.getFilename());
			prepStmt.setLong(2, file.getSize());
			rs=prepStmt.executeQuery();
			if(rs.next())
			{
				id=rs.getInt(1);
			}
			else
				throw new SQLException("Inserting file failed.");
			rs.close();
			prepStmt.close();
			
			
			
			prepStmt= Database.getPreparedStatement("insert into data(file_id,data) values(?,?)");
			
			
			if(id==-1)
				throw new SQLException("Invalid id.");
			prepStmt.setInt(1, id);
			prepStmt.setBinaryStream(2, file.getFs(),file.getSize());
			
			if(prepStmt.executeUpdate()>0)
			{
				
				prepStmt.close();
				file.getFs().close();
				
				return id;
			}
			else
				throw new SQLException("Saving byte object failed");
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return -1;
	}
	
	public boolean addProject(Project project)
	{
		PreparedStatement prepStmt;
		int presentationId=-1;
		int dataId=-1;
		
		if(project.getPresentation()!=null)
		{
			presentationId=addFile(project.getPresentation());
		}
		
		if(project.getData()!=null)
		{
			dataId=addFile(project.getData());
		}
		
		prepStmt = Database.getPreparedStatement("insert into projects(repository_id,user_id,description,presentation_id,data_id) values(?,?,?,?,?)");
		
		try {
			prepStmt.setInt(1, project.getRepository().getId());
			prepStmt.setInt(2, project.getUser().getId());
			prepStmt.setString(3, project.getDescription());
			if(presentationId!=-1)
				prepStmt.setInt(4,presentationId);
			else
				prepStmt.setString(4, null);
			if(dataId!=-1)
				prepStmt.setInt(5,dataId);
			else
				prepStmt.setString(5, null);
			
			if(prepStmt.executeUpdate()>0)
			{
				prepStmt.close();
				return true;
			}
			
			prepStmt.close();
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean deleteProject(Project project)
	{
		PreparedStatement prepStmt=Database.getPreparedStatement("delete from data where file_id=?");
		/*
		prepStmt.setInt(1, project.getPresentation().getId());
		prepStmt.execute();*/
		
		return false;
	}
	
	
	
}
