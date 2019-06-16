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
	
	
	public StreamedContent getFile() {
		this.data=new ViewDataAccess().getFileById(dataId);
		//file=new DefaultStreamedContent(data.getFs(), "application/zip", data.getFilename());
		file=new DefaultStreamedContent(data.getFs(), "application/zip", data.getFilename());
        return file;
    }
	public _File getData() {
		return data;
	}
	public void setData(_File data) {
		this.data = data;
	}
	
	
	public void downloadPDF() throws IOException {
		this.presentation=new ViewDataAccess().getFileById(presentationId);
		
        // Get the FacesContext
        FacesContext facesContext = FacesContext.getCurrentInstance();
         
        // Get HTTP response
        HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
         
        // Set response headers
        response.reset();   // Reset the response in the first place
        //response.setHeader("Content-Type", "application/pdf");  // Set only the content type
        response.setContentType("application/pdf");
        response.setContentLengthLong(presentation.getSize());
        response.setHeader("Content-Disposition", "inline; filename=\"" + presentation.getFilename() + "\"");
        //response.setHeader("Content-Disposition", "attachment; filename=\"" + presentation.getFilename() + "\"");
        
        
        //ec.setResponseContentLength((int)presentation.getSize()); 
        //ec.setResponseHeader("Content-Disposition", "attachment; filename=\"" + presentation.getFilename() + "\""); 
         
        // Open response output stream
        OutputStream responseOutputStream = response.getOutputStream();
         
        // Read PDF contents
        
        InputStream pdfInputStream = presentation.getFs();
         
        // Read PDF contents and write them to the output
        byte[] bytesBuffer = new byte[2048];
        int bytesRead;
        while ((bytesRead = pdfInputStream.read(bytesBuffer)) > 0) {
            responseOutputStream.write(bytesBuffer, 0, bytesRead);
        }
         
        // Make sure that everything is out
        responseOutputStream.flush();
          
        // Close both streams
        pdfInputStream.close();
        responseOutputStream.close();
         
        // JSF doc: 
        // Signal the JavaServer Faces implementation that the HTTP response for this request has already been generated 
        // (such as an HTTP redirect), and that the request processing lifecycle should be terminated
        // as soon as the current phase is completed.
        facesContext.responseComplete();
	}
	
	void sendBackPDFToClient()
	{
	        //File temp = File.createTempFile(fileName, ".pdf");
	        //File testPdfFile = new File("D:\AFC150_20180819_0103.pdf");
	        FacesContext fc = FacesContext.getCurrentInstance();
	        ExternalContext ec = fc.getExternalContext();

	        ec.responseReset(); 
	        ec.setResponseContentType("application/pdf"); 
	        ec.setResponseContentLength((int)presentation.getSize()); 

	        //Inline
	        //ec.setResponseHeader("Content-Disposition", "inline; filename=\"" + testPdfFile.getName() + "\""); 

	        //Attach for Browser
	        ec.setResponseHeader("Content-Disposition", "attachment; filename=\"" + presentation.getFilename() + "\""); 

	        //euOutputStream output = ec.getResponseOutputStream();
	        //euFiles.copy(testPdfFile.toPath(), output);
	        fc.responseComplete();


	}
	public _File getPresentation() {
		return presentation;
	}
	public void setPresentation(_File presentation) {
		this.presentation = presentation;
	}
}
