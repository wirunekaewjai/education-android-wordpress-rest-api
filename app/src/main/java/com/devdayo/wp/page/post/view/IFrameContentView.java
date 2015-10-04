package com.devdayo.wp.page.post.view;

import android.content.Context;
import android.graphics.Color;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.devdayo.wp.R;
import com.devdayo.wp.helper.Size;
import com.devdayo.wp.helper.Text;

/**
 * Created by Wirune Kaewjai on 10/4/2015.
 */
public class IFrameContentView extends WebView
{
    private int contentInstanceId = Integer.MIN_VALUE;

    public IFrameContentView(Context context)
    {
        super(context);

        WebSettings settings = getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setAllowFileAccess(true);

        setBackgroundColor(Color.TRANSPARENT);
        setLayerType(LAYER_TYPE_SOFTWARE, null);
    }

    public void setContent(int contentInstanceId, final String html)
    {
        if(this.contentInstanceId == contentInstanceId)
            return;

        this.contentInstanceId = contentInstanceId;

        this.post(new Runnable()
        {
            @Override
            public void run()
            {
                String raw = Text.read(getResources(), R.raw.iframe);
                int[] size = Size.calculate(IFrameContentView.this, 16f / 9f);

                String iframe = raw.replace("{$content}", html);
                iframe = iframe.replace("{$iframe-width}", size[0] + "px");
                iframe = iframe.replace("{$iframe-height}", size[1] + "px");

                loadData(iframe, "text/html; charset=UTF-8", null);
            }
        });
    }
}
