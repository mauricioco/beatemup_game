package com.learn.flavio_mauricio.beatemupgame.logic;

/**
 * Actors are everything that "follow a script" on the game map.
 * Following a script would be simply showing an animation, damage other actors,
 * bre pickable... etc.
 * TODO callback functions shoud be implemented for these "scripts".
 */
public class Actor extends GameObject {

    private int width;
    private int height;
    private float dx = 0;
    private float dy = 0;

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

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public void setDerivative(float dx, float dy) {
        this.dx = dx;
        this.dy = dy;
    }

}