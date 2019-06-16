package View;

import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import DataAccess._File;
import Model.ProjectDTO;
import resources.Database;

public class ViewDataAccess {

	public ViewDataAccess()
	{
		Database.makeConnection();
	}
	
	public List<ProjectDTO> getProjectsOfRepository(int repositoryId)
	{
		List<ProjectDTO> projects=new LinkedList<ProjectDTO>();
		
		
		PreparedStatement prepStmt= Database.getPreparedStatement("select username,description,user_id from projects inner join users on users.id=projects.user_id where repository_id=?");
		ResultSet rs;
		
		try {
			prepStmt.setInt(1, repositoryId);
			rs=prepStmt.executeQuery();
			
			while(rs.next())
			{
				ProjectDTO project=new ProjectDTO();
				project.setRepositoryId(repositoryId);
				
				project.setUserName(rs.getString(1));
				project.setDescription(rs.getString(2));
				project.setUserId(rs.getInt(3));
				
				projects.add(project);
			}
			rs.close();
			prepStmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return projects;
	}
	
	public _File getFileById(int id)
	{
		PreparedStatement prepStmt= Database.getPreparedStatement("select id,filename,size,data from files inner join data on id=file_id where id=?");
		ResultSet rs;
		_File file=new _File();
		try {
			prepStmt.setInt(1, id);
			
			rs=prepStmt.executeQuery();
			
			if(rs.next())
			{
				file.setId(rs.getInt(1));
				file.setFilename(rs.getString(2));
				file.setSize(rs.getLong(3));
				file.setFs(rs.getBinaryStream(4));
				
				return file;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	public ProjectView getProject(int repositoryId,int userId)
	{
		ProjectView project=null;
		PreparedStatement prepStmt= Database.getPreparedStatement("select username,description,presentation_id,data_id from projects inner join users on users.id=projects.user_id where repository_id=? and user_id=?");
		ResultSet rs;
		
		try {
			prepStmt.setInt(1, repositoryId);
			prepStmt.setInt(2, userId);
			rs=prepStmt.executeQuery();
			
			if(rs.next())
			{
				project=new ProjectView();
				
				project.setUserName(rs.getString(1));
				project.setDescription(rs.getString(2));
				project.setPresentationId(rs.getInt(3));
				project.setDataId(rs.getInt(4));
				
				//project.setPresentation(getFileById(rs.getInt(3)));
				//project.setData(getFileById(rs.getInt(4)));
				
			}
			rs.close();
			prepStmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return project;
	}
	
}
