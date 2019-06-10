package resources;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ValueChangeEvent;

import org.primefaces.context.RequestContext;

import DataAccess.DataAccessAPI;

@ManagedBean
@SessionScoped
public class RepositoriesView {


	List<RepositoryCardView> proiecte;
	private Proiect selectedProject;

	public Proiect getSelectedProject() {
		return selectedProject;
	}

	public void setSelectedProject(Proiect selectedProject) {
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
			
			selectedProject=api.getProjectById(proiecte.get(index).getId());
			if(selectedProject==null)
				System.out.println("object is null");
			
			Security.redirect("repository.xhtml");
	}


	
	
}
