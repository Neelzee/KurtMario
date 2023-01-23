package com.kaffekopp.game.constants;

import com.kaffekopp.game.game_object.actor.Effect;
import com.kaffekopp.game.game_object.actor.StatusEffect;
import com.kaffekopp.game.game_object.actor.monster.MonsterAI;

/**
 * Contains the expected properties in maps
 */
public class MapProperty {

    /**
     * Designates the area to Player spawn.
     * Type: Int.
     * Effect: Amount of Players spawning in that area.
     */
    public static final String PLAYER_SPAWN_PROPERTY = "PLAYER";

    /**
     * Designates the area to Monster spawn.
     * Type: Int.
     * Effect: Amount of Monsters spawning in that area.
     */
    public static final String MONSTER_SPAWN_PROPERTY = "MONSTER";

    /**
     * AI level for the Monster.
     * Type: String.
     * Effect: What AI level the Monster has.
     * @see MonsterAI
     */
    public static final String MONSTER_AI_PROPERTY = "AI";

    /**
     * Designation for the layer that contains the areas for spawning GameObjects.
     */
    public static final String GAME_OBJECT_SPAWN_LAYER = "SPAWNS";

    /**
     * Designation for the layer that contains the areas that are Walls/Ground
     */
    public static final String GAME_OBJECT_WALL_LAYER = "WALL";

    /**
     * Designates the area to Item spawn.
     * Type: Int.
     * Effect: Amount of Items spawning in that area.
     */
    public static final String ITEM_SPAWN_PROPERTY = "ITEM";

    /**
     * What kind of Sprite should be used.
     * Type: Int.
     * Effect: What sprite should be used in rendering the Item.
     */
    public static final String ITEM_SYMBOL_PROPERTY = "SYMBOL";

    /**
     * What kind of Effect the Item has.
     * Type: String.
     * @see Effect
     */
    public static final String ITEM_EFFECT_PROPERTY = "EFFECT";

    /**
     * How long the Effect lasts.
     * Type: Int.
     * @see StatusEffect
     */
    public static final String ITEM_EFFECT_TIME = "TIME";

    /**
     * How often the Effect is applied to the Player.
     * Type: Int.
     * @see StatusEffect
     */
    public static final String ITEM_EFFECT_RATE = "TICK";

    /**
     * Amount tick is increased by. Default is 1
     * Type: Int.
     * @see StatusEffect
     */
    public static final String  ITEM_TICK_RATE = "RATE";

    /**
     * What Value should be used in the Effect
     * Type: Int.
     * @see StatusEffect
     */
    public static final String ITEM_EFFECT_VALUE = "EFFECT_VALUE";

    /**
     * What score a Player gets for picking up the Item
     * Type: Int.
     * Effect: Score added to Player
     */
    public static final String ITEM_SCORE_PROPERTY = "SCORE";
}
