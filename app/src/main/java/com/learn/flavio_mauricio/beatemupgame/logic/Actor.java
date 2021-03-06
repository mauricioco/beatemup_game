package com.learn.flavio_mauricio.beatemupgame.logic;

import android.graphics.PointF;

/**
 * Actors are everything that "follow a script" on the game map.
 * Following a script would be simply showing an animation, damage other actors,
 * bre pickable... etc.
 */
public class Actor extends GameObject {
    protected int width;
    protected int height;
    protected int speed = 5;
    protected float dx = 0;
    protected float dy = 0;
    protected int direction = 1;
    protected int maxLife;
    protected int currentLife;
    protected PointF mask;

    private int cloneCount = 0;

    public Actor(String id, int width, int height) {
        super(id);
        this.width = width;
        this.height = height;
    }

    public float getDx() {
        return dx;
    }

    public float getDy() {
        return dy;
    }

    public PointF getMask() {
        return mask;
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

    public Actor clone() {
        Actor clone = new Actor(id+"$"+cloneCount, width, height);
        cloneCount++;
        return clone;
    }

    public Actor clone(int width, int height) {
        Actor clone = new Actor(id+"$"+cloneCount, width, height);
        cloneCount++;
        return clone;
    }

}