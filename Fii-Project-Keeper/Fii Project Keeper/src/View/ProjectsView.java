package View;

import java.util.List;

import javax.faces.bean.ManagedBean;

import Model.ProjectDTO;
import resources.Security;
import resources.SessionObject;

@ManagedBean
public class ProjectsView {

	
	private List<ProjectDTO> projects;
	
	public ProjectsView()
	{
		init();
	}
	
	private void init()
	{
		SessionObject session=Security.getSession();
		
		ViewDataAccess api=new ViewDataAccess();
		
		setProjects(api.getProjectsOfRepository(session.getSelectedRepository().getId()));
	}

	public List<ProjectDTO> getProjects() {
		return projects;
	}

	public void setProjects(List<ProjectDTO> projects) {
		this.projects = projects;
	}
	
	public void viewProject(int index)
	{
		ViewDataAccess api=new ViewDataAccess();
		ProjectDTO pr=projects.get(index);
		SessionObject session=Security.getSession();
		
		ProjectView selectedProject=api.getProject(pr.getRepositoryId(), pr.getUserId());
		session.setSelectedProject(selectedProject);
		
		if(selectedProject==null)
			System.out.println("shit's null");
		System.out.println(selectedProject.getUserName());
		
		Security.redirect("proiect.xhtml");
		
	}
	
}
