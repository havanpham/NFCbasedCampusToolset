package nfc.lib;

import nfc.main.R;
import nfc.main.view.MainView;
import nfc.room.RoomScheduleViewActivity;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class OpenLibWeb extends Activity {
	String mySsid = "";
	String url;

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();
		mySsid = intent.getStringExtra("ssid");
		url = intent.getStringExtra("url");

		if (!isConnectedtoMynetwork(this)) {
			showdialog();
		} else {
			openUrl();
			finish();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onResume()
	 */
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (!isConnectedtoMynetwork(this)) {
			showdialog();
		} else {
			openUrl();
			finish();
		}
	}

	private boolean isConnectedtoMynetwork(Context context) {
		if (isConnected(context)) {
			WifiManager wifiManager = (WifiManager) context
					.getSystemService(Context.WIFI_SERVICE);
			WifiInfo wifiInfo = wifiManager.getConnectionInfo();
			String ssid = wifiInfo.getSSID();
			if (ssid.equals(mySsid))
				return true;
			else
				return false;
		}
		return false;
	}

	private boolean isConnected(Context context) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = null;
		if (connectivityManager != null) {
			networkInfo = connectivityManager
					.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

		}
		return networkInfo == null ? false : networkInfo.isConnected();
	}

	private void openUrl() {
		Intent i = new Intent(Intent.ACTION_VIEW);
		i.setData(Uri.parse(url));
		startActivity(i);
	}

	private void showdialog() {
		final Dialog dialog = new Dialog(OpenLibWeb.this);
		dialog.setContentView(R.layout.dialog_network);
		dialog.setTitle("Network");

		TextView content = (TextView) dialog.findViewById(R.id.txtnet);
		content.setText("Please join " + mySsid
				+ " network to use our application.");

		Button btnok = (Button) dialog.findViewById(R.id.btnnetok);
		Button btncancel = (Button) dialog.findViewById(R.id.btnnetcancel);

		btnok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				OpenLibWeb.this.startActivity(new Intent(
						Settings.ACTION_WIFI_SETTINGS));
			}
		});

		btncancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				OpenLibWeb.this.startActivity(new Intent(OpenLibWeb.this,
						MainView.class));
				dialog.dismiss();
				OpenLibWeb.this.finish();
			}
		});

		dialog.show();
	}
}
