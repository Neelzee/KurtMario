package game_object.actor;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.kaffekopp.game.constants.GameConstants;
import com.kaffekopp.game.game_object.actor.Actor;
import com.kaffekopp.game.game_object.actor.monster.Monster;
import com.kaffekopp.game.game_object.actor.player.Player;
import com.kaffekopp.game.game_object.item.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ActorTest {

    ArrayList<Actor> actors;

    World world;

    @BeforeEach
    void setup() {
        world = new World(GameConstants.EARTH_GRAVITY, true);
        actors = new ArrayList<>();
        actors.add(new Player(world, new Vector2( 0, 0)));
        actors.add(new Player(world, new Vector2( 100, 0)));
        actors.add(new Player(world, new Vector2( -100, 0)));
        actors.add(new Player(world, new Vector2( 0, 100)));
        actors.add(new Monster(world, new Vector2( 200, 0)));
        actors.add(new Monster(world, new Vector2( -200, 0)));
        actors.add(new Monster(world, new Vector2( 0, 200)));
    }

    @Test
    void move() {
        for (Actor actor : actors) {
            assertEquals(new Vector2(0, 0), actor.getB2body().getLinearVelocity());
        }
        for (Actor actor : actors) {
            if (actor instanceof Player) {
                continue;
            }
            actor.move();
            assertEquals(new Vector2(actor.getVelocity() * -1, 0), actor.getB2body().getLinearVelocity());
        }
        for (Actor actor : actors) {
            if (actor instanceof Player) {
                continue;
            }
            actor.move();
            assertEquals(new Vector2(0, 0), actor.getB2body().getLinearVelocity());
        }
        for (Actor actor : actors) {
            if (actor instanceof Player) {
                continue;
            }
            actor.move();
            assertEquals(new Vector2(actor.getVelocity() * -1, 0), actor.getB2body().getLinearVelocity());
        }
    }

    @Test
    void attack() {
        Monster aliveMonster = new Monster(world, new Vector2( 0, 0));
        aliveMonster.setCurrentHp(1000);
        Monster deadMonster = new Monster(world, new Vector2( 0, 0));
        deadMonster.setCurrentHp(0);
        for (Actor actor : actors) {
            assertFalse(actor.attack(aliveMonster));
            assertTrue(actor.attack(deadMonster));
        }
    }

    @Test
    void addToScore() {
        Item item = new Item(world, new Vector2( 0, 0));
        for (Actor actor : actors) {
            actor.setScore(0);
            actor.addToScore(item.getScore());
            assertEquals(item.getScore(), actor.getScore());
        }
    }

    @Test
    void heal() {
        for (Actor actor : actors) {
            int actorHp = actor.getCurrentHp();
            actor.heal(10);
            assertEquals(actorHp, actor.getCurrentHp());
            actor.setCurrentHp(0);
            actor.heal(10);
            assertEquals(10, actor.getCurrentHp());
        }
    }
}