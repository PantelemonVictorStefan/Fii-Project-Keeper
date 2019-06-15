package resources;

import java.io.IOException;

import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
@ManagedBean()
public class Security {

	
	public static boolean checkRole(String role)
	{
		User user=getUser();
		if(user==null)
		{
			System.out.println("user is null");
			return false;
		}
		return user.getType().equals(role);
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
			if(!(checkRole("Student")||checkRole("Profesor")))
				{
				//System.out.println("REDIRECTING");
				redirect("login.xhtml");
				return false;
				}
			return true;
	}
	
	public static void authorize(String role)
	{
		System.out.println("authorize "+role);
		if(!checkRole(role))
			redirect("login.xhtml");
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
	
	public static SessionObject getSession()
	{
		return (SessionObject)((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest()).getSession().getAttribute("sessionObject");
	}
}
