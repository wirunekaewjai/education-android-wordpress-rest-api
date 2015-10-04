package com.devdayo.wp.helper;

import android.util.DisplayMetrics;
import android.view.View;

/**
 * Created by Wirune on 10/4/2015.
 */
public final class Size
{
    public static int[] calculate(View target, float aspectRatio)
    {
        DisplayMetrics metrics = target.getResources().getDisplayMetrics();

        int widthPX = target.getWidth();

        int width = (int)(widthPX / (metrics.densityDpi / 160f));
        int height = (int)(width / aspectRatio);

        return new int[] { width, height };
    }
}
