package com.learn.flavio_mauricio.beatemupgame.logic;

/**
 * This class represents every single thing that can exist on the game map.
 * All other map elements must extend from this class.
 */
public class GameObject {
    protected String id;

    public GameObject(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

}
