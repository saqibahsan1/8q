package com.q8safemobile.utils;

import android.content.res.AssetManager;
import android.graphics.Typeface;

import java.util.Hashtable;

/**
 * Created by ahsan on 27/05/2018.
 */

public class TypefaceCache {

    private static final Hashtable<String, Typeface> CACHE = new Hashtable<String, Typeface>();

    public static Typeface get(AssetManager manager, String name)
    {

        synchronized (CACHE)
        {

            if (!CACHE.containsKey(name))
            {

                try
                {

                    Typeface t = Typeface.createFromAsset(manager, name);
                    CACHE.put(name, t);

                } catch (Exception e)
                {
                    return null;
                }


            }
            return CACHE.get(name);
        }

    }
}