package com.kaffekopp.game.game_object.actor;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.kaffekopp.game.constants.ActorConstants;
import com.kaffekopp.game.screens.PlayScreen;
import com.kaffekopp.game.game_object.GameObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * All instances of Actor can move in the game world
 *
 * @author Nils Michael
 */
public abstract class Actor extends GameObject {

    private int currentHp;

    private int score;

    private int maxHp;

    private int damage;

    /**
     * Force applied to the Actor when it tries to jump
     */
    private float jumpForce;

    /**
     * Amount of force applied each time the Actor moves
     */
    private float velocity;

    /**
     * Limits how fast the Actor instance can move.
     * Ensuring the force acting on the Actor is never greater than the speed limit.
     */
    private float speedLimit;

    /**
     * List of all Status Effects that are affecting the Actor
     * @see StatusEffect
     */
    public final HashMap<Effect, StatusEffect> statusEffects;

    public Actor(World world, float x, float y) {
        super(world, x, y);
        this.currentHp = ActorConstants.MAX_HP;
        this.speedLimit = ActorConstants.SPEED_LIMIT;
        this.velocity = ActorConstants.VELOCITY;
        this.jumpForce = ActorConstants.JUMP_FORCE;
        this.damage = ActorConstants.DAMAGE;
        this.maxHp = ActorConstants.MAX_HP;
        this.statusEffects = new HashMap<>();
    }

    /**
     * Testing instance
     */
    public Actor(World world, Vector2 position) {
        super(world, position);
        this.currentHp = ActorConstants.MAX_HP;
        this.speedLimit = ActorConstants.SPEED_LIMIT;
        this.velocity = ActorConstants.VELOCITY;
        this.jumpForce = ActorConstants.JUMP_FORCE;
        this.damage = ActorConstants.DAMAGE;
        this.maxHp = ActorConstants.MAX_HP;
        this.statusEffects = new HashMap<>();
    }

    /**
     * Ensures Actor can have different body types,
     * such as Kinematic for platforms.
     * @return BodyType
     */
    @Override
    public BodyDef.BodyType getBodyType() {
        return BodyDef.BodyType.DynamicBody;
    }

    /**
     * Gets called when an Actor should move, is used by all Actors
     */
    public abstract void move();

    /**
     * Attacks the given Actor target
     * @param target Actor to attack
     * @return true if target died
     */
    public boolean attack(Actor target) {
        target.takeDamage(getDamage());
        if (target.isDead()) {
            addToScore(target.getScore());
        }
        return target.isDead();
    }

    public void addToScore(int score) {
        setScore(getScore() + score);
    }

    /**
     * @return Returns true if Actor is dead, less than 0 HP
     */
    public boolean isDead() {
        return getCurrentHp() <= 0;
    }

    /**
     * Gets called when an Actor is attacked, subtracts the damage amount from current hp
     *
     * @param damage damage taken
     */
    public void takeDamage(int damage) {
        if (statusEffects.containsKey(Effect.IMMUNE)) {
            return;
        }
        setCurrentHp(getCurrentHp() - damage);
    }

    /**
     * Heals the Actor for the given health.
     * Does not go over maxHp
     * @param health points to heal
     */
    public void heal(int health) {
        currentHp += health;
        if (getMaxHp() < getCurrentHp()) { // Checks that if the player is over healed, if it is, sets current health to max
            currentHp = maxHp;
        }
    }

    /**
     * If Actor instance has StatusEffects, they are applied here.
     */
    public void applyEffect() {
        for (Effect effect : statusEffects.keySet()) {
            if (statusEffects.get(effect).isOver()) {
                continue;
            }
            statusEffects.get(effect).applyEffect(this);
        }
        removeEffects(); // Removes old effects
    }

    /**
     * Removes StatusEffects that are no longer active.
     * @see StatusEffect
     */
    private void removeEffects() {
        ArrayList<Effect> keyList = new ArrayList<>(statusEffects.keySet());
        int k = 0;
        for (int i = 0; i < keyList.size(); i++) {
            if (!statusEffects.containsKey(keyList.get(k))) {
                continue;
            }
            if (statusEffects.get(keyList.get(k)).isOver()) {
                statusEffects.get(keyList.get(k)).cleanUp(this);
                statusEffects.remove(keyList.get(k));
            } else {
                k++;
            }
        }
    }

    /**
     * Adds StatusEffect to the list of StatusEffects.
     * If Actor is already under the influence of the StatusEffect,
     * it's time is added to the already existing one.
     * @param effect StatusEffect to be added
     */
    public void addStatusEffect(StatusEffect effect) {
        if (effect.isOver()) {
            effect.applyEffect(this);
            return;
        }
        if (statusEffects.containsKey(effect.getEffect())) {
            if (statusEffects.get(effect.getEffect()).getMaxTick() == -1) {
                statusEffects.get(effect.getEffect()).setMaxTick(effect.getMaxTick());
            }
            statusEffects.get(effect.getEffect()).setCurrentTick(effect.getCurrentTick());
        }
        statusEffects.put(effect.getEffect(), effect);
    }

    /**
     * Ensures that status effects decay.
     * Is called before an actor is rendered.
     *
     * @see PlayScreen
     */
    public void tick() {
        for (Effect effect : statusEffects.keySet()) {
            statusEffects.get(effect).tick();
        }
    }

    public int getCurrentHp() {
        return currentHp;
    }

    public void setCurrentHp(int currentHp) {
        this.currentHp = currentHp;
        if (currentHp > maxHp) {
            maxHp = currentHp;
        }
    }

    public float getJumpForce() {
        return jumpForce;
    }

    public void setJumpForce(float jumpForce) {
        this.jumpForce = jumpForce;
    }

    public float getVelocity() {
        return velocity;
    }

    public void setVelocity(float velocity) {
        this.velocity = velocity;
    }

    public float getSpeedLimit() {
        return speedLimit;
    }

    public void setSpeedLimit(float speedLimit) {
        this.speedLimit = speedLimit;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public void setMaxHp(int maxHp) {
        this.maxHp = maxHp;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void addScore(int score) {
        setScore(getScore() + score);
    }
}
