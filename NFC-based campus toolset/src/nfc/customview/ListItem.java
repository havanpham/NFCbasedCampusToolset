package nfc.customview;

import android.widget.CheckBox;

public class ListItem {
	private int imageURL;
	private String main_text;
	private String sub_text;
	private boolean isChecked;
	private Long event_id;
	
	public ListItem (int imageURL, String main_text,String sub_text) {
		this.imageURL=imageURL;
		this.main_text=main_text;
		this.sub_text = sub_text;
	}
	public ListItem (int imageURL, String place, String title, Long event_id) {
		this.imageURL=imageURL;
		this.main_text=place;
		this.sub_text = title;
		this.event_id = event_id;
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

}
