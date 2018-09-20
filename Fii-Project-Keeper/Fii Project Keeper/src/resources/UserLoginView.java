package resources;

import java.io.IOException;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletRequest;

import com.sun.research.ws.wadl.Request;
//import org.primefaces.PrimeFaces;
 
@ManagedBean
@SessionScoped
public class UserLoginView {
     
    private String username;
     
    private String password;

	private String year;
	
    private String type;
    
    private String firstName;
    

    public String getFirstName() {
		return firstName;
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
    
    public UserLoginView()
    {
    	System.out.println("New login object!");
    }
 
    public String getUsername() {
        return username;
    }
 
    public void setUsername(String username) {
        this.username = username;
        addFirstName();
    }
 
    public String getPassword() {
        return password;
    }
 
    public void setPassword(String password) {
        this.password = password;
    }
    

    public void addFirstName()
    {
    	firstName=(""+username.charAt(0)).toUpperCase()+username.substring(1,((username.indexOf("-")<1?(username.indexOf(".")<1?username.length()-1:username.indexOf(".")):username.indexOf("-")<1?username.length()-1:username.indexOf("-"))));
    }
   
    public void login()
    {
        boolean loggedIn = false;
        if(!Validator.stringContains(username,"'"))
        	loggedIn=Database.login(this);
        if(loggedIn) {
			((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest()).getSession().setAttribute("type",type);
			Security.mainPage();
		}
        else
        	FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Loggin Error", "Invalid credentials"));
    }   
}