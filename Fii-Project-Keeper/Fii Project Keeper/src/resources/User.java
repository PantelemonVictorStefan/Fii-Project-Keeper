package resources;

import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

@ManagedBean
public class User {

	private int id;
	private String username;
	private String year;
    private String type;
    private String firstName;
    
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public void setFirstName()
	{
		firstName=(""+username.charAt(0)).toUpperCase()+username.substring(1,((username.indexOf("-")<1?(username.indexOf(".")<1?username.length()-1:username.indexOf(".")):username.indexOf("-")<1?username.length()-1:username.indexOf("-"))));
	}
	
	public User(String username,String year,String type)
	{
		this.username=username;
		this.year=year;
		this.type=type;
		setFirstName();
	}
	
	public User()
	{
		//Security.logout();
		//Security.redirect("login.xhtml");
	}
	public int getTypeNumber()
	{
		if(type!=null)
		{
			if(type.equalsIgnoreCase("Student"))
				return 1;
			if(type.equalsIgnoreCase("Profesor"))
				return 2;
		}
		return 0;
	}
	
	public void hasAccess(int request)
	{
		int tip=getTypeNumber();
		if ((tip!=request)&&((tip!=0)&&(request!=12)))
			Security.redirect("login.xhtml");
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
}
