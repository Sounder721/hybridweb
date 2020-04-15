package com.sounder.web.core;

import android.content.Context;

/**
 * Bridge of JavascriptInterfaces and WebView
 */
public interface JsHandlerHost {
    /**
     * Execute js code by WebView
     *
     * @param js javascript code
     */
    void loadJs(String js);

    /**
     * load url by WebView
     *
     * @param url url
     */
    void loadUrl(String url);

    /**
     * run a runnable in UI thread
     *
     * @param runnable runnable
     */
    void post(Runnable runnable);

    /**
     * run a runnable in UI thread after a white
     *
     * @param runnable runnable
     * @param delay    delay in milliseconds
     */
    void postDelayed(Runnable runnable, long delay);

    Context getContext();
}
