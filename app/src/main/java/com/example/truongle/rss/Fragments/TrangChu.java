package com.example.truongle.rss.Fragments;

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
import com.example.truongle.rss.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import io.realm.RealmResults;

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
        News new1 = new News("Nhật Bản - thiên đường của những người làm mất đồ","http://vnexpress.net/tin-tuc/the-gioi/cuoc-song-do-day/nhat-ban-thien-duong-cua-nhung-nguoi-lam-mat-do-3617394.html","http://img.f29.vnecdn.net/2017/07/24/toptop-1500886387_180x108.jpg","Không phải ngẫu nhiên mà đất nước mặt trời mọc được mệnh danh là một trong những quốc gia trung thực nhất thế giới","Mon, 24 Jul 2017 19:00:00 +0700");
        News new2 = new News("Apple ra mắt iPhone SE2 tháng 8, iPhone 8 vào tháng 10","http://sohoa.vnexpress.net/tin-tuc/san-pham/apple-ra-mat-iphone-se2-thang-8-iphone-8-vao-thang-10-3617735.html","http://img.f8.sohoa.vnecdn.net/2017/07/24/iphone-se-colors-demo-copy-1789-1500889113_180x108.png","Apple vẫn trình làng phiên bản tiếp theo của iPhone SE trong năm nay, nhưng sẽ khiến iPhone 8 ra mắt chậm hơn.","Mon, 24 Jul 2017 19:00:00 +0700");
        News new3 = new News("Nhật Bản - thiên đường của những người làm mất đồ","http://vnexpress.net/tin-tuc/the-gioi/cuoc-song-do-day/nhat-ban-thien-duong-cua-nhung-nguoi-lam-mat-do-3617394.html","http://img.f29.vnecdn.net/2017/07/24/toptop-1500886387_180x108.jpg","Không phải ngẫu nhiên mà đất nước mặt trời mọc được mệnh danh là một trong những quốc gia trung thực nhất thế giới","Mon, 24 Jul 2017 19:00:00 +0700");
        News new4 = new News("Apple ra mắt iPhone SE2 tháng 8, iPhone 8 vào tháng 10","http://sohoa.vnexpress.net/tin-tuc/san-pham/apple-ra-mat-iphone-se2-thang-8-iphone-8-vao-thang-10-3617735.html","http://img.f8.sohoa.vnecdn.net/2017/07/24/iphone-se-colors-demo-copy-1789-1500889113_180x108.png","Apple vẫn trình làng phiên bản tiếp theo của iPhone SE trong năm nay, nhưng sẽ khiến iPhone 8 ra mắt chậm hơn.","Mon, 24 Jul 2017 19:00:00 +0700");
        News new5 = new News("Nhật Bản - thiên đường của những người làm mất đồ","http://vnexpress.net/tin-tuc/the-gioi/cuoc-song-do-day/nhat-ban-thien-duong-cua-nhung-nguoi-lam-mat-do-3617394.html","http://img.f29.vnecdn.net/2017/07/24/toptop-1500886387_180x108.jpg","Không phải ngẫu nhiên mà đất nước mặt trời mọc được mệnh danh là một trong những quốc gia trung thực nhất thế giới","Mon, 24 Jul 2017 19:00:00 +0700");
        News new6 = new News("Apple ra mắt iPhone SE2 tháng 8, iPhone 8 vào tháng 10","http://sohoa.vnexpress.net/tin-tuc/san-pham/apple-ra-mat-iphone-se2-thang-8-iphone-8-vao-thang-10-3617735.html","http://img.f8.sohoa.vnecdn.net/2017/07/24/iphone-se-colors-demo-copy-1789-1500889113_180x108.png","Apple vẫn trình làng phiên bản tiếp theo của iPhone SE trong năm nay, nhưng sẽ khiến iPhone 8 ra mắt chậm hơn.","Mon, 24 Jul 2017 19:00:00 +0700");
        News new7 = new News("Nhật Bản - thiên đường của những người làm mất đồ","http://vnexpress.net/tin-tuc/the-gioi/cuoc-song-do-day/nhat-ban-thien-duong-cua-nhung-nguoi-lam-mat-do-3617394.html","http://img.f29.vnecdn.net/2017/07/24/toptop-1500886387_180x108.jpg","Không phải ngẫu nhiên mà đất nước mặt trời mọc được mệnh danh là một trong những quốc gia trung thực nhất thế giới","Mon, 24 Jul 2017 19:00:00 +0700");

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
