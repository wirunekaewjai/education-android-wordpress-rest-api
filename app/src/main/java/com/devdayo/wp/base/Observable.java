package com.devdayo.wp.base;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Wirune Kaewjai on 10/4/2015.
 */
public abstract class Observable<T>
{
    protected final ArrayList<T> subscribers = new ArrayList<>();

    public void subscribe(T subscriber)
    {
        subscribers.add(subscriber);
    }

    public void unSubscribe(T subscriber)
    {
        subscribers.remove(subscriber);
    }

    public void clearSubscribers()
    {
        subscribers.clear();
    }
}
