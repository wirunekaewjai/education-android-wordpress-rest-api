package com.devdayo.wp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.devdayo.wp.model.Model;
import com.devdayo.wp.model.Post;
import com.devdayo.wp.model.PostModel;

import java.util.ArrayList;

public class MainActivity extends Activity implements Model.Callback<ArrayList<Post>>, AdapterView.OnItemClickListener
{
    private PostModel model;

    private GridView posts;
    private PostListAdapter postListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getActionBar().hide();

        postListAdapter = new PostListAdapter(getApplicationContext());

        posts = (GridView) findViewById(R.id.posts);
        posts.setAdapter(postListAdapter);
        posts.setOnItemClickListener(this);

        model = PostModel.getInstance();
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
    protected void onDestroy()
    {
        super.onDestroy();
        model.destroy();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        Post post = postListAdapter.getItem(position);

        Bundle bundle = new Bundle();
        bundle.putParcelable("post", post);

        Intent intent = new Intent(getApplicationContext(), PostActivity.class);
        intent.putExtra("bundle", bundle);

        startActivity(intent);
    }

    @Override
    public void onLoaded(ArrayList<Post> posts)
    {
        postListAdapter.addAll(posts);
    }

    private class PostListAdapter extends ArrayAdapter<Post>
    {
        public PostListAdapter(Context context)
        {
            super(context, 0);
        }

        public View onCreateView(ViewGroup parent)
        {
            LayoutInflater inflater = getLayoutInflater();
            View view = inflater.inflate(R.layout.layout_post_item, null, false);

            ViewHolder holder = new ViewHolder(view);
            view.setTag(holder);

            return view;
        }

        public void onBindViewHolder(ViewHolder viewHolder, int position)
        {
            Post post = getItem(position);
            viewHolder.title.setText(post.getTitle());
            viewHolder.excerpt.setText(Html.fromHtml(post.getExcerpt()).toString().trim());
            viewHolder.date.setText(post.getDate());


            String thumbnailUrl = post.getThumbnail();

            if(null != thumbnailUrl && !thumbnailUrl.isEmpty())
                Glide.with(getContext()).load(thumbnailUrl).dontAnimate().diskCacheStrategy(DiskCacheStrategy.ALL).into(viewHolder.thumbnail);
            else
                viewHolder.thumbnail.setImageResource(R.drawable.place_holder);

        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            if(null == convertView)
            {
                convertView = onCreateView(parent);
            }

            ViewHolder viewHolder = (ViewHolder) convertView.getTag();
            onBindViewHolder(viewHolder, position);

            return convertView;
        }

        private class ViewHolder
        {
            public ImageView thumbnail;
            public TextView title;
            public TextView excerpt;
            public TextView date;

            public ViewHolder(View view)
            {
                thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
                title = (TextView) view.findViewById(R.id.title);
                excerpt = (TextView) view.findViewById(R.id.excerpt);
                date = (TextView) view.findViewById(R.id.date);
            }
        }
    }
}
