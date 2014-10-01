package com.learn.flavio_mauricio.beatemupgame.game;

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
    private int posX;
    private int posY;
    private float size = 200;

    public DPad(Bitmap bmp, int x, int y, float scale) {
        this.bmp = bmp;
        this.posX = x;
        this.posY = y;
        float scaled = bmp.getWidth() * scale;
        if (scaled < this.size) {
            this.size = scaled;
        }
    }

    public int[] getButtonPressed(float x, float y) {
        int result[] = {0, 0};
        //DeadZone
        float splitX = size / 3;
        float splitY = size / 3;
        float deadZoneX[] = {splitX, splitX * 2};
        float deadZoneY[] = {splitY, splitY * 2};

        // checking first if touch is inside dpad area...
        if (x > posX && x < posX + size &&
                y > posY && y < posY + size) {
            //Mapping Touchable area
            //Left Button
            if ((x >= posX) && (x <= posX + deadZoneX[0])) {
                result[0] = -1;
            }
            //Right Button
            if ((x >= posX + deadZoneX[1]) && (x <= (size + posX))) {
                result[0] = 1;
            }
            //Up Button
            if ((y >= posY) && (y <= posY + deadZoneY[0])) {
                result[1] = -1;
            }
            //Down Button
            if ((y >= posY + deadZoneY[1]) && (y <= posY + size)) {
                result[1] = 1;
            }
        }
        return result;

    }

    public void Draw(Canvas canvas, Paint paint) {
        /* TODO
            find a appropriable place for this method later.
         */
        Rect bmpRect = new Rect(posX, posY,
                posX+(int)(size), posY+(int)(size));
        canvas.drawBitmap(bmp, null, bmpRect, paint);
    }
}
