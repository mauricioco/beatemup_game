package com.learn.flavio_mauricio.beatemupgame.logic;

import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.PointF;

import com.learn.flavio_mauricio.beatemupgame.R;
import com.learn.flavio_mauricio.beatemupgame.graphic.GraphicManager;
import com.learn.flavio_mauricio.beatemupgame.graphic.Sprite;

/**
 * So far, this class is only used to create (statically) resources
 * for the classes in the logic package use.
 */
public class LogicManager {

    static private Point playerSpawn;

    static public GameMap defaultInstance(Resources resources, int width, int height) {
        // Creating game objects:
        playerSpawn = new Point(width-64, height-64);
        Background bg_sky = new Background("sky");
        Floor fl_grass = new Floor("grass", height-180, width);
        Floor fl_grass_to_sand = new Floor("grass_to_sand", height-120, width);
        Floor fl_sand = new Floor("sand", height-180, width);
        IActor player = new IActor("player", playerSpawn.x, playerSpawn.y, 32, 32);
        Actor tree = new IActor("tree", width, height-52, 32, 64);
        EnemyActor enemy1 = new EnemyActor("enemy", width-5, height-30, 32, 32);

        // Binding sprites:
        GraphicManager.putSprite(bg_sky, new Sprite(bg_sky, resources, R.drawable.background_sky));
        GraphicManager.putSprite(fl_grass, new Sprite(fl_grass, resources, R.drawable.floor_grass));
        GraphicManager.putSprite(fl_grass_to_sand, new Sprite(fl_grass, resources, R.drawable.floor_grass_to_sand));
        GraphicManager.putSprite(fl_sand, new Sprite(fl_grass, resources, R.drawable.floor_sand));
        GraphicManager.putSprite(tree, new Sprite(tree, resources, R.drawable.tree));

        Sprite playerSprite = new Sprite(player, resources, R.drawable.ic_launcher);
        {
            playerSprite.addBmpAnim(resources, R.drawable.ic_launcher_2);
            playerSprite.addBmpAnim(resources, R.drawable.ic_launcher_3);
            playerSprite.setMaxUpdatesPerFrame(2);
        }
        GraphicManager.putSprite(player, playerSprite);

        Sprite enemySprite = new Sprite(player, resources, R.drawable.ic_enemy);
        {
            enemySprite.addBmpAnim(resources, R.drawable.ic_enemy_2);
            enemySprite.addBmpAnim(resources, R.drawable.ic_enemy_3);
            enemySprite.setMaxUpdatesPerFrame(2);
        }
        GraphicManager.putSprite(enemy1, enemySprite);

        // Creating map and putting contents on it:
        GameMap gameMap = new GameMap("mapTest", bg_sky, fl_grass, width*5, height);
        gameMap.putFloor(fl_grass.clone());
        gameMap.putFloor(fl_grass_to_sand.clone());
        gameMap.putFloor(fl_sand.clone());
        gameMap.putPlayer(player);
        gameMap.putActor(enemy1);
        gameMap.putActor(tree);
        gameMap.putActor(tree.clone(width+128, height-64));
        gameMap.putActor(tree.clone(width+12, height-61));
        gameMap.putActor(tree.clone(width+1000, height-2));
        gameMap.putActor(tree.clone(width+1234, height-54));
        gameMap.putActor(tree.clone(width+24, height-0));
        gameMap.putActor(tree.clone(width+124, height-11));
        gameMap.putActor(tree.clone(width+35, height-44));
        gameMap.putActor(tree.clone(width+1200, height-23));
        gameMap.putActor(tree.clone(width+546, height-12));
        gameMap.putActor(tree.clone(width*2, height-120-16, 64, 128));
        gameMap.putActor(tree.clone(width*3, height-120-16, 64, 128));


        return gameMap;
    }
}
