package com.devdayo.wp.page.feed;

import android.support.annotation.NonNull;

import com.devdayo.wp.core.base.Observable;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Wirune Kaewjai on 10/3/2015.
 */
public class FeedModel extends Observable<FeedModel.OnDataChangedListener>
{
    private final ArrayList<FeedItem> items = new ArrayList<>();

    public int count()
    {
        return items.size();
    }

    public FeedItem get(int position)
    {
        if(items.size() > position)
            return items.get(position);

        return null;
    }

    public FeedItem first()
    {
        if(items.isEmpty())
            return null;

        return items.get(0);
    }

    public FeedItem last()
    {
        if(items.isEmpty())
            return null;

        return items.get(items.size() - 1);
    }

    public void addToBottom(@NonNull Collection<FeedItem> collection)
    {
        items.addAll(collection);

        for (OnDataChangedListener subscriber : subscribers)
        {
            subscriber.onFetchedAndAddedToBottom(collection.size());
        }
    }

    public void addToTop(@NonNull Collection<FeedItem> collection)
    {
        items.addAll(0, collection);

        for (OnDataChangedListener subscriber : subscribers)
        {
            subscriber.onFetchedAndAddedToTop(collection.size());
        }
    }

    public interface OnDataChangedListener
    {
        void onFetchedAndAddedToTop(int count);
        void onFetchedAndAddedToBottom(int count);
    }
}
