package View;

import java.io.IOException;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.Part;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import DataAccess.DataAccessAPI;
import DataAccess.Project;
import DataAccess._File;
import resources.Security;
import resources.SessionObject;

@ManagedBean
public class ProjectUploadView {
     
	private String description;
	private Part presentation;
	private Part file;
     
    public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Part getPresentation() {
		return presentation;
	}

	public void setPresentation(Part presentation) {
		this.presentation = presentation;
	}

	public Part getFile() {
		return file;
	}

	public void setFile(Part file) {
		this.file = file;
	}

	private void display()
	{
		System.out.println("Descritpion: "+description);
		
		if(presentation != null)
		{
			System.out.println(presentation.getName());
            System.out.println(presentation.getSize());
            System.out.println(presentation.getSubmittedFileName());
		}
		else
		{
			System.out.println("Presentation is null");
		}
		
		if(file != null) {
            System.out.println(file.getName());
            System.out.println(file.getSize());
            System.out.println(file.getSubmittedFileName());
        }
		else
			System.out.println("file is null");
		
	}
	
	
	public boolean validateFiles()
	{
		boolean valid=true;
		
		
		
		if(description.length()>1000)
		{
			valid=false;
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "Validation Failed \n \"Description\" is too long",""));
		}
		
		if(presentation!=null)
			if(!presentation.getSubmittedFileName().toLowerCase().endsWith(".pdf"))
			{
				valid=false;
				FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "Validation Failed \n Presentation is not PDF",""));
			
			}
		
		if(file!=null)
			if(!file.getSubmittedFileName().toLowerCase().endsWith(".zip"))
			{
				valid=false;
				FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "Validation Failed \n Project is not ZIP",""));
			
			}
		
		
		
		return valid;
	}
	
	private Project getAsProject()
	{
		SessionObject session=Security.getSession();
		
		Project project=new Project();
		project.setDescription(description);
		project.setUser(session.getUser());
		project.setRepository(session.getSelectedRepository());
		
		try {
		if(presentation!=null)
		{
			_File _presentation=new _File();
			_presentation.setFilename(presentation.getSubmittedFileName());
			
				_presentation.setFs(presentation.getInputStream());
				_presentation.setSize(presentation.getSize());
			
			
			project.setPresentation(_presentation);
		}
		if(file!=null)
		{
			_File _data=new _File();
			_data.setFilename(file.getSubmittedFileName());
			_data.setFs(file.getInputStream());
			_data.setSize(file.getSize());
			project.setData(_data);
		}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		return project;
		
	}
	
	public void upload() {
		//System.out.println("Called upload");
		//
		if(validateFiles())
		{
			//display();
			
			DataAccessAPI api=new DataAccessAPI();
			
			
			if(api.addProject(getAsProject()))
			{
				FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO, "Proiectul a fost uploadat cu succes! ",""));
			}
			else
				FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "Proiectul nu a putut fi salvat!",""));
		}
		else
		{
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "Proiectul nu a putut fi salvat",""));
		}
        
    }
     
    public void handleFileUpload(FileUploadEvent event) {
        FacesMessage msg = new FacesMessage("Succesful", event.getFile().getFileName() + " is uploaded.");
        FacesContext.getCurrentInstance().addMessage(null, msg);
        if(event.getFile()==null)
        	System.out.println("handleFileUpload: file is null");
    }
}
