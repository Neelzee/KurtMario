package com.kaffekopp.game.game_object.actor.platform;

public enum PlatformMovement {
    /**
     * Moves towards X, then Y, ending at the goal location
     */
    XY_MOVEMENT,

    /**
     * Moves towards Y, then X, ending at the goal location
     */
    YX_MOVEMENT,

    /**
     * Moves in a straight line towards the goal location
     */
    DIRECT_MOVEMENT
}
