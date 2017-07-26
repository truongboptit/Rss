package com.example.truongle.rss.webView.view;

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

import com.example.truongle.rss.R;
import com.example.truongle.rss.webView.presenter.PresenterLogicWebView;
import com.example.truongle.rss.webView.view.ViewWebView;

public class WebViewActivity extends AppCompatActivity implements ViewWebView{

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
        Intent intent = getIntent();
        String link = intent.getStringExtra("link");
        webView = (WebView) findViewById(R.id.WebView);

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
}
