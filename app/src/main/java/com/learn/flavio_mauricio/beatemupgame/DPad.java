package com.learn.flavio_mauricio.beatemupgame;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.SurfaceView;

import com.learn.flavio_mauricio.beatemupgame.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Directional Pad
 */
public class DPad {
    private Bitmap sprite;
    private float posX;
    private float posY;

    public DPad(Bitmap sprt, float x, float y){
        sprite = sprt;
        posX = 0;
        posY = 0;
    }

    public int[] getButtonPressed(float x, float y){
        int result[] = {0, 0};
        //DeadZone
        float splitX = sprite.getWidth()/3;
        float splitY = sprite.getHeight()/3;
        float deadZoneX[] = {splitX, splitX*2};
        float deadZoneY[] = {splitY, splitY*2};


        //Mapping Touchable area
        //Left Button
        if ((x >= posX)&&(x <= posX + deadZoneX[0])){
            result[0] = -1;
        }
        //Right Button
        if ((x >= posX + deadZoneX[1])&&(x <= (sprite.getWidth() + posX))){
            result[0] = 1;
        }
        //Up Button
        if ((y >= posY)&&(y <= posY + deadZoneY[0])){
            result[1] = -1;
        }
        //Down Button
        if ((y >= posY + deadZoneY[1])&&(y <= posY + sprite.getHeight())){
            result[1] = 1;
        }

        return result;

    }

    public void Draw(Canvas canvas, Paint paint) {
        //paint.setAntiAlias(true);
        //paint.setFilterBitmap(true);
        /* TODO
            This little guy creates an object for the Bitmap every loop iteration. This
            cannot happen on future releases!!
         */
        System.out.println("Loop tamanhos");
        System.out.println(canvas.getWidth());
        System.out.println(canvas.getHeight());
        this.posY = canvas.getHeight() - sprite.getHeight();
        canvas.drawBitmap(sprite, posX, posY, paint);

    }
}
