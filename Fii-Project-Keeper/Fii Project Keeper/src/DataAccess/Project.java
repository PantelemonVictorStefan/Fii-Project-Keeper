package DataAccess;

import resources.Repository;
import resources.User;

public class Project {

	private User user;
	private Repository repository;
	private String description;
	private File presentation;
	private File data;
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Repository getRepository() {
		return repository;
	}
	public void setRepository(Repository repository) {
		this.repository = repository;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public File getPresentation() {
		return presentation;
	}
	public void setPresentation(File presentation) {
		this.presentation = presentation;
	}
	public File getData() {
		return data;
	}
	public void setData(File data) {
		this.data = data;
	}
	
	
}
