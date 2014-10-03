package com.learn.flavio_mauricio.beatemupgame.logic;

import android.graphics.PointF;

/**
 * Actors are everything that "follow a script" on the game map.
 * Following a script would be simply showing an animation, damage other actors,
 * bre pickable... etc.
 */
public class Actor extends GameObject {
    // Sizes
    protected int width;
    protected int height;

    // Mask is a square centered on the actor used to detect collision.
    protected PointF mask;

    // Movement and knowledge:
    protected float speed = 5;
    protected float dx = 0;
    protected float dy = 0;
    protected int direction = 1;
    protected GameMap currentMap;

    // Actor states - it says if it`s moving, attacking, etc.
    protected ActorState state = ActorState.Idle;
    protected int holdTime = -1;

    // Stats:
    protected boolean indestructible = true;
    protected int maxLife = 100;
    protected int currentLife = 100;

    // Number of clones so instances won`t repeat.
    protected int cloneCount = 0;

    public Actor(String id, int width, int height) {
        super(id);
        this.width = width;
        this.height = height;
        this.mask = new PointF(width/2-width/4, height/2-height/4);
            // default mask
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

    public PointF getMaskBegin(GameMap map) {
        PointF actorPos = map.getActorPos(this);
        return new PointF(actorPos.x-mask.x, actorPos.y-mask.y);

    }

    public PointF getMaskEnd(GameMap map) {
        PointF actorPos = map.getActorPos(this);
        return new PointF(actorPos.x+mask.x, actorPos.y+mask.y);
    }

    public float getSpeed() {
        return speed;
    }

    public PointF getPositionOnMap(GameMap map) {
        return map.getActorPos(this);
    }

    public int getCurrentLife() {
        return currentLife;
    }

    public int getDirection() {
        return direction;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public ActorState getState() {
        return state;
    }

    public boolean isOnHold() {
        return holdTime > 0;
    }

    public boolean isHoldEnded() {
        return holdTime == 0;
    }

    public int decreaseCurrentLife(int d) {
        this.currentLife -= d;
        return this.currentLife;
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

        if(dx > 0)
            this.direction = 1;
        else if(dx < 0)
            this.direction = -1;

    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public void setState(ActorState state) {
        if(!isOnHold() || state == ActorState.Unconscious)
            this.state = state;

    }

    public void setOnHold(int time) {
        this.holdTime = time;
    }

    public void setCurrentMap(GameMap currentMap) {
        this.currentMap = currentMap;
    }

    public void tickHoldTime() {
        holdTime--;
    }

    public boolean attack(Actor attackedActor) {
        this.setOnHold(20);
        if(attackedActor != null && attackedActor.getState() == ActorState.Unconscious) {
            attackedActor.decreaseCurrentLife(1);
            attackedActor.setState(ActorState.Attacked);
            attackedActor.setOnHold(30);
        }
        System.out.println(this + " attacked " + attackedActor + " looking to " + direction);
        return true;
    }

    public boolean onHoldEnd() {
        this.state = ActorState.Idle;
        return true;
    }

    public boolean ai() {
        return false;
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

    public String toString() {
        return id;
    }

}