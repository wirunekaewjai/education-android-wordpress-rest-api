package com.devdayo.wp.page.post;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Wirune Kaewjai on 10/4/2015.
 */
public class Content
{
    private Type type;
    private HashMap<String, String> vars = new HashMap<>();

    public Type getType()
    {
        return type;
    }

    public String get(String name)
    {
        return vars.get(name);
    }

    public static ArrayList<Content> parse(String contentString)
    {
        ArrayList<Content> contents = new ArrayList<>();

        String[] lines = contentString.split("\n");
        int length = lines.length;

        for (int index = 0; index < length; ++index)
        {
            String line = lines[index];

            if(line.contains("<iframe"))
            {
                Content content = new Content();
                content.type = Type.IFRAME;
                content.vars.put("value", line.trim());

                contents.add(content);

//                System.out.println("iframe: " + line);
            }
            else if(line.contains("<a") && line.contains("<img"))
            {
                Content content = new Content();
                content.type = Type.LINK_IMAGE;
                content.vars.put("src", getElementAttribute(line, "src"));
                content.vars.put("href", getElementAttribute(line, "href"));

                contents.add(content);
            }
            else if(line.contains("<img"))
            {
                Content content = new Content();
                content.type = Type.IMAGE;
                content.vars.put("src", getElementAttribute(line, "src"));

                contents.add(content);

//                System.out.println("img: " + line);
            }
            else if(line.contains("<p") || line.contains("<h"))
            {
                Content content = new Content();
                content.type = Type.TEXT;
                content.vars.put("value", line.trim());

                contents.add(content);

//                System.out.println("value: " + line);
            }
            else if(line.contains("<ol"))
            {
//                Content conten Tt = new Content();
//                content.type =ype.TEXT;
//                content.value = getList(lines, line, "ol", index + 1);

//                contents.add(content);

//                System.out.println("ol: "+content.value);

                readListItem(contents, lines, "ol", index);
            }
            else if(line.contains("<ul"))
            {
//                Content content = new Content();
//                content.type = Type.TEXT;
//                content.value = getList(lines, line, "ul", index + 1);
//
//                contents.add(content);
//                System.out.println("ul: " + content.value);

                readListItem(contents, lines, "ul", index);
            }
        }

        return contents;
    }

    private static void readListItem(ArrayList<Content> contents, String[] lines, String tag, int startIndex)
    {
        Type type = (tag.equalsIgnoreCase("ol")) ? Type.ORDERED_LIST_ITEM : Type.UNORDERED_LIST_ITEM;

        int index = startIndex;
        int length = lines.length;

        while(index < length)
        {
            String line = lines[++index];

            if(line.contains("</" + tag))
                break;

            Content content = new Content();
            content.type = type;
            content.vars.put("value", line.trim());
            content.vars.put("order", (index - startIndex) + "");

            contents.add(content);
        }
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
        TEXT, IMAGE, LINK_TEXT, LINK_IMAGE, IFRAME, ORDERED_LIST_ITEM, UNORDERED_LIST_ITEM
    }
}
