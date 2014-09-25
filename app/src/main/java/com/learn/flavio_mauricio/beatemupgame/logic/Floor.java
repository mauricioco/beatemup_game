package com.learn.flavio_mauricio.beatemupgame.logic;

/**
 * This is where actors would "gravitate" to.
 */
public class Floor extends GameObject {

    private float floorLimit;

    public Floor(String id, float floorLimit) {
        super(id);
        this.floorLimit = floorLimit;
    }

    public float getFloorLimit() {
        return floorLimit;
    }

}