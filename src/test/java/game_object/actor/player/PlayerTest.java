package game_object.actor.player;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.kaffekopp.game.constants.GameConstants;
import com.kaffekopp.game.game_object.actor.player.Player;
import com.kaffekopp.game.game_object.GameObjectIdentifier;
import com.kaffekopp.game.game_object.item.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    Player player;

    Item closeItem;

    World world;

    @BeforeEach
    void setup() {
        world = new World(GameConstants.EARTH_GRAVITY, true);
        player = new Player(world, new Vector2(0, 0));
        closeItem = new Item(world, new Vector2(0, 0));
    }

    @Test
    void getCenterUserData() {
        assertEquals(new GameObjectIdentifier<>(player).getGameObject(), player.getCenterUserData().getGameObject());
    }

    @Test
    void kick() {
        assertEquals(new Vector2(0, 0), closeItem.getB2body().getLinearVelocity());
        player.setKickTarget(closeItem);
        player.kick(closeItem);
        assertEquals(closeItem.getB2body().getLinearVelocity(), player.getKickPower());
    }
}