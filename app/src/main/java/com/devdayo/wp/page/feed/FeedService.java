package com.devdayo.wp.page.feed;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.devdayo.wp.app.App;
import com.devdayo.wp.helper.QueryString;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Wirune Kaewjai on 10/3/2015.
 */
public class FeedService
{
    private final String FIELDS = "ID,title,excerpt,featured_image,date";
    private final String ORDER_BY = "ID,date";
    private final String BASE_URL = App.API_URL + "/posts";

    private FeedModel model;

    private boolean fetching;

    public FeedService(FeedModel model)
    {
        this.model = model;
    }

    public void fetchNewer()
    {
        HashMap<String, Object> params = new HashMap<>();
        params.put("number", 100);
        params.put("fields", FIELDS);
        params.put("order", "DESC");
        params.put("order_by", ORDER_BY);

        FeedItem item = model.first();
        if(null != item)
        {
            params.put("after", item.getDate());
        }

        fetch(params, true);
    }

    public void fetchOlder()
    {
        HashMap<String, Object> params = new HashMap<>();
        params.put("number", 20);
        params.put("fields", FIELDS);
        params.put("order", "DESC");
        params.put("order_by", ORDER_BY);

        FeedItem item = model.last();
        if(null != item)
        {
//            System.out.println("Before: "+item.getTitle());
            params.put("before", item.getDate());
        }

        fetch(params, false);
    }

    private void fetch(HashMap<String, Object> queryParameters, final boolean addToTop)
    {
        if(fetching)
            return;

        String queryString = QueryString.create(queryParameters);
        String url = BASE_URL + queryString;
//        System.out.println("URL: "+url);

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                try
                {
                    JSONObject json = new JSONObject(response);
                    JSONArray array = json.optJSONArray("posts");

                    ArrayList<FeedItem> fetchedItems = FeedItem.parse(array);

                    if(addToTop)
                    {
                        model.addToTop(fetchedItems);
                    }
                    else
                    {
                        model.addToBottom(fetchedItems);
                    }
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
