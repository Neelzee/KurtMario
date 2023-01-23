package com.kaffekopp.game.constants;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.kaffekopp.game.game_object.actor.player.PlayerInput;
import com.kaffekopp.game.game_object.actor.player.PlayerKeyControls;

import java.util.HashMap;

public class PlayerConstants {

    /**
     * Amount of damage a player does, when stomping
     */
    public static final int DAMAGE = 10;

    /**
     * Force that is applied to the Player instance when it jumps
     */
    public static final float JUMP_FORCE = 5F;

    /**
     * Default speed limit for Player
     */
    public static final float SPEED_LIMIT = 2f;

    /**
     * Amount of force applied to a target that is kicked
     */
    public static final Vector2 KICK_FORCE = new Vector2(1f, 0.5f);

    /**
     * Player one controls
     * @return control map for player one
     */
    public static PlayerInput getPlayerOneControls() {
        HashMap<PlayerKeyControls, Integer> defaultControlsMap = new HashMap<>();
        defaultControlsMap.put(PlayerKeyControls.JUMP, Input.Keys.W);
        defaultControlsMap.put(PlayerKeyControls.LEFT, Input.Keys.A);
        defaultControlsMap.put(PlayerKeyControls.RIGHT, Input.Keys.D);
        defaultControlsMap.put(PlayerKeyControls.DOWN, Input.Keys.S);
        defaultControlsMap.put(PlayerKeyControls.KICK, Input.Keys.F);
        defaultControlsMap.put(PlayerKeyControls.USE, Input.Keys.E);
        PlayerInput defaultControls = new PlayerInput();
        defaultControls.setPlayerControls(defaultControlsMap);
        return defaultControls;
    }

    /**
     * Player two controls
     * @return control map for player two
     */
    public static PlayerInput getPlayerTwoControls() {
        HashMap<PlayerKeyControls, Integer> defaultControlsMap = new HashMap<>();
        defaultControlsMap.put(PlayerKeyControls.JUMP, Input.Keys.UP);
        defaultControlsMap.put(PlayerKeyControls.LEFT, Input.Keys.LEFT);
        defaultControlsMap.put(PlayerKeyControls.RIGHT, Input.Keys.RIGHT);
        defaultControlsMap.put(PlayerKeyControls.DOWN, Input.Keys.DOWN);
        defaultControlsMap.put(PlayerKeyControls.KICK, Input.Keys.PAGE_DOWN);
        defaultControlsMap.put(PlayerKeyControls.USE, Input.Keys.CONTROL_RIGHT);
        PlayerInput defaultControls = new PlayerInput();
        defaultControls.setPlayerControls(defaultControlsMap);
        return defaultControls;
    }
}
