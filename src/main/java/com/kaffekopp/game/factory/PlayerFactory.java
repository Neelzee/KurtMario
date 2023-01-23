package com.kaffekopp.game.factory;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.kaffekopp.game.constants.PlayerConstants;
import com.kaffekopp.game.constants.FilePaths;
import com.kaffekopp.game.constants.GameConstants;
import com.kaffekopp.game.constants.MapProperty;
import com.kaffekopp.game.game_object.actor.Effect;
import com.kaffekopp.game.game_object.actor.StatusEffect;
import com.kaffekopp.game.game_object.actor.player.Player;

/**
 * Creates Player instances
 *
 * TODO: Write better comments
 */
public class PlayerFactory implements IGameObjectFactory {

    /**
     * Creates and places the Player instances in the world.
     * Also gives them the IMMUNE StatusEffect, to ensure they are not killed,
     * before the game starts.
     * All information used in creation of the Player instance is given by MapProperties and the spawn bound Rectangle
     * @param objectProperty Contains information such as player count
     * @param rect Contains the spawning location of the Player
     * @return Array of all the Players created.
     */
    public Player[] construct(World world, MapProperties objectProperty, Rectangle rect) {
        // Count might not be initialised in the map, default is 1
        int playerCount = 1;
        if (objectProperty.containsKey(MapProperty.PLAYER_SPAWN_PROPERTY)) {
            playerCount = (int) objectProperty.get(MapProperty.PLAYER_SPAWN_PROPERTY);
        }

        Player[] players = new Player[playerCount];

        // Width is standard to Tiled, and will always be there
        float distanceBetweenPlayers = rect.getWidth() / GameConstants.PPM / playerCount;

        for (int i = 0; i < playerCount; i++) {
            Player plr = createPlayer(world, rect.getX() / GameConstants.PPM + distanceBetweenPlayers * i, rect.getY()  / GameConstants.PPM);
            if (i == 1) {
                plr.setPlayerControls(PlayerConstants.getPlayerTwoControls());
                plr.setSprite(new Sprite(new Texture(FilePaths.PLAYER_SPRITE_2)));
                plr.setStandingSprite(FilePaths.PLAYER_SPRITE_2);
                plr.setProneSprite(FilePaths.PLAYER_PRONE_SPRITE_2);
                plr.layDown();
                plr.standUp();
            }
            plr.addStatusEffect(new StatusEffect(Effect.IMMUNE, 16, 1, 1));
            players[i] = plr;
        }
        return players;
    }

    /**
     * Creates a Player instance in the given XY location
     * @param x X value
     * @param y Y value
     * @return Player instance
     */
    public Player createPlayer(World world, float x, float y) {
        Player player = new Player(world, x, y);
        player.setSprite(new Sprite(new Texture(FilePaths.PLAYER_SPRITE)));
        return player;
    }
}
