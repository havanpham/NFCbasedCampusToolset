package nfc.main.view;

import java.io.File;
import java.util.ArrayList;

import nfc.customview.ListItem;
import nfc.main.R;
import nfc.main.R.style;
import nfc.room.RoomMenuActivity;
import nfc.room.xml.Create_EditXML;
import nfc.room.xml.ParseXML;

import com.android.nfcRead.TagViewerActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainView extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);        
       
       File file = new File("/data/data/"+this.getPackageName()+"/files", "FileManager.xml");
       System.out.println(this.getPackageName());
       Create_EditXML.CreateXmlFileManager(this, file);      
       
        Button btnlib = (Button)findViewById(R.id.btnlib);
        //btnlib.setCompoundDrawablesWithIntrinsicBounds(R.drawable.liblary_map, 0, 0, 0);
        Button btnroom = (Button)findViewById(R.id.btnroom);
        //btnroom.setCompoundDrawablesWithIntrinsicBounds(R.drawable.room_schedule, 0, 0, 0);   
        btnlib.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i = new Intent(getApplicationContext(), TagViewerActivity.class);
				MainView.this.startActivity(i);
			}
		});
        
        btnroom.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(MainView.this, RoomMenuActivity.class);
				//i.putExtra("data", b);
				MainView.this.startActivity(i);
			}
		});
        
    }
}