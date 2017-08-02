package com.example.truongle.rss.weather.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.truongle.rss.R;
import com.example.truongle.rss.weather.model.RecyclerViewWeatherModel;
import com.example.truongle.rss.weather.model.WeatherOnWeekModel.Example;
import com.example.truongle.rss.weather.model.current_model.CurrentDataResponse;
import com.example.truongle.rss.weather.model.current_model.Weather;
import com.example.truongle.rss.weather.presenter.BuildRetrofit;
import com.example.truongle.rss.weather.presenter.WeatherAdapter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class WeatherActivity extends AppCompatActivity {

    TextView txtHumidity, txtTemp, txtWind, txtSunRise, txtSunSet, txtMain, txtCurrentDate;
    ImageView imgIconWeather;
    Spinner spnLocation;
    ArrayList<RecyclerViewWeatherModel> listWeather = new ArrayList<>() ;
    Toolbar toolbar;
    public static String iconUrl = "http://openweathermap.org/img/w/";
    public static String TAG = "AAA";
    String[] days = {"Sun","Mon","Tue","Wed","Thu","Fri","Sat"};
    String []dates;
    int numberDay=0, k;

    private Retrofit retrofit;
    WeatherAdapter adapter;
    RecyclerView mRecyclerView;
    LinearLayoutManager layoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        addUI();
        addActionBar();
        initSpinner();
        Calendar calendar = Calendar.getInstance();
        numberDay = calendar.get(Calendar.DAY_OF_WEEK);



        retrofit = BuildRetrofit.onBuildRetrofit();
        spnLocation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String location = spnLocation.getSelectedItem().toString();
                getDataCurrent(location);
                getData7Days(location);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void getDataCurrent(String location){
        retrofit.create(apiWeather.class).getWeather(location).enqueue(new Callback<CurrentDataResponse>() {
            @Override
            public void onResponse(Call<CurrentDataResponse> call, Response<CurrentDataResponse> response) {
                List<Weather> listWeather =  response.body().getWeather();
                //current date
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                String currentDateandTime = sdf.format(new Date());
                txtCurrentDate.setText(currentDateandTime);

                Glide.with(getApplication()).load(iconUrl+listWeather.get(0).getIcon()+".png").into(imgIconWeather);
                txtMain.setText(""+listWeather.get(0).getMain());
                txtHumidity.setText(""+response.body().getMain().getHumidity()+" %");
                txtTemp.setText(""+response.body().getMain().getTemp());
                txtWind.setText(""+response.body().getWind().getSpeed()+ " mps");

                SimpleDateFormat sdf2 = new SimpleDateFormat("h:mm a ");
                Date timeSunrise = new Date(response.body().getSys().getSunrise());
                String Sunrise = sdf2.format(timeSunrise);
                txtSunRise.setText(""+Sunrise);
            }

            @Override
            public void onFailure(Call<CurrentDataResponse> call, Throwable t) {
                Log.d("AAA", "onFailure: "+t);
            }
        });
    }
    private String[] getDate(){
        Calendar calendar = Calendar.getInstance();
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.set(Calendar.DAY_OF_WEEK, numberDay);

        String[] dates = new String[7];
        for (int i = 0; i < 7; i++)
        {
            dates[i] = format.format(calendar.getTime());
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        return dates;
    }
    private void getData7Days(String location){
        dates =getDate();
        k= numberDay;
        listWeather.clear();
        retrofit.create(apiWeather.class).getData7Day(location).enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Call<Example> call, Response<Example> response) {
                List<com.example.truongle.rss.weather.model.WeatherOnWeekModel.List> listdata = response.body().getList();
                for(int i=1;i<listdata.size();i++){

                    if(k>=7) k= k-7;
                    String link_icon = iconUrl+listdata.get(i).getWeather().get(0).getIcon()+".png";
                    RecyclerViewWeatherModel model = new RecyclerViewWeatherModel(link_icon,days[k++],dates[i],listdata.get(i).getTemp().getMin()+"-"+listdata.get(i).getTemp().getMax(),listdata.get(i).getWeather().get(0).getDescription());
                    listWeather.add(model);
                    adapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onFailure(Call<Example> call, Throwable t) {

            }
        });
    }
    private void initSpinner() {
        ArrayList<String> listSpinner = new ArrayList<>();
        listSpinner.add("Ha Noi");
        listSpinner.add("Bac Ninh");
        listSpinner.add("Nam Dinh");
        listSpinner.add("Thanh Hoa");
        listSpinner.add("Ho Chi Minh");
        ArrayAdapter adapter1 = new ArrayAdapter(getApplicationContext(), R.layout.spinner_item, listSpinner);
        adapter1.setDropDownViewResource(R.layout.spinner_dropdow);
        spnLocation.setAdapter(adapter1);

    }

    private void addActionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void addUI() {
        txtHumidity = (TextView) findViewById(R.id.humidity);
        txtTemp = (TextView) findViewById(R.id.temperature);
        txtWind = (TextView) findViewById(R.id.wind);
        txtSunRise = (TextView) findViewById(R.id.sunrise);
        txtSunSet = (TextView) findViewById(R.id.sunset);
        imgIconWeather = (ImageView) findViewById(R.id.imageView_IconWeather);
        imgIconWeather.setScaleType(ImageView.ScaleType.FIT_XY);
        txtMain = (TextView) findViewById(R.id.main);
        txtCurrentDate = (TextView) findViewById(R.id.lastUpdateTime);
        toolbar = (Toolbar) findViewById(R.id.toolbar_weather);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerViewWeather7Days);
        adapter = new WeatherAdapter(listWeather, getApplicationContext());
        layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(adapter);

        spnLocation= (Spinner) findViewById(R.id.spinner_localtion);

    }
}
