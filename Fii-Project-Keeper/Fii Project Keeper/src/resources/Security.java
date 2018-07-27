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
	
	public static void redirect(String page)
	{
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect(page);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void isAuthenticated()
	{
			if(!(isStudent()||(isProfessor())))
				redirect("login.xhtml");
			
	}
	
	public static void studentPage()
	{
		isAuthenticated();
		if(isProfessor())
			mainPage();
	}
	
	public static void professorPage()
	{
		isAuthenticated();
		if(isStudent())
			mainPage();
	}
	
	public static void mainPage()
	{
		isAuthenticated();
		if(isStudent())
				redirect("home.xhtml");
			else
				redirect("main.xhtml");
	}
	
	public static void logout()
	{
		((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest()).getSession().invalidate();
	}
}
