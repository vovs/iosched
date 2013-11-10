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

import android.content.ContentProviderOperation;
import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;
import org.uamobile.droidconsched.io.ConferenceAPI;
import org.uamobile.droidconsched.io.model.PresenterResponse;
import org.uamobile.droidconsched.io.model.PresentersResponse;
import org.uamobile.iosched.Config;
import org.uamobile.iosched.provider.ScheduleContract;
import org.uamobile.iosched.provider.ScheduleContract.SyncColumns;
import org.uamobile.iosched.util.Lists;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.uamobile.iosched.provider.ScheduleContract.Speakers;
import static org.uamobile.iosched.util.LogUtils.LOGE;
import static org.uamobile.iosched.util.LogUtils.LOGI;
import static org.uamobile.iosched.util.LogUtils.makeLogTag;

public class SpeakersHandler {

    private static final String TAG = makeLogTag(SpeakersHandler.class);

    public SpeakersHandler(Context context) {}

    public ArrayList<ContentProviderOperation> fetchAndParse(
            ConferenceAPI conferenceAPI)
            throws IOException {
        PresentersResponse presenters;

        try {
            presenters = conferenceAPI.events().presenters().list(Config.EVENT_ID).execute();

            if (presenters == null || presenters.getPresenters() == null) {
                throw new HandlerException("Speakers list was null.");
            }
        } catch (HandlerException e) {
            LOGE(TAG, "Error fetching speakers", e);
            return Lists.newArrayList();
        }

        return buildContentProviderOperations(presenters);
    }

    public ArrayList<ContentProviderOperation> parseString(String json) {
        try {
            PresentersResponse presenters = new PresentersResponse();
            presenters.fromJSON(new JSONObject(json));
            return buildContentProviderOperations(presenters);
        } catch (JSONException e) {
            LOGE(TAG, "Error reading speakers from packaged data", e);
            return Lists.newArrayList();
        }
    }

    private ArrayList<ContentProviderOperation> buildContentProviderOperations(
            PresentersResponse presenters) {
        final ArrayList<ContentProviderOperation> batch = Lists.newArrayList();
        if (presenters != null) {
            List<PresenterResponse> presenterList = presenters.getPresenters();
            int numSpeakers = presenterList.size();

            if (numSpeakers > 0) {
                LOGI(TAG, "Updating presenters data");

                // Clear out existing speakers
                batch.add(ContentProviderOperation.newDelete(
                        ScheduleContract.addCallerIsSyncAdapterParameter(
                                Speakers.CONTENT_URI))
                        .build());

                // Insert latest speaker data
                for (PresenterResponse presenter : presenterList) {
                    // Hack: Fix speaker URL so that it's not being resized
                    // Depends on thumbnail URL being exactly in the format we want
                    String thumbnail = presenter.getThumbnailUrl();
                    if (thumbnail != null) {
                        thumbnail = thumbnail.replace("?sz=50", "?sz=100");
                    }

                    batch.add(ContentProviderOperation.newInsert(ScheduleContract
                            .addCallerIsSyncAdapterParameter(Speakers.CONTENT_URI))
                            .withValue(SyncColumns.UPDATED, System.currentTimeMillis())
                            .withValue(Speakers.SPEAKER_ID, presenter.getId())
                            .withValue(Speakers.SPEAKER_NAME, presenter.getName())
                            .withValue(Speakers.SPEAKER_ABSTRACT, presenter.getBio())
                            .withValue(Speakers.SPEAKER_IMAGE_URL, thumbnail)
                            .withValue(Speakers.SPEAKER_URL, presenter.getPlusoneUrl())
                            .build());
                }
            }

        }
        return batch;
    }
}
