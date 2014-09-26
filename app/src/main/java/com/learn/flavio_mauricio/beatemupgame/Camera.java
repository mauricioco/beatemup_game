package com.learn.flavio_mauricio.beatemupgame;

import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;

import com.learn.flavio_mauricio.beatemupgame.graphic.GraphicManager;
import com.learn.flavio_mauricio.beatemupgame.graphic.Sprite;
import com.learn.flavio_mauricio.beatemupgame.logic.Actor;
import com.learn.flavio_mauricio.beatemupgame.logic.Floor;
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
        float scale = width / 320;
        this.dPad = new DPad(BitmapFactory.decodeResource(resources, R.drawable.button_dpad), 0, 0, scale);
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
        Rect bmpRect0 = new Rect(-bgAnim, 0, width - bgAnim, height);
        Rect bmpRect1 = new Rect(width - bgAnim, 0, 2 * width - bgAnim, height);

        bgAnim += 1;
        if (width - bgAnim == 0) {
            bgAnim = 0;
        }

        canvas.drawBitmap(sprite.update(), null, bmpRect0, paint);
        canvas.drawBitmap(sprite.update(), null, bmpRect1, paint);
    }

    void drawFloor(Canvas canvas, Paint paint) {
        PointF actorPos = activeMap.getActorPos(actorToFollow);
        PointF beginDraw = new PointF(actorPos.x - width / 2, actorPos.y - height / 2);
        PointF centerDraw = new PointF(actorPos.x - width / 2, actorPos.y - height / 2);
        PointF endDraw = new PointF(beginDraw.x + width, beginDraw.y + height);

        // getting bg index. Hero is at index 1. Index 0 is the left floor. Index 2 is right.
        Floor[] floorList = activeMap.getFloorRenderList(actorPos.x);
        int actorFloorX = activeMap.getActorFloorPosX(actorPos.x, actorToFollow);

        System.out.println(floorList);
        if (actorPos.x < floorList[1].getSizeX() / 2) {
            // render center and left floor
            Sprite spriteCenter = GraphicManager.getSprite(floorList[1]);
            int centerFloorLimit = (int) floorList[1].getFloorLimit();
            Rect bmpCenterRect = new Rect(width / 2 - actorFloorX, centerFloorLimit,
                    width / 2 - actorFloorX + spriteCenter.getWidth(), height);
            canvas.drawBitmap(spriteCenter.update(), null, bmpCenterRect, paint);

            Sprite spriteLeft = GraphicManager.getSprite(floorList[0]);
            int leftFloorLimit = (int) floorList[0].getFloorLimit();
            Rect bmpLeftRect = new Rect(width / 2 - actorFloorX - width, leftFloorLimit,
                    width / 2 - actorFloorX, height);
            canvas.drawBitmap(spriteLeft.update(), null, bmpLeftRect, paint);

        } else {
            // render center and right floor
            Sprite spriteCenter = GraphicManager.getSprite(floorList[1]);
            int centerFloorLimit = (int) floorList[1].getFloorLimit();
            Rect bmpCenterRect = new Rect(width / 2 - actorFloorX, centerFloorLimit,
                    width / 2 - actorFloorX + spriteCenter.getWidth(), height);
            canvas.drawBitmap(spriteCenter.update(), null, bmpCenterRect, paint);

            Sprite spriteRight = GraphicManager.getSprite(floorList[2]);
            int rightFloorLimit = (int) floorList[2].getFloorLimit();
            Rect bmpRightRect = new Rect(width / 2 + width - actorFloorX, rightFloorLimit,
                    width / 2 + 2 * width - actorFloorX, height);
            canvas.drawBitmap(spriteRight.update(), null, bmpRightRect, paint);
        }

    }

    void drawActors(Canvas canvas, Paint paint) {
        //paint.setColor(Color.TRANSPARENT);
        ArrayList<Actor> renderList = activeMap.getActorRenderList();
        for (Actor actor : renderList) {
            Sprite sprite = GraphicManager.getSprite(actor);
            PointF pos = activeMap.getActorPos(actor);
            int left = (int) pos.x - (int) (actor.getWidth() * (width / 320) / 2);
            int top = (int) pos.y - (int) actor.getHeight() * (height / 240) / 2;
            int right = (int) pos.x + (int) actor.getWidth() * (width / 320) / 2;
            int bottom = (int) pos.y + (int) actor.getHeight() * (height / 240) / 2;
            Rect bmpRect = new Rect(left, top, right, bottom);
            if (actor.getDx() == 0 && actor.getDy() == 0) {  // is not animated
                canvas.drawBitmap(sprite.update(), null, bmpRect, paint);
            } else {  // is animated
                canvas.drawBitmap(sprite.updateAnim(), null, bmpRect, paint);
            }

        }
    }

    void drawControls(Canvas canvas, Paint paint) {
        dPad.Draw(canvas, paint);
    }

    public int getActorToFollowX() {
        return (int) activeMap.getActorPos(actorToFollow).x;
    }

}
