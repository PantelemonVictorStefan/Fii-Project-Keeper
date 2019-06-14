package View;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.Part;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

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
	
	public void upload() {
		System.out.println("Called upload");
		display();
        /*if(file != null) {
            FacesMessage message = new FacesMessage("Succesful", file.getName() + " is uploaded.");
            FacesContext.getCurrentInstance().addMessage(null, message);
            
            System.out.println(file.getName());
            System.out.println(file.getSize());
            System.out.println(file.getSubmittedFileName());
        }
        else
        	System.out.println("upload:file is null");*/
    }
     
    public void handleFileUpload(FileUploadEvent event) {
        FacesMessage msg = new FacesMessage("Succesful", event.getFile().getFileName() + " is uploaded.");
        FacesContext.getCurrentInstance().addMessage(null, msg);
        if(event.getFile()==null)
        	System.out.println("handleFileUpload: file is null");
    }
}
