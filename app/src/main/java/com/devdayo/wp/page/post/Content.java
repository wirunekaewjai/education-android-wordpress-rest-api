package com.devdayo.wp.page.post;

import java.util.ArrayList;

/**
 * Created by Wirune Kaewjai on 10/4/2015.
 */
public class Content
{
    private Type type;
    private String value;

    public Type getType()
    {
        return type;
    }

    public String getValue()
    {
        return value;
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
//            else if(line.contains("<a"))
//            {
//                Content content = new Content();
//                content.type = Type.LINK_TEXT;
//                content.value = line;
//
//                contents.add(content);
//            }
            else if(line.contains("<img"))
            {
                String temp = line.split("src=\"")[1];
                String src = temp.split("\"")[0];

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

    public enum Type
    {
        TEXT, IMAGE, LINK_TEXT, LINK_IMAGE, IFRAME
    }
}
