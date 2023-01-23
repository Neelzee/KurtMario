package com.kaffekopp.game.factory;

import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.kaffekopp.game.game_object.actor.platform.Platform;
import com.kaffekopp.game.constants.MapProperty;
import com.kaffekopp.game.game_object.GameObject;
import com.kaffekopp.game.screens.PlayScreen;

import java.util.ArrayList;
import java.util.List;


/**
 *
 * Every Tiled map contains specific layers, i.e. "SPAWNS"
 * @see MapProperties
 *
 * The "SPAWNS" layer contains every single spawn area for every single GameObject that are in that specific map
 * GameObjectSpawner creates and places each GameObject in an even distribution inside the spawn area,
 * the amount corresponding to the specific attribute in that spawn area.
 *
 * GameObjectSpawner takes in a PlayScreen instance, and adds the specific instances of GameObjects to it,
 * so that they are rendered.
 * @see PlayScreen
 *
 * @author Nils Michael
 */
public class GameObjectSpawner {

    private ArrayList<GameObject> gameObjects;

    private final PlayerFactory playerFactory;

    private final ItemFactory itemFactory;

    private final MonsterFactory monsterFactory;

    private final PlatformFactory platformFactory;

    public GameObjectSpawner() {
        this.gameObjects = new ArrayList<>();
        this.playerFactory = new PlayerFactory();
        this.itemFactory = new ItemFactory();
        this.monsterFactory = new MonsterFactory();
        this.platformFactory = new PlatformFactory();
    }

    public ArrayList<GameObject> constructGameObjects(World world, TiledMap map) {
        preConstruct(world, map);
        postConstruct(map);
        return gameObjects;
    }

    public void cleanUp() {
        this.gameObjects = new ArrayList<>();
    }

    private void preConstruct(World world, TiledMap map) {
        for (RectangleMapObject mapObject : map.getLayers().get(MapProperty.GAME_OBJECT_SPAWN_LAYER).getObjects().getByType(RectangleMapObject.class)) {
            MapProperties objectProperty = mapObject.getProperties();
            Rectangle rect = mapObject.getRectangle();
            // Players
            if (objectProperty.containsKey(MapProperty.PLAYER_SPAWN_PROPERTY)) {
                gameObjects.addAll(List.of(playerFactory.construct(world, objectProperty, rect)));
            }

            // Monsters
            if (objectProperty.containsKey(MapProperty.MONSTER_SPAWN_PROPERTY)) {
                gameObjects.addAll(List.of(monsterFactory.construct(world, objectProperty, rect)));
            }

            // Items
            if (objectProperty.containsKey(MapProperty.ITEM_SPAWN_PROPERTY)) {
                gameObjects.addAll(List.of(itemFactory.construct(world, objectProperty, rect)));
            }

            // Platforms
            if (objectProperty.containsKey("PLATFORM")) {
                gameObjects.addAll(List.of(platformFactory.construct(world, objectProperty, rect)));
            }
        }
    }

    private void postConstruct(TiledMap map) {
        for (GameObject object : gameObjects) {
            // Platform post-processing
            if (object instanceof Platform) {
                ((Platform) object).addStops(platformFactory.createStops(map.getLayers().get(MapProperty.GAME_OBJECT_SPAWN_LAYER), ((Platform) object).getPID()));
            }
        }
    }
}
