package resources;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;

@ManagedBean
public class RepositoriesView {


	List<RepositoryDTO> proiecte;
	

	public List<RepositoryDTO> getProiecte() {
		return proiecte;
	}

	public void setProiecte(List<RepositoryDTO> proiecte) {
		this.proiecte = proiecte;
	}
	
	public RepositoriesView()
	{
		init();
	}
	
	private void init()
	{
		/*proiecte=new ArrayList<RepositoryDTO>();
		proiecte.add(new RepositoryDTO(1,"Java","Laborator 3","12/03/2020","00:00",0));
		proiecte.add(new RepositoryDTO(2,"Java","Laborator 5","12/03/2020","",0));
		proiecte.add(new RepositoryDTO(3,"Java","Laborator 2","12/03/2020","",2));
		proiecte.add(new RepositoryDTO(1,"Java","Laborator 3","12/03/2020","00:00",0));
		proiecte.add(new RepositoryDTO(2,"Java","Laborator 5","12/03/2020","",0));
		proiecte.add(new RepositoryDTO(3,"Java","Laborator 2","12/03/2020","",2));*/
		User user;
		user=Security.getUser();
		proiecte=Database.getProjects(user.getUsername(), user.getYear());
		//Proiect(int id,String materie,String numeRepository,String data,String ora,int incarcariPermise)
		
	}
}
