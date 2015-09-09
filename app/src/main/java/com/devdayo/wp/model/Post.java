package com.devdayo.wp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by wirune on 9/8/15 AD.
 */
public class Post implements Parcelable
{
    @SerializedName("ID")
    private int id;

    private String title;
    private String content;
    private String excerpt;

    @SerializedName("featured_image")
    private String thumbnail;

    private Date date;

    public int getId()
    {
        return id;
    }

    public String getTitle()
    {
        return title;
    }

    public String getContent()
    {
        return content;
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
        DateFormat df = new SimpleDateFormat("dd MMMM yyyy");
        String dateTime = df.format(date);

        return dateTime;
    }


    private void setDate(String dateTime)
    {
        DateFormat df = new SimpleDateFormat("dd MMMM yyyy");

        try
        {
            date = df.parse(dateTime);
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
    }

    public static String toJson(ArrayList<Post> posts)
    {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        return gson.toJson(new Posts(posts));
    }

    public static ArrayList<Post> fromJson(String json)
    {
        // 2015-09-08T00:30:27+00:00
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        return gson.fromJson(json, Posts.class).posts;
    }

    private static class Posts
    {
        public ArrayList<Post> posts;

        public Posts(ArrayList<Post> posts)
        {
            this.posts = posts;
        }
    }



    public Post()
    {

    }

    public Post(Parcel in)
    {
        id = in.readInt();
        title = in.readString();
        excerpt = in.readString();
        content = in.readString();
        thumbnail = in.readString();

        setDate(in.readString());
    }

    public int describeContents()
    {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags)
    {
        out.writeInt(id);
        out.writeString(title);
        out.writeString(excerpt);
        out.writeString(content);
        out.writeString(thumbnail);
        out.writeString(getDate());
    }

    public static final Parcelable.Creator<Post> CREATOR = new Parcelable.Creator<Post>()
    {
        public Post createFromParcel(Parcel in)
        {
            return new Post(in);
        }

        public Post[] newArray(int size)
        {
            return new Post[size];
        }
    };
}
