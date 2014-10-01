package com.learn.flavio_mauricio.beatemupgame.logic;

import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;

/**
 * Actors are everything that "follow a script" on the game map.
 * Following a script would be simply showing an animation, damage other actors,
 * bre pickable... etc.
 */
public class Actor extends GameObject {
    protected int width;
    protected int height;
    protected PointF position;
    protected int speed = 4;
    protected float dx = 0;
    protected float dy = 0;
    protected int direction = 1;
    protected int maxLife;
    protected int currentLife;
    protected Rect mask;

    private int cloneCount = 0;

    public Actor(String id, int x, int y, int width, int height) {
        super(id);
        this.position = new PointF(x, y);
        this.width = width;
        this.height = height;
    }

    public float getDx() {
        return dx;
    }

    public float getDy() {
        return dy;
    }

    public Rect getMask() {
        return mask;
    }

    public void setMask(float widthRate, float heightRate) {
        this.mask = new Rect(0, 0, (int) widthRate * width, (int) heightRate * height);
    }

    public PointF getPosition() {
        return position;
    }

    public float getPositionX() {
        return position.x;
    }

    public float getPositionY() {
        return position.y;
    }

    public void setPosition(float x, float y) {
        this.position = new PointF(x, y);
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public int getSpeed() {
        return speed;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setDerivative(float dx, float dy) {
        this.dx = speed*dx;
        this.dy = speed*dy;
    }

    public Actor clone(int x, int y) {
        Actor clone = new Actor(id+"$"+cloneCount, x, y, width, height);
        cloneCount++;
        return clone;
    }

    public Actor clone(int x, int y, int width, int height) {
        Actor clone = new Actor(id+"$"+cloneCount, x, y, width, height);
        cloneCount++;
        return clone;
    }

}