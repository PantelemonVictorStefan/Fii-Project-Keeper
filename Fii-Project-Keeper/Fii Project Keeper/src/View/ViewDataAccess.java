package View;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

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
