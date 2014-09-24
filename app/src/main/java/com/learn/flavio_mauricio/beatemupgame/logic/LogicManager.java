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
    static public GameMap defaultInstance(Resources resources, int width, int height) {
        Background bg_sky = new Background("sky");
        Floor fl_grass = new Floor("grass");
        IActor player = new IActor("player", 32, 32);
        GraphicManager.putSprite(bg_sky, new Sprite(bg_sky, resources, R.drawable.background_sky));
        GraphicManager.putSprite(fl_grass, new Sprite(fl_grass, resources, R.drawable.floor_grass));
        Sprite playerSprite = new Sprite(player, resources, R.drawable.ic_launcher);
        playerSprite.addBmpAnim(resources, R.drawable.ic_launcher_2);
        playerSprite.addBmpAnim(resources, R.drawable.ic_launcher_3);
        GraphicManager.putSprite(player, playerSprite);

        GameMap gameMap = new GameMap("mapTest", bg_sky, fl_grass, width, height);
        gameMap.putPlayerAt(player, 20, 60);
        return gameMap;
    }
}
