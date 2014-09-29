package com.learn.flavio_mauricio.beatemupgame.logic;

/**
 * This is where actors would "gravitate" to.
 */
public class Floor extends GameObject {

    private int floorLimit;
    private int sizeX;

    private int cloneCount = 0;

    public Floor(String id, int floorLimit, int sizeX) {
        super(id);
        this.floorLimit = floorLimit;
        this.sizeX = sizeX;
    }

    public float getFloorLimit() {
        return floorLimit;
    }

    public int getSizeX() {
        return sizeX;
    }

    public Floor clone() {
        Floor clone = new Floor(id+"$"+cloneCount, floorLimit, sizeX);
        cloneCount++;
        return clone;
    }

}