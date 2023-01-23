package com.kaffekopp.game.factory;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.kaffekopp.game.constants.*;
import com.kaffekopp.game.game_object.actor.Effect;
import com.kaffekopp.game.game_object.actor.StatusEffect;
import com.kaffekopp.game.game_object.item.Item;

public class ItemFactory implements IGameObjectFactory {

    public Item[] construct(World world, MapProperties objectProperty, Rectangle rect) {
        // Count might not be initialised in the map, default is 1
        int itemCount;
        if (objectProperty.containsKey(MapProperty.ITEM_SPAWN_PROPERTY)) {
            itemCount = objectProperty.get(MapProperty.ITEM_SPAWN_PROPERTY, Integer.class);
        } else {
            itemCount = 1;
        }

        Item[] items = new Item[itemCount];

        // Symbol might not be initialised in the map, default is Item.CATNIP_SYMBOL
        char itemSymbol;
        if (objectProperty.containsKey(MapProperty.ITEM_SYMBOL_PROPERTY))
            itemSymbol = objectProperty.get(MapProperty.ITEM_SYMBOL_PROPERTY, String.class).charAt(0);
        else itemSymbol = ItemConstants.CATNIP_SYMBOL;

        // Effect might not be initialised in the map, default is Effect.NONE
        Effect itemEffect;
        if (objectProperty.containsKey(MapProperty.ITEM_EFFECT_PROPERTY)) itemEffect = ItemConstants.parseEffect(objectProperty.get(MapProperty.ITEM_EFFECT_PROPERTY, String.class));
        else itemEffect = Effect.NONE;

        // Score might not be initialised in the map, default is ITEM.DEFAULT_SCORE
        int itemScore;
        if (objectProperty.containsKey(MapProperty.ITEM_SCORE_PROPERTY)) itemScore = objectProperty.get(MapProperty.ITEM_SCORE_PROPERTY, Integer.class);
        else itemScore = ItemConstants.SCORE;

        int itemMaxTick;
        if (objectProperty.containsKey(MapProperty.ITEM_EFFECT_TIME)) {
            itemMaxTick = objectProperty.get(MapProperty.ITEM_EFFECT_TIME, Integer.class);
        } else {
            itemMaxTick = itemEffect.getMaxTick();
        }

        int itemEffectRate;
        if (objectProperty.containsKey(MapProperty.ITEM_EFFECT_RATE)) {
            itemEffectRate = objectProperty.get(MapProperty.ITEM_EFFECT_RATE, Integer.class);
        } else {
            itemEffectRate = itemEffect.getEffectTick();
        }

        int itemEffectValue;
        if (objectProperty.containsKey(MapProperty.ITEM_EFFECT_VALUE)) {
            itemEffectValue = objectProperty.get(MapProperty.ITEM_EFFECT_VALUE, Integer.class);
        } else {
            itemEffectValue = ItemConstants.EFFECT_VALUE;
        }

        int itemTickRate;
        if (objectProperty.containsKey(MapProperty.ITEM_TICK_RATE)) {
            itemTickRate = objectProperty.get(MapProperty.ITEM_TICK_RATE, Integer.class);
        } else {
            itemTickRate = ItemConstants.TICK_RATE;
        }

        float gravityScale = objectProperty.containsKey("GRAVITY_SCALE") ? objectProperty.get("GRAVITY_SCALE", Float.class) : 1;

        float distanceBetweenItems = rect.getWidth() / GameConstants.PPM / itemCount;

        for (int i = 0; i < itemCount; i++) {
            Item item = createItem(world, Functions.getPosition(rect).x / GameConstants.PPM + distanceBetweenItems * i, Functions.getPosition(rect).y / GameConstants.PPM);
            item.setSprite(new Sprite(new Texture(ItemConstants.getSpritePath(itemSymbol))));
            StatusEffect statusEffect = new StatusEffect(itemEffect, itemMaxTick, itemEffectRate, itemTickRate);
            item.setStatusEffect(statusEffect);
            item.setScore(itemScore);
            item.setEffectValue(itemEffectValue);
            item.getB2body().setGravityScale(gravityScale);
            items[i] = item;
        }
        return items;
    }

    /**
     * Creates an Item instance in the given XY location
     * @param x X value
     * @param y Y value
     * @return Item instance
     */
    public Item createItem(World world, float x, float y) {
        return new Item(world, x, y);
    }
}
