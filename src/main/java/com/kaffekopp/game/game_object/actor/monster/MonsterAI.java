package com.kaffekopp.game.game_object.actor.monster;

import com.badlogic.gdx.math.Vector2;
import com.kaffekopp.game.game_object.actor.Actor;

/**
 * Calculate what move the Monster instance should do, with what "smartness" level it has
 *
 * @author Nils Michael
 */
public class MonsterAI {

    private Monster.MonsterAILevel AILevel;

    private final Monster monster;

    private float prevX;

    private Vector2 prevMove;

    private Actor target;

    public MonsterAI(Monster monster, Monster.MonsterAILevel AILevel) {
        this.monster = monster;
        this.AILevel = AILevel;
    }

    public Vector2 move() { // TODO: Implement more AI
        Vector2 movement = new Vector2(monster.getVelocity(), 0);
        switch (AILevel) {
            case STUDENT: // Dumbest AI level, moves left til it can't, then moves right
                // If no move has been made before, move towards left
                if (prevMove == null) {
                    movement =  new Vector2(monster.getVelocity() * -1, 0);
                    break;
                }
                // If stuck, move in the inverse direction of the previous move
                if (prevX == monster.getB2body().getPosition().x) {
                    movement = new Vector2(prevMove.x * -1, 0);
                    break;
                }
                // Else continue to move in the previous direction
                movement = prevMove;
                break;
            case DUMB:  // Moves if it can see player
                movement = new Vector2(0, 0);
                if (monster.canSee(target)) {
                    // Player is to the right of the monster
                    if (target.getB2body().getPosition().x > monster.getB2body().getPosition().x) {
                        movement.x = monster.getVelocity();
                    } else {
                        movement.x = monster.getVelocity() * (-1);
                    }
                    // Player is above the monster
                    if (target.getB2body().getPosition().y > monster.getB2body().getPosition().y) {
                        if (canJump(monster)) movement.y = monster.getJumpForce();
                    }
                }
                break;
            case SMART:
            case NONE:
                movement = new Vector2(0, 0);
                break;
        }
        prevX = monster.getB2body().getPosition().x;
        prevMove = movement;
        return movement;
    }

    private boolean canJump(Monster monster) {
        return monster.getB2body().getLinearVelocity().y == 0;
    }

    public void setTarget(Actor target) {
        this.target = target;
    }

    public void setAILevel(Monster.MonsterAILevel AILevel) {
        this.AILevel = AILevel;
    }

}
