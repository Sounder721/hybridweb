package com.sounder.web.core;

import android.graphics.Bitmap;
import android.webkit.ConsoleMessage;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

public class DefWebChromeClient extends WebChromeClient {
    private HybridClient mHybridClient;

    public DefWebChromeClient(HybridClient client) {
        this.mHybridClient = client;
    }

    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        mHybridClient.onProgressChanged(newProgress);
    }

    @Override
    public void onReceivedTitle(WebView view, String title) {
        mHybridClient.onReceivedTitle(title);
    }

    @Override
    public void onReceivedIcon(WebView view, Bitmap icon) {
        mHybridClient.onReceivedIcon(icon);
    }

    @Override
    public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
        if (!mHybridClient.onJsAlert(url, message, result))
            return super.onJsAlert(view, url, message, result);
        return true;
    }

    @Override
    public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
        if (!mHybridClient.onJsConfirm(url, message, result))
            return super.onJsConfirm(view, url, message, result);
        else
            return true;
    }

    @Override
    public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
        if (!mHybridClient.onJsPrompt(url, message, defaultValue, result))
            return super.onJsPrompt(view, url, message, defaultValue, result);
        return true;
    }

    @Override
    public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
        if (!mHybridClient.onConsoleMessage(consoleMessage.message(), consoleMessage.lineNumber(), consoleMessage.sourceId()))
            return super.onConsoleMessage(consoleMessage);
        else
            return true;
    }

    @Override
    public void onConsoleMessage(String message, int lineNumber, String sourceID) {
        mHybridClient.onConsoleMessage(message, lineNumber, sourceID);
    }

    public void recycle() {
        mHybridClient = null;
    }
}
