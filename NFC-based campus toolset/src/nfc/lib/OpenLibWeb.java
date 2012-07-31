package nfc.lib;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.provider.Settings;

public class OpenLibWeb extends Activity {
	String mySsid = "";
	String url;
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();
		mySsid = intent.getStringExtra("ssid");
		url = intent.getStringExtra("url");
		
		
	}
	private boolean isConnectedtoMynetwork (Context context){
		if (isConnected(context)){		       
        	WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        	WifiInfo wifiInfo = wifiManager.getConnectionInfo();        	
    	    String ssid = wifiInfo.getSSID();
    	    if (ssid.equals(mySsid)) return true;
    	    else return false;
    	    }               
		return false;		
	}
	private boolean isConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager)
            context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = null;
        if (connectivityManager != null) {
            networkInfo =
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
       
        }
        return networkInfo == null ? false : networkInfo.isConnected();
    }
	
	private void showdialog(){
		
	}
}
