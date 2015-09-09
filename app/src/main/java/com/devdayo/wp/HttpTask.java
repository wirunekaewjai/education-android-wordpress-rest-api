package com.devdayo.wp;

import android.os.AsyncTask;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * Created by wirune on 9/8/15 AD.
 */
public class HttpTask extends AsyncTask<String, Void, String>
{
    public interface Callback
    {
        void onHttpResponse(String response);
    }

    private Callback callback;

    public HttpTask(Callback callback)
    {
        this.callback = callback;
    }

    @Override
    protected String doInBackground(String... params)
    {
        String url = params[0];

        OkHttpClient client = new OkHttpClient();

        Request.Builder builder = new Request.Builder();
        Request request = builder.url(url).build();

        try
        {
            Response response = client.newCall(request).execute();
            return response.body().string();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(String result)
    {
        super.onPostExecute(result);

        if(null != result && null != callback)
            callback.onHttpResponse(result);
    }
}
