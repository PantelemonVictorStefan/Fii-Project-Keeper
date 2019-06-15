package resources;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ValueChangeEvent;


import DataAccess.DataAccessAPI;

@ManagedBean
@SessionScoped
public class RepositoriesView {


	List<RepositoryCardView> proiecte;
	private Repository selectedProject;

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
		init();
	}
	
	private void init()
	{
		User user;
		user=Security.getUser();
		DataAccessAPI api=new DataAccessAPI();
		proiecte=api.getRepositories(user.getYear());
		
		
		
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


	
	
}
