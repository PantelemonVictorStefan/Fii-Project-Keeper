package resources;

import javax.faces.bean.ManagedBean;

@ManagedBean
public class RepositoryDTO {

	private int id;
	private String title;
	private String data;
	private String ora;
	private int incarcariPermise;
	private String detalii;
	
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
	public String getDetalii() {
		return detalii;
	}
	public void setDetalii(String detalii) {
		this.detalii = detalii;
	}
	public String getOra() {
		return ora;
	}
	public void setOra(String ora) {
		this.ora = ora;
	}
	public int getIncarcariPermise() {
		return incarcariPermise;
	}
	public void setIncarcariPermise(int incarcariPermise) {
		this.incarcariPermise = incarcariPermise;
	}
	
	RepositoryDTO(String materie,String numeRepository,String data,String ora,int incarcariPermise,String detalii)
	{
		this.title=materie+": "+numeRepository;
		if(data.isEmpty())
		{
			this.data="- -";
			this.ora="";
		}
		else
		{
			this.data=data;
			this.ora=ora;
		}
		this.incarcariPermise=incarcariPermise;
		this.detalii=detalii;
	}
	
}
