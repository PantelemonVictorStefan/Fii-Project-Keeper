package resources;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;

@ManagedBean
public class Proiect {

	private String materie;
	private String numeRepository;
	private String data;
	private String ora;
	private String audienta;
	private String[] studentiSelectati;
	private boolean limitat;
	private int incarcariPermise;
	private String detalii;
	private String[] limbajeSelectate;
	
	public String getMaterie() {
		return materie;
	}
	public void setMaterie(String materie) {
		this.materie = materie;
	}
	public String getNumeRepository() {
		return numeRepository;
	}
	public void setNumeRepository(String numeRepository) {
		this.numeRepository = numeRepository;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String getOra() {
		return ora;
	}
	public void setOra(String ora) {
		this.ora = ora;
	}
	public String getAudienta() {
		return audienta;
	}
	public void setAudienta(String audienta) {
		this.audienta = audienta;
	}
	public String[] getStudentiSelectati() {
		return studentiSelectati;
	}
	public void setStudentiSelectati(String[] studentiSelectati) {
		this.studentiSelectati = studentiSelectati;
	}
	public boolean isLimitat() {
		return limitat;
	}
	public void setLimitat(boolean limitat) {
		this.limitat = limitat;
	}
	public int getIncarcariPermise() {
		return incarcariPermise;
	}
	public void setIncarcariPermise(int incarcariPermise) {
		this.incarcariPermise = incarcariPermise;
	}
	public String getDetalii() {
		return detalii;
	}
	public void setDetalii(String detalii) {
		this.detalii = detalii;
	}
	public String[] getLimbajeSelectate() {
		return limbajeSelectate;
	}
	public void setLimbajeSelectate(String[] limbajeSelectate) {
		this.limbajeSelectate = limbajeSelectate;
	}
	
	Proiect(String materie,String numeRepository,String data,String ora, String audienta,String[] studentiSelectati,boolean limitat,int incarcariPermise,String detalii,String[] limbajeSelectate)
	{
		this.materie=materie;
		this.numeRepository=numeRepository;
		this.data=data;
		this.ora=ora;
		this.audienta=audienta;
		this.studentiSelectati=studentiSelectati;
		this.limitat=limitat;
		this.incarcariPermise=incarcariPermise;
		this.detalii=detalii;
		this.limbajeSelectate=limbajeSelectate;
	}

	
}
