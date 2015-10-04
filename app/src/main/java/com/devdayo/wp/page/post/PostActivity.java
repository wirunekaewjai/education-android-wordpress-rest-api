package com.devdayo.wp.page.post;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.devdayo.wp.R;
import com.github.ksoichiro.android.observablescrollview.ObservableRecyclerView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;


/**
 * Created by Wirune Kaewjai on 10/4/2015.
 */
public class PostActivity extends AppCompatActivity
{
    private RelativeLayout contentLayout;
    private SwipeRefreshLayout swipeLayout;
    private ObservableRecyclerView listView;
    private ProgressBar progressBar;

    private LinearLayoutManager layoutManager;

    private PostAdapter adapter;
    private PostService service;
    private PostModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        ActionBar actionBar = getSupportActionBar();
        if(null != actionBar)
        {
            actionBar.hide();
        }

        int id = getIntent().getIntExtra("id", 0);

        model = new PostModel(id);
        service = new PostService(model);
        adapter = new PostAdapter(model);

        layoutManager = new LinearLayoutManager(this);

        contentLayout = (RelativeLayout) findViewById(R.id.contentLayout);
        swipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipeLayout);
        listView = (ObservableRecyclerView) findViewById(R.id.listView);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        listView.setLayoutManager(layoutManager);
        listView.setAdapter(adapter);
    }

    @Override
    protected void onStart()
    {
        super.onStart();

        model.subscribe(onDataChangedListener);
        swipeLayout.setOnRefreshListener(onRefreshListener);
        listView.setScrollViewCallbacks(observableScrollViewCallbacks);

        service.fetch();
    }

    @Override
    protected void onStop()
    {
        super.onStop();

        model.unSubscribe(onDataChangedListener);
        swipeLayout.setOnRefreshListener(null);
        listView.setScrollViewCallbacks(null);
    }

    private void setupActionBar(Post post)
    {
        String title = post.getTitle();

        if(title.length() > 20)
        {
            title = title.substring(0, 20) + "...";
        }

        ActionBar actionBar = getSupportActionBar();
        if(null != actionBar)
        {
            actionBar.setTitle(title);

            if(post.hasThumbnail())
            {
                ColorDrawable colorDrawable = new ColorDrawable(Color.TRANSPARENT);
                actionBar.setBackgroundDrawable(colorDrawable);
            }

            actionBar.show();
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu)
//    {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_post, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item)
//    {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings)
//        {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    PostModel.OnDataChangedListener onDataChangedListener = new PostModel.OnDataChangedListener()
    {
        @Override
        public void onPostChanged(Post post)
        {
            setupActionBar(post);

            adapter.notifyDataSetChanged();
            contentLayout.removeView(progressBar);
            swipeLayout.setRefreshing(false);
        }
    };

    SwipeRefreshLayout.OnRefreshListener onRefreshListener = new SwipeRefreshLayout.OnRefreshListener()
    {
        @Override
        public void onRefresh()
        {
            service.fetch();
        }
    };

    ObservableScrollViewCallbacks observableScrollViewCallbacks = new ObservableScrollViewCallbacks()
    {
        @Override
        public void onScrollChanged(int i, boolean b, boolean b1)
        {

        }

        @Override
        public void onDownMotionEvent()
        {

        }

        @Override
        public void onUpOrCancelMotionEvent(ScrollState scrollState)
        {
            ActionBar actionBar = getSupportActionBar();

            if(null == actionBar)
                return;

            if (scrollState == ScrollState.UP)
            {
                if (actionBar.isShowing())
                {
                    actionBar.hide();
                }
            }
            else if (scrollState == ScrollState.DOWN)
            {
                if (!actionBar.isShowing())
                {
                    actionBar.show();
                }
            }
        }
    };
}
