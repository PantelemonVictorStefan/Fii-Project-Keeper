package View;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ValueChangeEvent;


import DataAccess.DataAccessAPI;
import DataAccess.Repository;
import resources.Security;
import resources.SessionObject;
import resources.User;

@ManagedBean
@SessionScoped
public class RepositoriesView {


	private List<RepositoryCardView> proiecte;
	private Repository selectedProject;
	
	private boolean viewingActiveRepositories=true;

	public Repository getSelectedProject() {
		return selectedProject;
	}

	public void setSelectedProject(Repository selectedProject) {
		this.selectedProject = selectedProject;
	}
	

	public List<RepositoryCardView> getProiecte() {
		return proiecte;
	}

	public void setProiecte(List<RepositoryCardView> proiecte) {
		this.proiecte = proiecte;
	}
	
	public RepositoriesView()
	{
		System.out.println("new RepositoriesView");
		init();
	}
	
	private void init()
	{
		SessionObject session=Security.getSession();
		DataAccessAPI api=new DataAccessAPI();
		if(session.getUser().getType().equals("Student"))
			proiecte=api.getRepositoriesByStatusAndYear(viewingActiveRepositories,session.getUser().getYear());
		if(session.getUser().getType().equals("Profesor"))
			proiecte=api.getRepositoriesByStatus(viewingActiveRepositories);
	}
	
	
	
	public void viewProject(int index)
	{
		
			DataAccessAPI api=new DataAccessAPI();
			SessionObject session=Security.getSession();
			
			
			selectedProject=api.getRepositoryById(proiecte.get(index).getId());
			session.setSelectedRepository(selectedProject);
			if(selectedProject==null)
				System.out.println("object is null");
			
			Security.redirect("repository.xhtml");
	}

	public boolean isViewingActiveRepositories() {
		return viewingActiveRepositories;
	}

	public void setViewingActiveRepositories(boolean viewingActiveRepositories) {
		this.viewingActiveRepositories = viewingActiveRepositories;
	}
	
	public void viewOthers()
	{
		viewingActiveRepositories=!viewingActiveRepositories;
		init();
	}


	
	
}
