package DataAccess;

import resources.Repository;
import resources.User;

public class Project {

	private User user;
	private Repository repository;
	private String description;
	private _File presentation;
	private _File data;
	
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
	public _File getPresentation() {
		return presentation;
	}
	public void setPresentation(_File presentation) {
		this.presentation = presentation;
	}
	public _File getData() {
		return data;
	}
	public void setData(_File data) {
		this.data = data;
	}
	
	
}
