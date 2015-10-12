package com.devdayo.wp.page.post;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.devdayo.wp.core.App;
import com.devdayo.wp.core.helper.QueryString;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by Wirune Kaewjai on 10/4/2015.
 */
public class PostService
{
    private final String FIELDS = "ID,title,content,featured_image,date";
    private final String BASE_URL = App.API_URL + "/posts";

    private PostModel model;

    private boolean fetching;

    public PostService(PostModel model)
    {
        this.model = model;
    }

    public void fetch()
    {
        HashMap<String, Object> params = new HashMap<>();
        params.put("fields", FIELDS);

        fetch(params);
    }

    private void fetch(HashMap<String, Object> queryParameters)
    {
        if(fetching)
            return;

        String queryString = QueryString.create(queryParameters);
        String url = BASE_URL + "/" + model.getId() + queryString;
//        System.out.println("URL: "+url);

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                try
                {
                    // Post Data
                    JSONObject object = new JSONObject(response);
                    Post post = Post.parse(object);

                    model.setPost(post);
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }

                fetching = false;
            }
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                System.out.println(error.getMessage());
                fetching = false;
            }
        });

        fetching = true;
        App.request(request);
    }
}
