package com.devdayo.wp.model;

import android.os.Bundle;

/**
 * Created by wirune on 9/8/15 AD.
 */
public abstract class Model<T>
{
    public interface Callback<T>
    {
        void onLoaded(T t);
    }

    private Callback<T> callback;

    public void setCallback(Callback<T> callback)
    {
        this.callback = callback;
    }

    public void load(Bundle savedInstanceState)
    {

    }
    public void save(Bundle outState)
    {

    }
    public void destroy()
    {
        callback = null;
    }

    public void notify(T t)
    {
        if(null != callback)
            callback.onLoaded(t);
    }
}
