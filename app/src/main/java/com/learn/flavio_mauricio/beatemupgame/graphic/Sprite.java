package com.learn.flavio_mauricio.beatemupgame.graphic;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Represents a graphical
 */
public class Sprite {
    /* TODO
        Add more attributes to be manipulated, such as size, animation frames, etc.
     */
    private Bitmap bmp;

    public Sprite(Resources resources, int resourceId) {
        this.bmp = BitmapFactory.decodeResource(resources, resourceId);
    }

    public Bitmap update() {
        return bmp;
    }
}
