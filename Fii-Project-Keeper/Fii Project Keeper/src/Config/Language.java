package Config;

import java.io.Serializable;
import java.util.Locale;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@SessionScoped
public class Language implements Serializable {
	private static final long serialVersionUID = 1L;
    
    private Locale locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();
 
    
    public Language()
    {
    	System.out.println("New Language");
    	changeLanguage("ro");
    }
    
    public Locale getLocale() {
        return locale;
    }
 
    public String getLanguage() {
        return locale.getLanguage();
    }
 
    public void changeLanguage(String language) {
    	System.out.println("Changed language");
        locale = new Locale(language);
        FacesContext.getCurrentInstance().getViewRoot().setLocale(new Locale(language));
    }
}
