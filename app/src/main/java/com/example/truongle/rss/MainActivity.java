package com.example.truongle.rss;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.truongle.rss.fragments.NewsFragment;
import com.example.truongle.rss.fragments.WorldNewsFragment;
import com.example.truongle.rss.home.presenter.PresenterLogicHome;
import com.example.truongle.rss.home.view.HomeFragment;
import com.example.truongle.rss.weather.model.current_model.Main;
import com.example.truongle.rss.weather.view.WeatherActivity;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    //up code bitbucket
    //git remote and origin https://2TruongLv@bitbucket.org/thctp/rss.git
    //git push -u origin master

    private ViewPager mViewPaper;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    Toolbar toolbar;
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    String fontStyle = "";
    String bigStyle="trang_chu_row_fullscreen";
    String smallStyle = "trang_chu_row";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPaper = (ViewPager) findViewById(R.id.viewPaper);
        mViewPaper.setAdapter(mSectionsPagerAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPaper);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        navigationView = (NavigationView) findViewById(R.id.navigationView);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(Gravity.START);
            }
        });
        navigationView.setNavigationItemSelectedListener(this);
        fontStyle = getPreferences();
        if(fontStyle.equals(""))
        savePreferences(smallStyle);
       // getPreferences();

    }

    private String getPreferences() {
        SharedPreferences pre = getSharedPreferences("fontNews", MODE_PRIVATE);
        String font = pre.getString("font","");
        return font;
    }

    private void savePreferences(String tyle) {
        SharedPreferences pre = getSharedPreferences("fontNews", MODE_PRIVATE);
        SharedPreferences.Editor edit = pre.edit();
        edit.putString("font",tyle);
        edit.commit();

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("AAA", "onPause: ");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("AAA", "onResume: ");
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.nav_bookmark){
            startActivity(new Intent(this, BookMarkActivity.class));
        }else if(id == R.id.nav_weather){
            startActivity(new Intent(this, WeatherActivity.class));
        }else if(id == R.id.big){
            savePreferences(bigStyle);
            startActivity(new Intent(this, MainActivity.class));
        }else if(id == R.id.small){
            savePreferences(smallStyle);
            startActivity(new Intent(this, MainActivity.class));
        }
        drawerLayout.closeDrawer(Gravity.START);
        return true;
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    HomeFragment trangChu = new HomeFragment();
                    return trangChu;
                case 1:
                    NewsFragment tab2 = new NewsFragment();
                    return tab2;
                case 2:
                    WorldNewsFragment tab3 = new WorldNewsFragment();
                    return tab3;

                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Tin Mới";
                case 1:
                    return "Thời Sự";
                case 2:
                    return "Thế Giới";

            }
            return null;
        }
    }


}
