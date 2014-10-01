package com.learn.flavio_mauricio.beatemupgame.graphic;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.learn.flavio_mauricio.beatemupgame.logic.GameObject;

import java.util.ArrayList;

/**
 * Represents a graphical
 */
public class Sprite {
    /* TODO
        Add more attributes to be manipulated, such as size, animation frames, etc.
     */
    private GameObject gameObject;
    private Bitmap bmp;
    private ArrayList<Bitmap> bmpAnim;
    private int animIndex = 1;
    private boolean still = false;

    private int maxUpdatesPerFrame = 0;
    private int updatesPerFrame = 0;

    public Sprite(GameObject gameObject, Resources resources, int resourceId) {
        this.bmp = BitmapFactory.decodeResource(resources, resourceId);
        this.gameObject = gameObject;
    }

    public void addBmpAnim(Resources resources, int resourceId) {
        if(bmpAnim == null) {
            bmpAnim = new ArrayList<Bitmap>();
            bmpAnim.add(bmp);
        }
        bmpAnim.add(BitmapFactory.decodeResource(resources, resourceId));
    }

    public void setMaxUpdatesPerFrame(int maxUpdatesPerFrame) {
        this.maxUpdatesPerFrame = maxUpdatesPerFrame;
    }

    /**
     * Very crude animation method... but it works!
     * @return the bmp of an animation frame
     */
    public Bitmap updateAnim() {
        if (bmpAnim == null)
            return bmp;
        updatesPerFrame++;
        if(updatesPerFrame >= maxUpdatesPerFrame) {
            updatesPerFrame=0;
            if (still) {
                still = false;
                return bmpAnim.get(0);
            } else {
                still = true;
                if (animIndex >= 3) {
                    animIndex = 1;
                }
                Bitmap toRet = bmpAnim.get(animIndex);
                animIndex++;
                return toRet;
            }
        }else{
            if (still) {
                return bmpAnim.get(0);
            } else {
                if (animIndex >= bmpAnim.size())
                    animIndex = 1;
                return bmpAnim.get(animIndex);
            }
        }
    }

    public Bitmap update() {
        return bmp;
    }

    public int getBmpWidth() {
        return bmp.getWidth();
    }

    public int getBmpHeight() {
        return bmp.getHeight();
    }
}
