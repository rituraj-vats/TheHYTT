/**
 * Copyright 2015 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.bitscanvas.thehytt.service;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.bitscanvas.thehytt.R;
import com.bitscanvas.thehytt.activity.HomeScreen;
import com.bitscanvas.thehytt.constants.AppConstant;
import com.bitscanvas.thehytt.emuns.DialogType;
import com.bitscanvas.thehytt.emuns.UserAuth;
import com.bitscanvas.thehytt.model.request.SignUpRequest;
import com.bitscanvas.thehytt.model.response.AuthResponse;
import com.bitscanvas.thehytt.utils.CommonUtil;
import com.bitscanvas.thehytt.utils.HttpConnectionUtil;
import com.bitscanvas.thehytt.utils.JSONConvertor;
import com.bitscanvas.thehytt.utils.SharedPrefUtil;
import com.google.android.gms.gcm.GcmPubSub;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

import java.io.IOException;

public class RegistrationIntentService extends IntentService {

    private static final String TAG = "RegIntentService";
    private static final String[] TOPICS = {"global"};
    private SignUpRequest signUpRequest;
    public RegistrationIntentService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        try {
            // Initially this call goes out to the network to retrieve the token, subsequent calls are local.
            InstanceID instanceID = InstanceID.getInstance(this);
            String token = instanceID.getToken("983710354561",
                    GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
            Log.i(TAG, "GCM Registration Token: " + token);

            sendRegistrationToServer(token, intent);

            // Subscribe to topic channels
            subscribeTopics(token);

            // You should store a boolean that indicates whether the generated token has been
            // sent to your server. If the boolean is false, send the token to your server,
            // otherwise your server should have already received the token.
            sharedPreferences.edit().putBoolean(AppConstant.SENT_TOKEN_TO_SERVER, true).apply();
        } catch (Exception e) {
            Log.d(TAG, "Failed to complete token refresh", e);
            sharedPreferences.edit().putBoolean(AppConstant.SENT_TOKEN_TO_SERVER, false).apply();
        }
        // Notify UI that registration has completed, so the progress indicator can be hidden.
        /*Intent registrationComplete = new Intent(AppConstant.REGISTRATION_COMPLETE);
        LocalBroadcastManager.getInstance(this).sendBroadcast(registrationComplete);*/
    }


    private void sendRegistrationToServer(String token,Intent intent) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        try {
            signUpRequest = (SignUpRequest)intent.getSerializableExtra("signUpForm");
            if(signUpRequest!=null) {
                signUpRequest.setGcmRegId(token);
                String req = JSONConvertor.objectToJson(signUpRequest);


                HttpConnectionUtil httpConnectionUtil = new HttpConnectionUtil();
                String response = httpConnectionUtil.loadFromNetwork(AppConstant.BASE_URI + AppConstant.SIGN_UP, req);

                SharedPrefUtil sharedPrefUtil = new SharedPrefUtil(getApplicationContext());
                if (response != null && !("").equals(response)) {
                    if (response.equalsIgnoreCase("\"" + UserAuth.ADDED.toString() + "\"")) {
                        AuthResponse authResponse = prepareSignUpResponse(signUpRequest);
                        sharedPrefUtil.save("userProfile", authResponse);
                        Intent intent1 = new Intent(getApplicationContext(), HomeScreen.class);
                        intent1.putExtra("userProfile", authResponse);
                        intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        this.startActivity(intent1);
                    } else if (response.equalsIgnoreCase("\"" + UserAuth.EXISTS.toString() + "\"")) {
                        CommonUtil.showAlert(this, getString(R.string.title_error), getString(R.string.msgUserExists), DialogType.ERROR);
                    }

                } else {
                    CommonUtil.showAlert(this, getString(R.string.title_error), "Error while processing.", DialogType.ERROR);
                }
            }
        } catch (Exception e) {
            sharedPreferences.edit().putBoolean(AppConstant.SENT_TOKEN_TO_SERVER, false).apply();
            Log.d(TAG, "Failed to register user", e);
            e.printStackTrace();
        }


    }

    /**
     * Subscribe to any GCM topics of interest, as defined by the TOPICS constant.
     *
     * @param token GCM token
     * @throws IOException if unable to reach the GCM PubSub service
     */
    // [START subscribe_topics]
    private void subscribeTopics(String token) throws IOException {
        GcmPubSub pubSub = GcmPubSub.getInstance(this);
        for (String topic : TOPICS) {
            pubSub.subscribe(token, "/topics/" + topic, null);
        }
    }



    public AuthResponse prepareSignUpResponse(SignUpRequest signUpRequest){
        AuthResponse authResponse = new AuthResponse();
        authResponse.setFirstName(signUpRequest.getFirstName());
        authResponse.setLastName(signUpRequest.getLastName());
        authResponse.setDisplayName(signUpRequest.getDisplayName());
        authResponse.setVehicleNumber(signUpRequest.getVehicleNumber());
        authResponse.setGender(signUpRequest.getGender());
        authResponse.setImagePath("");
        authResponse.setOwned(signUpRequest.getOwnedVehicle());
        authResponse.setMobileNumber(signUpRequest.getMobNum());
        authResponse.setUserId(signUpRequest.getUserId());
        authResponse.setImagePath(signUpRequest.getImageUri());

        return authResponse;
    }
}
