package com.learn.flavio_mauricio.beatemupgame.graphic;

import com.learn.flavio_mauricio.beatemupgame.logic.GameObject;

import java.util.Hashtable;

/**
 * This class is populated by static methods and attributes that manage
 * sprites to its respective game objects.
 */
public class GraphicManager {
    static private Hashtable<String, Sprite> spriteTable = new Hashtable<String, Sprite>();

    static public void putSprite(GameObject gameObject, Sprite sprite) {
        spriteTable.put(gameObject.getId(), sprite);
    }

    static public Sprite getSprite(GameObject gameObject) {
        String id = gameObject.getId();
        int indexOfCloneId = id.indexOf("$");
        if(indexOfCloneId >= 0)
            id = id.substring(0, indexOfCloneId);
        return spriteTable.get(id);
    }

}
