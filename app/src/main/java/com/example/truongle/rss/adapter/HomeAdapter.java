package com.example.truongle.rss.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.truongle.rss.model.News;
import com.example.truongle.rss.R;
import com.example.truongle.rss.RealmDB;
import com.example.truongle.rss.WebViewActivity;

import java.util.ArrayList;

import io.realm.Realm;

/**
 * Created by TruongLe on 23/07/2017.
 */

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.RecyclerViewHolder> {
    ArrayList<News> listData;
    Context context;
    Realm realm;

    public HomeAdapter(ArrayList<News> listData, Context context) {
        this.listData = listData;
        this.context = context;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemview = inflater.inflate(R.layout.trang_chu_row, parent, false);
        return new RecyclerViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, final int position) {
        int lastPosition  = -1;
        holder.txtTitle.setText(listData.get(position).getTitle());
        holder.txtDesc.setText(listData.get(position).getDescription());
        //if(listData.get(position).isClickBookMart() == true){
        //}
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listData.get(position).isClickBookMart()==false){
                    RealmDB realmDB = new RealmDB(context);
                    realmDB.bookMart(listData.get(position));
                    listData.get(position).setClickBookMart(true);

                }
            }
        });
        Glide.with(context).load(listData.get(position).getImage_link()).into(holder.imageView);
        Animation animation = AnimationUtils.loadAnimation(context,
                (position > lastPosition) ? R.anim.up_from_bottom
                        : R.anim.down_from_top);
        holder.itemView.startAnimation(animation);
        lastPosition = position;

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String link = listData.get(position).getLink();
                Intent intent = new Intent(context, WebViewActivity.class);
                intent.putExtra("link", link);
                context.startActivity(intent);

            }
        });
    }
    @Override
    public int getItemCount() {
        return listData.size();
    }
    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView txtTitle, txtDesc;
        ImageView imageView;
        CheckBox checkBox;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            txtTitle = (TextView) itemView.findViewById(R.id.titleNewsTrangChu);
            txtDesc = (TextView) itemView.findViewById(R.id.descNewsTrangChu);
            imageView = (ImageView) itemView.findViewById(R.id.imageViewTrangChu);
            checkBox = (CheckBox) itemView.findViewById(R.id.checkboxTrangChu);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        }

    }

    @Override
    public void onViewDetachedFromWindow(RecyclerViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.itemView.clearAnimation();
    }
}