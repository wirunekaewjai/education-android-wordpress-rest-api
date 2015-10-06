package com.devdayo.wp.page.post;

import android.text.Html;

import java.util.ArrayList;

/**
 * Created by Wirune Kaewjai on 10/4/2015.
 */
public class Content
{
    private Type type;
    private String value;
    private String href;

    public Type getType()
    {
        return type;
    }

    public String getValue()
    {
        return value;
    }

    public String getHref()
    {
        return href;
    }

    public static ArrayList<Content> parse(String contentString)
    {
        ArrayList<Content> contents = new ArrayList<>();

        String[] lines = contentString.split("\n");
        for (String line : lines)
        {
            if(line.contains("<iframe"))
            {
                Content content = new Content();
                content.type = Type.IFRAME;
                content.value = line.trim();

                contents.add(content);

                System.out.println("iframe: " + line);
            }
            else if(line.contains("<a") && line.contains("<img"))
            {
                Content content = new Content();
                content.type = Type.LINK_IMAGE;
                content.value = getElementAttribute(line, "src");
                content.href = getElementAttribute(line, "href");

                contents.add(content);
            }
            else if(line.contains("<img"))
            {
                String src = getElementAttribute(line, "src");

                Content content = new Content();
                content.type = Type.IMAGE;
                content.value = src;

                contents.add(content);

                System.out.println("img: " + line);
            }
            else if(line.contains("<p") || line.contains("<h"))
            {
                Content content = new Content();
                content.type = Type.TEXT;
                content.value = line.trim();

                contents.add(content);

                System.out.println("value: " + line);
            }
        }

        return contents;
    }

    private static String getElementValue(String text)
    {
        String temp = text.split(">")[1];
        return temp.split("<")[0];
    }

    private static String getElementAttribute(String text, String attributeName)
    {
        String temp = text.split(attributeName+"=\"")[1];
        return temp.split("\"")[0];
    }

    public enum Type
    {
        TEXT, IMAGE, LINK_TEXT, LINK_IMAGE, IFRAME
    }
}
