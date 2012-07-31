package nfc.room.xml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import nfc.customview.ListItem;
import nfc.main.R;
import nfc.room.datatype.DailyItem;
import nfc.room.datatype.Subject;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.R.bool;
import android.content.Context;
import android.util.Log;
import android.util.Xml;

public class ParseXML {
	public static ArrayList<ListItem> getFileList(Context context, String fileName) {
		ArrayList<ListItem> list = null;
		XmlPullParser parser = Xml.newPullParser();
		File file = new File("/data/data/"+"nfc.main"+"/files", fileName + ".xml");
		try {
			FileInputStream fis = new FileInputStream(file);
			parser.setInput(fis, null);
			int eventType = parser.getEventType();
			boolean done = true;
			while (eventType!=XmlPullParser.END_DOCUMENT) {
				switch (eventType) {
				case XmlPullParser.START_DOCUMENT:
					list = new ArrayList<ListItem>();
					break;
				case XmlPullParser.START_TAG:
					
					if(parser.getName().equals("fileList")){
						String date = Create_EditXML.praseDateTime(parser.getAttributeValue(null, "id"));
						String name = parser.nextText();
						list.add(new ListItem(R.drawable.tag_icon ,name ,date ));
						
					}
				case XmlPullParser.END_TAG:
					if (parser.getName().equals("FileManager")) {
						done = false;
					}
				default:
					break;
				}
				
				eventType = parser.next();
			}
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;		
	}
	
	public static DailyItem XMLToDailyItem(Context context, String fileName) {
		DailyItem roomSchedule = null;
		ArrayList<Subject> monday= null;
		ArrayList<Subject> tuesday= null;
		ArrayList<Subject> wednesday= null;
		ArrayList<Subject> thursday= null ;
		ArrayList<Subject> friday= null;
		XmlPullParser parser = Xml.newPullParser();
		File file = new File("/data/data/"+"nfc.main/files", fileName + ".xml");
		try {
			FileInputStream fis = new FileInputStream(file);
			
			parser.setInput(fis, null);
			int eventType = parser.getEventType();
			boolean mon =  false;
			boolean tue =  false;
			boolean wed =  false;
			boolean thu =  false;
			boolean fri = false;
			while (eventType!=XmlPullParser.END_DOCUMENT) {
				switch (eventType) {
				case XmlPullParser.START_DOCUMENT:
					roomSchedule = new DailyItem();
					
					break;
				case XmlPullParser.START_TAG:
					String name = parser.getName();					
					if(name.equals("Monday")){
						monday = new ArrayList<Subject>();
						mon= true;
					}else if (name.equals("Tuesday")){
						tuesday = new ArrayList<Subject>();
						tue= true;
					}else if (name.equals("Wednesday")){
						wednesday = new ArrayList<Subject>();
						wed= true;
					}else if (name.equals("Thursday")){
						thursday = new ArrayList<Subject>();
						thu= true;
					}else if (name.equals("Friday")){
						friday = new ArrayList<Subject>();
						fri= true;
					}else if(name.equals("subject")){
						String start = parser.getAttributeValue(null, "start");
						String end = parser.getAttributeValue(null, "end");
						String subname = parser.nextText();
						Subject sub = new Subject(start, end, subname);
						if(mon) monday.add(sub);
						if(tue) tuesday.add(sub);
						if(wed) wednesday.add(sub);
						if(thu) thursday.add(sub);
						if(fri) friday.add(sub);
					}
				
					break;
					
				case XmlPullParser.END_TAG :
					String endname = parser.getName();
					if(endname.equals("Monday")){
						roomSchedule.setMonday(monday);
						mon= false;
					}else if (endname.equals("Tuesday")){
						roomSchedule.setTuesday(tuesday);
						tue= false;
					}else if (endname.equals("Wednesday")){
						roomSchedule.setWednesday(wednesday) ;
						wed= false;
					}else if (endname.equals("Thursday")){
						roomSchedule.setThursday(thursday);
						thu= false;
					}else if (endname.equals("Friday")){
						roomSchedule.setFriday(friday);
						fri= false;
					}
					
					break;
				default:
					break;
				}
				eventType = parser.next();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return roomSchedule;		
	}
}
