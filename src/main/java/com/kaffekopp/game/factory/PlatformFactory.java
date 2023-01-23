package com.kaffekopp.game.factory;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.kaffekopp.game.constants.FilePaths;
import com.kaffekopp.game.game_object.actor.platform.Platform;
import com.kaffekopp.game.constants.Functions;

import java.util.HashMap;
// TODO: Fix
public class PlatformFactory implements IGameObjectFactory {

    public Platform[] construct(World world, MapProperties objectProperty, Rectangle rect) {
        Vector2 position = Functions.gameScaleVector(Functions.getPosition(rect));
        Platform platform = new Platform(world, position.x, position.y);
        Sprite sprite = new Sprite(new Texture(FilePaths.DEFAULT_SPRITE));
        sprite.setSize(rect.getWidth(), rect.getHeight());
        platform.setSprite(sprite);
        platform.setVelocity(objectProperty.containsKey("SPEED") ? objectProperty.get("SPEED", Float.class) : 0.5f);
        platform.setPID(objectProperty.containsKey("PID") ? objectProperty.get("PID", Integer.class) : -1);
        platform.setAi(Functions.parsePlatformAI(objectProperty.get("AI", String.class)));
        return new Platform[]{platform};
    }

    public Vector2[] createStops(MapLayer mapLayer, int pid) {
        HashMap<Integer, Vector2> locationMap = new HashMap<>();
        for (RectangleMapObject mapObject : mapLayer.getObjects().getByType(RectangleMapObject.class)) {
            if (!mapObject.getProperties().containsKey("POINT") || !mapObject.getProperties().containsKey("PID")) {
                continue;
            }
            if (mapObject.getProperties().get("PID", Integer.class) != pid) {
                continue;
            }
            // Creates point and pid
            Vector2 point = Functions.getPosition(mapObject.getRectangle());
            point = Functions.gameScaleVector(point);
            int key = mapObject.getProperties().get("POINT", Integer.class);
            // Ensures all points are sorted
            while (locationMap.containsKey(key)) {
                key++;
            }
            locationMap.put(key, point);
        }
        return locationMap.values().toArray(new Vector2[0]);
    }
}
