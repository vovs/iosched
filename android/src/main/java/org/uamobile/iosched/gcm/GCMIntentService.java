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
package org.uamobile.iosched.gcm;

import android.content.Context;
import android.content.Intent;

import com.google.android.gcm.GCMBaseIntentService;

import org.uamobile.iosched.Config;
import org.uamobile.iosched.gcm.command.AnnouncementCommand;
import org.uamobile.iosched.gcm.command.SyncCommand;
import org.uamobile.iosched.gcm.command.SyncUserCommand;
import org.uamobile.iosched.gcm.command.TestCommand;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.uamobile.iosched.util.LogUtils.LOGD;
import static org.uamobile.iosched.util.LogUtils.LOGE;
import static org.uamobile.iosched.util.LogUtils.LOGI;
import static org.uamobile.iosched.util.LogUtils.LOGW;
import static org.uamobile.iosched.util.LogUtils.makeLogTag;

/**
 * {@link android.app.IntentService} responsible for handling GCM messages.
 */
public class GCMIntentService extends GCMBaseIntentService {

    private static final String TAG = makeLogTag("GCM");

    private static final Map<String, GCMCommand> MESSAGE_RECEIVERS;
    static {
        // Known messages and their GCM message receivers
        Map <String, GCMCommand> receivers = new HashMap<String, GCMCommand>();
        receivers.put("test", new TestCommand());
        receivers.put("announcement", new AnnouncementCommand());
        receivers.put("sync_schedule", new SyncCommand());
        receivers.put("sync_user", new SyncUserCommand());
        MESSAGE_RECEIVERS = Collections.unmodifiableMap(receivers);
    }

    public GCMIntentService() {
        super(Config.GCM_SENDER_ID);
    }

    @Override
    protected void onRegistered(Context context, String regId) {
        LOGI(TAG, "Device registered: regId=" + regId);
        ServerUtilities.register(context, regId);
    }

    @Override
    protected void onUnregistered(Context context, String regId) {
        LOGI(TAG, "Device unregistered");
        if (ServerUtilities.isRegisteredOnServer(context)) {
            ServerUtilities.unregister(context, regId);
        } else {
            // This callback results from the call to unregister made on
            // ServerUtilities when the registration to the server failed.
            LOGD(TAG, "Ignoring unregister callback");
        }
    }

    @Override
    protected void onMessage(Context context, Intent intent) {
        String action = intent.getStringExtra("action");
        String extraData = intent.getStringExtra("extraData");
        if (action == null) {
            LOGE(TAG, "Message received without command action");
            return;
        }

        action = action.toLowerCase();
        GCMCommand command = MESSAGE_RECEIVERS.get(action);
        if (command == null) {
            LOGE(TAG, "Unknown command received: " + action);
        } else {
            command.execute(this, action, extraData);
        }

    }

    @Override
    public void onError(Context context, String errorId) {
        LOGE(TAG, "Received error: " + errorId);
    }

    @Override
    protected boolean onRecoverableError(Context context, String errorId) {
        // log message
        LOGW(TAG, "Received recoverable error: " + errorId);
        return super.onRecoverableError(context, errorId);
    }
}