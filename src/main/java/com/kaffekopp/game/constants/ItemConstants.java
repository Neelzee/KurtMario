package com.kaffekopp.game.constants;

import com.kaffekopp.game.game_object.actor.Effect;

public class ItemConstants {
    /**
     * Default score for Objects that has a score
     */
    public static final int SCORE = 5;

    public static final int EFFECT_VALUE = 1;

    public static final char CATNIP_SYMBOL = 'C';

    public static final char GRAPE_SYMBOL = 'G';

    public static final char PINK_DIAMOND_SYMBOL = 'd';

    public static final char PURPLE_DIAMOND_SYMBOL = 'D';

    public static final char STAR_SYMBOL = '*';
    public static final int TICK_RATE = 1;

    /**
     * Returns the Effect version of the given string
     * @param s effect in string
     * @return Effect
     */
    public static Effect parseEffect(String s) {
        Effect effect = Effect.NONE;
        switch (s) {
            case "DAMAGE" -> effect =  Effect.DAMAGE;
            case "HEALING" -> effect =  Effect.HEALING;
            case "MAX_HEALTH_INCREASE" -> effect =  Effect.MAX_HEALTH_INCREASE;
            case "IMMUNE" -> effect =  Effect.IMMUNE;
            case "POISON" -> effect =  Effect.POISON;
            case "JUMP_BOOST" -> effect =  Effect.JUMP_BOOST;
            case "SPEED_BOOST" -> effect =  Effect.SPEED_BOOST;
            case "REGENERATION" -> effect =  Effect.REGENERATION;
            case "FIRE" -> effect =  Effect.FIRE;
        }
        return effect;
    }

    /**
     * Returns the path to a sprite based on the given symbol
     * @param c symbol
     * @return path to sprite
     */
    public static String getSpritePath(char c) {
        String texturePath = FilePaths.DEFAULT_SPRITE;
        switch (c) {
            case ItemConstants.CATNIP_SYMBOL -> texturePath = FilePaths.CATNIP_SPRITE;
            case ItemConstants.GRAPE_SYMBOL -> texturePath = FilePaths.GRAPE_SPRITE;
            case ItemConstants.PINK_DIAMOND_SYMBOL -> texturePath = FilePaths.PINK_DIAMOND_SPRITE;
            case ItemConstants.PURPLE_DIAMOND_SYMBOL -> texturePath = FilePaths.PURPLE_DIAMOND_SPRITE;
            case ItemConstants.STAR_SYMBOL -> texturePath = FilePaths.STAR_SPRITE;
        }
        return texturePath;
    }
}
