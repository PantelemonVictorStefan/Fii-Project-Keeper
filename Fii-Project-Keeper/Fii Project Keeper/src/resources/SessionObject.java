package resources;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class SessionObject {

	private User user;
	private Repository selectedRepository;
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Repository getSelectedRepository() {
		return selectedRepository;
	}
	public void setSelectedRepository(Repository selectedRepository) {
		this.selectedRepository = selectedRepository;
	}
}
