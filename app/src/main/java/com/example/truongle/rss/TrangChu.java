package com.example.truongle.rss;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.truongle.rss.Adapter.TrangChuAdapter;
import com.example.truongle.rss.Model.News;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by TruongLe on 23/07/2017.
 */

public class TrangChu extends Fragment {

     ArrayList<News> list = new ArrayList<>();
     RecyclerView mRecyclerView;
    TrangChuAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
         View rootView = inflater.inflate(R.layout.trang_chu, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerViewTrangChu);
         //list = getNews();
        News new1 = new News("Ánh Viên thiếu 33% giây để vào bán kết giải bơi VĐTG","s","http://img.f4.thethao.vnecdn.net/2017/07/23/a-4113-1481448034-9240-1500817996_180x108.jpg","Kình ngư số một Việt Nam ra quân không thành công khi dự giải vô địch thế giới 2017 tại Hungary.","sf");
        News new2 = new News("Man City mua xong Danilo, hoàn tất cuộc cách mạng hàng thủ","ds","http://img.f1.thethao.vnecdn.net/2017/07/23/1-2952-1500816747_180x108.jpg","Hậu vệ phải người Brazil vừa ký hợp đồng năm năm với đội chủ sân Etihad sau vụ chuyển nhượng từ Real Madrid.","sd");
        News new3 = new News("Ánh Viên thiếu 33% giây để vào bán kết giải bơi VĐTG","s","http://img.f4.thethao.vnecdn.net/2017/07/23/a-4113-1481448034-9240-1500817996_180x108.jpg","Kình ngư số một Việt Nam ra quân không thành công khi dự giải vô địch thế giới 2017 tại Hungary.","sf");
        News new4 = new News("Man City mua xong Danilo, hoàn tất cuộc cách mạng hàng thủ","ds","http://img.f1.thethao.vnecdn.net/2017/07/23/1-2952-1500816747_180x108.jpg","Hậu vệ phải người Brazil vừa ký hợp đồng năm năm với đội chủ sân Etihad sau vụ chuyển nhượng từ Real Madrid.","sd");
        News new5 = new News("Ánh Viên thiếu 33% giây để vào bán kết giải bơi VĐTG","s","http://img.f4.thethao.vnecdn.net/2017/07/23/a-4113-1481448034-9240-1500817996_180x108.jpg","Kình ngư số một Việt Nam ra quân không thành công khi dự giải vô địch thế giới 2017 tại Hungary.","sf");
        News new6 = new News("Man City mua xong Danilo, hoàn tất cuộc cách mạng hàng thủ","ds","http://img.f1.thethao.vnecdn.net/2017/07/23/1-2952-1500816747_180x108.jpg","Hậu vệ phải người Brazil vừa ký hợp đồng năm năm với đội chủ sân Etihad sau vụ chuyển nhượng từ Real Madrid.","sd");
        News new7 = new News("Ánh Viên thiếu 33% giây để vào bán kết giải bơi VĐTG","s","http://img.f4.thethao.vnecdn.net/2017/07/23/a-4113-1481448034-9240-1500817996_180x108.jpg","Kình ngư số một Việt Nam ra quân không thành công khi dự giải vô địch thế giới 2017 tại Hungary.","sf");

        list.add(new1);
        list.add(new2);
        list.add(new3);list.add(new4);list.add(new5);list.add(new6);list.add(new7);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(adapter);
        return rootView;
    }

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new TrangChuAdapter(list, getContext());
    }

    public ArrayList<News> getNews(){
        ArrayList<News> listNews = new ArrayList<>();
        ArrayList<String> stringList = readTxtFromAsset(getContext(),"tinmoi.txt");

        for(int i=0;i<stringList.size()/5;i++)
        {
            String title = stringList.get(i*5);
            String link = stringList.get(i*5+1);
            String image_link = stringList.get(i*5+2);
            String description = stringList.get(i*5+3);
            String date = stringList.get(i*5+4);
            listNews.add(new News(title,link, image_link, description,date));
        }
        return listNews;
    }
    public ArrayList<String> readTxtFromAsset(Context context, String fileName){
        ArrayList<String> list = new ArrayList<>();
        String str="";
        try {
            InputStream inputStream= context.getResources().getAssets().open(fileName);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
            BufferedReader bufferedReader  = new BufferedReader(inputStreamReader);
            while((str = bufferedReader.readLine())!=null){
                list.add(str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }
}
