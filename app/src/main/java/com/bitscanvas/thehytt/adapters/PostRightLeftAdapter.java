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
import com.bitscanvas.thehytt.dtos.PostDto;
import com.bitscanvas.thehytt.utils.CommonUtil;
import com.bitscanvas.thehytt.utils.RoundedTransformation;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.URL;
import java.util.List;

/**
 * Created by vats on 6/7/2015.
 */
public class PostRightLeftAdapter extends BaseAdapter {
    List<PostDto> postDtos;

    protected LayoutInflater inflator;
    protected int iLayout;
    private Context context;
    public PostRightLeftAdapter(Context context, int viewlayout, List<PostDto> postDtos) {

        this.context = context;
        this.inflator = LayoutInflater.from(context);
        this.postDtos = postDtos;
        this.iLayout = viewlayout;
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return postDtos.size();
    }

    @Override
    public PostDto getItem(int position) {
        // TODO Auto-generated method stub
        return postDtos.get(position);
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

            objViewHolder.imageUrl = postDtos.get(position).getUserImageUrl();

            // new DownloadAsyncTask().execute(objViewHolder);
            Picasso.with(context)
                    .load(postDtos.get(position).getUserImageUrl())
                    .transform(new RoundedTransformation(50, 4))
                    .resize(100, 100)
                    .centerCrop()
                    .into(objViewHolder.userImage);

            objViewHolder.userName.setText(postDtos.get(position).getDisplayName());
            objViewHolder.userDistance.setText(postDtos.get(position).getSrcDist());
            objViewHolder.verificationImage.setImageResource(R.mipmap.screen_logo_verified);
            objViewHolder.userDestination.setText(postDtos.get(position).getDestAddress());
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
