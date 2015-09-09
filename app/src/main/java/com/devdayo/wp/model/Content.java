package com.devdayo.wp.model;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by wirune on 9/8/15 AD.
 */
public class Content
{
    private String title;
    private String date;

    private ArrayList<HashMap<String, String>> contents;

    public Content()
    {
        contents = new ArrayList<>();
    }

    public String getTitle()
    {
        return title;
    }

    public String getDate()
    {
        return date;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public void setDate(String date)
    {
        this.date = date;
    }

    public void addText(String text)
    {
        HashMap<String, String> item = new HashMap<>();

        item.put("type", "text");
        item.put("value", text);

        contents.add(item);
    }

    public void addImage(String url)
    {
        HashMap<String, String> item = new HashMap<>();

        item.put("type", "image");
        item.put("value", url);

        contents.add(item);
    }

    public int size()
    {
        return contents.size();
    }

    public HashMap<String, String> get(int index)
    {
        return contents.get(index);
    }
}
