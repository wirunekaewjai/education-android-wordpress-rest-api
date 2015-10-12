package com.devdayo.wp.core;

import android.app.Application;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.RetryPolicy;
import com.android.volley.toolbox.Volley;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by Wirune Kaewjai on 10/4/2015.
 */
public class App extends Application
{
    public static final String API_URL = "https://public-api.wordpress.com/rest/v1.1/sites/devdayo.com";
//    public static final String API_URL = "https://public-api.wordpress.com/rest/v1.1/sites/wirunedayoblog.wordpress.com";

    private static final SimpleDateFormat JSON_API_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
    private static final SimpleDateFormat JETPACK_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZZZZZ", Locale.getDefault());

    private static App instance;
    private RequestQueue requestQueue;
    private RetryPolicy retryPolicy;

    private Plugin plugin = Plugin.JETPACK;

    @Override
    public void onCreate()
    {
        super.onCreate();

        instance = this;
        retryPolicy = new DefaultRetryPolicy(1000 * 60, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);

        if(!API_URL.contains("public-api.wordpress.com"))
        {
            plugin = Plugin.JSON_API;
        }
    }

    private RequestQueue getRequestQueue()
    {
        if(null == requestQueue)
        {
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return requestQueue;
    }

    public static synchronized <T> void request(Request<T> request)
    {
        request.setRetryPolicy(instance.retryPolicy);
        instance.getRequestQueue().add(request);
    }

    public static synchronized Plugin getPlugin()
    {
        return instance.plugin;
    }

    public static synchronized SimpleDateFormat getDateFormat()
    {
        return (instance.plugin == Plugin.JETPACK) ? JETPACK_DATE_FORMAT : JSON_API_DATE_FORMAT;
    }
}
