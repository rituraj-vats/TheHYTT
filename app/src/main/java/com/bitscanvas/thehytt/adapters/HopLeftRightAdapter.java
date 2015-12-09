package com.bitscanvas.thehytt.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bitscanvas.thehytt.R;
import com.bitscanvas.thehytt.dtos.HopDto;
import com.bitscanvas.thehytt.utils.CommonUtil;

import java.io.IOException;
import java.net.URL;
import java.util.List;

/**
 * Created by vatsritu on 6/7/2015.
 */
public class HopLeftRightAdapter extends BaseAdapter {
    public static final String TAG = "HopLeftRightAdapter";
    List<HopDto> hopDtos;

    protected LayoutInflater inflator;
    protected int iLayout;
    private Context context;
    public HopLeftRightAdapter(Context context, int viewlayout, List<HopDto> hopDtos) {

        this.context = context;
        this.inflator = LayoutInflater.from(context);
        this.hopDtos = hopDtos;
        this.iLayout = viewlayout;
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return hopDtos.size();
    }

    @Override
    public HopDto getItem(int position) {
        return hopDtos.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup arg2) {
        try {
            ViewHolder objViewHolder;
            if (convertView == null) {
                objViewHolder = new ViewHolder();
                LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = vi.inflate(iLayout, null);

                objViewHolder.userImage = (ImageView) convertView.findViewById(R.id.iv_profileImg);
                objViewHolder.userName = (TextView) convertView.findViewById(R.id.tv_profilename);
                objViewHolder.verificationImage =(ImageView)convertView.findViewById(R.id.imgVerifiedLog);
                objViewHolder.userDistance = (TextView)convertView.findViewById(R.id.tv_profiledistance);

                convertView.setTag(objViewHolder);
            } else {
                objViewHolder = (ViewHolder) convertView.getTag();
            }
            objViewHolder.imageUrl = hopDtos.get(position).getUserImageUrl();
            new DownloadAsyncTask().execute(objViewHolder);

           /* Picasso.with(context)
                    .load(hopDtos.get(position).getUserImageUrl()).transform(new RoundedTransformation(100,0))
                    .into(objViewHolder.userImage);*/

            objViewHolder.userName.setText(hopDtos.get(position).getDisplayName());
            objViewHolder.userDistance.setText(hopDtos.get(position).getSrcDist());
            objViewHolder.verificationImage.setImageResource(R.mipmap.screen_logo_verified);
            objViewHolder.userDestination.setText(hopDtos.get(position).getDestAddress());
        } catch (Exception e) {
            System.out.println("CampaignAdapter.getView() e " + e.getLocalizedMessage());
        }
        return convertView;
    }
    class ViewHolder {
        public ImageView userImage;
        public Bitmap bitmap;
        public String imageUrl;
        public TextView userName;
        public TextView userDestination;
        public ImageView verificationImage;
        public TextView userDistance;

    }

    private class DownloadAsyncTask extends AsyncTask<ViewHolder, Void, ViewHolder> {

        @Override
        protected ViewHolder doInBackground(ViewHolder... params) {
            // TODO Auto-generated method stub
            //load image directly
            ViewHolder viewHolder = params[0];
            try {
                URL imageURL = new URL(viewHolder.imageUrl);
                viewHolder.bitmap = BitmapFactory.decodeStream(imageURL.openStream());
                viewHolder.bitmap = CommonUtil.getCircleBitmap(viewHolder.bitmap);
            } catch (IOException e) {
                // TODO: handle exception
                Log.e("error", "Downloading Image Failed");
                viewHolder.bitmap = null;
            }

            return viewHolder;
        }

        @Override
        protected void onPostExecute(ViewHolder result) {
            // TODO Auto-generated method stub
            if (result.bitmap == null) {
                result.userImage.setImageResource(R.mipmap.profie_icon);
            } else {
                result.userImage.setImageBitmap(result.bitmap);
            }
        }
    }

}
