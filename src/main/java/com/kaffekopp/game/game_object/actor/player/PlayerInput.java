package com.kaffekopp.game.game_object.actor.player;

import java.util.HashMap;

public class PlayerInput {
    private HashMap<PlayerKeyControls, Integer> playerControls;

    public HashMap<PlayerKeyControls, Integer> getPlayerControls() {
        return playerControls;
    }

    public void setPlayerControls(HashMap<PlayerKeyControls, Integer> playerControls) {
        this.playerControls = playerControls;
    }
}
