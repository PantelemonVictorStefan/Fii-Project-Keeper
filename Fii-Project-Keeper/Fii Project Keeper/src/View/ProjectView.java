package View;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import DataAccess._File;
import resources.Security;

public class ProjectView {

	private String userName;
	private String description;
	private int presentationId;
	private int dataId;
	private _File presentation;
	private _File data;
	
	private StreamedContent file;
	
	
	public void setFile(StreamedContent file) {
		this.file = file;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getPresentationId() {
		return presentationId;
	}
	public void setPresentationId(int presentationId) {
		this.presentationId = presentationId;
	}
	public int getDataId() {
		return dataId;
	}
	public void setDataId(int dataId) {
		this.dataId = dataId;
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
	
	public StreamedContent getFile() {
		this.data=new ViewDataAccess().getFileById(dataId);
		file=new DefaultStreamedContent(data.getFs(), "application/zip", data.getFilename());
        return file;
    }
	
	
	public void downloadPDF() throws IOException {
		
		this.presentation=new ViewDataAccess().getFileById(presentationId);
		
        FacesContext facesContext = FacesContext.getCurrentInstance();
         
        HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
        response.reset();
        response.setContentType("application/pdf");
        response.setContentLengthLong(presentation.getSize());
        response.setHeader("Content-Disposition", "inline; filename=\"" + presentation.getFilename() + "\"");
        OutputStream responseOutputStream = response.getOutputStream();
         
        
        InputStream pdfInputStream = presentation.getFs();
         
        byte[] bytesBuffer = new byte[2048];
        int bytesRead;
        while ((bytesRead = pdfInputStream.read(bytesBuffer)) > 0) {
            responseOutputStream.write(bytesBuffer, 0, bytesRead);
        }
        responseOutputStream.flush();
        pdfInputStream.close();
        responseOutputStream.close();
        facesContext.responseComplete();
	}
	
	public boolean isOwner()
	{
		return Security.getSession().getUser().getUsername().equals(userName);
	}
	
	
}
