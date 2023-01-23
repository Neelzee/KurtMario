package com.kaffekopp.game.game_object.item;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.kaffekopp.game.game_object.actor.StatusEffect;
import com.kaffekopp.game.constants.ItemConstants;
import com.kaffekopp.game.game_object.actor.Effect;
import com.kaffekopp.game.game_object.GameObject;
import com.kaffekopp.game.game_object.GameObjectIdentifier;

// TODO: Add comments
public class Item extends GameObject {

    private int score;

    private Effect effect;

    private StatusEffect statusEffect;

    public Item(World world, float x, float y) {
        super(world, x, y);
        this.score = ItemConstants.SCORE;
    }

    /**
     * Testing instance
     */
    public Item(World world, Vector2 position) {
        super(world, position);
        this.score = ItemConstants.SCORE;
    }

    @Override
    public BodyDef.BodyType getBodyType() {
        return BodyDef.BodyType.DynamicBody;
    }

    @Override
    public GameObjectIdentifier<Item> getCenterUserData() {
        return new GameObjectIdentifier<>(this);
    }

    public void setEffectValue(int value) {
        this.statusEffect.setValue(value);
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public StatusEffect getStatusEffect() {
        return statusEffect;
    }

    public void setStatusEffect(StatusEffect effect) {
        this.effect = effect.getEffect();
        this.statusEffect = effect;
    }

    public Effect getEffect() {
        return effect;
    }
}
