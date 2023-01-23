package com.kaffekopp.game;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import com.kaffekopp.game.constants.GameConstants;
import com.kaffekopp.game.constants.MapProperty;
import com.kaffekopp.game.game_object.terrain.TerrainObject;
import com.kaffekopp.game.game_object.terrain.TerrainType;

/**
 *
 * Every Tiled map contains specific layers, i.e. "WALL"
 * @see MapProperty
 *
 * The "WALL" layer contains the places where the Player is supposed to collide with the terrain.
 * B2WorldCreator creates and places hit-boxes where specified, and LibGDX takes care of the actuall physics
 *
 * Every hit-box is scaled by the constant Game.PPM, (Pixels per meter), so that sprite sizes and the gravitity (10 m/s)
 * is scaled properly.
 * @see GameConstants
 *
 * TODO: Implement try/catch for edgecases where maps are improperly made, so that a default map is loaded, or the map being loaded is skipped.
 *
 * @author Nils Michael
 */
public class B2WorldCreator {

    private final World world;

    /**
     * Creates hit-boxes where specified in the tiled map
     * @param world where LibGDX is simulating physics
     * @param map where the hit-boxes are found
     */
    public B2WorldCreator(World world, TiledMap map) {
        this.world = world;
        for (MapLayer mapLayer : map.getLayers()) {
            switch (mapLayer.getName()) {
                case (MapProperty.GAME_OBJECT_WALL_LAYER) -> createWallHitBoxes(mapLayer);
                case ("HAZARD") -> createHazardHitBoxes(mapLayer);
            }
        }
    }

    public Body createMapBody(World world, Rectangle rect) {
        BodyDef bDef = new BodyDef();
        bDef.position.set((rect.getX() + rect.getWidth() / 2) / GameConstants.PPM, (rect.getY() + rect.getHeight() / 2) / GameConstants.PPM);
        return world.createBody(bDef);
    }

    /**
     * Creates hit-boxes for MapHazards like lava
     * @param mapLayer layers in the given map
     */
    private void createHazardHitBoxes(MapLayer mapLayer) {
        for (MapObject mapObject : mapLayer.getObjects()) {
            PolygonShape shape = new PolygonShape();
            FixtureDef fDef = new FixtureDef();

            Body body = createMapBody(world, ((RectangleMapObject) mapObject).getRectangle());

            shape.setAsBox(((RectangleMapObject) mapObject).getRectangle().getWidth() / 2 / GameConstants.PPM, ((RectangleMapObject) mapObject).getRectangle().getHeight() / 2 / GameConstants.PPM);

            fDef.shape = shape;
            fDef.isSensor = true;
            body.createFixture(fDef).setUserData((new TerrainObject(TerrainType.LAVA)).getUserData());
        }
    }

    /**
     * Creates hit-boxes for Walls and Ground
     * @param mapLayer layers in the given map
     */
    private void createWallHitBoxes(MapLayer mapLayer) {
        for (RectangleMapObject mapObject : mapLayer.getObjects().getByType(RectangleMapObject.class)) {
            PolygonShape shape = new PolygonShape();
            BodyDef bDef = new BodyDef();
            FixtureDef fDef = new FixtureDef();

            bDef.type = BodyDef.BodyType.StaticBody;
            Body body = createMapBody(world, mapObject.getRectangle());

            shape.setAsBox(mapObject.getRectangle().getWidth() / 2 / GameConstants.PPM, mapObject.getRectangle().getHeight() / 2 / GameConstants.PPM);

            fDef.shape = shape;
            if (mapObject.getProperties().containsKey("JUMPPAD")) {
                fDef.isSensor = true;
                body.createFixture(fDef).setUserData(new TerrainObject(TerrainType.JUMPPAD).getUserData());
            } else if (mapObject.getProperties().containsKey("EASTERSKIP")) {
                fDef.isSensor = true;
                body.createFixture(fDef).setUserData(new TerrainObject(TerrainType.EASTERSKIP).getUserData());
            } else if (mapObject.getProperties().containsKey("BLOCKED")) {
                if (!((Boolean) mapObject.getProperties().get("BLOCKED"))) {
                    fDef.isSensor = true;
                }
                body.createFixture(fDef).setUserData(new TerrainObject(TerrainType.TUNNEL).getUserData());
            } else {
                body.createFixture(fDef).setUserData(new TerrainObject(TerrainType.WALL).getUserData());
            }
            shape.dispose();
        }
    }
}