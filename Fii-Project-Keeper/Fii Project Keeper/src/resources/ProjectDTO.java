package resources;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

@ManagedBean
public class ProjectDTO {

	
	private String materie;
	private ArrayList<String> materii;
	private String numeRepository;
	private Date data;
	private Date ora;
	private ArrayList<String> audienta;
	private String[] studentiSelectati;
	private List<String> studenti;
	private String limitat;
	private int incarcariPermise;
	private String detalii;
	private ArrayList<String> limbaje;
	private String[] limbajeSelectate;
	
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
	public Date getOra() {
		return ora;
	}
	public void setOra(Date ora) {
		this.ora = ora;
	}
	public ArrayList<String> getAudienta() {
		return audienta;
	}
	public void setAudienta(ArrayList<String> audienta) {
		this.audienta = audienta;
	}
	public String[] getStudentiSelectati() {
		return studentiSelectati;
	}
	public void setStudentiSelectati(String[] studentiSelectati) {
		this.studentiSelectati = studentiSelectati;
	}
	public List<String> getStudenti() {
		return studenti;
	}
	public void setStudenti(List<String> studenti) {
		this.studenti = studenti;
	}
	public String getLimitat() {
		return limitat;
	}
	public void setLimitat(String limitat) {
		this.limitat=limitat;
	}
	public int getIncarcariPermise() {
		if(limitat.equalsIgnoreCase("true"))
			return 0;
		return incarcariPermise;
	}
	public void setIncarcariPermise(int incarcariPermise) {
		if(isLimitat())
			this.incarcariPermise = incarcariPermise;
	}
	public String getDetalii() {
		if(detalii==null)
			return"";
		return detalii;
	}
	public void setDetalii(String detalii) {
		this.detalii = detalii;
	}
	public ArrayList<String> getLimbaje() {
		return limbaje;
	}
	public void setLimbaje(ArrayList<String> limbaje) {
		this.limbaje = limbaje;
	}
	public String[] getLimbajeSelectate() {
		return limbajeSelectate;
	}
	public void setLimbajeSelectate(String[] limbajeSelectate) {
		this.limbajeSelectate = limbajeSelectate;
	}
    public String getDeadlineDay()
    {
    if(data!=null)
    	return (new SimpleDateFormat("MM/dd/yyyy").format(data));
    return "";
    }
    public String getDeadlineTime()
    {
    if(ora!=null)
    	return (new SimpleDateFormat("HH:mm").format(ora));
    return "00:00";
    }
    public String getYearCode()
    {
    	String yearCode="000";
    	for(int i=0;i<audienta.size();i++)
    	{
    		if(audienta.get(i).equalsIgnoreCase("1"))
    			yearCode="1"+yearCode.substring(1);
    		if(audienta.get(i).equalsIgnoreCase("2"))
    			yearCode=yearCode.substring(0,1)+"1"+yearCode.substring(2);
    		if(audienta.get(i).equalsIgnoreCase("3"))
    			yearCode=yearCode.substring(0,2)+"1";
    	}
    	return yearCode;
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
		materii=Database.getMaterii();
		
		audienta=new ArrayList<String>();
		
		studenti=Database.getNumeStudenti();
		
		data=null;
		if(data!=null)
		System.out.println("DATA:"+data);
		ora=null;
		limitat="false";
		
		limbaje=Database.getLimbaje();
	}
	
	public void display()
	{
		System.out.println("materie: "+materie);
		System.out.println("numeRepository: "+numeRepository);
		//DateFormat dataFormat = new SimpleDateFormat("MM/dd/yyyy");			//"MM/dd/yyyy HH:mm:ss"
		//DateFormat oraFormat = new SimpleDateFormat("HH:mm");
		//System.out.println("data:"+dataFormat.format(data));
		//System.out.println("Ora :"+oraFormat.format(ora));
		for(int i=0;i<audienta.size();i++)
			System.out.println("audienta["+(i+1)+"]= "+audienta.get(i));
		for(int i=0;i<studentiSelectati.length;i++)
			System.out.println("Student: "+studentiSelectati[i]);
		if(isLimitat())
			System.out.println("Incarcari permise: "+incarcariPermise);
		else
			System.out.println("Incarcari nelimitate");
		System.out.println("Details: "+detalii);
		for(int i=0;i<limbajeSelectate.length;i++)
			System.out.println("Limbaj folosit: "+limbajeSelectate[i]);
	}
	
	boolean isLimitat()
	{
		if(limitat!=null)
			return limitat.equalsIgnoreCase("true");
		return false;
	}
	
	public ProjectDTO()
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
		
		
		
		if(((audienta.isEmpty()))&&(studentiSelectati.length==0))
		{
			valid=false;
			messages.add(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Validation Failed \n No students selected",""));
		}
		
		if((isLimitat())&&((incarcariPermise<1)||(incarcariPermise>1000)))
		{
			valid=false;
			messages.add(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Validation Failed \n \"Numar incarcari permise\" is invalid",""));
		}
		
		
		if((data!=null)&&(data.compareTo(new Date())<0)&&(!isToday(data)))
		{
			valid=false;
			messages.add(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Validation Failed \n Invalid Date",""));
		}
		
		if((ora==null)&&(ora!=null))
		{
			valid=false;
			messages.add(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Validation Failed \n Date is null",""));
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
	
	public void submit()
	{	
		if(objectIsValid())
			if(Database.addProject(new Proiect(materie,numeRepository,getDeadlineDay(),getDeadlineTime(),getYearCode(),studentiSelectati,isLimitat(),incarcariPermise,detalii,limbajeSelectate)))
				FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO, "Repository-ul a fost creat cu succes! ",""));
			else
				FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "Repository-ul nu a putut fi salvat",""));
		else
			System.out.println("object not valid");
	}
	
	
	
}

