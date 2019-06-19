package View;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.faces.bean.ManagedBean;

@ManagedBean
public class RepositoryCardView {

	private int id;
	private String title;
	private String subject;
	private Date createdAt;
	private String data;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	public RepositoryCardView()
	{
		
	}
	
	RepositoryCardView(String materie,String numeRepository,String data,String ora,int incarcariPermise)
	{
		this.title=materie+": "+numeRepository;
		if(data.isEmpty())
		{
			this.data="- -";
		}
		else
		{
			this.data=data;
		}
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	
	public String getFormattedDate()
	{
		return new SimpleDateFormat("MM/dd/yyyy").format(createdAt);
	}
	
	
}
