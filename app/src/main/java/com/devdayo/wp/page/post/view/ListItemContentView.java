package com.devdayo.wp.page.post.view;

import android.content.Context;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.devdayo.wp.R;

/**
 * Created by Wirune Kaewjai on 10/7/2015.
 */
public class ListItemContentView extends LinearLayout
{
    private TextView orderTextView;
    private TextView contentTextView;

    public ListItemContentView(Context context)
    {
        super(context);

        orderTextView = new TextView(context);
        contentTextView = new TextView(context);

        addView(orderTextView);
        addView(contentTextView);

        setOrientation(HORIZONTAL);

        int dp = (int) getResources().getDimension(R.dimen.activity_horizontal_margin);
        setPadding((int)(dp * 1.5f), dp / 2, dp, dp / 2);

        // ViewGroup.LayoutParams.WRAP_CONTENT
        orderTextView.setLayoutParams(new LayoutParams(dp, ViewGroup.LayoutParams.MATCH_PARENT));
        orderTextView.setTextAlignment(TEXT_ALIGNMENT_TEXT_END);
//        orderTextView.setPadding(0, 0, dp / 2, 0);

        // Enable <a href=""/>
        contentTextView.setMovementMethod(LinkMovementMethod.getInstance());
        contentTextView.setTextAlignment(TEXT_ALIGNMENT_TEXT_START);
        contentTextView.setPadding(dp / 4, 0, 0, 0);

        orderTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, TextContentView.BASE_TEXT_SIZE);
        contentTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, TextContentView.BASE_TEXT_SIZE);
    }

    public void setContent(String order, String content)
    {
        orderTextView.setText(order);
        contentTextView.setText(Html.fromHtml(content));
    }

}
