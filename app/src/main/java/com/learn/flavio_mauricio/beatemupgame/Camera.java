package com.learn.flavio_mauricio.beatemupgame;

import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;

import com.learn.flavio_mauricio.beatemupgame.graphic.GraphicManager;
import com.learn.flavio_mauricio.beatemupgame.graphic.Sprite;
import com.learn.flavio_mauricio.beatemupgame.logic.Actor;
import com.learn.flavio_mauricio.beatemupgame.logic.GameMap;

import java.util.ArrayList;

/**
 * This class represents the game camera. In the future, this will even scroll!!
 * Currently, it only shows one unscrollable screen.
 */
public class Camera {

    private int bgAnim = 0;

    private GameMap activeMap;
    private int width;
    private int height;
    private Actor actorToFollow;
    private DPad dPad;

    public Camera(int width, int height, GameMap activeMap, Actor actorToFollow, Resources resources) {
        this.width = width;
        this.height = height;
        this.activeMap = activeMap; // preferably, this map should have the same width and height of this camera.
        this.actorToFollow = actorToFollow;
        float scale = width/320;
        this.dPad = new DPad(BitmapFactory.decodeResource(resources, R.drawable.button_dpad), 0, 0, 40, height);
    }

    public int[] getButtonPressed(float x, float y) {
        return dPad.getButtonPressed(x, y);
    }

    public GameMap getActiveMap() {
        return activeMap;
    }

    void drawBackground(Canvas canvas, Paint paint) {
        //PointF actorPos = activeMap.getActorPos(actorToFollow);
        //PointF beginDraw = new PointF(actorPos.x-width/2, actorPos.y-height/2);
        //PointF endDraw = new PointF(beginDraw.x+width, beginDraw.y+height);

        Sprite sprite = GraphicManager.getSprite(activeMap.getBackground());
        Rect bmpRect0 = new Rect(-bgAnim, 0, width-bgAnim, height);
        Rect bmpRect1 = new Rect(width-bgAnim, 0, 2*width-bgAnim, height);

        bgAnim+=1;
        if(width-bgAnim == 0) {
            bgAnim=0;
        }

        canvas.drawBitmap(sprite.update(), null, bmpRect0, paint);
        canvas.drawBitmap(sprite.update(), null, bmpRect1, paint);
    }

    void drawFloor(Canvas canvas, Paint paint) {
        //PointF actorPos = activeMap.getActorPos(actorToFollow);
        //PointF beginDraw = new PointF(actorPos.x-width/2, actorPos.y-height/2);
        //PointF endDraw = new PointF(beginDraw.x+width, beginDraw.y+height);
        Sprite sprite = GraphicManager.getSprite(activeMap.getFloor());

        Rect bmpRect0 = new Rect(0, (int)activeMap.getFloor().getFloorLimit(), width, height);
        //Rect bmpRect1 = new Rect(0, 0, width, (int)activeMap.getFloor().getFloorLimit());

        canvas.drawBitmap(sprite.update(), null, bmpRect0, paint);
    }

    void drawActors(Canvas canvas, Paint paint) {
        //paint.setColor(Color.TRANSPARENT);
        ArrayList<Actor> renderList = activeMap.getRenderList();
        for(Actor actor : renderList) {
            Sprite sprite = GraphicManager.getSprite(actor);
            PointF pos = activeMap.getActorPos(actor);
            int left = (int)pos.x-(int)(actor.getWidth()*(width/320)/2);
            int top = (int)pos.y-(int)actor.getHeight()*(height/240)/2;
            int right = (int)pos.x+(int)actor.getWidth()*(width/320)/2;
            int bottom = (int)pos.y+(int)actor.getHeight()*(height/240)/2;
            Rect bmpRect = new Rect(left, top, right, bottom);
            if(actor.getDx() == 0 && actor.getDy() == 0) {  // is not animated
                canvas.drawBitmap(sprite.update(), null, bmpRect, paint);
            }else{  // is animated
                canvas.drawBitmap(sprite.updateAnim(), null, bmpRect, paint);
            }

        }
    }

    void drawControls(Canvas canvas, Paint paint) {
        dPad.Draw(canvas, paint);
    }
}
