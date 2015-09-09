package com.devdayo.wp.model;

import android.os.Bundle;

import com.google.gson.Gson;

/**
 * Created by wirune on 9/8/15 AD.
 */
public class ContentModel extends Model<Content>
{
    private Content content;

    private ContentModel()
    {

    }

    @Override
    public void setCallback(Callback<Content> callback)
    {
        super.setCallback(callback);
    }

    @Override
    public void load(Bundle savedInstanceState)
    {
        super.load(savedInstanceState);

        if(savedInstanceState.containsKey("content"))
        {
            String json = savedInstanceState.getString("content");
            Gson gson = new Gson();

            content = gson.fromJson(json, Content.class);
            notify(content);
        }
        else if(savedInstanceState.containsKey("post"))
        {
            Post post = savedInstanceState.getParcelable("post");

            content = new Content();
            content.setTitle(post.getTitle());
            content.setDate(post.getDate());

            String text = post.getContent();
            String[] lines = text.split("\n");

            for(String line : lines)
            {
                if(line.contains("<img"))
                {
                    String url = line.split("src=\"")[1].split("\"")[0];
                    content.addImage(url);
                }
                else
                {
                    content.addText(line);
                }
            }

            notify(content);
        }
    }

    @Override
    public void save(Bundle outState)
    {
        super.save(outState);

        Gson gson = new Gson();
        String json = gson.toJson(content);

        outState.putString("content", json);
    }

    @Override
    public void destroy()
    {
        super.destroy();
    }

    @Override
    public void notify(Content content)
    {
        super.notify(content);
    }


    private static ContentModel instance = null;
    public static ContentModel getInstance()
    {
        if(null == instance)
            instance = new ContentModel();

        return instance;
    }

}
