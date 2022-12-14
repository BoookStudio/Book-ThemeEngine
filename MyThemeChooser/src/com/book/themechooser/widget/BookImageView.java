package com.book.themechooser.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

public class bookImageView extends ImageView {
    public bookImageView(Context context) {
        super(context);
    }

    public bookImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public bookImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, 0);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Drawable d = getDrawable();
        if (null == d)
            return;

        if (d instanceof BitmapDrawable) {
            Bitmap bmp = ((BitmapDrawable) d).getBitmap();
            if (null == bmp || bmp.isRecycled()) {
                Log.d("AndroidRuntime", "Can't draw a recycled bitmap");
                return;
            }
        }

        try {
            super.onDraw(canvas);
        } catch (IllegalArgumentException e) {
        }
    }
}
