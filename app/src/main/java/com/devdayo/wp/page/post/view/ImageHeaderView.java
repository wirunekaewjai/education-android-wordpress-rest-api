package com.devdayo.wp.page.post.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.devdayo.wp.R;

/**
 * Created by Wirune on 10/4/2015.
 */
public class ImageHeaderView extends ImageView
{
    public ImageHeaderView(Context context)
    {
        super(context);
        setAdjustViewBounds(true);
        setScaleType(ScaleType.FIT_CENTER);

//        int[] attrs = { android.R.attr.actionBarSize };
//        final TypedArray styledAttributes = getContext().getTheme().obtainStyledAttributes(attrs);
//        int actionBarSize = (int) styledAttributes.getDimension(0, 0);
//
//        setPadding(0, actionBarSize, 0, 0);
//
//        styledAttributes.recycle();
    }

    public void setImageURL(String url)
    {
        setPadding(0, 0, 0, 0);
        Glide.with(getContext()).load(url).fitCenter().diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.drawable.place_holder_image).into(this);
    }
}
