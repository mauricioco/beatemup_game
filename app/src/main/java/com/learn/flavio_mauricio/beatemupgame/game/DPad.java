package com.learn.flavio_mauricio.beatemupgame.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Directional Pad class representation.
 */
public class DPad {
    private Bitmap bmp;
    private int posX;
    private int posY;
    private float scale;

    public DPad(Bitmap bmp, int x, int y, float scale) {
        this.bmp = bmp;
        this.posX = x;
        this.posY = y;
        this.scale = scale;
    }

    public int[] getButtonPressed(float x, float y) {
        int result[] = {0, 0};
        //DeadZone
        float splitX = bmp.getWidth() * scale / 3;
        float splitY = bmp.getHeight() * scale / 3;
        float deadZoneX[] = {splitX, splitX * 2};
        float deadZoneY[] = {splitY, splitY * 2};

        // checking first if touch is inside dpad area...
        if (x > posX && x < posX + bmp.getWidth() * scale &&
                y > posY && y < posY + bmp.getHeight() * scale) {
            //Mapping Touchable area
            //Left Button
            if ((x >= posX) && (x <= posX + deadZoneX[0])) {
                result[0] = -1;
            }
            //Right Button
            if ((x >= posX + deadZoneX[1]) && (x <= (bmp.getWidth() * scale + posX))) {
                result[0] = 1;
            }
            //Up Button
            if ((y >= posY) && (y <= posY + deadZoneY[0])) {
                result[1] = -1;
            }
            //Down Button
            if ((y >= posY + deadZoneY[1]) && (y <= posY + bmp.getHeight() * scale)) {
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
                posX+(int)(bmp.getWidth()*scale), posY+(int)(bmp.getHeight()*scale));
        canvas.drawBitmap(bmp, null, bmpRect, paint);
    }
}
