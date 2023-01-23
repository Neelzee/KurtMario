package game_object.actor.monster;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.kaffekopp.game.constants.GameConstants;
import com.kaffekopp.game.constants.MonsterConstants;
import com.kaffekopp.game.game_object.GameObjectIdentifier;
import com.kaffekopp.game.game_object.actor.monster.Monster;
import com.kaffekopp.game.game_object.actor.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MonsterTest {

    Monster monster;

    Player playerClose;

    Player playerFar;

    World world;

    @BeforeEach
    void setup() {
        world = new World(GameConstants.EARTH_GRAVITY, true);
        monster = new Monster(world, new Vector2(0, 0));
        playerClose = new Player(world, new Vector2(monster.getMonsterSight() * GameConstants.PPM - 1, 0));
        playerFar = new Player(world, new Vector2(monster.getMonsterSight() * GameConstants.PPM + 1, 0));
    }

    @Test
    void getCenterUserData() {
        assertEquals(new GameObjectIdentifier<>(monster).getGameObject(), monster.getCenterUserData().getGameObject());
    }

    @Test
    void canSee() {
        assertEquals(MonsterConstants.SIGHT, monster.getMonsterSight());
        assertTrue(monster.canSee(playerClose));
        assertFalse(monster.canSee(playerFar));
    }

    @Test
    void move() {
        assertEquals(new Vector2(0, 0), monster.getB2body().getLinearVelocity());
        monster.move();
        assertEquals(new Vector2(monster.getVelocity() * -1, 0), monster.getB2body().getLinearVelocity());
    }

    @Test
    void testNoneAI() {
        monster.setAI(Monster.MonsterAILevel.NONE);
        monster.move();
        assertEquals(new Vector2(0, 0), monster.getB2body().getLinearVelocity());
    }

    @Test
    void testStudentAI() {
        monster.setAI(Monster.MonsterAILevel.STUDENT);
        monster.move();
        assertEquals(new Vector2(monster.getVelocity() * -1, 0), monster.getB2body().getLinearVelocity());
    }

    @Test
    void testDumbAINoTarget() {
        monster.setAI(Monster.MonsterAILevel.DUMB);
        monster.move();
        assertEquals(new Vector2(0, 0), monster.getB2body().getLinearVelocity());
    }

    @Test
    void testDumbAICloseTarget() {
        monster.setAI(Monster.MonsterAILevel.DUMB);
        monster.setTarget(playerClose);
        monster.move();
        assertEquals(new Vector2(monster.getVelocity(), 0), monster.getB2body().getLinearVelocity());
    }

    @Test
    void testDumbAIFarTarget() {
        monster.setAI(Monster.MonsterAILevel.DUMB);
        monster.setTarget(playerFar);
        monster.move();
        assertEquals(new Vector2(0, 0), monster.getB2body().getLinearVelocity());
    }
}