package DataAccess;

import java.io.FileInputStream;

public class File {

	private int id;
	private String filename;
	private Byte[]data;
	private FileInputStream fs;
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
	public Byte[] getData() {
		return data;
	}
	public void setData(Byte[] data) {
		this.data = data;
	}
	public FileInputStream getFs() {
		return fs;
	}
	public void setFs(FileInputStream fs) {
		this.fs = fs;
	}
	public long getSize() {
		return size;
	}
	public void setSize(long size) {
		this.size = size;
	}
	
}
