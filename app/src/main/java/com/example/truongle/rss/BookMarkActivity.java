package com.example.truongle.rss;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.truongle.rss.Adapter.TrangChuAdapter;
import com.example.truongle.rss.Model.News;

import java.util.ArrayList;

import io.realm.RealmResults;

public class BookMarkActivity extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_mark);

        toolbar = (Toolbar) findViewById(R.id.toolbar_bookmark);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewBookMark);
        RealmDB realmDB = new RealmDB(BookMarkActivity.this);
        ArrayList<News> list = new ArrayList<>(realmDB.getAllNews());
        TrangChuAdapter adapter = new TrangChuAdapter(list, BookMarkActivity.this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }
}
