package nfc.lib;

import java.io.Serializable;

public class LibItem implements Serializable {
	private int x;
	private int y;
	private int length;
	private int width;
	private int id;
	
	public LibItem(int x, int y, int length, int width, int id) {
		// TODO Auto-generated constructor stub
		this.x=x;
		this.y=y;
		this.length= length;
		this.width=width;
		this.id = id;
	}
	
	public int getX(){
		return x;		
	}
	
	public int getY() {
		return y;
	}
	
	public int getLength() {
		return length;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getId() {
		return id;
	}
}
