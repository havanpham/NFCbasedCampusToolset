package nfc.room;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import nfc.customview.ListItem;
import nfc.customview.ListWorkAdapter;
import nfc.main.R;
import nfc.room.xml.Create_EditXML;
import nfc.room.xml.ParseXML;
import android.R.drawable;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.sax.StartElementListener;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.TextView;

public class RoomManagerActivity extends Activity {

	ArrayList<ListItem> array;
	ListWorkAdapter arrayAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_item);
		array = new ArrayList<ListItem>();
		arrayAdapter = new ListWorkAdapter(this, R.layout.list_item, array) {

			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				// TODO Auto-generated method stub
				Activity activity = (Activity) getContext();
				LayoutInflater inflater = activity.getLayoutInflater();

				View rowView = inflater.inflate(R.layout.list_has_check, null);
				final ListItem pt = getItem(position);

				ImageView imageView = (ImageView) rowView
						.findViewById(R.id.imageTag);
				imageView.setImageResource(pt.getImage());

				final TextView textView1 = (TextView) rowView
						.findViewById(R.id.main_content_tag);
				TextView textView2 = (TextView) rowView
						.findViewById(R.id.sub_content_tag);
				textView1.setText(pt.getMainText());
				textView2.setText(pt.getSubText());

				CheckBox checked = (CheckBox) rowView
						.findViewById(R.id.check_work);
				checked.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						// TODO Auto-generated method stub
						pt.setChecked(isChecked);
					}
				});

				rowView.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub

						String fileName = Create_EditXML.toFileName(pt
								.getSubText());

						Bundle sendBundle = new Bundle();
						sendBundle.putSerializable("data", ParseXML
								.XMLToDailyItem(RoomManagerActivity.this,
										fileName));

						Intent mIntent = new Intent(getContext(),
								RoomScheduleViewActivity.class);
						mIntent.putExtra("Room Name", pt.getMainText());
						mIntent.putExtra("FileName", fileName);
						mIntent.putExtra("data", sendBundle);
						getContext().startActivity(mIntent);
					}
				});

				return rowView;

			}
		};

		// //////// Change title of
		// view/////////////////////////////////////////////////////////////
		TextView mainTitle = (TextView) findViewById(R.id.textView1);
		TextView subTitle = (TextView) findViewById(R.id.textView2);
		mainTitle.setText("Room manager");
		subTitle.setText("NFC tag list");

		// /////// Set alpha's button is 0 <=>
		// invisible///////////////////////////////////////////////
		Button btn1 = (Button) findViewById(R.id.btn1);
		btn1.setAlpha(0);

		Button btn2 = (Button) findViewById(R.id.btn2);
		btn2.setAlpha(0);

		Button btn3 = (Button) findViewById(R.id.btn3);
		btn3.setAlpha(0);

		Button btn4 = (Button) findViewById(R.id.btn4);
		btn4.setAlpha(0);

		Button btn5 = (Button) findViewById(R.id.btn5);
		btn5.setAlpha(0);

		// /////// Set new
		// background////////////////////////////////////////////
		LinearLayout background = (LinearLayout) findViewById(R.id.background);
		background.setBackgroundResource(R.drawable.common_background);

		// ////// Put data to list View/////////////////////////////////////////
		array.clear();
		// array = ParseXML.getFileList(this, "FileManager");
		ArrayList<ListItem> mArray = ParseXML.getFileList(this, "FileManager");
		for (int i = mArray.size() - 1; i >= 0; i--) {
			array.add(mArray.get(i));
		}

		// ///// Using listView////////////////////////////////////////////////
		final ListView list = (ListView) findViewById(R.id.listView1);
		list.setAdapter(arrayAdapter);

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
			for (ListItem list_item : array) {
				if (list_item.isChecked())
					Create_EditXML.deleteFile(RoomManagerActivity.this,
							Create_EditXML.toFileName(list_item.getSubText()));
			}
			Intent i_fresh = new Intent(RoomManagerActivity.this, RoomManagerActivity.class);
			RoomManagerActivity.this.startActivity(i_fresh);
			break;
		case 1:
			break;

		default:
			break;
		}
		return true;
	}

}
