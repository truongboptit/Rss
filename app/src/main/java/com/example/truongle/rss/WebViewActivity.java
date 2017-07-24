package com.example.truongle.rss;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class WebViewActivity extends AppCompatActivity {

    WebView webView;
    ProgressDialog progressDialog;
    Toolbar toolbar;
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
        progressDialog.setMessage("Please wait a few minute...");
        progressDialog.show();
        progressDialog.setCancelable(false);
        Intent intent = getIntent();
        String link = intent.getStringExtra("link");
        webView = (WebView) findViewById(R.id.WebView);
        if(link!= null){
            webView.setWebViewClient(loadWebViewClient);
            webView.loadUrl(link);
        }


    }
    private WebViewClient loadWebViewClient = new WebViewClient(){
        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
            progressDialog.dismiss();
            Toast.makeText(getApplicationContext(), "Load that bai", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            progressDialog.dismiss();
        }
    };
}
