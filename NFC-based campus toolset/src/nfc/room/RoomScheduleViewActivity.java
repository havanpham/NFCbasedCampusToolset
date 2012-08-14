package nfc.room;

import java.util.ArrayList;
import java.util.Calendar;

import nfc.customview.ListItem;
import nfc.customview.ListWorkAdapter;
import nfc.main.R;
import nfc.room.calendar.WorkBusy;
import nfc.room.datatype.DailyItem;
import nfc.room.datatype.Subject;
import nfc.room.xml.Create_EditXML;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class RoomScheduleViewActivity extends Activity implements
		OnClickListener {

	int nextActivity;
	final int TAKE_NOTE = 0;
	final int NOTE_MANAGER = 1;

	ArrayList<ListItem> array;
	ListWorkAdapter arrayAdapter;
	ArrayList<Subject> mon;
	ArrayList<Subject> tue;
	ArrayList<Subject> wed;
	ArrayList<Subject> thurs;
	ArrayList<Subject> fri;
	TextView txtRoom;
	String title;

	String fileName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_item);

		// //////// Get data from other
		// activity/////////////////////////////////////////////
		Intent intent = getIntent();

		fileName = intent.getStringExtra("FileName");

		Bundle bun = intent.getBundleExtra("data");
		DailyItem daily = (DailyItem) bun.getSerializable("data");

		mon = null;
		mon = daily.getMonday();
		tue = null;
		tue = daily.getTuesday();
		wed = null;
		wed = daily.getWednesday();
		thurs = null;
		thurs = daily.getThursday();
		fri = null;
		fri = daily.getFriday();

		String roomName = intent.getStringExtra("Room Name");

		txtRoom = (TextView) findViewById(R.id.textView2);
		txtRoom.setText((String) roomName);
		title = (String) roomName;

		// //////// Get button and handle
		// event//////////////////////////////////////////////////////////////////////////
		Button btn1 = (Button) findViewById(R.id.btn1);
		btn1.setOnClickListener(this);

		Button btn2 = (Button) findViewById(R.id.btn2);
		btn2.setOnClickListener(this);

		Button btn3 = (Button) findViewById(R.id.btn3);
		btn3.setOnClickListener(this);

		Button btn4 = (Button) findViewById(R.id.btn4);
		btn4.setOnClickListener(this);

		Button btn5 = (Button) findViewById(R.id.btn5);
		btn5.setOnClickListener(this);

		// /////////// Set list
		// View////////////////////////////////////////////////
		array = new ArrayList<ListItem>();
		arrayAdapter = new ListWorkAdapter(this, R.layout.list_item, array) {

			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				// TODO Auto-generated method stub
				Activity activity = (Activity) getContext();
				LayoutInflater inflater = activity.getLayoutInflater();

				View rowView = inflater.inflate(R.layout.list, null);
				ListItem pt = getItem(position);

				ImageView imageView = (ImageView) rowView
						.findViewById(R.id.imageView1);
				imageView.setImageResource(pt.getImage());

				final TextView textView1 = (TextView) rowView
						.findViewById(R.id.main_content);
				TextView textView2 = (TextView) rowView
						.findViewById(R.id.sub_content);
				textView1.setText(pt.getMainText());
				textView2.setText(pt.getSubText());
				return rowView;
			}
		}; // (this, R.layout.list_item, array);

		final ListView list = (ListView) findViewById(R.id.listView1);
		list.setAdapter(arrayAdapter);

		// ///////////// Set first input data for list
		// view//////////////////////////////////////////////////////
		Calendar calendar = Calendar.getInstance();
		int day = calendar.get(Calendar.DAY_OF_WEEK);
		switch (day) {
		case 2:
			day = R.id.btn1;
			break;
		case 3:
			day = R.id.btn2;
			break;
		case 4:
			day = R.id.btn3;
			break;
		case 5:
			day = R.id.btn4;
			break;
		case 6:
			day = R.id.btn5;
			break;
		default:
			day = R.id.btn1;
			break;
		}
		setSubjectList(day);

		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View arg1,
					int position, long id) {
				// TODO Auto-generated method stub
				ListItem item = array.get(position);
				String date = item.getMainText();
				String input_date = date.substring(0, 2) + ":"
						+ date.substring(2, 7) + ":" + date.substring(7);
				Log.d("in put date", input_date);
				// nfc.room.calendar.Calendar.Start(RoomScheduleViewActivity.this);
				showDialog(input_date);
			}
		});
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		setSubjectList(arg0.getId());
	}

	private void setSubjectList(int day) {
		switch (day) {
		case R.id.btn1:
			txtRoom.setText(title + " - Monday");
			arrayAdapter.clear();
			for (int i = 0; i < mon.size(); i++) {
				arrayAdapter.add(new ListItem(mon.get(i).getName()
						.equals("PhongTrong") ? R.drawable.flower_ice_icon
						: R.drawable.flower_fire_icon, mon.get(i)
						.getStartTime() + "-" + mon.get(i).getEndTime(), mon
						.get(i).getName()));
			}
			break;
		case R.id.btn2:
			txtRoom.setText(title + " - Tuesday");
			arrayAdapter.clear();
			for (int i = 0; i < tue.size(); i++) {
				arrayAdapter.add(new ListItem(tue.get(i).getName()
						.equals("PhongTrong") ? R.drawable.flower_ice_icon
						: R.drawable.flower_fire_icon, tue.get(i)
						.getStartTime() + "-" + tue.get(i).getEndTime(), tue
						.get(i).getName()));
			}
			break;
		case R.id.btn3:
			txtRoom.setText(title + " - Wednesday");
			arrayAdapter.clear();
			for (int i = 0; i < wed.size(); i++) {
				arrayAdapter.add(new ListItem(wed.get(i).getName()
						.equals("PhongTrong") ? R.drawable.flower_ice_icon
						: R.drawable.flower_fire_icon, wed.get(i)
						.getStartTime() + "-" + wed.get(i).getEndTime(), wed
						.get(i).getName()));
			}
			break;
		case R.id.btn4:
			txtRoom.setText(title + " - Thursday");
			arrayAdapter.clear();
			for (int i = 0; i < thurs.size(); i++) {
				arrayAdapter.add(new ListItem(thurs.get(i).getName()
						.equals("PhongTrong") ? R.drawable.flower_ice_icon
						: R.drawable.flower_fire_icon, thurs.get(i)
						.getStartTime() + "-" + thurs.get(i).getEndTime(),
						thurs.get(i).getName()));
			}
			break;
		case R.id.btn5:
			txtRoom.setText(title + " - Friday");
			arrayAdapter.clear();
			for (int i = 0; i < fri.size(); i++) {
				arrayAdapter.add(new ListItem(fri.get(i).getName()
						.equals("PhongTrong") ? R.drawable.flower_ice_icon
						: R.drawable.flower_fire_icon, fri.get(i)
						.getStartTime() + "-" + fri.get(i).getEndTime(), fri
						.get(i).getName()));
			}
			break;

		default:
			break;
		}
	}

	private void showDialog(final String date) {
		final Dialog dialog = new Dialog(RoomScheduleViewActivity.this);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.dialog_calendar);

		TextView item_take_note = (TextView) dialog
				.findViewById(R.id.Item_take_note);
		TextView item_note_manager = (TextView) dialog
				.findViewById(R.id.Item_note_manager);
		final RadioButton rbtn_take_note = (RadioButton) dialog
				.findViewById(R.id.rbtn_take_note);
		final RadioButton rbtn_note_manager = (RadioButton) dialog
				.findViewById(R.id.rbtn_note_manager);

		OnClickListener checked_take_note = new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				rbtn_take_note.setChecked(true);
				if (rbtn_note_manager.isChecked())
					rbtn_note_manager.setChecked(false);
				nextActivity = TAKE_NOTE;
			}
		};

		OnClickListener checked_note_manager = new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				rbtn_note_manager.setChecked(true);
				if (rbtn_take_note.isChecked())
					rbtn_take_note.setChecked(false);
				nextActivity = NOTE_MANAGER;
			}
		};
		item_take_note.setOnClickListener(checked_take_note);
		item_note_manager.setOnClickListener(checked_note_manager);
		rbtn_take_note.setOnClickListener(checked_take_note);
		rbtn_note_manager.setOnClickListener(checked_note_manager);

		Button btn_ok = (Button) dialog.findViewById(R.id.btnok);
		Button btn_cancel = (Button) dialog.findViewById(R.id.btncancel);

		btn_ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO intent to next activity
				switch (nextActivity) {
				case TAKE_NOTE:
					// nfc.room.calendar.Calendar.Start(RoomScheduleViewActivity.this);
					Intent intent = new Intent(RoomScheduleViewActivity.this,
							nfc.room.calendar.Calendar.class);
					Bundle extras1 = new Bundle();
					extras1.putString("Room", title);
					extras1.putString("Hour", date);
					intent.putExtras(extras1);
					startActivity(intent);
					dialog.dismiss();
					break;

				case NOTE_MANAGER:
					Intent i = new Intent(RoomScheduleViewActivity.this,
							WorkBusy.class);
					Bundle extras = new Bundle();
					extras.putString("Hour:", date);
					i.putExtras(extras);
					startActivity(i);
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
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.optionmenu_share, menu);
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onMenuItemSelected(int, android.view.MenuItem)
	 */
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getOrder()) {
		case 0:
			Create_EditXML.deleteFile(RoomScheduleViewActivity.this, fileName);
			Intent i_menu = new Intent(RoomScheduleViewActivity.this,
					RoomMenuActivity.class);
			startActivity(i_menu);
			break;
		case 1:
			Intent i_manager = new Intent(RoomScheduleViewActivity.this,
					RoomManagerActivity.class);
			startActivity(i_manager);
			break;

		default:
			break;
		}
		return true;

	}

}