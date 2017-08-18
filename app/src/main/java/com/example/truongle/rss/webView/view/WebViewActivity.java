package com.example.truongle.rss.webView.view;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.truongle.rss.R;
import com.example.truongle.rss.util.DataPreferences;
import com.example.truongle.rss.webView.presenter.PresenterLogicWebView;

public class WebViewActivity extends AppCompatActivity implements ViewWebView{

    WebView webView;
    WebSettings webSettings;
    ProgressDialog progressDialog;
    Toolbar toolbar;
    Dialog dialog;
    int zoom=0;
    int currentZoom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        toolbar = (Toolbar) findViewById(R.id.toolbarWebView);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        progressDialog = new ProgressDialog(this);
        Intent intent = getIntent();
        String link = intent.getStringExtra("link");
        webView = (WebView) findViewById(R.id.WebView);

        webSettings = webView.getSettings();
        String zoom = DataPreferences.getPreferences(getApplicationContext(),"zoom","zoom");
        if(!zoom.equals("")){
            currentZoom = Integer.parseInt(zoom);
            webSettings.setTextZoom(currentZoom);
        }
        else{
            currentZoom = webSettings.getTextZoom();
        }
        PresenterLogicWebView presenterLogicWebView = new PresenterLogicWebView(this);
        presenterLogicWebView.onLoadWebView(webView, link);
    }

    @Override
    public void onShowDialog() {
        progressDialog.setMessage("Please wait a few minute...");
        progressDialog.show();
        progressDialog.setCancelable(false);
    }

    @Override
    public void onDismissDialog() {
        progressDialog.dismiss();
    }

    @Override
    public void onLoadPageFail() {
        Toast.makeText(getApplicationContext(), "Load that bai", Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu_webview,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.action_zoom){
                showDialog();
        }
        return super.onOptionsItemSelected(item);
    }

    private void showDialog(){
        dialog = new Dialog(WebViewActivity.this);
        dialog.setTitle("Zoom");
        dialog.setContentView(R.layout.activity_webview_zoom);
        dialog.show();
        Button btnZoomIn = (Button) dialog.findViewById(R.id.btnZoomIn);
        Button btnZoomOut = (Button) dialog.findViewById(R.id.btnZoomOut);
        final TextView txtZoom = (TextView) dialog.findViewById(R.id.textViewZoom);
        btnZoomIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentZoom = currentZoom+10;
                webSettings.setTextZoom(currentZoom);
                txtZoom.setText(currentZoom+"");
                DataPreferences.savePreferences(getApplicationContext(),"fontNews","font",currentZoom+"");

            }
        });
        btnZoomOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentZoom = currentZoom-10;
                webSettings.setTextZoom(currentZoom);
                txtZoom.setText(currentZoom+"");
                DataPreferences.savePreferences(getApplicationContext(),"fontNews","font",currentZoom+"");
            }
        });
    }
//    private String getPreferencesZoom() {
//        SharedPreferences pre = getSharedPreferences("zoom", MODE_PRIVATE);
//        String zoom = pre.getString("zoom","");
//        return zoom;
//    }
//
//    private void savePreferencesZoom(String zoom) {
//        SharedPreferences pre = getSharedPreferences("zoom", MODE_PRIVATE);
//        SharedPreferences.Editor edit = pre.edit();
//        edit.putString("zoom",zoom);
//        edit.commit();
//
//    }
}
