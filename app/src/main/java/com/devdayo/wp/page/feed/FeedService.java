package com.devdayo.wp.page.feed;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.devdayo.wp.core.App;
import com.devdayo.wp.core.Plugin;
import com.devdayo.wp.core.helper.QueryString;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Wirune Kaewjai on 10/3/2015.
 */
public abstract class FeedService
{
    protected final FeedModel model;
    private boolean fetching;

    protected FeedService(FeedModel model)
    {
        this.model = model;
    }

    public abstract void fetchNewer();
    public abstract void fetchOlder();

    protected abstract void onFetchNewerSuccess(ArrayList<FeedItem> fetchedItems);
    protected abstract void onFetchOlderSuccess(ArrayList<FeedItem> fetchedItems);

    protected void fetch(String baseUrl, HashMap<String, Object> queryParameters, final boolean fetchNewer)
    {
        if(fetching)
            return;

        String queryString = QueryString.create(queryParameters);
        String url = baseUrl + queryString;
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

                    if(fetchNewer)
                    {
//                        model.addToTop(fetchedItems);
                        onFetchNewerSuccess(fetchedItems);
                    }
                    else
                    {
//                        model.addToBottom(fetchedItems);
                        onFetchOlderSuccess(fetchedItems);
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

    public static FeedService create(FeedModel model)
    {
        Plugin plugin = App.getPlugin();

        if(plugin == Plugin.JETPACK)
            return new JetPackFeedService(model);

        return new JsonApiFeedService(model);
    }
}

class JetPackFeedService extends FeedService
{
    private final String FIELDS = "ID,title,excerpt,featured_image,date";
//    private final String ORDER_BY = "ID,date";
    private final String BASE_URL = App.API_URL + "/posts";

    protected JetPackFeedService(FeedModel model)
    {
        super(model);
    }

    @Override
    public void fetchNewer()
    {
        HashMap<String, Object> params = new HashMap<>();
        params.put("number", 100);
        params.put("fields", FIELDS);
//        params.put("order", "DESC");
//        params.put("order_by", ORDER_BY);

        FeedItem item = model.first();
        if(null != item)
        {
            params.put("after", item.getDate());
        }

        fetch(BASE_URL, params, true);
    }

    @Override
    public void fetchOlder()
    {
        HashMap<String, Object> params = new HashMap<>();
        params.put("offset", model.count());
        params.put("number", 20);
        params.put("fields", FIELDS);

//        params.put("order", "DESC");
//        params.put("order_by", ORDER_BY);

//        FeedItem item = model.last();
//        if(null != item)
//        {
////            System.out.println("Before: "+item.getTitle());
//            params.put("before", item.getDate());
//        }

        fetch(BASE_URL, params, false);
    }

    @Override
    protected void onFetchNewerSuccess(ArrayList<FeedItem> fetchedItems)
    {
        model.addToTop(fetchedItems);
    }

    @Override
    protected void onFetchOlderSuccess(ArrayList<FeedItem> fetchedItems)
    {
        model.addToBottom(fetchedItems);
    }
}

class JsonApiFeedService extends FeedService
{
    private final String FIELDS = "id,title,excerpt,featured_image,date";
    private final String BASE_URL = App.API_URL + "/get_posts";

    protected JsonApiFeedService(FeedModel model)
    {
        super(model);
    }

    @Override
    public void fetchNewer()
    {

    }

    @Override
    public void fetchOlder()
    {
        HashMap<String, Object> params = new HashMap<>();
        params.put("offset", model.count());
        params.put("number", 20);
        params.put("fields", FIELDS);

        fetch(BASE_URL, params, false);
    }

    @Override
    protected void onFetchNewerSuccess(ArrayList<FeedItem> fetchedItems)
    {

    }

    @Override
    protected void onFetchOlderSuccess(ArrayList<FeedItem> fetchedItems)
    {
        model.addToBottom(fetchedItems);
    }
}
