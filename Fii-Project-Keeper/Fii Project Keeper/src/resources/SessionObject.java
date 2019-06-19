package resources;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import DataAccess.DataAccessAPI;
import DataAccess.Repository;
import Model.ProjectDTO;
import View.ProjectView;
import View.ViewDataAccess;

@ManagedBean
@SessionScoped
public class SessionObject {

	private User user;
	private Repository selectedRepository;
	private ProjectView selectedProject;
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Repository getSelectedRepository() {
		return selectedRepository;
	}
	public void setSelectedRepository(Repository selectedRepository) {
		this.selectedRepository = selectedRepository;
	}
	public ProjectView getSelectedProject() {
		return selectedProject;
	}
	public void setSelectedProject(ProjectView selectedProject) {
		this.selectedProject = selectedProject;
	}
	
	public void viewProject()
	{
		ViewDataAccess api=new ViewDataAccess();
		
		ProjectView selectedProject=api.getProject(selectedRepository.getId(), user.getId());
		setSelectedProject(selectedProject);
		
		
		
		Security.redirect("proiect.xhtml");
		
	}
	
	public void activateDeactivateRepository()
	{
		
			if(selectedRepository.activateDeactivateRepository())
			{
				FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO, "Modificarea a fost salvata! ",""));
			}
			else
				FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "Modificarea a putut fi efectuata! ",""));
				
	}
}
