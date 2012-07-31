package nfc.lib;

import java.util.ArrayList;

import nfc.main.R;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.AutoCompleteTextView;

public class LibMapActivity extends Activity {

	
	AutoCompleteTextView txtSearch;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lib_map);
		txtSearch = (AutoCompleteTextView)findViewById(R.id.actxt);
		txtSearch.setVisibility(txtSearch.GONE);
		
		Bundle data = this.getIntent().getBundleExtra("data");
		ArrayList<LibItem> map = (ArrayList<LibItem>) data.getSerializable("data");
		System.out.println(map.get(0).getX());
		System.out.println("Drawing map activity");
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub		
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.optionmenu, menu);		
		return true;		
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		txtSearch.setVisibility(txtSearch.VISIBLE);
		return true;
	}

	
	
}
