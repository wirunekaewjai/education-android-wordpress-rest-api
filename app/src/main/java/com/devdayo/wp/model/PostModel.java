package com.devdayo.wp.model;

import android.os.Bundle;

import com.devdayo.wp.HttpTask;

import java.util.ArrayList;

/**
 * Created by wirune on 9/8/15 AD.
 */
public class PostModel extends Model<ArrayList<Post>> implements HttpTask.Callback
{
    private static final String BASE_URL = "wirunedayo.wordpress.com";
    private static final String POST_URL = "https://public-api.wordpress.com/rest/v1.1/sites/%1$s/posts/";

    private ArrayList<Post> posts;

    private PostModel()
    {

    }

    @Override
    public void load(Bundle savedInstanceState)
    {
        super.load(savedInstanceState);

        if(null == savedInstanceState)
        {
            System.out.println("Load from Server");

            String url = String.format(POST_URL, BASE_URL);

            HttpTask task = new HttpTask(this);
            task.execute(url);
        }
        else
        {
            System.out.println("Load from Cache");

            String json = savedInstanceState.getString("posts");
            onHttpResponse(json);
        }
    }

    @Override
    public void save(Bundle outState)
    {
        super.save(outState);

        String json = Post.toJson(posts);
        outState.putString("posts", json);
    }

    @Override
    public void destroy()
    {
        super.destroy();
        posts = null;
    }

    @Override
    public void onHttpResponse(String response)
    {
        posts = Post.fromJson(response);
        notify(posts);
    }

    private static PostModel instance = null;
    public static PostModel getInstance()
    {
        if(null == instance)
            instance = new PostModel();

        return instance;
    }


}
