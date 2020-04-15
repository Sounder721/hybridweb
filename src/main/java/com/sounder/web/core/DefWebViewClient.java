package com.sounder.web.core;

import android.graphics.Bitmap;
import android.net.http.SslError;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class DefWebViewClient extends WebViewClient {

    private HybridClient mHybridClient;

    public DefWebViewClient(HybridClient client) {
        this.mHybridClient = client;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        return mHybridClient.shouldOverrideUrlLoading(request);
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        return mHybridClient.shouldOverrideUrlLoading(url);
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        mHybridClient.onPageStarted(url, favicon);
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        mHybridClient.onPageFinished(url);
    }

    @Override
    public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
        mHybridClient.onReceivedError(request, error);
    }

    @Override
    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
        mHybridClient.onReceivedSslError(handler, error);
    }

    @Override
    public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
        mHybridClient.onReceivedHttpError(request, errorResponse);
    }

    public void recycle() {
        mHybridClient = null;
    }
}
