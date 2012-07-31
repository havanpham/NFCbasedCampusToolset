package nfc.room.calendar;

import java.util.GregorianCalendar;

import nfc.lib.LibMapActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.provider.CalendarContract;
import android.provider.CalendarContract.Calendars;
import android.provider.CalendarContract.Events;

public class Calendar extends Activity {
	private static final String[] EVENT_PROJECTION = new String[] {
	    Calendars._ID,                           // 0
	    Calendars.ACCOUNT_NAME,                  // 1
	    Calendars.CALENDAR_DISPLAY_NAME,         // 2
	    Calendars.OWNER_ACCOUNT                  // 3
				
		};
	public static void Start(Context context){
		Intent calIntent = new Intent(Intent.ACTION_INSERT);
		calIntent.setData(CalendarContract.Events.CONTENT_URI);
		context.startActivity(calIntent);

		Intent intent = new Intent(Intent.ACTION_INSERT);
		intent.setType("vnd.android.cursor.item/event");
		intent.putExtra(Events.TITLE, "Learn Android");
		intent.putExtra(Events.EVENT_LOCATION, "Home suit home");
		intent.putExtra(Events.DESCRIPTION, "Download Examples");

		// Setting dates
		GregorianCalendar calDate = new GregorianCalendar(2012, 10, 02);
		intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME,
				calDate.getTimeInMillis());
		intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME,
				calDate.getTimeInMillis());

		// Make it a full day event
		intent.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, true);

		// Make it a recurring Event
		intent.putExtra(Events.RRULE,
				"FREQ=WEEKLY;COUNT=11;WKST=SU;BYDAY=TU,TH");

		// Making it private and shown as busy
		intent.putExtra(Events.ACCESS_LEVEL, Events.ACCESS_PRIVATE);
		intent.putExtra(Events.AVAILABILITY, Events.AVAILABILITY_BUSY);

		context.startActivity(intent);
	}
}
