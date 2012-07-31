package nfc.room.calendar;

import java.text.Format;
import java.util.ArrayList;

import nfc.customview.ListItem;
import nfc.customview.ListWorkAdapter;
import nfc.main.R;

import android.app.Activity;
import android.app.Dialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.provider.CalendarContract.Events;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class WorkBusy extends Activity implements OnClickListener {
	ArrayList<ListItem> array;
	ListWorkAdapter arrayAdapter;
	TextView txtHour;
	String valueExtras;
	int noon = 12;
	StringBuilder valueExtras1 = new StringBuilder();
	StringBuilder valueExtras2 = new StringBuilder();
	CheckBox checkBox;
	
	int nAc;
	final int Edit = 1;
	final int Delete = 0;
	final Context context = this;
	private Cursor mCursor = null;
	private static final String[] COLS = new String[] { CalendarContract.Events.EVENT_LOCATION, CalendarContract.Events.TITLE, CalendarContract.Events.DTSTART, CalendarContract.Events._ID};
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.work_busy_item);
		
		checkBox = (CheckBox)findViewById(R.id.check_work);
		
		mCursor = getContentResolver().query(CalendarContract.Events.CONTENT_URI, COLS, null, null, null);
        mCursor.moveToFirst();
		
//////////Get button and handle event//////////////////////////////////////////////////////////////////////////
		//Button btnDelete = (Button) findViewById(R.id.btnDelete);
		//btnDelete.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.delete_icon, 0);
		//btnDelete.setOnClickListener(this);
  	
		Button btnOK = (Button) findViewById(R.id.btnOK);
		btnOK.setCompoundDrawablesWithIntrinsicBounds(0, 0 , R.drawable.ok_icon, 0);
		btnOK.setOnClickListener(this);
		
		txtHour = (TextView) findViewById(R.id.textView2);
		
		//lay bundle
		Bundle extras = this.getIntent().getExtras();
		//final String receiveExtras = extras.getString("Hour:");
		
		//lay gia tri
		if (extras != null) {
		    valueExtras = extras.getString("Hour:");
		    String[] hour = valueExtras.split("-");
		    String[] hour1 = hour[0].split(":");
		    String[] hour2 = hour[1].split(":");
		    
		    if((Integer.parseInt(hour1[0]) <= noon)){
		    	valueExtras1.append(hour1[0]+":"+hour1[1] + " AM");
		    }
		    else{
		    	//hour[0] = hour[0] +" PM";
		    	//Toast.makeText(Calendar.this, hour[0], Toast.LENGTH_SHORT).show();
		    	hour1[0] = String.valueOf(Integer.parseInt(hour1[0])-12);
		    	valueExtras1.append(hour1[0]+":"+hour1[1] + " PM");
		    }
		    if(Integer.parseInt(hour2[0]) <= noon){
		    	valueExtras2.append(hour2[0]+":"+hour2[1] + " AM");
		    }
		    else{
		    	hour2[0] = String.valueOf(Integer.parseInt(hour2[0])-12);
		    	valueExtras2.append(hour2[0]+":"+hour2[1] + " PM");
		    }
		}
		//Toast.makeText(WorkBusy.this, valueExtras1.toString(), Toast.LENGTH_SHORT).show();
		//Toast.makeText(Calendar.this, valueExtras2.toString(), Toast.LENGTH_SHORT).show();
		//Time t1 = Time.valueOf(valueExtras1.toString());
		//long l1 = t1.getTime();
		//Time t2 = Time.valueOf(valueExtras2.toString());
		//long l2 = t2.getTime();
		
		// /////////// Set list View////////////////////////////////////////////////
    	array = new ArrayList<ListItem>();
    	arrayAdapter = new ListWorkAdapter(this, R.layout.work_busy_item, array){

			/* (non-Javadoc)
			 * @see nfc.customview.ListWorkAdapter#getView(int, android.view.View, android.view.ViewGroup)
			 */
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				// TODO Auto-generated method stub
				Activity activity = (Activity) getContext();
				LayoutInflater inflater = activity.getLayoutInflater();
				
				View rowView = inflater.inflate(R.layout.list, null);
				ListItem pt = getItem(position);
				
				ImageView imageView = (ImageView) rowView.findViewById(R.id.imageView1);
				imageView.setImageResource(pt.getImage());
				
				final TextView 	textView1 = (TextView) rowView.findViewById(R.id.main_content);
				TextView textView2 = (TextView) rowView.findViewById(R.id.sub_content);
		       	textView1.setText(pt.getMainText());
		       	textView2.setText(pt.getSubText());
		       	
		    
//		       	textView1.setOnClickListener(new OnClickListener() {
//					
//					@Override
//					public void onClick(View arg0) {
//						// TODO Auto-generated method stub
//						Log.d("On Item Click", (String)textView1.getText());
//					}
//				});
		       		return rowView;
			}
    		
    	};

    	final ListView list = (ListView) findViewById(R.id.listView1);
    	list.setAdapter(arrayAdapter);
    	
    	list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View arg1,
					int position, long id) {
				
				Object o = list.getItemAtPosition(position);
            	final ListItem fullObject = (ListItem)o;
            	final Long event_id = fullObject.getID();
            	final Dialog dialog = new Dialog(context);
				dialog.setContentView(R.layout.dialog);
				dialog.setTitle("Let's choose: ");
				
				//TextView edit = (TextView) dialog.findViewById(R.id.edit);
				final RadioButton btn_edit = (RadioButton) dialog.findViewById(R.id.rbtn_edit);
				final RadioButton btn_delete = (RadioButton) dialog.findViewById(R.id.rbtn_delete);
				Button btn_ok = (Button)dialog.findViewById(R.id.btnok);
				Button btn_cancel = (Button)dialog.findViewById(R.id.btncancel);
				
				btn_edit.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						btn_edit.setChecked(true);
						btn_delete.setChecked(false);
						nAc= Edit;
					}
				});
				
				btn_delete.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						btn_delete.setChecked(true);
						btn_delete.setChecked(false);
						nAc = Delete;
					}
				});
				
				btn_ok.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						switch(nAc){
						case Edit:
							Uri uri = ContentUris.withAppendedId(Events.CONTENT_URI, event_id);
							Intent intent = new Intent(Intent.ACTION_EDIT).setData(uri);
							startActivity(intent);
							uri = null;
							dialog.dismiss();
							break;
						case Delete:
							Uri deleteUri = null;
							deleteUri = ContentUris.withAppendedId(Events.CONTENT_URI, event_id);
							getContentResolver().delete(deleteUri, null, null);
							Toast.makeText(WorkBusy.this, "Deleted calendar at: " + deleteUri.toString(), Toast.LENGTH_SHORT).show();
				            deleteUri = null;
				            dialog.dismiss();
							break;
						}
					}
				});
				
				btn_cancel.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						dialog.dismiss();
					}
				});
				dialog.show();
			}});
    	
    	Format df = android.text.format.DateFormat.getDateFormat(this);
		Format tf = android.text.format.DateFormat.getTimeFormat(this);
    	//Toast.makeText(Calendar.this, mCursor.getLong(2), Toast.LENGTH_SHORT)
		
		//String v1 = tf.format(valueExtras1.toString());
		//String v2 = tf.format(valueExtras2.toString());
		
    	//set data in list view//
    	txtHour.setText("Hour: " + valueExtras);
    	arrayAdapter.clear();
    	while (mCursor.moveToNext()){
    	if((valueExtras1.toString().compareTo(tf.format(mCursor.getLong(2)).toString()) <= 0) 
    			&& 
    			(valueExtras2.toString().compareTo(tf.format(mCursor.getLong(2)).toString())) >=0)
    	{
    		arrayAdapter.add(new ListItem(R.drawable.yammi_icon, mCursor.getString(1), mCursor.getString(0) +"-" +df.format(mCursor.getLong(2)), mCursor.getLong(3)));
    	}

    	}
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		action(arg0.getId());
	}

	private void action(int id) {
		// TODO Auto-generated method stub
		switch(id){
		case R.id.btnOK:
			
			finish();
			break;
		}
	}

	/*private void deleteCheckwork() {
		// TODO Auto-generated method stub
		
		  
		if (array.size() > 0) {
            for (int i = array.size() - 1; i >= 0; i--) {
                if (array.get(i).isChecked()) {
                    array.remove(i);
                    arrayAdapter.notifyDataSetChanged();
                    continue;
                }
            }
        }*/
		
		
	}

