package com.devdayo.wp.page.feed;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.devdayo.wp.R;

/**
 * Created by Wirune Kaewjai on 10/3/2015.
 */
public class FeedAdapter extends RecyclerView.Adapter<ViewHolder>
{
    private final int VIEW_TYPE_ITEM = 1;
    private final int VIEW_TYPE_FOOTER = 2;

    private OnItemClickListener onItemClickListener;
    private FeedModel model;

    private boolean footerEnabled = true;

    public FeedAdapter(FeedModel model)
    {
        this.model = model;
    }

    @Override
    public int getItemViewType(int position)
    {
        if(position >= model.count())
        {
            return VIEW_TYPE_FOOTER;
        }

        return VIEW_TYPE_ITEM;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        if(viewType == VIEW_TYPE_ITEM)
        {
            View view = inflater.inflate(R.layout.layout_feed_item, parent, false);
            view.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    if (null != onItemClickListener)
                        onItemClickListener.onItemClick(v);
                }
            });

            return new ItemViewHolder(view);
        }

        View view = inflater.inflate(R.layout.layout_feed_footer, parent, false);
        return new FooterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position)
    {
        int viewType = getItemViewType(position);

        if(viewType == VIEW_TYPE_ITEM)
        {
            ItemViewHolder vh = (ItemViewHolder) viewHolder;
            FeedItem item = model.get(position);

            vh.title.setText(item.getTitle());
            vh.excerpt.setText(item.getExcerpt());
            vh.date.setText(item.getDate("dd MMMM yyyy"));

            Glide.with(vh.thumbnail.getContext()).load(item.getThumbnail()).fitCenter().into(vh.thumbnail);
        }
        else if(viewType == VIEW_TYPE_FOOTER)
        {
            FooterViewHolder vh = (FooterViewHolder) viewHolder;
            vh.progressBar.setIndeterminate(true);
        }
    }

    public void setFooterEnabled(boolean footerEnabled)
    {
        this.footerEnabled = footerEnabled;
        notifyDataSetChanged();
    }

    public boolean isFooterEnabled()
    {
        return footerEnabled;
    }

    @Override
    public int getItemCount()
    {
        return (footerEnabled) ? model.count() + 1 : model.count();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener)
    {
        this.onItemClickListener = onItemClickListener;
    }


    // Sub-Classes
    class ItemViewHolder extends RecyclerView.ViewHolder
    {
        public TextView title;
        public TextView date;
        public TextView excerpt;
        public ImageView thumbnail;

        public ItemViewHolder(View view)
        {
            super(view);

            title = (TextView) view.findViewById(R.id.titleView);
            date = (TextView) view.findViewById(R.id.dateView);
            excerpt = (TextView) view.findViewById(R.id.excerptView);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnailView);
        }
    }

    class FooterViewHolder extends RecyclerView.ViewHolder
    {
        public ProgressBar progressBar;

        public FooterViewHolder(View view)
        {
            super(view);

            progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        }
    }

    public interface OnItemClickListener
    {
        void onItemClick(View view);
    }
}
