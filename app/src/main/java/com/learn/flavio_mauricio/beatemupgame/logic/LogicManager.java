package com.learn.flavio_mauricio.beatemupgame.logic;

import android.content.res.Resources;

import com.learn.flavio_mauricio.beatemupgame.R;
import com.learn.flavio_mauricio.beatemupgame.graphic.GraphicManager;
import com.learn.flavio_mauricio.beatemupgame.graphic.Sprite;

/**
 * So far, this class is only used to create (statically) resources
 * for the classes in the logic package use.
 */
public class LogicManager {
    static public GameMap defaultInstance(Resources resources) {
        Background bg_sky = new Background("sky");
        Floor fl_grass = new Floor("grass");
        IActor player = new IActor("player", 32, 32);
        GraphicManager.putSprite(bg_sky, new Sprite(resources, R.drawable.background_sky));
        GraphicManager.putSprite(fl_grass, new Sprite(resources, R.drawable.floor_grass));
        GraphicManager.putSprite(player, new Sprite(resources, R.drawable.ic_launcher));

        GameMap gameMap = new GameMap("mapTest", bg_sky, fl_grass);
        gameMap.putPlayerAt(player, 32, 32);
        return gameMap;
    }
}
