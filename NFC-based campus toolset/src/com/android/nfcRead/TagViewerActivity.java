/*
 * Copyright 2011, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.nfcRead;

import android.app.Activity;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentFilter.MalformedMimeTypeException;
import android.net.Uri;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Window;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import nfc.lib.OpenLibWeb;
import nfc.main.R;
import nfc.room.RoomScheduleViewActivity;
import nfc.room.datatype.DailyItem;
import nfc.room.datatype.Subject;
import nfc.room.xml.Create_EditXML;

import com.android.nfcRead.record.SmartPoster;
import com.android.nfcRead.record.TextRecord;
import com.android.nfcRead.record.UriRecord;
import com.android.nfcRead.record.SmartPoster.RecommendedAction;

public class TagViewerActivity extends Activity {
	private static final String TAG = "TagViewer";
	boolean mResumed = false;
	private boolean mWriteMode = false;
	NfcAdapter mNfcAdapter;

	PendingIntent mNfcPendingIntent;
	IntentFilter[] mWriteTagFilters;
	IntentFilter[] mNdefExchangeFilters;

	ArrayList<SmartPoster> array;
	nfc.customview.SmartPosterAdapter arrayAdapter;

	String path = null;
	String currentTagContent = null;

	boolean active = false;

	// ID of special tag
	private final String ID_APP = "@#:-$";
	private boolean isLibMap = false;

	private String tmp;

	// Notification
	private NotificationManager mNotificationManager;
	private static final int DIALOG1_KEY = 0, DIALOG2_KEY = 1;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Remove title bar
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.tag_viewer_list);
		// setTitle("Waiting for NFC-Tag...");
		// path = "/data/data/" + getPackageName() + "/files";
		Intent intent = getIntent();
		String action = intent.getAction();
		NdefMessage[] msgs = null;
		if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)
				|| NfcAdapter.ACTION_TECH_DISCOVERED.equals(action)
				|| NfcAdapter.ACTION_TAG_DISCOVERED.equals(action)) {
			active = true;
			msgs = getNdefMessages(getIntent());
			Log.d("TagViewer", "Active Mode !");
			// Wait >>> doi 1 khoang thoi gian de luu tru ket noi
		}
		array = new ArrayList<SmartPoster>();
		arrayAdapter = new nfc.customview.SmartPosterAdapter(this,
				R.layout.list, array);

		final ListView list = (ListView) findViewById(R.id.list1);
		list.setAdapter(arrayAdapter);

		mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

		mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
		// Handle all of our received NFC intents in this activity.
		mNfcPendingIntent = PendingIntent.getActivity(this, 0, new Intent(this,
				getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);

		// Intent filters for reading a note from a tag or exchanging over p2p.
		IntentFilter ndefDetected = new IntentFilter(
				NfcAdapter.ACTION_NDEF_DISCOVERED);
		try {
			ndefDetected.addDataType("*/*");
		} catch (MalformedMimeTypeException e) {
		}
		mNdefExchangeFilters = new IntentFilter[] { ndefDetected };

		// Intent filters for writing to a tag
		// IntentFilter tagDetected = new
		// IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED);
		// mWriteTagFilters = new IntentFilter[] { tagDetected };
		showDialog(DIALOG2_KEY);

		if (active) {
			array.clear();
			buildTagViews(msgs);
			notificationFunc();
			dismissDialog(DIALOG2_KEY);
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		mResumed = true;
		// Sticky notes received from Android
		if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(getIntent().getAction())) {
			setIntent(new Intent()); // Consume this intent.
		}
		enableNdefExchangeMode();
	}

	@Override
	protected void onPause() {
		super.onPause();
		mResumed = false;
		mNfcAdapter.disableForegroundNdefPush(this);
	}

	@Override
	protected void onNewIntent(Intent intent) {
		// NDEF exchange mode
		if (!mWriteMode
				&& (NfcAdapter.ACTION_NDEF_DISCOVERED
						.equals(intent.getAction())
						|| NfcAdapter.ACTION_TECH_DISCOVERED.equals(intent
								.getAction()) || NfcAdapter.ACTION_TAG_DISCOVERED
							.equals(intent.getAction()))) {
			NdefMessage[] msgs = getNdefMessages(intent);
			setTitle(TAG + "/" + "NDEF exchange mode !!");

			buildTagViews(msgs);
			notificationFunc();
			dismissDialog(DIALOG2_KEY);
		}
	}

	NdefMessage[] getNdefMessages(Intent intent) {
		// Parse the intent
		NdefMessage[] msgs = null;
		Parcelable[] rawMsgs = intent
				.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
		if (rawMsgs != null) {
			msgs = new NdefMessage[rawMsgs.length];
			for (int i = 0; i < rawMsgs.length; i++) {
				msgs[i] = (NdefMessage) rawMsgs[i];
			}
		} else {
			// Unknown tag type
			byte[] empty = new byte[] {};
			NdefRecord record = new NdefRecord(NdefRecord.TNF_UNKNOWN, empty,
					empty, empty);
			NdefMessage msg = new NdefMessage(new NdefRecord[] { record });
			msgs = new NdefMessage[] { msg };
		}
		return msgs;
	}

	private void buildTagViews(NdefMessage[] msgs) {
		if (msgs == null || msgs.length == 0) {
			// Toast.makeText(this, "Count NDEF return" + msgs.length,
			// Toast.LENGTH_LONG).show();
			return;
		}
		// Create New null URI NdefRecord
		byte[] id = { 0x00 };
		byte[] payload = new StringBuffer(new String(id)).append("unknown")
				.toString().getBytes();
		NdefRecord nRecord = new NdefRecord(NdefRecord.TNF_WELL_KNOWN,
				NdefRecord.RTD_URI, id, payload);

		// Build views for all of the sub records
		System.out.println("cout NDEF " + msgs.length);
		NdefRecord[] records = msgs[0].getRecords();
		DailyItem weekSchedule = new DailyItem();
		String roomName = null;
		int count = 0;
		boolean isRomSchedule = false;

		if (records != null) {
			for (NdefRecord record : records) {
				if (UriRecord.isUri(record)) {
					Log.d("Uri Record:", "is URI record");
					if (isLibMap) {
						isLibMap = false;

						Intent intent_lib = new Intent(TagViewerActivity.this,
								OpenLibWeb.class);
						intent_lib.putExtra("ssid", tmp);
						Log.d("ssid", tmp);
						tmp = null;
						intent_lib.putExtra("url", UriRecord.parse(record)
								.getUri().toString());
						Log.d("url", UriRecord.parse(record).getUri()
								.toString());
						TagViewerActivity.this.startActivity(intent_lib);
						finish();
					} else {
						String str = UriRecord.typeOfUriRecord(record);
						arrayAdapter.add(new SmartPoster(UriRecord
								.parse(record), new TextRecord("en", str),
								RecommendedAction.DO_ACTION, "sp"));
						// elements.add(UriRecord.parse(record));
					}

				} else if (TextRecord.isText(record)) {
					Log.d("Text Record:", "is Text record");
					int lenth = ID_APP.length();
					if (TextRecord.parse(record).getText().length() > lenth
							&& TextRecord.parse(record).getText()
									.substring(0, lenth).equals(ID_APP)
							&& records.length == 2) {
						isLibMap = true;
						tmp = TextRecord.parse(record).getText()
								.substring(lenth);
						continue;
					}
					if (count == 0
							&& TextRecord.parse(record).getText().length() > lenth
							&& TextRecord.parse(record).getText()
									.substring(0, lenth).equals(ID_APP)
							&& records.length > 4) {
						isRomSchedule = true;
						roomName = TextRecord.parse(record).getText()
								.substring(lenth);
						count++;
					} else {
						if (!isRomSchedule) {
							Toast.makeText(this,
									"This is not a tag of room schedule",
									Toast.LENGTH_SHORT).show();
							arrayAdapter.add(new SmartPoster(UriRecord
									.parse(nRecord), new TextRecord("en",
									TextRecord.parse(record).getText()),
									RecommendedAction.UNKNOWN, "sp"));
						} else {
							ArrayList<Subject> array = new ArrayList<Subject>();
							String temp = TextRecord.parse(record).getText();
							String[] parts = temp.split("\\#");
							// for(String part : parts ){
							// Log.d("text Record", part);
							// }
							for (String part : parts) {
								Subject sub = new Subject(part.substring(0, 4),
										part.substring(4, 8), part.substring(8,
												part.length()));
								array.add(sub);
							}

							switch (count) {
							case 1:
								weekSchedule.setMonday(array);
								break;
							case 2:
								weekSchedule.setTuesday(array);
								break;
							case 3:
								weekSchedule.setWednesday(array);
								break;
							case 4:
								weekSchedule.setThursday(array);
								break;
							case 5:
								weekSchedule.setFriday(array);
								break;
							default:
								break;
							}

							if (count == 5) {
								String fileName = Create_EditXML.saveTagToFile(
										TagViewerActivity.this, weekSchedule,
										roomName);
								Bundle sendBundle = new Bundle();
								sendBundle
										.putSerializable("data", weekSchedule);
								Intent intent = new Intent(
										TagViewerActivity.this,
										RoomScheduleViewActivity.class);
								intent.putExtra("data", sendBundle);
								intent.putExtra("Room Name", roomName);
								intent.putExtra("FileName", fileName);
								TagViewerActivity.this.startActivity(intent);
								finish();
							}
							count++;
						}
					}

				} else if (SmartPoster.isPoster(record)) {
					Log.d("Smart Poster Record:", "is Smart Poster");

					int lenth = ID_APP.length();
					SmartPoster smartPoster = SmartPoster.parse(record);
					TextRecord textRecord = smartPoster.getTitle();

					if (textRecord != null) {
						Log.d("NFC", textRecord.getText().substring(lenth));
						Log.d("NFC", smartPoster.getUriRecord().toString());
						// if (textRecord.getText().substring(0,
						// lenth).equals(ID_APP)){
						// Intent webintent = new Intent(TagViewerActivity.this,
						// OpenLibWeb.class);
						// webintent.putExtra("ssid",textRecord.getText().substring(lenth)
						// );
						// webintent.putExtra("url",
						// smartPoster.getUriRecord().toString());
						// }

						arrayAdapter.add(new SmartPoster(UriRecord
								.parse(nRecord), new TextRecord("en",
								textRecord.getText()),
								RecommendedAction.UNKNOWN, "sp"));
					}

					arrayAdapter.add(SmartPoster.parse(record));
				} else {
					arrayAdapter.add(new SmartPoster(new UriRecord(Uri
							.parse("unknown")), new TextRecord("en",
							"Unknown tag type"), RecommendedAction.UNKNOWN,
							"sp"));
				}
			}
			arrayAdapter.notifyDataSetChanged();
		} else {

		}

	}

	private void enableNdefExchangeMode() {
		mNfcAdapter.enableForegroundDispatch(this, mNfcPendingIntent,
				mNdefExchangeFilters, null);
	}

	private void notificationFunc() {
		// TODO Auto-generated method stub
		Notification notification = new Notification();
		notification.defaults = Notification.DEFAULT_VIBRATE;
		notification.sound = Uri.parse("android.resource://" + getPackageName()
				+ "/raw/discovered_tag_notification");
		mNotificationManager.notify(RESULT_OK, notification);
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DIALOG1_KEY: {
			ProgressDialog dialog = new ProgressDialog(this);
			dialog.setTitle("Touch NFC Tag to read");
			dialog.setMessage("Please wait while reading...");
			dialog.setIcon(R.drawable.nfc_icon_small);
			dialog.setIndeterminate(true);
			dialog.setCancelable(false);
			dialog.setCanceledOnTouchOutside(false);
			dialog.setOnKeyListener(new Dialog.OnKeyListener() {

				@Override
				public boolean onKey(DialogInterface dialog, int keyCode,
						KeyEvent event) {
					if (keyCode == KeyEvent.KEYCODE_BACK)
						finish();
					// TODO Auto-generated method stub
					return false;
				}
			});

			dialog.setOnDismissListener(new Dialog.OnDismissListener() {

				@Override
				public void onDismiss(DialogInterface dialog) {
					// TODO Auto-generated method stub
					// finish(); // Neu co finish o day thi khi dismiss dialog
					// no se finish activity luon !!!
				}
			});
			return dialog;
		}

		case DIALOG2_KEY: {
			// Dialog dialog = new Dialog(TagViewer.this);
			Dialog dialog = new Dialog(TagViewerActivity.this,
					R.style.Theme_White);

			dialog.setContentView(R.layout.custom_dialog);

			dialog.setCancelable(false);
			dialog.setCanceledOnTouchOutside(false);
			dialog.setOnKeyListener(new Dialog.OnKeyListener() {

				@Override
				public boolean onKey(DialogInterface dialog, int keyCode,
						KeyEvent event) {
					if (keyCode == KeyEvent.KEYCODE_BACK)
						finish();
					// TODO Auto-generated method stub
					return false;
				}
			});

			dialog.show();
			return dialog;
		}
		}
		return null;
	}

}