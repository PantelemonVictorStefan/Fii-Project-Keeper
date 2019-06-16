package DataAccess;

import java.io.FileInputStream;
import java.io.InputStream;

public class _File {

	private int id;
	private String filename;
	private InputStream fs;
	private long size;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public InputStream getFs() {
		return fs;
	}
	public void setFs(InputStream inputStream) {
		this.fs = inputStream;
	}
	public long getSize() {
		return size;
	}
	public void setSize(long size) {
		this.size = size;
	}
	
}
