package com.kaffekopp.game.listener;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.kaffekopp.game.constants.FilePaths;
import com.kaffekopp.game.game_object.GameObjectIdentifier;
import com.kaffekopp.game.game_object.actor.Actor;
import com.kaffekopp.game.game_object.actor.Effect;
import com.kaffekopp.game.game_object.actor.StatusEffect;
import com.kaffekopp.game.game_object.actor.player.Player;
import com.kaffekopp.game.game_object.terrain.TerrainObject;
import com.kaffekopp.game.game_object.terrain.TerrainType;

public class ActorContact implements IGameObjectListener {

    private Actor actor;

    public void beginContact(Fixture actorFixture, Fixture objectFixture) {
        this.actor = (Actor) ((GameObjectIdentifier<?>) actorFixture.getUserData()).getGameObject();
        Class<?> objectClass = ((GameObjectIdentifier<?>) objectFixture.getUserData()).getGameObject().getClass();
        // Terrain interaction
        if (objectClass.equals(TerrainObject.class)) {
            TerrainObject terrain = (TerrainObject) ((GameObjectIdentifier<?>) objectFixture.getUserData()).getGameObject();
            // Lava interaction
            if (terrain.type().equals(TerrainType.LAVA)) {
                if (actor instanceof Player plr) {
                    if (plr.getIsMuted()) {
                        Gdx.audio.newSound(Gdx.files.internal(FilePaths.SET_ON_FIRE)).play(0.1f);
                    }
                }
                actor.addStatusEffect(new StatusEffect(Effect.FIRE, -1, 30, 1));
            }
        }
    }

    public void endContact(Fixture objectFixture) {

        Class<?> objectClass = ((GameObjectIdentifier<?>) objectFixture.getUserData()).getGameObject().getClass();

        if (TerrainObject.class.equals(objectClass)) {
            TerrainObject terrain = (TerrainObject) ((GameObjectIdentifier<?>) objectFixture.getUserData()).getGameObject();
            // Actor lava interaction
            if (terrain.type().equals(TerrainType.LAVA)) {
                actor.addStatusEffect(new StatusEffect(Effect.FIRE, 600, 30, 1));
            }
        }
    }
}
