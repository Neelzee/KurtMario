package com.kaffekopp.game.game_object.actor;

/**
 * Status effect that affect the Actor over time.
 */
public enum Effect {
    // int maxTick, int effectTick
    DAMAGE (0, 0),
    HEALING (0, 0),
    MAX_HEALTH_INCREASE (0, 0),
    NONE (0, 0),
    IMMUNE (640, 32),
    POISON (640, 32),
    JUMP_BOOST (640, 32),
    SPEED_BOOST (640, 32),
    REGENERATION (640, 32),
    FIRE (640, 32);

    private final int maxTick;

    private final int effectTick;

    Effect(int maxTick, int effectTick) {
        this.effectTick = effectTick;
        this.maxTick = maxTick;
    }


    public int getMaxTick() {
        return maxTick;
    }

    public int getEffectTick() {
        return effectTick;
    }
}
