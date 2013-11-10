/*
 * Copyright 2012 Google Inc.
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

package org.uamobile.iosched.io;

import android.app.SearchManager;
import android.content.ContentProviderOperation;
import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.uamobile.iosched.Config;
import org.uamobile.iosched.provider.ScheduleContract;
import org.uamobile.iosched.util.Lists;

import java.io.IOException;
import java.util.ArrayList;

public class SearchSuggestHandler extends JSONHandler {

    public SearchSuggestHandler(Context context) {
        super(context);
    }

    public ArrayList<ContentProviderOperation> parse(String json)
            throws IOException {
        final ArrayList<ContentProviderOperation> batch = Lists.newArrayList();

        try {
            JSONObject root = new JSONObject(json);
            JSONArray suggestions = root.getJSONArray("words");

            batch.add(ContentProviderOperation
                    .newDelete(ScheduleContract.addCallerIsSyncAdapterParameter(
                            ScheduleContract.SearchSuggest.CONTENT_URI))
                    .build());
            for(int i = 0 ; i < suggestions.length() ; i++) {
                batch.add(ContentProviderOperation
                        .newInsert(ScheduleContract.addCallerIsSyncAdapterParameter(
                                ScheduleContract.SearchSuggest.CONTENT_URI))
                        .withValue(SearchManager.SUGGEST_COLUMN_TEXT_1, suggestions.getString(i))
                        .build());
            }
        } catch (JSONException e) {
            Log.e(Config.LOG_TAG, "Problem building word list", e);
        }

        return batch;
    }
}
