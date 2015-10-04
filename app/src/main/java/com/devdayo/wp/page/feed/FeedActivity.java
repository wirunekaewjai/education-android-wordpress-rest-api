package com.devdayo.wp.page.feed;

import android.content.Intent;
import android.os.Bundle;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.devdayo.wp.R;
import com.devdayo.wp.page.post.PostActivity;

/**
 * Created by Wirune Kaewjai on 10/3/2015.
 */
public class FeedActivity extends AppCompatActivity
{
    private SwipeRefreshLayout swipeLayout;
    private RecyclerView listView;

    private LinearLayoutManager layoutManager;

    private FeedAdapter adapter;
    private FeedService service;
    private FeedModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        model = new FeedModel();
        service = new FeedService(model);
        adapter = new FeedAdapter(model);

        layoutManager = new LinearLayoutManager(this);

        swipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipeLayout);

        listView = (RecyclerView) findViewById(R.id.listView);
        listView.setLayoutManager(layoutManager);
        listView.setAdapter(adapter);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);

    }

    @Override
    protected void onStart()
    {
        super.onStart();

        model.subscribe(onDataChangedListener);
        adapter.setOnItemClickListener(onItemClickListener);
        swipeLayout.setOnRefreshListener(onRefreshListener);
        listView.addOnScrollListener(onScrollListener);

        service.fetchOlder();
    }

    @Override
    protected void onStop()
    {
        super.onStop();

        model.unSubscribe(onDataChangedListener);
        adapter.setOnItemClickListener(null);
        swipeLayout.setOnRefreshListener(null);
        listView.removeOnScrollListener(onScrollListener);
    }

    FeedModel.OnDataChangedListener onDataChangedListener = new FeedModel.OnDataChangedListener()
    {
        @Override
        public void onFetchedAndAddedToTop(int count)
        {
            swipeLayout.setRefreshing(false);
            adapter.notifyDataSetChanged();
        }

        @Override
        public void onFetchedAndAddedToBottom(int count)
        {
            if(count == 0)
            {
                adapter.setFooterEnabled(false);
            }

            adapter.notifyDataSetChanged();
        }
    };

    SwipeRefreshLayout.OnRefreshListener onRefreshListener = new SwipeRefreshLayout.OnRefreshListener()
    {
        @Override
        public void onRefresh()
        {
            service.fetchNewer();
        }
    };

    RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener()
    {
        int currentSize;

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState)
        {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy)
        {
            super.onScrolled(recyclerView, dx, dy);

            int lastPosition = layoutManager.findLastVisibleItemPosition();
            int size = adapter.getItemCount();

            if (lastPosition + 1 >= size && currentSize != size && adapter.isFooterEnabled())
            {
                currentSize = size;
                service.fetchOlder();
            }
        }
    };

    FeedAdapter.OnItemClickListener onItemClickListener = new FeedAdapter.OnItemClickListener()
    {
        @Override
        public void onItemClick(View view)
        {
            int position = listView.getChildAdapterPosition(view);
            FeedItem item = model.get(position);

            Intent intent = new Intent(getApplicationContext(), PostActivity.class);
            intent.putExtra("id", item.getId());

            startActivity(intent);

//            System.out.println("Click: "+item.getTitle());
        }
    };
}
