package com.learn.flavio_mauricio.beatemupgame;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;

import java.sql.SQLOutput;

/**
 * Directional Pad class representation.
 */
public class DPad {
    private Bitmap bmp;
    private float posX;
    private float posY;
    private float scaleX;
    private float scaleY;

    public DPad(Bitmap bmp, float x, float y, float scale, int windowHeight){
        this.bmp = bmp;
        this.posX = 0;
        this.posY = 0;
        this.scaleY = windowHeight*(scale/100);
        this.scaleX = (bmp.getWidth()/bmp.getHeight())*scaleY;
        System.out.println(scaleX);
        System.out.println(scaleY);
    }

    public int[] getButtonPressed(float x, float y) {
        int result[] = {0, 0};
        //DeadZone
        float splitX = scaleX / 3;
        float splitY = scaleY / 3;
        float deadZoneX[] = {splitX, splitX * 2};
        float deadZoneY[] = {splitY, splitY * 2};

        // checking first if touch is inside dpad area...
        if (x > posX && x < posX + scaleX &&
                y > posY && y < posY + scaleY) {
            //Mapping Touchable area
            //Left Button
            if ((x >= posX) && (x <= posX + deadZoneX[0])) {
                result[0] = -1;
            }
            //Right Button
            if ((x >= posX + deadZoneX[1]) && (x <= (scaleX + posX))) {
                result[0] = 1;
            }
            //Up Button
            if ((y >= posY) && (y <= posY + deadZoneY[0])) {
                result[1] = -1;
            }
            //Down Button
            if ((y >= posY + deadZoneY[1]) && (y <= posY + scaleY)) {
                result[1] = 1;
            }
        }
        return result;

    }

    public void Draw(Canvas canvas, Paint paint) {
        /* TODO
            find a appropriable place for this method later.
         */
        //this.posY = canvas.getHeight() - bmp.getHeight()*scale;
        Rect bmpRect = new Rect((int)posX, (int)posY,
                (int)posX+(int)(scaleX), (int)posY+(int)(scaleY));
        canvas.drawBitmap(bmp, null, bmpRect, paint);

    }
}
