package nfc.room;

import com.android.nfcRead.TagViewerActivity;

import nfc.main.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class RoomMenuActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.room_menu);
		
		Button btnReadTag = (Button)findViewById(R.id.btnReadTag);
		Button btnManager = (Button)findViewById(R.id.btnManager);
		
		btnReadTag.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(RoomMenuActivity.this, TagViewerActivity.class);
				RoomMenuActivity.this.startActivity(i);
			}
		});
		
		btnManager.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i  = new Intent(RoomMenuActivity.this, RoomManagerActivity.class);
				startActivity(i);
			}
		});
	}

}
