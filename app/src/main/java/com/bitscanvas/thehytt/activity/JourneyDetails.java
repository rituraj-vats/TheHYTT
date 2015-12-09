package com.bitscanvas.thehytt.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bitscanvas.thehytt.R;
import com.bitscanvas.thehytt.dtos.HopDto;
import com.bitscanvas.thehytt.dtos.PostDto;
import com.squareup.picasso.Picasso;

/**
 * Created by vatsritu on 23/08/15.
 */
public class JourneyDetails extends Activity {

    private ImageView profileImage;
    private TextView operationType;
    private TextView fromAddress;
    private TextView toAddress;
    private TextView distance;
    private TextView price;
    private Intent intent1;

    public static final String TAG = "JourneyDetails";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.journey_details);
        Intent intent = getIntent();
        initializeFields();
        populateDisplayData(intent);
    }

    public void initializeFields(){
        profileImage = (ImageView)findViewById(R.id.imgProfilePic);
        operationType = (TextView)findViewById(R.id.operationType);
        fromAddress = (TextView)findViewById(R.id.txtFromValue);
        toAddress = (TextView)findViewById(R.id.txtToValue);
        distance = (TextView)findViewById(R.id.txtDistanceValue);
        price = (TextView)findViewById(R.id.txtPriceValue);
        intent1 = new Intent(JourneyDetails.this, JourneyPool.class);
    }

    public void populateDisplayData(Intent intent){
        PostDto postDto = (PostDto)intent.getSerializableExtra("postDto");
        HopDto hopDto =(HopDto)intent.getSerializableExtra("hopDto");
        if(postDto != null){
            populatePostDetails(postDto);
        }else if(hopDto !=null){
            populateHopDetails(hopDto);
        }
    }

    public void populateHopDetails(HopDto hopDto){
        Picasso.with(this)
                .load(hopDto.getUserImageUrl())
                .into(profileImage);
        operationType.setText("Hop Details");
        fromAddress.setText(hopDto.getSrcAddress());
        toAddress.setText(hopDto.getDestAddress());
        distance.setText(hopDto.getSrcDist()+" km");
        //price.setText(hopDto.getPps());
        intent1.putExtra("hopDto",hopDto);
    }

    public void populatePostDetails(PostDto postDto){
        Picasso.with(this)
                .load(postDto.getUserImageUrl())
                .into(profileImage);
        operationType.setText("Post Details");
        fromAddress.setText(postDto.getSrcAddress());
        toAddress.setText(postDto.getDestAddress());
        distance.setText(postDto.getSrcDist()+" km");
        //price.setText(postDto.getPps());
        intent1.putExtra("postDto",postDto);
    }

    public void onConfirm(View v){
        startActivity(intent1);
    }

    public void onCancel(View v){
        finish();
    }


}
