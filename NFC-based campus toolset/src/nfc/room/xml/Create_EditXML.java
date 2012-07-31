package nfc.room.xml;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import nfc.main.view.MainView;
import nfc.room.datatype.DailyItem;
import nfc.room.datatype.Subject;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlSerializer;
import android.app.Activity;
import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.util.Xml;

public class Create_EditXML extends Activity {
	
	public static void CreateXmlFileManager(Context context, File file) {
		if (file.exists() || context.getFileStreamPath(file.getName()).exists())
			return;
		else {
			FileOutputStream fos = null;
			try {
				fos = new FileOutputStream(file);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			XmlSerializer xmlSerializer = Xml.newSerializer();
			// we set the FileOutputStream as output for the serializer, using
			// UTF-8 encoding
			try {
				xmlSerializer.setOutput(fos, "UTF-8");
				// Write <?xml declaration with encoding (if encoding not null)
				// and standalone flag (if standalone not null)
				xmlSerializer.startDocument(null, Boolean.valueOf(true));
				// set indentation option
				xmlSerializer
						.setFeature(
								"http://xmlpull.org/v1/doc/features.html#indent-output",
								true);
				// start a tag called "FileManager"
				xmlSerializer.startTag(null, "FileManager");
				xmlSerializer.endTag(null, "FileManager");
				xmlSerializer.endDocument(); // Dong lai file xml
				// write xml data into the FileOutputStream
				xmlSerializer.flush();
				fos.close(); // Ket thuc qua trinh ghi file vao SD Card
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	/*
	 * Lưu nội dung đối tượng dailyItem vào file xml;
	 * dailyItem = |-- Mon--|--Subject 1 = time + name
	 * 			   |		|--Subject n 
	 * 			   |--Tues--|--Subject n
	 * 			   |--Wed--	|--Subject n
	 * ............
	 */			   	 
	public static void dataToXML(Context context , DailyItem dailyItem, String filename){
		//File file =createInternalFile(context, filename+".xml",0);
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(new File("/data/data/"+"nfc.main"+"/files", filename+".xml"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		XmlSerializer xmlSerializer = Xml.newSerializer();
		// we set the FileOutputStream as output for the serializer, using
		// UTF-8 encoding
		try {
			xmlSerializer.setOutput(fos, "UTF-8");
			// Write <?xml declaration with encoding (if encoding not null)
			// and standalone flag (if standalone not null)
			xmlSerializer.startDocument(null, Boolean.valueOf(true));
			// set indentation option
			xmlSerializer
					.setFeature(
							"http://xmlpull.org/v1/doc/features.html#indent-output",
							true);
			// start a tag called "RoomSchedule"
			xmlSerializer.startTag(null, "RoomSchedule");
			xmlSerializer.startTag(null, "Monday");
			for(Subject subject : dailyItem.getMonday()){
				if(subject==null) break;
				xmlSerializer.startTag("","subject");
				xmlSerializer.attribute(null, "start", subject.getStartTime());
				xmlSerializer.attribute(null, "end", subject.getEndTime());
				xmlSerializer.text(subject.getName());				
				xmlSerializer.endTag("","subject");
			}
			xmlSerializer.endTag(null, "Monday");
			
			xmlSerializer.startTag(null, "Tuesday");
			for(Subject subject : dailyItem.getTuesday()){
				if(subject==null) break;
				xmlSerializer.startTag(null, "subject");
				xmlSerializer.attribute(null, "start", subject.getStartTime());
				xmlSerializer.attribute(null, "end", subject.getEndTime());
				xmlSerializer.text(subject.getName());				
				xmlSerializer.endTag(null,"subject");
			}
			xmlSerializer.endTag(null, "Tuesday");
			
			xmlSerializer.startTag(null, "Wednesday");
			for(Subject subject : dailyItem.getWednesday()){
				if(subject==null) break;
				xmlSerializer.startTag(null, "subject");
				xmlSerializer.attribute(null, "start", subject.getStartTime());
				xmlSerializer.attribute(null, "end", subject.getEndTime());
				xmlSerializer.text(subject.getName());				
				xmlSerializer.endTag(null, "subject");
			}
			xmlSerializer.endTag(null, "Wednesday");
			
			
			xmlSerializer.startTag(null, "Thursday");
			for(Subject subject : dailyItem.getThursday()){
				if(subject==null) break;
				xmlSerializer.startTag(null, "subject");
				xmlSerializer.attribute(null, "start", subject.getStartTime());
				xmlSerializer.attribute(null, "end", subject.getEndTime());
				xmlSerializer.text(subject.getName());				
				xmlSerializer.endTag(null, "subject");
			}
			xmlSerializer.endTag(null, "Thursday");
			
			xmlSerializer.startTag(null, "Friday");
			for(Subject subject : dailyItem.getFriday()){
				if(subject==null) break;
				xmlSerializer.startTag(null, "Subject");
				xmlSerializer.attribute(null, "start", subject.getStartTime());
				xmlSerializer.attribute(null, "end", subject.getEndTime());
				xmlSerializer.text(subject.getName());				
				xmlSerializer.endTag(null, "Subject");
			}
			xmlSerializer.endTag(null, "Friday");
			
			xmlSerializer.endTag(null, "RoomSchedule");
			xmlSerializer.endDocument(); // Dong lai file xml
			// write xml data into the FileOutputStream
			xmlSerializer.flush();
			fos.close(); // Ket thuc qua trinh ghi file vao SD Card
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.e("XML error", e.getMessage());
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * Thêm 1 tag vào trong file xml với nội dung là content
	 */
	public static boolean addTagXmlFile(Context context, File file, String content) {
		// Create instance of DocumentBuilderFactory
		CreateXmlFileManager(context, file);
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		// Get the DocumentBuilder
		try {
			DocumentBuilder docBuilder = factory.newDocumentBuilder();
			// Using existing XML Document
			Document doc = docBuilder.parse(file);
			// create the root element
			Element root = doc.getDocumentElement();
			// create child element having tagName=fileList
			Element childElement = doc.createElement("fileList");
			// Add the attribute to the child
			childElement.setAttribute("id", content.substring(0, 14));
			childElement.setTextContent(content.substring(18));
			// Adding number tag havind id attribute to root element
			root.appendChild(childElement);

			// set up a transformer
			TransformerFactory transfac = TransformerFactory.newInstance();
			Transformer trans = transfac.newTransformer();

			// create string from xml tree
			StringWriter sw = new StringWriter();
			StreamResult result = new StreamResult(sw);
			DOMSource source = new DOMSource(doc);
			trans.transform(source, result);
			String xmlString = sw.toString();

			OutputStream f0;
			byte buf[] = xmlString.getBytes();
			f0 = new FileOutputStream(file);
			for (int i = 0; i < buf.length; i++) {
				f0.write(buf[i]);
			}
			f0.close();
			buf = null;

		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * Xóa bỏ 1 tag khỏi file xml với nội dung tag cần xóa là content Tương ứng
	 * trong hàm sẽ xóa 1 file xml với nội dung là content >>> cần phải bóc tách
	 * được tên file từ content
	 * content chi can la 14byte dau la du !!
	 */
	public static boolean RemoveTagXmlFile(File file, String content) {
		// Create instance of DocumentBuilderFactory
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		// Get the DocumentBuilder
		try {
			DocumentBuilder docBuilder = factory.newDocumentBuilder();
			// Using existing XML Document
			Document doc = docBuilder.parse(file);
			// create the root element
			Element root = doc.getDocumentElement();
			
//			NodeList list = root.getChildNodes();
//			for(int i = 0; i<list.getLength(); i++){
//				
//			}
			Element childElement = doc.getElementById(content);
			// create child element having tagName=fileList
//			Element childElement = doc.createElement("fileList");
//			// Add the attribute to the child
//			childElement.setTextContent(content);
//			// Adding number tag havind id attribute to root element
			root.removeChild(childElement);
			// set up a transformer
			TransformerFactory transfac = TransformerFactory.newInstance();
			Transformer trans = transfac.newTransformer();

			// create string from xml tree
			StringWriter sw = new StringWriter();
			StreamResult result = new StreamResult(sw);
			DOMSource source = new DOMSource(doc);
			trans.transform(source, result);
			String xmlString = sw.toString();

			OutputStream f0;
			byte buf[] = xmlString.getBytes();
			f0 = new FileOutputStream(file);
			for (int i = 0; i < buf.length; i++) {
				f0.write(buf[i]);
			}
			f0.close();
			buf = null;

		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
	/*
	 * 
	 */
	public static boolean RemoveXmlFile(Context context, File file){
		// Save NFC message to xml file >> Data file
		boolean privateMode = true;
		boolean done = false;
		if(!privateMode){
			String state = Environment.getExternalStorageState();
			if (Environment.MEDIA_MOUNTED.equals(state)) {
				// Check ManagerFile is avaliable
				done = file.delete();
			} else Log.d("Mount SD", "is not done");
		} else{
			done = context.deleteFile(file.getName());
		}
		if(done) Log.d("Delete File", "is OK");
		else Log.d("Delete File", "is not done");
		
		return true;
	}
	
	public static File createInternalFile(Context context,String fileName, int mode){
		File file = null;
		FileOutputStream fos;
		try {
			fos = context.openFileOutput(fileName, mode);
	        fos.close();
	        file = new File("/data/data/"+context.getPackageName()+"/files", fileName);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return file;
	}
	
	public static String saveTagToFile(Context context, DailyItem data, String title ) {
		String filename = getDateTime();
		dataToXML(context, data, filename);
		addTagXmlFile(context,new File("/data/data/nfc.main/files", "FileManager.xml"), filename+".xml"+ title);
		return filename;
	}
	
	public static void deleteFile(Context context, String fileName ) {
		Create_EditXML.RemoveXmlFile(context, new File("/data/data/nfc.main/files"+fileName+".xml"));
		Create_EditXML.RemoveTagXmlFile(new File("/data/data/nfc.main/files", "FileManager.xml"), fileName);
	}
	
	public static String getDateTime() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = new Date();
		return dateFormat.format(date);
	}
	
	public static String praseDateTime(String dateTime){
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = null;
		try {
			date = dateFormat.parse(dateTime);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		dateFormat = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss");
		return dateFormat.format(date);
	}
	
	public static String toFileName(String dateTime) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss");
		Date date = null;
		try {
			date = dateFormat.parse(dateTime);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		return dateFormat.format(date);
	}
}
