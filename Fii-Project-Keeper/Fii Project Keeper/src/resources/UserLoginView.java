package resources;

import java.io.IOException;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
 
@ManagedBean
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
        User user=null;
        if(!Validator.stringContains(username,"'"))
        	user=Database.login(username,password);
        if(user!=null)
        {
        	((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest()).getSession().setAttribute("user",user);
        	try {
				FacesContext.getCurrentInstance().getExternalContext().redirect("home.xhtml");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
        else
        	FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Loggin Error", "Invalid credentials"));
    }   
}