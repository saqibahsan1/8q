package com.q8safemobile.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;

import com.q8safemobile.R;
import com.q8safemobile.utils.TypefaceCache;


/**
 * Created by ahsan on 27/05/2018.
 */

public class EditTextCustomFont extends AppCompatEditText {


    public EditTextCustomFont(Context context) {
        this(context, null);
    }

    public EditTextCustomFont(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EditTextCustomFont(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs);
        if (this.isInEditMode()) return;

        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.TextViewCustomFont,
                0, 0);

        try {
            final String customFont = a.getString(R.styleable.TextViewCustomFont_fontName);
            //Build a custom typeface-cache here!
            this.setTypeface(TypefaceCache.get(context.getAssets(), "fonts/" + customFont));


        } finally {
            a.recycle();
        }


    }

}