package com.bitscanvas.thehytt.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bitscanvas.thehytt.R;
import com.bitscanvas.thehytt.constants.AppConstant;
import com.bitscanvas.thehytt.model.request.HopRequest;
import com.bitscanvas.thehytt.utils.CommonUtil;
import com.bitscanvas.thehytt.utils.HttpConnectionUtil;
import com.bitscanvas.thehytt.utils.JSONConvertor;
import com.bitscanvas.thehytt.utils.SharedPrefUtil;
import com.squareup.picasso.Picasso;

import java.io.IOException;

/**
 * Created by THEHYTT on 04/10/15.
 */
public class HopRequestActivity extends Activity {
    private ImageView profileImage;
    private TextView operationType;
    private TextView fromAddress;
    private TextView toAddress;
    private TextView distance;
    private TextView price;

    HopRequest hopRequest;

    public static final String TAG = "HopRequest";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hop_request);
        Intent intent = getIntent();
        initializeFields();
        populateDisplayData(intent);
    }

    public void initializeFields(){
        operationType = (TextView)findViewById(R.id.operationType);
        fromAddress = (TextView)findViewById(R.id.txtFromValue);
        toAddress = (TextView)findViewById(R.id.txtToValue);
        distance = (TextView)findViewById(R.id.txtDistanceValue);
        price = (TextView)findViewById(R.id.txtPriceValue);
        loadProfileImage(null);
    }

    public void populateDisplayData(Intent intent){
         hopRequest =(HopRequest)intent.getSerializableExtra("hopRequest");
        if(hopRequest !=null){
            populateHopDetails();
        }
    }

    public void populateHopDetails(){

        operationType.setText("Hop Request");
        fromAddress.setText(hopRequest.getSrcAddress());
        toAddress.setText(hopRequest.getDestAddress());
        //price.setText(hopDto.getPps());
    }

    public void loadProfileImage(String imagePath) {
        if (imagePath == null) {
            SharedPrefUtil sharedPrefUtil = new SharedPrefUtil(this);
            String imageUri = sharedPrefUtil.get("profilePic");
            profileImage = (ImageView) findViewById(R.id.imgProfilePic);
            profileImage.setImageBitmap(CommonUtil.loadImageFromStorage(imageUri));

        } else {
            Picasso.with(this)
                    .load(imagePath)
                    .into(profileImage);
        }
    }

    public void onConfirm(View v){
        String request = JSONConvertor.objectToJson(hopRequest);
        HopRequestTask hopRequestTask = new HopRequestTask();
        hopRequestTask.execute(AppConstant.BASE_URI_1+AppConstant.HOP_URI,request);
    }

    public void onCancel(View v){
        finish();
    }


    /**
     * Implementation of AsyncTask, to fetch the data in the background away from
     * the UI thread.
     */
    private class HopRequestTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            try {
                HttpConnectionUtil httpConnectionUtil = new HttpConnectionUtil();
                return httpConnectionUtil.loadFromNetwork(urls[0], urls[1]);
            } catch (IOException e) {
                Log.e(TAG, "Error while posting hop request: " + e.getMessage());
                return "";
            }
        }

        /**
         * Once the doInBackground method is complete this will be called
         * Based on the response take appropriate action
         */
        @Override
        protected void onPostExecute(String result) {
            try {

                if(result !=null){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(HopRequestActivity.this, "Your request posted successfully", Toast.LENGTH_SHORT);
                        }
                    });

                }

                    finish();

            } catch (Exception e) {
                Log.e(TAG, "Error while posting hop request: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

}
