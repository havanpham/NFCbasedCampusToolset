package nfc.room.calendar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.provider.CalendarContract.Events;
import android.widget.Toast;

public class Calendar extends Activity {

	private String valueExtrasRoom;
	private String valueExtrasHour;
	private String[] hour1;
	private String[] hour2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Bundle extras = this.getIntent().getExtras();
		if (extras != null) {
			valueExtrasRoom = extras.getString("Room");
			valueExtrasHour = extras.getString("Hour");

			String[] hour = valueExtrasHour.split("-");
			Toast.makeText(this, hour[0], Toast.LENGTH_SHORT).show();
			hour1 = hour[0].split(":");
			hour2 = hour[1].split(":");

		}

		java.util.Calendar begin = java.util.Calendar.getInstance();
		java.util.Calendar end = java.util.Calendar.getInstance();

		begin.set(java.util.Calendar.HOUR_OF_DAY, Integer.valueOf(hour1[0]));
		begin.set(java.util.Calendar.MINUTE, Integer.valueOf(hour1[1]));

		if (Integer.valueOf(hour1[0]) <= 12) {
			begin.get(java.util.Calendar.AM);

		} else {
			begin.get(java.util.Calendar.PM);
		}

		end.set(java.util.Calendar.HOUR_OF_DAY, Integer.valueOf(hour2[0]));
		end.set(java.util.Calendar.MINUTE, Integer.valueOf(hour2[1]));

		if (Integer.valueOf(hour2[0]) <= 12) {
			end.get(java.util.Calendar.AM);

		} else {
			end.get(java.util.Calendar.PM);
		}

		Intent calIntent = new Intent(Intent.ACTION_INSERT);
		calIntent.setData(CalendarContract.Events.CONTENT_URI);
		calIntent.putExtra(Events.EVENT_LOCATION, valueExtrasRoom);
		calIntent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME,
				begin.getTimeInMillis());
		calIntent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME,
				end.getTimeInMillis());
		startActivity(calIntent);
		finish();
	}
}