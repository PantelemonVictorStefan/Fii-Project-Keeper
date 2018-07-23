package resources;

import java.io.IOException;

import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
@ManagedBean()
public class Security {

	public static boolean isStudent()
	{
		return("Student"==((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest()).getSession().getAttribute("type"));
	}
	
	public static boolean isProfessor()
	{
		return("Profesor"==((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest()).getSession().getAttribute("type"));
	}
	
	public static void redirect()
	{
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("login.xhtml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void isAuthenticated()
	{
			if(!(isStudent()||(isProfessor())))
				redirect();
			
	}
	
}
