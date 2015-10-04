package com.devdayo.wp.page.post;

import com.devdayo.wp.app.App;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Wirune Kaewjai on 10/4/2015.
 */
public class Post
{
    private int id;
    private String title;
    private String thumbnail;
    private String date;

    private ArrayList<Content> contents = new ArrayList<>();

    public int getId()
    {
        return id;
    }

    public String getTitle()
    {
        return title;
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

    public ArrayList<Content> getContents()
    {
        return contents;
    }

    public boolean hasThumbnail()
    {
        return null != thumbnail && thumbnail.length() > 0 && !thumbnail.equalsIgnoreCase("null");
    }

    public static Post parse(JSONObject object)
    {
        Post post = new Post();

        post.id = object.optInt("ID");
        post.title = object.optString("title");
        post.contents = Content.parse(object.optString("content").trim());
        post.thumbnail = object.optString("featured_image");
        post.date = object.optString("date");

        return post;
    }
}
