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
    }
 
    public String getPassword() {
        return password;
    }
 
    public void setPassword(String password) {
        this.password = password;
    }
   
    public String login(ActionEvent event) {
        FacesMessage message = null;
        boolean loggedIn = false;
         
        message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Loggin Error", "Invalid credentials");
        loggedIn=Database.login(username, password.hashCode(),this);
        if(loggedIn)
			try {
				((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest()).getSession().setAttribute("type",type==1?"Student":"Profesor");
				FacesContext.getCurrentInstance().getExternalContext().redirect("main.xhtml");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        FacesContext.getCurrentInstance().addMessage(null, message);
        message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Loggin Error", "Invalid credentials");
        return "";
    }   
}