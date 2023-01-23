package com.kaffekopp.game.constants;

import com.kaffekopp.game.game_object.actor.monster.Monster;

public class MonsterConstants {

    /**
     * Distance a monster can detect an Actor instance
     * Monster can see things that are 3 meters close
     */
    public static final float SIGHT = 3 / GameConstants.PPM;

    /**
     * How much HP an Actor loses if damaged by a Monster
     */
    public static final int DAMAGE = 1;

    /**
     * HP of a Monster
     */
    public static final int HP = 10;

    /**
     * How many points a Player receives from killing a monster
     */
    public static final int SCORE = 100;

    /**
     * Default AI level of Monster
     */
    public static final Monster.MonsterAILevel AI = Monster.MonsterAILevel.STUDENT;
}
