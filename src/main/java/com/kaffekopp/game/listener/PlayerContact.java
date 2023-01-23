package com.kaffekopp.game.listener;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.kaffekopp.game.constants.FilePaths;
import com.kaffekopp.game.constants.GameConstants;
import com.kaffekopp.game.game_object.GameObjectIdentifier;
import com.kaffekopp.game.game_object.actor.Actor;
import com.kaffekopp.game.game_object.actor.monster.Monster;
import com.kaffekopp.game.game_object.actor.player.Player;
import com.kaffekopp.game.game_object.item.Item;
import com.kaffekopp.game.game_object.terrain.TerrainObject;
import com.kaffekopp.game.game_object.terrain.TerrainType;

public class PlayerContact implements IGameObjectListener {

    public void beginContact(Fixture playerFixture, Fixture objectFixture) {
        Player player = (Player) ((GameObjectIdentifier<?>) playerFixture.getUserData()).getGameObject();
        Class<?> objectClass = ((GameObjectIdentifier<?>) objectFixture.getUserData()).getGameObject().getClass();
        player.setCanJump(true);

        if (((GameObjectIdentifier<?>) playerFixture.getUserData()).isAppendage()) {
            if (((GameObjectIdentifier<?>) objectFixture.getUserData()).getGameObject() instanceof Actor actor) {
                player.setKickTarget(actor);
            }
            return;
        }

        // Player monster interaction
        if (Monster.class.equals(objectClass)) {
            Monster monster = (Monster) ((GameObjectIdentifier<?>) objectFixture.getUserData()).getGameObject();
            // Checks if bottom of the Player sprite is above and within the monster width
            float rightLimit = (monster.getSprite().getWidth() / 2) + monster.getB2body().getPosition().x;
            float leftLimit = (monster.getSprite().getWidth() / 2) - monster.getB2body().getPosition().x;
            float heightLimit = (monster.getSprite().getHeight() + monster.getB2body().getPosition().y);
            if (leftLimit < player.getB2body().getPosition().x && player.getB2body().getPosition().x < rightLimit && player.getB2body().getPosition().y > heightLimit) {
                if (player.getIsMuted()) {
                    Gdx.audio.newSound(Gdx.files.internal(FilePaths.ATTACK)).play(0.1f);
                }
                player.attack(monster);

                // Force that is applied when a player "jumps" on top of monster, makes them bounce
                Vector2 force = new Vector2(player.getB2body().getLinearVelocity().x / 2, 2);

                force.x *= player.getHeading() ? 1 : -1;
                player.getB2body().setLinearVelocity(force);
            } else {
                monster.attack(player);
                player.applyForce(new Vector2(monster.getB2body().getLinearVelocity().x * 2, monster.getJumpForce()));
            }
            return;
        }

        // Player item interaction
        if (Item.class.equals(objectClass)) {
            Item item = (Item) ((GameObjectIdentifier<?>) objectFixture.getUserData()).getGameObject();
            player.pickUp(item);
            item.setShouldRender(false);
            return;
        }

        // Player terrain interaction
        if (TerrainObject.class.equals(objectClass)) {
            TerrainObject terrain = (TerrainObject) ((GameObjectIdentifier<?>) objectFixture.getUserData()).getGameObject();
            // Player tunnel interaction
            if (terrain.type().equals(TerrainType.TUNNEL)) {
                player.setStanding(false);
                return;
            }

            // Player is in contact with jump-pad
            if (terrain.type().equals(TerrainType.JUMPPAD)) {
                Vector2 force = new Vector2(0, (GameConstants.EARTH_GRAVITY.y * (-1)));
                player.applyForce(force);
                return;
            }
            // Player is in contact with Easter-egg-skip
            if (terrain.type().equals(TerrainType.EASTERSKIP)) {
                Vector2 force = new Vector2(15, 0);
                player.applyForce(force);
                return;

            }
            if(terrain.type().equals(TerrainType.WALL)){
                player.setCanJump(false);
                if (player.getIsMuted()) {
                    Gdx.audio.newSound(Gdx.files.internal(FilePaths.BUMP_SOUND)).play(0.1f);
                }


            }
        }
    }

    public void endContact(Fixture playerFixture, Fixture objectFixture) {
        Player player = (Player) ((GameObjectIdentifier<?>) playerFixture.getUserData()).getGameObject();
        player.setCanJump(false);

        Class<?> objectClass = ((GameObjectIdentifier<?>) objectFixture.getUserData()).getGameObject().getClass();

        // Player terrain interaction
        if (TerrainObject.class.equals(objectClass)) {
            TerrainObject terrain = (TerrainObject) ((GameObjectIdentifier<?>) objectFixture.getUserData()).getGameObject();
            // Player tunnel interaction
            if (terrain.type().equals(TerrainType.TUNNEL)) {
                player.setStanding(true);
            }
        }

        // Removes kick target
        if (objectClass.equals(Monster.class)) {
            player.setKickTarget(null);
        }
    }
}
