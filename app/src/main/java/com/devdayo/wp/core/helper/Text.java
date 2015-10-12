package com.devdayo.wp.core.helper;

import android.content.res.Resources;

import java.io.InputStream;
import java.util.Scanner;

/**
 * Created by Wirune Kaewjai on 10/4/2015.
 */
public final class Text
{
    public static String read(Resources res, int rawResourceId)
    {
        InputStream in = res.openRawResource(rawResourceId);
        Scanner scanner = new Scanner(in);

        StringBuilder builder = new StringBuilder();

        while(scanner.hasNextLine())
        {
            builder.append(scanner.nextLine());
        }

        scanner.close();
        return builder.toString().trim();
    }
}
