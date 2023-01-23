package com.kaffekopp.game.factory;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.kaffekopp.game.constants.FilePaths;
import com.kaffekopp.game.constants.GameConstants;
import com.kaffekopp.game.constants.MapProperty;
import com.kaffekopp.game.game_object.actor.monster.Monster;

public class MonsterFactory implements IGameObjectFactory {

    public Monster[] construct(World world, MapProperties objectProperty, Rectangle rect) {
        // Count might not be initialised in the map, default is 1
        int monsterCount = 1;
        if (objectProperty.containsKey(MapProperty.MONSTER_SPAWN_PROPERTY)) {
            monsterCount = objectProperty.get(MapProperty.MONSTER_SPAWN_PROPERTY, Integer.class);
        }

        Monster[] monsters = new Monster[monsterCount];

        // AI level might not be initialised in the map, default is "MonsterAILevel.NONE"
        Monster.MonsterAILevel ai = Monster.MonsterAILevel.NONE;
        if (objectProperty.containsKey(MapProperty.MONSTER_AI_PROPERTY)) {
            switch (objectProperty.get(MapProperty.MONSTER_AI_PROPERTY, String.class)) {
                case "STUDENT" -> ai = Monster.MonsterAILevel.STUDENT;
                case "DUMB" -> ai = Monster.MonsterAILevel.DUMB;
                case "SMART" -> ai =  Monster.MonsterAILevel.SMART;
            }
        }

        float distanceBetweenItems = rect.getWidth() / GameConstants.PPM / monsterCount;

        for (int i = 0; i < monsterCount; i++) {
            Monster monster = createMonster(world, rect.getX() / GameConstants.PPM + distanceBetweenItems * i, rect.getY() / GameConstants.PPM);
            monster.setAI(ai);
            monsters[i] = monster;
        }
        return monsters;
    }

    public Monster createMonster(World world, float x, float y) {
        Monster monster = new Monster(world, x, y);
        monster.setSprite(new Sprite(new Texture(FilePaths.MONSTER_SPRITE)));
        return monster;
    }
}
