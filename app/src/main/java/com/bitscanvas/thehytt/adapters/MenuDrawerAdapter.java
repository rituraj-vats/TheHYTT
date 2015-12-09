package com.bitscanvas.thehytt.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bitscanvas.thehytt.R;
import com.bitscanvas.thehytt.utils.CommonUtil;
import com.bitscanvas.thehytt.utils.MenuItems;
import com.bitscanvas.thehytt.utils.SharedPrefUtil;

import java.util.Collections;
import java.util.List;

/**
 * Created by lbsin on 10-10-2015.
 */
public class MenuDrawerAdapter extends RecyclerView.Adapter<MenuDrawerAdapter.MyViewHolder> {
    List<MenuItems> data = Collections.emptyList();
    private LayoutInflater inflater;
    private Context context;
    ImageView profileImage;

    public MenuDrawerAdapter(Context context, List<MenuItems> data) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    public void delete(int position) {
        data.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.menu_row_items, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        profileImage = (ImageView)view.findViewById(R.id.imgProfilePic);
        //loadProfileImage();
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        MenuItems current = data.get(position);
        holder.title.setText(current.getTitle());
        holder.icons.setImageResource(current.getIcon());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView icons;

        public MyViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            icons =(ImageView) itemView.findViewById(R.id.imgItemIcon);
        }
    }

    public void loadProfileImage() {
            SharedPrefUtil sharedPrefUtil = new SharedPrefUtil(context);
            String imageUri = sharedPrefUtil.get("profilePic");
            profileImage.setImageBitmap(CommonUtil.loadImageFromStorage(imageUri));
    }
}