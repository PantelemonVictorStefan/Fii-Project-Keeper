package DataAccess;

public class An {

	private int id;
	private String denumire;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDenumire() {
		return denumire;
	}
	public void setDenumire(String denumire) {
		this.denumire = denumire;
	}
	
	public An()
	{
		
	}
	
	public String toString()
	{
		return this.denumire;
	}
	
}
