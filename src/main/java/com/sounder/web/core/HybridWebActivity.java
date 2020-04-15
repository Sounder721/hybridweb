package com.sounder.web.core;

import android.graphics.Bitmap;
import android.net.http.SslError;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import java.util.Map;


public abstract class HybridWebActivity extends AppCompatActivity implements HybridClient {

    private OnBackDelegate mBackDelegate;

    @Override
    public void onBackPressed() {
        if (mBackDelegate == null || !mBackDelegate.onBackPressed()) {
            super.onBackPressed();
        }
    }

    protected void addWebView(String url) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        HybridWebFragment fragment = HybridWebFragment.instance(url);
        mBackDelegate = fragment;
        ft.add(getWebViewContainerId(), fragment);
        ft.commitAllowingStateLoss();
    }

    public abstract int getWebViewContainerId();

    @Override
    public void onSetting(WebSettings settings) {

    }

    @Override
    public Map<String, Object> getJavascriptInterfaces() {
        return null;
    }

    @Override
    public Map<String, String> getAdditionalHeader(String url) {
        return null;
    }

    @Override
    public void onProgressChanged(int progress) {

    }

    @Override
    public void onReceivedTitle(String title) {

    }

    @Override
    public void onReceivedIcon(Bitmap icon) {

    }

    @Override
    public boolean onJsAlert(String url, String message, JsResult result) {
        return false;
    }

    @Override
    public boolean onJsConfirm(String url, String message, JsResult result) {
        return false;
    }

    @Override
    public boolean onJsPrompt(String url, String message, String defValue, JsPromptResult result) {
        return false;
    }

    @Override
    public boolean onConsoleMessage(String message, int lineNumber, String sourceID) {
        return false;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebResourceRequest request) {
        return false;
    }

    @Override
    public boolean shouldOverrideUrlLoading(String url) {
        return false;
    }

    @Override
    public void onPageStarted(String url, Bitmap favicon) {

    }

    @Override
    public void onPageFinished(String url) {

    }

    @Override
    public void onReceivedError(WebResourceRequest request, WebResourceError error) {

    }

    @Override
    public void onReceivedSslError(SslErrorHandler handler, SslError error) {

    }

    @Override
    public void onReceivedHttpError(WebResourceRequest request, WebResourceResponse errorResponse) {

    }
}
