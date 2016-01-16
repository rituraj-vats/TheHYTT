package com.bitscanvas.thehytt.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by lsingh013 on 20/12/15 AD.
 */
public class DroidSansTextView extends TextView {

    public DroidSansTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setTypeface(Typeface.createFromAsset(context.getAssets(),"droid_sans.ttf"));
    }
}
