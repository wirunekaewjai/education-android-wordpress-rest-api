package com.devdayo.wp;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.devdayo.wp.model.Content;
import com.devdayo.wp.model.ContentModel;
import com.devdayo.wp.model.Model;
import com.devdayo.wp.model.Post;
import com.devdayo.wp.model.PostModel;

import java.util.ArrayList;
import java.util.HashMap;

public class PostActivity extends Activity implements Model.Callback<Content>
{
    private ContentModel model;

    private LinearLayout htmlLayout;
    private TextView title;
    private TextView date;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        getActionBar().hide();

        htmlLayout = (LinearLayout) findViewById(R.id.htmlLayout);
        title = (TextView) findViewById(R.id.title);
        date = (TextView) findViewById(R.id.date);

        if(null == savedInstanceState)
            savedInstanceState = getIntent().getBundleExtra("bundle");

        model = ContentModel.getInstance();
        model.setCallback(this);
        model.load(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        model.save(outState);
    }

    @Override
    public void onLoaded(Content content)
    {
        title.setText(content.getTitle());
        date.setText(content.getDate());

        for (int i = 0; i < content.size(); i++)
        {
            HashMap<String, String> item = content.get(i);
            loadView(item);
        }
    }

    private void loadView(HashMap<String, String> item)
    {
        String type = item.get("type");
        String value = item.get("value");

        if(type.equals("image"))
        {
            ImageView view = new ImageView(getApplicationContext());

            htmlLayout.addView(view);

            int rId = R.dimen.activity_vertical_margin;
            int margin = (int) getResources().getDimension(rId);

            ((LinearLayout.LayoutParams) view.getLayoutParams()).setMargins(0, margin, 0, margin);

            Glide.with(getApplicationContext()).load(value).dontAnimate().diskCacheStrategy(DiskCacheStrategy.ALL).into(view);
        }
        else if(type.equals("text"))
        {
            TextView view = new TextView(getApplicationContext());

            htmlLayout.addView(view);

            int rId = R.dimen.activity_vertical_margin;
            int margin = (int) getResources().getDimension(rId);

            ((LinearLayout.LayoutParams) view.getLayoutParams()).setMargins(margin, 0, margin, 0);

            view.setTextColor(Color.BLACK);
            view.setText(Html.fromHtml(value));

            //view.setBackgroundColor(Color.TRANSPARENT);
            //view.loadData(value, "text/html; charset=UTF-8", null);
            //view.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
    }
}
