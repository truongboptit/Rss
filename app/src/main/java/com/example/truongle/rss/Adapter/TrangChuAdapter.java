package com.example.truongle.rss.Adapter;

import android.content.Context;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.truongle.rss.Model.News;
import com.example.truongle.rss.R;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by TruongLe on 23/07/2017.
 */

public class TrangChuAdapter extends RecyclerView.Adapter<TrangChuAdapter.RecyclerViewHolder> {
    ArrayList<News> listData;
    Context context;

    public TrangChuAdapter(ArrayList<News> listData, Context context) {
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
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        int lastPosition  = -1;
        holder.txtTitle.setText(listData.get(position).getTitle());
        holder.txtDesc.setText(listData.get(position).getDescription());
        Glide.with(context).load(listData.get(position).getImage_link()).into(holder.imageView);
        Animation animation = AnimationUtils.loadAnimation(context,
                (position > lastPosition) ? R.anim.up_from_bottom
                        : R.anim.down_from_top);
        holder.itemView.startAnimation(animation);
        lastPosition = position;
    }
    @Override
    public int getItemCount() {
        return listData.size();
    }
    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView txtTitle, txtDesc;
        ImageView imageView;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            txtTitle = (TextView) itemView.findViewById(R.id.titleNewsTrangChu);
            txtDesc = (TextView) itemView.findViewById(R.id.descNewsTrangChu);
            imageView = (ImageView) itemView.findViewById(R.id.imageViewTrangChu);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        }

    }

    @Override
    public void onViewDetachedFromWindow(RecyclerViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.itemView.clearAnimation();
    }
}