package com.devdayo.wp.page.post.view;

import android.content.Context;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.TypedValue;
import android.widget.TextView;

import com.devdayo.wp.R;

/**
 * Created by Wirune Kaewjai on 10/4/2015.
 */
public class TextContentView extends TextView
{
    private static final int BASE_TEXT_SIZE = 14; // sp unit

    public TextContentView(Context context)
    {
        super(context);

        int dp = (int) getResources().getDimension(R.dimen.activity_horizontal_margin);
        setPadding(dp, dp, dp, dp);

        // Enable <a href=""/>
        setMovementMethod(LinkMovementMethod.getInstance());
    }

    private static CharSequence trim(CharSequence sequence)
    {
        int start = 0;
        int end = sequence.length();

        while(start < end && Character.isWhitespace(sequence.charAt(start)))
        {
            start++;
        }

        while (end > start && Character.isWhitespace(sequence.charAt(end - 1)))
        {
            end--;
        }

        return sequence.subSequence(start, end);
    }

    public void setContent(String content, Style style)
    {
        CharSequence seq = Html.fromHtml(content);
        seq = trim(seq);

        setText(seq);

        float size = BASE_TEXT_SIZE;

        if(style == Style.H1)
        {
            size *= 1.4f;
        }
        else if(style == Style.H2)
        {
            size *= 1.34f;
        }
        else if(style == Style.H3)
        {
            size *= 1.28f;
        }
        else if(style == Style.H4)
        {
            size *= 1.22f;
        }
        else if(style == Style.H5)
        {
            size *= 1.16f;
        }
        else if(style == Style.H6)
        {
            size *= 1.1f;
        }
        else if(style == Style.SMALL)
        {
            size *= 0.8f;
        }

        setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
    }

    public enum Style
    {
        H1, H2, H3, H4, H5, H6, P, SMALL
    }
}
