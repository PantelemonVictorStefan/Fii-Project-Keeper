package resources;

import java.io.IOException;

import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
@ManagedBean()
public class Security {

	public static boolean isStudent()
	{
		return("Student".equalsIgnoreCase((String) ((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest()).getSession().getAttribute("type")));
	}
	
	public static boolean isProfessor()
	{
		return("Profesor".equalsIgnoreCase((String) ((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest()).getSession().getAttribute("type")));
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
	
	public static boolean isAuthenticated()
	{
			if(!(isStudent()||(isProfessor())))
				{
				System.out.println("REDIRECTING");
				redirect("login.xhtml");
				return false;
				}
			return true;
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
		redirect("home.xhtml");
		/*if(isAuthenticated())
			if(isStudent())
					redirect("home.xhtml");
				else
					redirect("main.xhtml");*/
	}
	
	public static void logout()
	{
		System.out.println("Logged out!");
		((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest()).getSession().invalidate();
		redirect("login.xhtml");
	}
	
	public static User getUser()
	{
		return (User)((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest()).getSession().getAttribute("user");
	}
}
