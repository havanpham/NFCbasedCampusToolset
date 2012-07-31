package nfc.room.datatype;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;


public class DailyItem implements Serializable {
	private ArrayList<Subject> Monday;
	private ArrayList<Subject> Tuesday;
	private ArrayList<Subject> Wednesday;
	private ArrayList<Subject> Thursday;
	private ArrayList<Subject> Friday;
	
	public void setMonday (ArrayList<Subject> array){
		this.Monday = array;
	}
	
	public ArrayList<Subject> getMonday (){
		return Monday;		
	}
	
	public void setTuesday (ArrayList<Subject> array){
		this.Tuesday = array;
	}
	
	public ArrayList<Subject> getTuesday(){
		return Tuesday;		
	}
	
	public void setWednesday (ArrayList<Subject> array){
		this.Wednesday = array;
	}
	
	public ArrayList<Subject> getWednesday (){
		return Wednesday;		
	}
	
	public void setThursday (ArrayList<Subject> array){
		this.Thursday = array;
	}
	
	public ArrayList<Subject> getThursday (){
		return Thursday;		
	}
	
	public void setFriday (ArrayList<Subject> array){
		this.Friday = array;
	}
	
	public ArrayList<Subject> getFriday(){
		return Friday;		
	}
	
	Date[] t = new Date[12];
	{
		t[0] = new Date(0, 0, 0, 6, 45);
		t[1] = new Date(0, 0, 0, 7, 35);
		t[2] = new Date(0, 0, 0, 8, 30);
		t[3] = new Date(0, 0, 0, 9, 20);
		t[4] = new Date(0, 0, 0, 10, 5);
		t[5] = new Date(0, 0, 0, 6, 45);
		
		t[6] = new Date(0, 0, 0, 6, 45);
		t[7] = new Date(0, 0, 0, 6, 45);
		t[8] = new Date(0, 0, 0, 6, 45);
		t[9] = new Date(0, 0, 0, 6, 45);
		t[10] = new Date(0, 0, 0, 6, 45);
		t[11] = new Date(0, 0, 0, 6, 45);
	}
	
}
