package com.example.truongle.rss.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.truongle.rss.home.model.News;
import com.example.truongle.rss.R;
import com.example.truongle.rss.RealmDB;
import com.example.truongle.rss.home.view.ViewHome;
import com.example.truongle.rss.webView.view.WebViewActivity;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import io.realm.Realm;

/**
 * Created by TruongLe on 23/07/2017.
 */

public class HomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    ArrayList<News> listData;
    Context context;
    Realm realm;
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    public static final String TAG = "AAA";

    private int lastVisibleItem, totalItemCount;
    private int visibleThreshold = 5;
    private boolean isLoading;
    String fontStyle;
    public HomeAdapter( RecyclerView recyclerView, ArrayList<News> listData, final Context context, String fontStyle) {
        this.listData = listData;
        this.context = context;
        this.fontStyle = fontStyle;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View itemview = null;
            if(fontStyle.equals("trang_chu_row_fullscreen")){
         itemview = inflater.inflate(R.layout.trang_chu_row_fullscreen, parent, false);}
            else{
                itemview = inflater.inflate(R.layout.trang_chu_row, parent, false);
            }
        return new NewsViewHolder(itemview);
        }else if (viewType == VIEW_TYPE_LOADING) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.footer_view, parent, false);
            return new LoadingViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof NewsViewHolder) {
            NewsViewHolder newsViewHolder = (NewsViewHolder) holder;
            int lastPosition = -1;
            newsViewHolder.txtTitle.setText(listData.get(position).getTitle());
            newsViewHolder.txtDesc.setText(listData.get(position).getDescription());
            //if(listData.get(position).isClickBookMart() == true){
            //}
            if(listData.get(position).isClickBookMart()==true){
                newsViewHolder.checkBox.setChecked(true);
            }
            else {
                newsViewHolder.checkBox.setChecked(false);
            }
            newsViewHolder.checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listData.get(position).isClickBookMart() == false) {
                        RealmDB realmDB = new RealmDB(context);
                        realmDB.bookMart(listData.get(position));
                        listData.get(position).setClickBookMart(true);
                        Toast.makeText(context, "Lưu tin tức thành công", Toast.LENGTH_SHORT).show();

                    }
                }
            });

            Glide.with(context).load(listData.get(position).getImage_link()).into(newsViewHolder.imageView);
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
        else if (holder instanceof LoadingViewHolder) {
            LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
            loadingViewHolder.progressBar.setIndeterminate(true);
        }
    }

    @Override
    public int getItemCount() {
        return listData == null ? 0 : listData.size();
    }
    @Override
    public int getItemViewType(int position) {
        return listData.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    public class LoadingViewHolder extends RecyclerView.ViewHolder{

        public ProgressBar progressBar;
        public LoadingViewHolder(View itemView) {
            super(itemView);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progress_footer_view);
        }
    }
    public class NewsViewHolder extends RecyclerView.ViewHolder {
        TextView txtTitle, txtDesc;
        ImageView imageView;
        CheckBox checkBox;

        public NewsViewHolder(View itemView) {
            super(itemView);
            txtTitle = (TextView) itemView.findViewById(R.id.titleNewsTrangChu);
            txtDesc = (TextView) itemView.findViewById(R.id.descNewsTrangChu);
            imageView = (ImageView) itemView.findViewById(R.id.imageViewTrangChu);
            checkBox = (CheckBox) itemView.findViewById(R.id.checkboxTrangChu);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        }
    }

    @Override
    public void onViewDetachedFromWindow(RecyclerView.ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.itemView.clearAnimation();
    }

}