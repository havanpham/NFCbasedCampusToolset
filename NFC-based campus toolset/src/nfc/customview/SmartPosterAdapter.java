package nfc.customview;

import java.util.ArrayList;

import nfc.main.R;


import com.android.nfcRead.record.SmartPoster;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class SmartPosterAdapter extends ArrayAdapter<SmartPoster> {
	 ArrayList<SmartPoster> array;
	    int resource;
	    Context context;
	    
	    public SmartPosterAdapter(Context context, int textViewResourceId,
	            ArrayList<SmartPoster> objects) {
	        super(context, textViewResourceId, objects);
	        this.context = context;
	        resource = textViewResourceId;
	        array = objects;        
	    }    

	    //PhÆ°Æ¡ng thá»©c xÃ¡c Ä‘á»‹nh View mÃ  Adapter hiá»ƒn thá»‹, á»Ÿ Ä‘Ã¢y chÃ­nh lÃ  CustomViewGroup
	    //Báº¯t buá»™c pháº£i Override khi káº¿ thá»«a tá»« ArrayAdapter
	    @Override
	    public View getView(int position, View convertView, ViewGroup parent) {
	    	Activity activity = (Activity) getContext();
			LayoutInflater inflater = activity.getLayoutInflater();
			
			View rowView = inflater.inflate(R.layout.list_single, null);
			final SmartPoster pt = getItem(position);
			
			ImageView imageView = (ImageView) rowView.findViewById(R.id.imageView1);			
			
			final TextView 	mainContent = (TextView) rowView.findViewById(R.id.main_content);
			 // Lấy về đối tượng smartPoster hiện tại
	        final SmartPoster sp = array.get(position);

	        if (sp != null) {	            
	            // Ở đây sẽ cho thông tin về ảnh tương ứng với nội dung của dữ liệu đọc được
	            if(!sp.getUriRecord().getUri().toString().equals("unknown")) {
	            	mainContent.setText(sp.getUriRecord().getUri().toString());
	            	
	            	Log.d("URI scheme:", sp.getUriRecord().getUri().getScheme());
	            	String scheme = sp.getUriRecord().getUri().getScheme();
	            	
	            	if(scheme.equals("http")) imageView.setImageResource(R.drawable.internet_icon);
	            	else if (scheme.equals("tel")) imageView.setImageResource(R.drawable.call_icon);
	            	else if (scheme.equals("mailto"))imageView.setImageResource(R.drawable.email_icon);
	            	
	            }
	            else {
	            	mainContent.setText(sp.getTitle().getText());
	            	imageView.setImageResource(R.drawable.text_icon);
	            }
	            //checkWork.setChecked(work.isChecked());
	            rowView.setOnClickListener(new OnClickListener() {
					
					public void onClick(View v) {
						// TODO Auto-generated method stub
						if(sp.getUriRecord().getUri().toString().equals("unknown")) return;
						Intent i= new Intent(Intent.ACTION_VIEW,sp.getUriRecord().getUri());
						getContext().startActivity(i);
					}
				});
	            

	        }        
	        return rowView;
	    }    

}
