/*
 * Copyright (C) 2010 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.android.nfcRead;

import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.util.Log;

import com.android.nfcRead.record.ParsedNdefRecord;
import com.android.nfcRead.record.SmartPoster;
import com.android.nfcRead.record.TextRecord;
import com.android.nfcRead.record.UriRecord;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for creating {@link ParsedNdefMessage}s.
 */
public class NdefMessageParser {

    // Utility class
    private NdefMessageParser() {

    }

    /** Parse an NdefMessage */
    public static List<ParsedNdefRecord> parse(NdefMessage message) {
        return getRecords(message.getRecords());
    }

    public static List<ParsedNdefRecord> getRecords(NdefRecord[] records) {
        List<ParsedNdefRecord> elements = new ArrayList<ParsedNdefRecord>();
        for (NdefRecord record : records) {
            if (UriRecord.isUri(record)) {
            	Log.d("Uri Record", "is URI record");
                elements.add(UriRecord.parse(record));
            } else if (TextRecord.isText(record)) {
            	Log.d("Text Record", "is Text record");
                elements.add(TextRecord.parse(record));
            } else if (SmartPoster.isPoster(record)) {
            	Log.d("Smart Poster Record", "is Smart Poster");
                elements.add(SmartPoster.parse(record));
            }
        }
        return elements;
    }
}
