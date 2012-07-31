package nfc.customview;

import java.util.List;

import nfc.main.R;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public abstract class ListWorkAdapter extends ArrayAdapter<ListItem> {
	
	@Override
	public abstract View getView(int position, View convertView, ViewGroup parent);
	
	

	public ListWorkAdapter(Context context, int resource,
			List<ListItem> objects) {
		super(context, resource, objects);
		// TODO Auto-generated constructor stub
		
	}
	

}
