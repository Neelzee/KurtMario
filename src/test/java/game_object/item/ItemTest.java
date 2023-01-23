package game_object.item;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.kaffekopp.game.constants.GameConstants;
import com.kaffekopp.game.game_object.GameObjectIdentifier;
import com.kaffekopp.game.game_object.item.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ItemTest {

    Item item;

    World world;

    @BeforeEach
    void setup() {
        world = new World(GameConstants.EARTH_GRAVITY, true);
        item = new Item(world, new Vector2(0, 0));
    }

    @Test
    void getCenterUserData() {
        assertEquals(new GameObjectIdentifier<>(item).getGameObject(), item.getCenterUserData().getGameObject());
    }
}