package com.example.truongle.rss.webView.presenter;

import android.content.res.Resources;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.truongle.rss.R;
import com.example.truongle.rss.webView.view.ViewWebView;

/**
 * Created by TruongLe on 26/07/2017.
 */

public class PresenterLogicWebView implements  PresenterImplWebView {
    ViewWebView viewWebView;

    public PresenterLogicWebView(ViewWebView viewWebView) {
        this.viewWebView = viewWebView;
    }

    @Override
    public void onLoadWebView(WebView webView, String url) {
        if(url != null){
            viewWebView.onShowDialog();
            webView.setWebViewClient(loadWebViewClient);
//            WebSettings webSettings = webView.getSettings();
//            webSettings.setTextZoom(webSettings.getTextZoom());
            webView.loadUrl(url);

        }
    }
    private WebViewClient loadWebViewClient = new WebViewClient(){
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);

            viewWebView.onDismissDialog();
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);

            viewWebView.onDismissDialog();
            viewWebView.onLoadPageFail();
        }
    };
}
