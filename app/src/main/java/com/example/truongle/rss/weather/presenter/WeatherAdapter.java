package com.example.truongle.rss.weather.presenter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.truongle.rss.R;
import com.example.truongle.rss.adapter.HomeAdapter;
import com.example.truongle.rss.weather.model.RecyclerViewWeatherModel;

import java.util.ArrayList;

/**
 * Created by TruongLe on 01/08/2017.
 */

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.RecyclerViewHolderWeather>{

    ArrayList<RecyclerViewWeatherModel> list ;
    Context context;

    public WeatherAdapter(ArrayList<RecyclerViewWeatherModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public RecyclerViewHolderWeather onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemview = inflater.inflate(R.layout.week_row, parent, false);
        return new RecyclerViewHolderWeather(itemview);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolderWeather holder, int position) {
        Glide.with(context).load(list.get(position).getImage()).into(holder.imageView);
        holder.txtStatus.setText(list.get(position).getStatus());
        holder.txtTemp.setText(list.get(position).getTemp());
        holder.txtDay.setText(list.get(position).getDays());
        holder.txtDate.setText(list.get(position).getDate());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class RecyclerViewHolderWeather extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView txtDay, txtTemp, txtStatus,txtDate;
        public RecyclerViewHolderWeather(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.imageViewIcon);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            txtDay = (TextView) itemView.findViewById(R.id.dayOfWeek);
            txtDate = (TextView) itemView.findViewById(R.id.dateOfWeek);
            txtStatus = (TextView) itemView.findViewById(R.id.statusWeather);
            txtTemp = (TextView) itemView.findViewById(R.id.temperatureOnWeek);
        }

    }
}
