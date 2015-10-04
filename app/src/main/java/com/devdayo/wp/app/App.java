package com.devdayo.wp.app;

import android.app.Application;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by Wirune Kaewjai on 10/4/2015.
 */
public class App extends Application
{
    public static final String API_URL = "https://public-api.wordpress.com/rest/v1.1/sites/100381386";
    public static final SimpleDateFormat DEFAULT_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZZZZZ", Locale.getDefault());

    private static App instance;
    private RequestQueue requestQueue;

    @Override
    public void onCreate()
    {
        super.onCreate();
        instance = this;
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
        instance.getRequestQueue().add(request);
    }
}
