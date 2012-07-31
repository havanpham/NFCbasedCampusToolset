package nfc.room.datatype;

import java.io.Serializable;

public class Subject implements Serializable  {
	private String startTime;
	private String endTime;
	private String name;
	
	public Subject (String startTime, String endTime, String name){
		this.startTime = startTime;
		this.endTime = endTime;
		this.name= name;
	}
	
	public String getStartTime(){
		return startTime;
	}
	
	public String getEndTime(){
		return endTime;
	}
	
	public String getName(){
		return name;
	}
}
