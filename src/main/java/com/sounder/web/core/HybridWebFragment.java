package com.sounder.web.core;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ScrollView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.sounder.web.HybridConfigs;

import java.io.File;
import java.util.Map;

public class HybridWebFragment extends Fragment implements OnBackDelegate, JsHandlerHost {
    public static final String TAG = "HybridWebFragment";
    public static final String URL = "url";
    private Context mContext;
    private WebView mWebView;
    private HybridClient mHybridClient;
    private DefWebViewClient mWebViewClient;
    private DefWebChromeClient mChromeClient;
    private Handler mHandler;
    private String mUrl;

    public static HybridWebFragment instance(String url) {
        Bundle bundle = new Bundle();
        bundle.putString(URL, url);
        HybridWebFragment fragment = new HybridWebFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
        if (context instanceof HybridClient) {
            mHybridClient = (HybridClient) context;
        } else {
            throw new IllegalStateException("Your activity must implement HybridClient");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mWebView = new WebView(mContext);
        return mWebView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mHandler = new Handler();
        initWebView();
        WebSettings settings = mWebView.getSettings();
        defaultWebSetting(settings);
        mHybridClient.onSetting(settings);
        load();
    }

    private void load() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            load(bundle.getString(URL));
        } else {
            Log.w(TAG, "no url found");
        }
    }

    private void load(String url) {
        mUrl = url;
        mWebView.loadUrl(url, mHybridClient.getAdditionalHeader(mUrl));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        recycle();
    }

    @SuppressLint("JavascriptInterface")
    private void initWebView() {
        mChromeClient = new DefWebChromeClient(mHybridClient);
        mWebView.setWebChromeClient(mChromeClient);
        mWebViewClient = new DefWebViewClient(mHybridClient);
        mWebView.setWebViewClient(mWebViewClient);
        mWebView.setScrollBarStyle(ScrollView.SCROLLBARS_INSIDE_OVERLAY);
        mWebView.removeJavascriptInterface("searchBoxJavaBridge_");
        mWebView.removeJavascriptInterface("accessibility");
        mWebView.removeJavascriptInterface("accessibilityTraversal");
        Map<String, Object> interfaces = mHybridClient.getJavascriptInterfaces();
        if (interfaces != null) {
            for (Map.Entry<String, Object> entry : interfaces.entrySet()) {
                mWebView.addJavascriptInterface(entry.getValue(), entry.getKey());
            }
        }
        WebView.setWebContentsDebuggingEnabled(HybridConfigs.isDebug());
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void defaultWebSetting(WebSettings settings) {
        settings.setBuiltInZoomControls(false);
        settings.setSupportZoom(false);
        settings.setSaveFormData(false);
        settings.setSavePassword(false);
        settings.setAllowFileAccess(true);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setLoadsImagesAutomatically(true);
        settings.setJavaScriptEnabled(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setDomStorageEnabled(true);
        settings.setDatabaseEnabled(true);
        settings.setDatabasePath(mContext.getApplicationContext().getDatabasePath("webview").getAbsolutePath());
        settings.setAppCacheEnabled(true);
        settings.setAppCachePath(mContext.getApplicationContext().getCacheDir().getAbsolutePath()
                + File.separator + "webview");
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        settings.setGeolocationEnabled(true);
        settings.setMediaPlaybackRequiresUserGesture(false);
        settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        settings.setAllowUniversalAccessFromFileURLs(true);
    }

    public void recycle() {
        mWebView.stopLoading();
        mWebView.getSettings().setJavaScriptEnabled(false);
        mWebView.clearHistory();
        mWebView.removeAllViews();
        mWebView.setWebViewClient(null);
        mWebView.setWebChromeClient(null);
        mWebViewClient.recycle();
        mChromeClient.recycle();
        Map<String, Object> interfaces = mHybridClient.getJavascriptInterfaces();
        if (interfaces != null) {
            for (Map.Entry<String, Object> entry : interfaces.entrySet()) {
                mWebView.removeJavascriptInterface(entry.getKey());
            }
        }
        mHybridClient = null;
        mWebView = null;
    }

    @Override
    public boolean onBackPressed() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        }
        return false;
    }

    @Override
    public void loadJs(String js) {
        mWebView.loadUrl(js);
    }

    @Override
    public void loadUrl(String url) {
        load(url);
    }

    @Override
    public void post(Runnable runnable) {
        mHandler.post(runnable);
    }

    @Override
    public void postDelayed(Runnable runnable, long delay) {
        mHandler.postDelayed(runnable, delay);
    }
}
