package com.devdayo.wp.page.post.view;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.devdayo.wp.R;

/**
 * Created by Wirune Kaewjai on 10/4/2015.
 */
public class ImageContentView extends ImageView
{
    public ImageContentView(Context context)
    {
        super(context);
        setAdjustViewBounds(true);
        setScaleType(ScaleType.FIT_CENTER);
    }

    public void setImageURL(String url)
    {
        Glide.with(getContext()).load(url).fitCenter().diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.drawable.place_holder_image).into(this);
    }
}
