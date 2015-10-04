package com.devdayo.wp.page.post;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.devdayo.wp.page.post.view.IFrameContentView;
import com.devdayo.wp.page.post.view.ImageContentView;
import com.devdayo.wp.page.post.view.ImageHeaderView;
import com.devdayo.wp.page.post.view.TextContentView;

import java.util.ArrayList;

/**
 * Created by Wirune on 10/4/2015.
 */
public class PostAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    // Header
    private final int VIEW_TYPE_HEADER = 10000;

    // Content : Text
    private final int VIEW_TYPE_CONTENT_TEXT = 20001;

    // Content : Image
    private final int VIEW_TYPE_CONTENT_IMAGE = 30001;

    // Content : Text + OnClick
    private final int VIEW_TYPE_CONTENT_LINK_TEXT  = 40001;

    // Content : Image + OnClick
    private final int VIEW_TYPE_CONTENT_LINK_IMAGE = 40002;

    // Content : WebView
    private final int VIEW_TYPE_CONTENT_IFRAME  = 50001;

    // Comment
    private final int VIEW_TYPE_COMMENT_TEXT_LEVEL_1  = 90001;
    private final int VIEW_TYPE_COMMENT_INPUT_LEVEL_1 = 90002;
    private final int VIEW_TYPE_COMMENT_TEXT_LEVEL_2  = 90003;
    private final int VIEW_TYPE_COMMENT_INPUT_LEVEL_2 = 90004;

    private PostModel model;

    public PostAdapter(PostModel model)
    {
        this.model = model;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        Context context = parent.getContext();

        if(viewType == VIEW_TYPE_CONTENT_TEXT)
        {
            TextContentView v = new TextContentView(context);
            return new TextContentViewHolder(v);
        }
        else if(viewType == VIEW_TYPE_CONTENT_IMAGE)
        {
            ImageContentView v = new ImageContentView(context);
            return new ImageContentViewHolder(v);
        }
        else if(viewType == VIEW_TYPE_CONTENT_IFRAME)
        {
            IFrameContentView v = new IFrameContentView(context);
            return new IFrameContentViewHolder(v);
        }

        ImageHeaderView v = new ImageHeaderView(context);
        return new ImageHeaderViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
    {
        int viewType = getItemViewType(position);
        int actualPosition = position - 1; // Exclude Thumbnail.

        if(viewType == VIEW_TYPE_HEADER)
        {
            if(model.hasPost())
            {
                ImageHeaderViewHolder vh = (ImageHeaderViewHolder) holder;
                ImageHeaderView v = vh.contentView;

                Post post = model.getPost();

                if(post.hasThumbnail())
                {
                    v.setImageURL(post.getThumbnail());
                }
            }
        }
        else if(viewType == VIEW_TYPE_CONTENT_TEXT)
        {
            TextContentViewHolder vh = (TextContentViewHolder) holder;
            TextContentView v = vh.contentView;

            v.setOnClickListener(null);

            Content content = model.getPost().getContents().get(actualPosition);
            String value = content.getValue();

            if(value.contains("h1"))
            {
                v.setContent(content.getValue(), TextContentView.Style.H1);
            }
            else if(value.contains("h2"))
            {
                v.setContent(content.getValue(), TextContentView.Style.H2);
            }
            else if(value.contains("h3"))
            {
                v.setContent(content.getValue(), TextContentView.Style.H3);
            }
            else if(value.contains("h4"))
            {
                v.setContent(content.getValue(), TextContentView.Style.H4);
            }
            else if(value.contains("h5"))
            {
                v.setContent(content.getValue(), TextContentView.Style.H5);
            }
            else if(value.contains("h6"))
            {
                v.setContent(content.getValue(), TextContentView.Style.H6);
            }
            else
            {
                v.setContent(content.getValue(), TextContentView.Style.P);
            }
        }
        else if(viewType == VIEW_TYPE_CONTENT_IMAGE)
        {
            ImageContentViewHolder vh = (ImageContentViewHolder) holder;
            ImageContentView v = vh.contentView;

            v.setOnClickListener(null);

            Content content = model.getPost().getContents().get(actualPosition);
            String value = content.getValue();

            v.setImageURL(value);
        }
        else if(viewType == VIEW_TYPE_CONTENT_IFRAME)
        {
            IFrameContentViewHolder vh = (IFrameContentViewHolder) holder;
            IFrameContentView v = vh.contentView;

            Content content = model.getPost().getContents().get(actualPosition);
            String value = content.getValue();

            v.setContent(content.hashCode(), value);
        }
    }

    @Override
    public int getItemCount()
    {
        int count = 1;

        if(model.hasPost())
        {
            Post post = model.getPost();

//            count += (post.hasThumbnail()) ? 1 : 0;
            count += post.getContents().size();
        }

        return count;
    }

    @Override
    public int getItemViewType(int position)
    {
        int count = 1;

        // Detect Header
        if(position == 0)
        {
            System.out.println("Header !!!");
            return VIEW_TYPE_HEADER;
        }

        if(model.hasPost())
        {
            Post post = model.getPost();
            ArrayList<Content> contents = post.getContents();

            if(position - count < contents.size())
            {
                Content content = contents.get(position - count);
                Content.Type type = content.getType();

                if(type == Content.Type.TEXT)
                {
                    return VIEW_TYPE_CONTENT_TEXT;
                }
                else if(type == Content.Type.IMAGE)
                {
                    return VIEW_TYPE_CONTENT_IMAGE;
                }
                else if(type == Content.Type.IFRAME)
                {
                    return VIEW_TYPE_CONTENT_IFRAME;
                }
            }

//            count += contents.size();
        }

        return 0;
    }


    // Inner Classes
    private class ImageHeaderViewHolder extends RecyclerView.ViewHolder
    {
        public ImageHeaderView contentView;

        public ImageHeaderViewHolder(ImageHeaderView itemView)
        {
            super(itemView);
            contentView = itemView;
        }
    }

    private class TextContentViewHolder extends RecyclerView.ViewHolder
    {
        public TextContentView contentView;

        public TextContentViewHolder(TextContentView itemView)
        {
            super(itemView);
            contentView = itemView;
        }
    }

    private class ImageContentViewHolder extends RecyclerView.ViewHolder
    {
        public ImageContentView contentView;

        public ImageContentViewHolder(ImageContentView itemView)
        {
            super(itemView);
            contentView = itemView;
        }
    }

    private class IFrameContentViewHolder extends RecyclerView.ViewHolder
    {
        public IFrameContentView contentView;

        public IFrameContentViewHolder(IFrameContentView itemView)
        {
            super(itemView);
            contentView = itemView;
        }
    }

}
