package com.learn.flavio_mauricio.beatemupgame.logic;

/**
 * This is where actors would "gravitate" to.
 */
public class Floor extends GameObject {

    private float floorLimit;
    private float sizeX;

    public Floor(String id, float floorLimit, float sizeX) {
        super(id);
        this.floorLimit = floorLimit;
        this.sizeX = sizeX;
    }

    public float getFloorLimit() {
        return floorLimit;
    }

    public int getSizeX() {
        return (int) sizeX;
    }

}