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
	
    private int type;
    
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
		return type==1?"Student":"Profesor";
	}

	public void setType(int type) {
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
    	firstName= (""+username.charAt(0)).toUpperCase()+username.substring(1, (username.indexOf("-")==-1)?username.indexOf("."):username.indexOf("-"));
    }
   
    public String login(ActionEvent event) throws IOException {
        FacesMessage message = null;
        boolean loggedIn = false;
         
        message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Loggin Error", "Invalid credentials");
        loggedIn=Database.login(username, password.hashCode(),this);
        if(loggedIn) {
			((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest()).getSession().setAttribute("type",type==1?"Student":"Profesor");
			Security.mainPage();
		}
        FacesContext.getCurrentInstance().addMessage(null, message);
        message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Loggin Error", "Invalid credentials");
        return "";
    }   
}