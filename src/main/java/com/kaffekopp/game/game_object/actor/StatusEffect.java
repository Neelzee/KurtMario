package com.kaffekopp.game.game_object.actor;

import com.badlogic.gdx.graphics.Color;
import com.kaffekopp.game.constants.GameConstants;
import com.kaffekopp.game.constants.ItemConstants;

/**
 * Status effects are effects that affect an Actor over time.
 * StatusEffect class contains method to ensure an Actor is affected by the effect, and also what that effect does.
 *
 * A "Tick" is defined in GameConstants.TIME_STEP, and is once every frame.
 * @see GameConstants
 *
 * @author Nils Michael
 */
public class StatusEffect {
    /**
     * Max time span for the effect.
     */
    private int maxTick;

    /**
     * How often the effect occurs.
     */
    private final int effectTick;

    /**
     * Current time/tick
     */
    private int currentTick;

    /**
     * What effect is affecting the Actor
     */
    private final Effect effect;

    /**
     * What value is added to the effect
     */
    private int value = ItemConstants.EFFECT_VALUE;

    /**
     * How much the tick is increased by, each game tick
     * Default is 1
     */
    private final int tickRate;

    /**
     * @param effect ActorEffect.
     * @param maxTick Max tick limit.
     * @param effectTick How often the effect is applied to the Actor.
     */
    public StatusEffect(Effect effect, int maxTick, int effectTick, int tickRate) {
        this.maxTick = maxTick;
        this.currentTick = 0;
        this.effectTick = effectTick;
        this.effect = effect;
        this.tickRate = tickRate;
    }

    public void applyEffect(Actor target) {
        switch (effect) {
            case DAMAGE -> {
                target.takeDamage(value);
                currentTick = maxTick;
            }
            case HEALING -> {
                target.heal(value);
                currentTick = maxTick;
            }
            case MAX_HEALTH_INCREASE -> {
                target.setMaxHp(target.getMaxHp() + value);
                currentTick = maxTick;
            }
            case IMMUNE -> {
                // Left empty, as immunity does only take effect when target takes damage.
            }
            case POISON -> {
                target.setCurrentColor(Color.LIME);
                if (currentTick % effectTick == 0) { // Happens every other effect tick
                    target.takeDamage(value);
                }
            }
            case FIRE -> {
                target.setCurrentColor(Color.RED);
                if (currentTick % effectTick == 0) { // Happens every other effect tick
                    target.takeDamage(value);
                }
            }
            case JUMP_BOOST -> {
                if (currentTick == 0) { // Happens once
                    target.setJumpForce(target.getJumpForce() * 2);
                }
            }
            case SPEED_BOOST -> {
                if (currentTick == 0) { // Happens once
                    target.setVelocity(target.getVelocity() * 2);
                }
            }
            case REGENERATION -> {
                target.setCurrentColor(Color.PINK);
                if (currentTick % effectTick == 0) { // Happens every other effect tick
                    target.heal(value);
                }
            }
        }
    }

    /**
     * Removes status effects
     * @param target affected target
     */
    public void cleanUp(Actor target) {
        switch (effect) {
            case POISON, FIRE, REGENERATION -> target.setCurrentColor(target.getOriginalColor()); // Sets color to original
            case JUMP_BOOST -> target.setJumpForce(target.getJumpForce() / 2);
            case SPEED_BOOST -> target.setVelocity(target.getVelocity() / 2);
        }
    }

    /**
     * Increases the currentTick by one.
     */
    public void tick() {
        currentTick += tickRate;
    }

    /**
     * Returns true if the effect is over.
     * @return currentTick >= maxTick.
     */
    public boolean isOver() {
        return currentTick == maxTick;
    }

    @Override
    public String toString() {
        return effect + " Ticks: " + currentTick + "/" + maxTick + ". Tick rate: " + tickRate + ". Effect rate: " + effectTick;
    }

    public int getMaxTick() {
        return maxTick;
    }

    public void setMaxTick(int maxTick) {
        this.maxTick = maxTick;
    }

    public int getCurrentTick() {
        return currentTick;
    }

    public void setCurrentTick(int tick) {
        this.currentTick = tick;
    }

    public Effect getEffect() {
        return effect;
    }

    public void setValue(int value) {
        this.value = value;
    }

}
