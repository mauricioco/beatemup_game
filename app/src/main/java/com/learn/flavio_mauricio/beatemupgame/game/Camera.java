package com.learn.flavio_mauricio.beatemupgame.game;

import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;

import com.learn.flavio_mauricio.beatemupgame.R;
import com.learn.flavio_mauricio.beatemupgame.graphic.GraphicManager;
import com.learn.flavio_mauricio.beatemupgame.graphic.Sprite;
import com.learn.flavio_mauricio.beatemupgame.logic.Actor;
import com.learn.flavio_mauricio.beatemupgame.logic.Floor;
import com.learn.flavio_mauricio.beatemupgame.logic.GameMap;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * This class represents the game camera. This even scrolls!!
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
        PointF actorPos = actorToFollow.getPosition();

        // Center floor draw:
        Floor currFloor = activeMap.getFloorAt(actorPos.x);
        float xAtFloor = actorPos.x - activeMap.getFloorOffset(currFloor);
            // Getting position relative to the current floor.
        Sprite spriteCenter = GraphicManager.getSprite(currFloor);
        int centerFloorLimit = (int) currFloor.getFloorLimit();
        int centerStartX = width/2 - (int)xAtFloor;
        int centerEndX = centerStartX + currFloor.getSizeX();
        int centerStartY = centerFloorLimit;
        int centerEndY = centerStartY + height-centerFloorLimit;
        Rect bmpCenterRect = new Rect(centerStartX, centerStartY, centerEndX, centerEndY);
        canvas.drawBitmap(spriteCenter.update(), null, bmpCenterRect, paint);

        if (xAtFloor < currFloor.getSizeX()/2) {
            // Drawing left floor:
            Floor leftFloor = activeMap.getPreviousFloor(currFloor);
            Sprite spriteLeft = GraphicManager.getSprite(leftFloor);
            int leftFloorLimit = (int) leftFloor.getFloorLimit();
            Rect bmpLeftRect = new Rect(centerStartX - leftFloor.getSizeX(), leftFloorLimit,
                    centerStartX, height);
            canvas.drawBitmap(spriteLeft.update(), null, bmpLeftRect, paint);
        }else{
            // Drawing right floor
            Floor rightFloor = activeMap.getNextFloor(currFloor);
            Sprite spriteRight = GraphicManager.getSprite(rightFloor);
            int rightFloorLimit = (int) rightFloor.getFloorLimit();
            Rect bmpRightRect = new Rect(centerEndX, rightFloorLimit,
                    centerEndX + rightFloor.getSizeX(), height);
            canvas.drawBitmap(spriteRight.update(), null, bmpRightRect, paint);

        }
    }

    private boolean isInsideCameraPlusBuff(float x) {
        PointF center = actorToFollow.getPosition();
        return (x >= (center.x - width)) && (x < (center.x + width));
    }

    /**
     * Gets a list of all actors in crescent order of their `y` position.
     * @return
     */
    private ArrayList<Actor> getActorRenderList() {
        ArrayList<Actor> renderList = new ArrayList<Actor>();
        Iterator<Actor> actorIter = activeMap.getActorListIterator();
        renderList.add(actorIter.next());
        while(actorIter.hasNext()) {
            Actor actorToAdd = actorIter.next();
            PointF posToAdd = actorToAdd.getPosition();
            if(isInsideCameraPlusBuff(posToAdd.x)) {
                int j;
                for (j = 0; j < renderList.size(); j++) {
                    Actor actorToCompare = renderList.get(j);
                    PointF posToCompare = actorToCompare.getPosition();
                    if (posToCompare.y > posToAdd.y) {
                        break;
                    }
                }
                renderList.add(j, actorToAdd);
            }
        }
        return renderList;
    }

    void drawActors(Canvas canvas, Paint paint) {
        //paint.setColor(Color.TRANSPARENT);
        ArrayList<Actor> renderList = getActorRenderList();
        PointF actorToFollowPos = actorToFollow.getPosition();
        PointF beginDraw = new PointF(actorToFollowPos.x - width / 2,
                actorToFollowPos.y - height / 2);
        for (Actor actor : renderList) {
            if(actor.equals(actorToFollow)) {
                Sprite sprite = GraphicManager.getSprite(actor);
                PointF pos = actor.getPosition();
                int left = width/2 - (int) (actor.getWidth() * (width / 320) / 2);
                int top = (int)pos.y - (int) actor.getHeight() * (height / 240) / 2;
                int right = width/2 + (int) actor.getWidth() * (width / 320) / 2;
                int bottom = (int)pos.y + (int) actor.getHeight() * (height / 240) / 2;
                Rect bmpRect = new Rect(left, top, right, bottom);
                if (actor.getDx() == 0 && actor.getDy() == 0) {  // is not animated
                    canvas.drawBitmap(sprite.update(), null, bmpRect, paint);
                } else {  // is animated
                    canvas.drawBitmap(sprite.updateAnim(), null, bmpRect, paint);
                }
            }else{
                Sprite sprite = GraphicManager.getSprite(actor);
                PointF pos = actor.getPosition();
                int left = (int) pos.x - (int) (actor.getWidth() * (width / 320) / 2);
                left -= (int) beginDraw.x;
                int top =  (int) pos.y - (int) actor.getHeight() * (height / 240) / 2;
                int right = left + (int) actor.getWidth() * (width / 320);
                int bottom = top + (int) actor.getHeight() * (height / 240);
                Rect bmpRect = new Rect(left, top, right, bottom);
                if (actor.getDx() == 0 && actor.getDy() == 0) {  // is not animated
                    canvas.drawBitmap(sprite.update(), null, bmpRect, paint);
                } else {  // is animated
                    canvas.drawBitmap(sprite.updateAnim(), null, bmpRect, paint);
                }
            }

        }
    }

    void drawControls(Canvas canvas, Paint paint) {
        dPad.Draw(canvas, paint);
    }

    void drawHud(Canvas canvas, Paint paint) {
        canvas.drawText("This will be the hud!!", 0, 25, paint);
    }

    public PointF getActorToFollowPos() {
        return actorToFollow.getPosition();
    }

    public Point getTopLeftCoord() {
        PointF actorToFollowPos = getActorToFollowPos();
        return new Point((int)actorToFollowPos.x-width/2, 0);
    }

    public void startMovingActorToFollowTo(float x, float y) {
        activeMap.startMovingActorTo(actorToFollow, x, y);
    }



}
