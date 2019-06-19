package DataAccess;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.faces.bean.ManagedBean;

import View.ViewDataAccess;
import resources.Security;

@ManagedBean
public class Repository {

	private int id;
	private String materie;
	private String numeRepository;
	private List<Limbaj> limbaje=new LinkedList<Limbaj>();
	private List<An> ani=new LinkedList<An>();
	private String data;
	private String detalii;
	private boolean activ=true;
	private Date createdAt;
	
	
	public String getMaterie() {
		return materie;
	}
	public void setMaterie(String materie) {
		this.materie = materie;
	}
	public String getNumeRepository() {
		return numeRepository;
	}
	public void setNumeRepository(String numeRepository) {
		this.numeRepository = numeRepository;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String getDetalii() {
		return detalii;
	}
	public void setDetalii(String detalii) {
		this.detalii = detalii;
	}
	
	public List<Limbaj> getLimbaje() {
		return limbaje;
	}
	public void setLimbaje(List<Limbaj> limbaje) {
		this.limbaje = limbaje;
	}
	
	public List<An> getAni() {
		return ani;
	}
	public void setAni(List<An> ani) {
		this.ani = ani;
	}
	
	public boolean isActiv() {
		return activ;
	}
	public void setActiv(boolean activ) {
		this.activ = activ;
	}
	
	public Repository()
	{
		
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public boolean userHasProject()
	{
		ViewDataAccess api=new ViewDataAccess();
		
		return api.projectExists(id, Security.getSession().getUser().getId());
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	
	public boolean activateDeactivateRepository()
	{
		if(new DataAccessAPI().updateRespositoryActive(id, !activ))
		{
			activ=!activ;
			return true;
		}
		return false; 
	}


	
	

	
}
