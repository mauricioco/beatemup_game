package com.learn.flavio_mauricio.beatemupgame.graphic;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.DisplayMetrics;

import com.learn.flavio_mauricio.beatemupgame.logic.Actor;
import com.learn.flavio_mauricio.beatemupgame.logic.Background;
import com.learn.flavio_mauricio.beatemupgame.logic.GameObject;
import com.learn.flavio_mauricio.beatemupgame.logic.IActor;

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

    /**
     * Very crude animation method... but it works!
     * We need to set a timer for when to update the sprite animation.
     * @return
     */
    public Bitmap updateAnim() {
        if(bmpAnim == null) {
            return bmp;
        }else{
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
        }
    }

    public Bitmap update() {
        return bmp;
    }

    public int getWidth() {
        return bmp.getWidth();
    }

    public int getHeight() {
        return bmp.getHeight();
    }
}
