package com.devdayo.wp.page.post;

import com.devdayo.wp.base.Observable;

import java.util.HashMap;

/**
 * Created by Wirune Kaewjai on 10/4/2015.
 */
public class PostModel extends Observable<PostModel.OnDataChangedListener>
{
    public static final String CMD_SET_POST = "SET_POST";
    public static final String VALUE_POST = "POST";

    private Post post;
    private int id;

    public PostModel(int id)
    {
        this.id = id;
    }

    public int getId()
    {
        return id;
    }

    public void setPost(Post post)
    {
        this.post = post;

        for (OnDataChangedListener subscriber : subscribers)
        {
            subscriber.onPostChanged(post);
        }
    }

    public Post getPost()
    {
        return post;
    }

    public boolean hasPost()
    {
        return null != post;
    }

    public interface OnDataChangedListener
    {
        void onPostChanged(Post post);
    }
}
