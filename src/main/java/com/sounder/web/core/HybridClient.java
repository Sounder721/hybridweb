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

import java.util.Map;

public interface HybridClient {

    void onSetting(WebSettings settings);

    /**
     * 设置自己的javascriptInterface
     */
    Map<String, Object> getJavascriptInterfaces();

    Map<String, String> getAdditionalHeader(String url);

    void onProgressChanged(int progress);

    void onReceivedTitle(String title);

    void onReceivedIcon(Bitmap icon);

    boolean onJsAlert(String url, String message, JsResult result);

    boolean onJsConfirm(String url, String message, JsResult result);

    boolean onJsPrompt(String url, String message, String defValue, JsPromptResult result);

    boolean onConsoleMessage(String message, int lineNumber, String sourceID);

    boolean shouldOverrideUrlLoading(WebResourceRequest request);

    boolean shouldOverrideUrlLoading(String url);

    void onPageStarted(String url, Bitmap favicon);

    void onPageFinished(String url);

    void onReceivedError(WebResourceRequest request, WebResourceError error);

    void onReceivedSslError(SslErrorHandler handler, SslError error);

    void onReceivedHttpError(WebResourceRequest request, WebResourceResponse errorResponse);
}
