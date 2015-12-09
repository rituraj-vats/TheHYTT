package com.bitscanvas.thehytt.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.bitscanvas.thehytt.R;
import com.bitscanvas.thehytt.adapters.JourneyItemAdapter;
import com.bitscanvas.thehytt.dtos.HopDto;
import com.bitscanvas.thehytt.dtos.JourneyItemDto;
import com.bitscanvas.thehytt.dtos.PostDto;
import com.bitscanvas.thehytt.utils.CommonUtil;
import com.bitscanvas.thehytt.utils.SharedPrefUtil;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by THEHYTT on 30/09/15.
 */
public class JourneyPool extends Activity {

    private ImageView profileImage;
    protected int iLayout;
    private ListView journeyPoolListView;
    private JourneyItemDto journeyItemDto;
    private List<HopDto> hopDtoList = new ArrayList<>();
    private List<PostDto> postDtoList = new ArrayList<>();

    @Override
    public Intent getIntent() {
        return super.getIntent();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.journey_pool);
        initializeFields();
        prepareJourneyItems();

    }

    public void initializeFields(){
        profileImage = (ImageView)findViewById(R.id.imgProfilePic);
        journeyPoolListView = (ListView)findViewById(R.id.journeyItemList);
        loadProfileImage(null);

    }

    public void populatePool(){
        journeyPoolListView.setAdapter(new JourneyItemAdapter(this, R.layout.journey_item, journeyItemDto));
    }

    public void prepareJourneyItems(){
        Intent intent = getIntent();
        HopDto hopDto = (HopDto)intent.getSerializableExtra("hopDto");
        PostDto postDto = (PostDto)intent.getSerializableExtra("postDto");
        journeyItemDto = new JourneyItemDto();
        if(hopDto != null){
            hopDtoList.add(hopDto);
            journeyItemDto.setHopDtos(hopDtoList);
            populatePool();
        }else if(postDto != null){
            postDtoList.add(postDto);
            journeyItemDto.setPostDtos(postDtoList);
            populatePool();
        }
    }

    public void loadProfileImage(String imagePath) {
        if (imagePath == null) {
            SharedPrefUtil sharedPrefUtil = new SharedPrefUtil(JourneyPool.this);
            String imageUri = sharedPrefUtil.get("profilePic");
            profileImage = (ImageView) findViewById(R.id.imgProfilePic);
            profileImage.setImageBitmap(CommonUtil.loadImageFromStorage(imageUri));

        } else {
            Picasso.with(this)
                    .load(imagePath)
                    .into(profileImage);
        }
    }

    public void onAdd(View v){
        Intent intent = new Intent(JourneyPool.this,HomeScreen.class);
        intent.putExtra("journeyPool", "journeyPool");
        startActivity(intent);
    }
}
