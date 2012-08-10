package nfc.customview;

import android.widget.CheckBox;

public class ListItem {
	private int imageURL;
	private String main_text;
	private String sub_text;
	private boolean isChecked;
	private Long event_id;
	private Long startTime;
	private Long endTime;
	public ListItem (int imageURL, String main_text,String sub_text) {
		this.imageURL=imageURL;
		this.main_text=main_text;
		this.sub_text = sub_text;
	}
	public ListItem (int imageURL, String place, String title, Long event_id, Long startTime, Long endTime) {
		this.imageURL=imageURL;
		this.main_text=place;
		this.sub_text = title;
		this.event_id = event_id;
		this.startTime = startTime;
		this.endTime = endTime;
	}
	
	public int getImage() {
		return imageURL;	
	}
	public String getMainText() {
		return main_text;
	}

	public String getSubText(){
		return sub_text;
	}
	
	public void setChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}

	public boolean isChecked() {
		return isChecked;
	}

	public Long getID(){
		return event_id;
	}
	
	public Long getStart(){
		return startTime;
	}
	
	public Long getEnd(){
		return endTime;
	}

}
