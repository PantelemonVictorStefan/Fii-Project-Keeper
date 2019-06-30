package Model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import DataAccess.An;
import DataAccess.DataAccessAPI;
import DataAccess.Limbaj;
import DataAccess.Repository;
import resources.Database;
import resources.Security;
import resources.Validator;

@ManagedBean
public class RepositoryDTO {

	private ArrayList<String> materii;
	private List<Limbaj> limbaje;
	private List<An> ani;
	
	private String materie;
	private String numeRepository;
	private List<Limbaj> limbajeSelectate;
	private List<An> aniSelectati;
	private Date data;
	private String detalii;
	
	
	public String getMaterie() {
		if(materie==null)
			return"";
		return materie;
	}
	public void setMaterie(String materie) {
		this.materie = materie;
	}
	public ArrayList<String> getMaterii() {
		return materii;
	}
	public void setMaterii(ArrayList<String> materii) {
		this.materii = materii;
	}
	public String getNumeRepository() {
		if(numeRepository==null)
			return"";
		return numeRepository;
	}
	public void setNumeRepository(String numeRepository) {
		this.numeRepository = numeRepository;
	}
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public String getDetalii() {
		if(detalii==null)
			return"";
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
	public List<Limbaj> getLimbajeSelectate() {
		return limbajeSelectate;
	}
	public void setLimbajeSelectate(List<Limbaj> limbajeSelectate) {
		this.limbajeSelectate = limbajeSelectate;
	}
    public String getDeadlineDate()
    {
    if(data!=null)
    	return (new SimpleDateFormat("MM/dd/yyyy").format(data));
    return "";
    }
    
    public List<An> getAniSelectati() {
		return aniSelectati;
	}
	public void setAniSelectati(ArrayList<An> aniSelectati) {
		this.aniSelectati = aniSelectati;
	}
	
	public List<An> getAni() {
		return ani;
	}
	public void setAni(ArrayList<An> ani) {
		this.ani = ani;
	}
    
    @SuppressWarnings("deprecation")
	private boolean isToday(Date date)
    {
    	Date today=new Date();
    	if((date.getYear()==today.getYear())&&(date.getMonth()==today.getMonth())&&(date.getDay()==today.getDay()))
    		return true;
    	return false;
    }
	
	void init()
	{
		DataAccessAPI api=new DataAccessAPI();
		
		materii=Database.getMaterii();
		
		data=null;
		if(data!=null)
		System.out.println("DATA:"+data);
		
		limbaje=api.getLanguages();
		
		ani=api.getYears();
	}
	
	public void display()
	{
		System.out.println("materie: "+materie);
		System.out.println("numeRepository: "+numeRepository);
		System.out.println("Deadline: "+getDeadlineDate());
		//DateFormat dataFormat = new SimpleDateFormat("MM/dd/yyyy");			//"MM/dd/yyyy HH:mm:ss"
		//DateFormat oraFormat = new SimpleDateFormat("HH:mm");
		//System.out.println("data:"+dataFormat.format(data));
		//System.out.println("Ora :"+oraFormat.format(ora));
		
//		for(int i=0;i<studentiSelectati.length;i++)
//			System.out.println("Student: "+studentiSelectati[i]);
//		if(isLimitat())
//			System.out.println("Incarcari permise: "+incarcariPermise);
//		else
//			System.out.println("Incarcari nelimitate");
		
		System.out.println("Details: "+detalii);
		
		System.out.println("Limbaje selectate");
		for(Limbaj limbaj:limbajeSelectate)
			System.out.println("ID: "+limbaj.getId()+"Denumire: "+limbaj.getDenumire());
		System.out.println("ani selectati");
		for(An an:aniSelectati)
			System.out.println("ID: "+an.getId()+"Denumire: "+an.getDenumire());
	}
	
	
	public RepositoryDTO()
	{
		init();
	}

	private boolean objectIsValid()
	{
		boolean valid=true;
		ArrayList<FacesMessage> messages=new ArrayList<FacesMessage>();
		
		if(materie==null)
		{
			valid=false;
			messages.add(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Validation Failed \n \"Materie\" is null",""));
		}
		
		if(materie.length()>30)
		{
			valid=false;
			messages.add(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Validation Failed \n \"Materie\" is too long",""));
		}
		
		if(Validator.stringContains(materie,"'","/","\\",":","*","?","\"","<",">","|"))
		{
			valid=false;
			messages.add(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Validation Failed \n \"Materie\" containing unallowed characters",""));
		}
		
		
		
		
		if(numeRepository==null)
		{
			valid=false;
			messages.add(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Validation Failed \n \"Nume Repository\" is null",""));
		}
		
		if(Validator.stringContains(numeRepository,"'","/","\\",":","*","?","\"","<",">","|"))
		{
			valid=false;
			messages.add(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Validation Failed \n \"Nume Repository\" containing unallowed characters",""));
		}
		
		if(numeRepository.length()>30)
		{
			valid=false;
			messages.add(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Validation Failed \n \"Nume Repository\" is too long",""));
		}
		
		
		
		
		if((data!=null)&&(data.compareTo(new Date())<0)&&(!isToday(data)))
		{
			valid=false;
			messages.add(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Validation Failed \n Invalid Date",""));
		}
		
		
		
		if(Validator.stringContains(detalii,"'"))
		{
			valid=false;
			messages.add(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Validation Failed \n \"Nume Repository\" containing unallowed characters",""));
		}
		
		if(detalii.length()>1000)
		{
			valid=false;
			messages.add(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Validation Failed \n \"Nume Repository\" is too long",""));
		}
		
		if(!valid)
			for(int i=0;i<messages.size();i++)
				FacesContext.getCurrentInstance().addMessage(null,messages.get(i));
		return valid;
	}
	
	private Repository getAsProject()
	{
		Repository project=new Repository();
		project.setActiv(true);
		project.setAni(aniSelectati);
		project.setData(getDeadlineDate());
		project.setDetalii(detalii);
		project.setLimbaje(limbajeSelectate);
		project.setMaterie(materie);
		project.setNumeRepository(numeRepository);
		
		project.setCreatedAt(new Date());
		
		return project;
	}
	
	public void submit()
	{	
		if(objectIsValid())
		{
			DataAccessAPI api=new DataAccessAPI();
			if(api.addRepository(getAsProject()))
			{
				FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO, "Repository-ul a fost creat cu succes! ",""));
				Security.redirect("repositories.xhtml");
			}
			else
				FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "Repository-ul nu a putut fi salvat",""));
		}
	}
	
	
	public Date getCurrentDate() {
		return new Date();
	}
	
	
	
	
	
}

