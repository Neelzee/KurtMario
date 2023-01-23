package com.kaffekopp.game.game_object.actor.monster;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.kaffekopp.game.constants.MonsterConstants;
import com.kaffekopp.game.constants.GameConstants;
import com.kaffekopp.game.game_object.GameObjectIdentifier;
import com.kaffekopp.game.game_object.actor.Actor;
import com.kaffekopp.game.constants.Functions;

/**
 *
 * Monster class can move and tries to attack a player
 *
 * It can kill players
 *
 * @author Nils Michael
 */
public class Monster extends Actor {

    private final float monsterSight;

    private final MonsterAI AI;

    public Monster(World world, float x, float y) {
        super(world, x, y);
        super.setCurrentHp(MonsterConstants.HP);
        super.setDamage(MonsterConstants.DAMAGE);
        super.setMaxHp(MonsterConstants.HP);
        super.setScore(MonsterConstants.SCORE);
        this.AI = new MonsterAI(this, MonsterConstants.AI);
        this.monsterSight = MonsterConstants.SIGHT;
    }

    /**
     * Testing instance
     */
    public Monster(World world, Vector2 position) {
        super(world, position);
        super.setCurrentHp(MonsterConstants.HP);
        super.setDamage(MonsterConstants.DAMAGE);
        super.setMaxHp(MonsterConstants.HP);
        super.setScore(MonsterConstants.SCORE);
        this.AI = new MonsterAI(this, MonsterConstants.AI);
        this.monsterSight = MonsterConstants.SIGHT;
    }

    @Override
    public GameObjectIdentifier<Monster> getCenterUserData() {
        return new GameObjectIdentifier<>(this);
    }

    /**
     * Checks if the target is within a specific range
     * @param target target actor
     * @return true if its within range
     */
    public boolean canSee(Actor target) {
        if (target == null) {
            return false;
        }
        return Functions.distance(this, target) / GameConstants.PPM < getMonsterSight();
    }

    @Override
    public void move() {
        if (super.getB2body().getLinearVelocity().x >= super.getSpeedLimit() || super.getB2body().getLinearVelocity().x <= -1 * super.getSpeedLimit()) {
            return;
        }
        super.applyForce(AI.move());
    }

    public float getMonsterSight() {
        return monsterSight;
    }

    public void setAI(MonsterAILevel AI) {
        this.AI.setAILevel(AI);
    }

    public void setTarget(Actor target) {
        AI.setTarget(target);
    }

    public enum MonsterAILevel {
        STUDENT,
        DUMB,
        SMART,
        NONE
    }
}
