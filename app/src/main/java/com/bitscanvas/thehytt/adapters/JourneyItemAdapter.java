package com.bitscanvas.thehytt.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bitscanvas.thehytt.R;
import com.bitscanvas.thehytt.dtos.HopDto;
import com.bitscanvas.thehytt.dtos.JourneyItemDto;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by THEHYTT on 29/08/15.
 */
public class JourneyItemAdapter extends BaseAdapter {
    
    JourneyItemDto journeyItemDtos ;
    protected int viewlayout;
    private Context context;

    public JourneyItemAdapter(Context context, int viewlayout,JourneyItemDto journeyItemDtos){
        this.context=context;
        this.viewlayout=viewlayout;
        this.journeyItemDtos=journeyItemDtos;
    }

    @Override
    public int getCount() {
        int count = 0;
        if(journeyItemDtos.getHopDtos() != null){
            count = journeyItemDtos.getHopDtos().size();
        }else if(journeyItemDtos.getPostDtos() != null){
            count = journeyItemDtos.getPostDtos().size();
        }
        return count;
    }

    @Override
    public Object getItem(int position) {
        if(journeyItemDtos.getPostDtos()!=null && journeyItemDtos.getPostDtos().size()>0)
            return journeyItemDtos.getPostDtos().get(position);
        else if(journeyItemDtos.getHopDtos()!= null && journeyItemDtos.getHopDtos().size()>0)
            return journeyItemDtos.getHopDtos().get(position);

        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        try {
            if(view == null){
                viewHolder = new ViewHolder();
                LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = vi.inflate(viewlayout, null);

                viewHolder.userImage = (ImageView) view.findViewById(R.id.iv_profileImg);
                viewHolder.userName = (TextView) view.findViewById(R.id.tv_profilename);
                viewHolder.userSourceAddress = (TextView) view.findViewById(R.id.tv_srcAddress);
                view.setTag(viewHolder);
            }else{
                viewHolder = (ViewHolder)view.getTag();
            }

            if(journeyItemDtos.getHopDtos() != null && journeyItemDtos.getHopDtos().size()>0){
                List<HopDto> hopDtos = journeyItemDtos.getHopDtos();
                Picasso.with(context)
                        .load(hopDtos.get(position).getUserImageUrl())
                        .into(viewHolder.userImage);

                viewHolder.userName.setText(hopDtos.get(position).getDisplayName());
                viewHolder.userSourceAddress.setText(hopDtos.get(position).getSrcAddress());
            }else if(journeyItemDtos.getPostDtos() != null && journeyItemDtos.getPostDtos().size()>0){
                List<HopDto> hopDtos = journeyItemDtos.getHopDtos();
                Picasso.with(context)
                        .load(hopDtos.get(position).getUserImageUrl())
                        .into(viewHolder.userImage);

                viewHolder.userName.setText(hopDtos.get(position).getDisplayName());
                viewHolder.userSourceAddress.setText(hopDtos.get(position).getSrcAddress());
            }


        }catch (Exception e){
            System.out.println("Journey Item adapter " + e.getLocalizedMessage());
        }
        return view;
    }

    class ViewHolder{
        public ImageView userImage;
        public TextView userName;
        public TextView userSourceAddress;

    }
}
