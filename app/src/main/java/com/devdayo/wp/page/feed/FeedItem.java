package com.devdayo.wp.page.feed;

import android.text.Html;

import com.devdayo.wp.app.App;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Wirune Kaewjai on 10/3/2015.
 */
public class FeedItem
{
    private int id;
    private String title;
    private String excerpt;
    private String thumbnail;
    private String date;

    public int getId()
    {
        return id;
    }

    public String getTitle()
    {
        return title;
    }

    public String getExcerpt()
    {
        return excerpt;
    }

    public String getThumbnail()
    {
        return thumbnail;
    }

    public String getDate()
    {
        return date;
    }

    public String getDate(String pattern)
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern, Locale.getDefault());
        try
        {
            Date dateObject = App.DEFAULT_DATE_FORMAT.parse(date);
            return dateFormat.format(dateObject);
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }

        return date;
    }

    public static ArrayList<FeedItem> parse(JSONArray array)
    {
        ArrayList<FeedItem> items = new ArrayList<>();

        int length = array.length();
        for (int i = 0; i < length; ++i)
        {
            JSONObject object = array.optJSONObject(i);

            FeedItem item = new FeedItem();

            item.id = object.optInt("ID");
            item.title = object.optString("title");
            item.excerpt = Html.fromHtml(object.optString("excerpt")).toString().trim();
            item.thumbnail = object.optString("featured_image");
            item.date = object.optString("date");

            items.add(item);
        }

        return items;
    }
}
