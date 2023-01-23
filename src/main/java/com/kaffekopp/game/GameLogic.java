package com.kaffekopp.game;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Disposable;
import com.kaffekopp.game.listener.GameContactListener;
import com.kaffekopp.game.constants.GameConstants;
import com.kaffekopp.game.constants.ScreenConstants;
import com.kaffekopp.game.game_object.GameObject;
import com.kaffekopp.game.game_object.actor.Actor;
import com.kaffekopp.game.game_object.actor.monster.Monster;
import com.kaffekopp.game.game_object.actor.player.Player;
import com.kaffekopp.game.game_object.item.Item;
import com.kaffekopp.game.constants.Functions;
import com.kaffekopp.game.screens.PlayScreen;

import java.util.ArrayList;

/**
 * Keeps track of KurtMarioGame specific action that should occur.
 * Contains all GameObjects on the current map.
 * Checks if they should be rendered or not.
 * @see PlayScreen
 *
 * @author Nils Michael
 */
public class GameLogic implements Disposable {

    private final World world;

    private ArrayList<GameObject> gameObjects;

    private ArrayList<Player> players;

    private Vector2 camPos;

    private int itemCount;

    private int currentItemCount = 0;

    public GameLogic(World world) {

        this.gameObjects = new ArrayList<>();
        this.players = new ArrayList<>();
        this.world = world;

        // Sets contact listener
        world.setContactListener(new GameContactListener());
    }

    /**
     * Returns true if a GameObject is below the map
     * @param object GameObject
     * @return true if it's off the game map
     */
    private boolean isBelowMap(GameObject object) {
        return object.getB2body().getPosition().y < object.getSprite().getHeight() * (-1);
    }

    private boolean isOutSideCam(GameObject object) {
        return object.getB2body().getPosition().x < camPos.x - ScreenConstants.SCALED_VIEWPORT_WIDTH_HALF - object.getSprite().getWidth();
    }

    /**
     * Checks whether a GameObject should be rendered or not
     */
    private void updateRenderState() {
        for (GameObject object : gameObjects) {
            // If it shouldn't be rendered, skip
            if (!object.shouldRender()) {
                continue;
            }
            // If its below the map, it shouldn't be rendered
            if (isBelowMap(object)) {
                object.setShouldRender(false);
            }
            if (object instanceof Actor) {
                if (((Actor) object).isDead()) {
                    object.setShouldRender(false);
                }
            }
            if (object instanceof Player) {
                if (isOutSideCam(object)) {
                    ((Player) object).takeDamage(((Player) object).getCurrentHp());
                    return;
                }
            }
            if (object instanceof Item) {
                if (((Item) object).getScore() == 0) {
                    object.setShouldRender(false);
                }
            }
            if (object instanceof Monster) {
                ((Monster) object).setTarget(Functions.closestObject(players, object));
            }
        }

    }

    /**
     * Applies ticks, effects and moves Actors
     */
    public void updateActors() {
        for (GameObject object : gameObjects) {
            // If it shouldn't be rendered, skip
            if (!object.shouldRender()) {
                continue;
            }
            /*
             All Actors should move,
             also checks if they should be under a StatusEffect
             */
            if (object instanceof Actor) {
                ((Actor) object).move();
                ((Actor) object).applyEffect();
                ((Actor) object).tick();
            }
        }
    }

    /**
     * Updates GameObjects
     *
     * @author Nils Michael
     */
    public void update() {
        updateRenderState();
        if (players.size() > 1 && allPlayersAreAlive()) {
            positionPlayer();
        }
        updateActors();
        removeGameObjects();
    }

    /**
     * Removes GameObjects that should not be rendered
     */
    public void removeGameObjects() {
        int i = 0;
        for (int j = 0; j < gameObjects.size(); j++) {
            GameObject object = gameObjects.get(i);
            if (object.shouldRender()) {
                i++;
                continue;
            }
            // Means an item has been collected
            if (object instanceof Item) {
                currentItemCount++;
            }
            object.setSize(0, 0);
            world.destroyBody(object.getB2body());
            gameObjects.remove(i);
            if (object instanceof Player) {
                ((Player) object).setCurrentHp(0);
            }
        }
    }

    /**
     * Applies a force to the player if it's outside the camera's line of sight.
     */
    private void positionPlayer() {
        float playerDiff = Math.abs(getPlayers().get(0).getB2body().getPosition().x - getPlayers().get(1).getB2body().getPosition().x);
        if (playerDiff > ScreenConstants.SCALED_VIEWPORT_WIDTH_HALF) {
            Player player = getPlayers().get(0).getB2body().getPosition().x < getPlayers().get(1).getB2body().getPosition().x ? getPlayers().get(0) : getPlayers().get(1);
            player.applyForce(new Vector2(2, 0));
        }
    }

    /**
     * @return Returns true if all Players are dead
     */
    public boolean allPlayersDead() {
        for (Player p : players) {
            if (!p.isDead()) {
                return false;
            }
        }
        return true;
    }

    public ArrayList<GameObject> getGameObjects() {
        return gameObjects;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public World getWorld() {
        return world;
    }

    /**
     * Sets the GameObject list for the current map.
     * Also sets the Player list
     * @param objects all GameObjects on the current map
     */
    public void setGameObjects(ArrayList<GameObject> objects) {
        this.gameObjects = objects;
        this.players = new ArrayList<>();
        this.itemCount = 0;
        for (GameObject o : objects) {
            if (o instanceof Player) {
                this.players.add((Player) o);
            }
            if (o instanceof Item) {
                itemCount++;
            }
        }
    }

    /**
     * Seps the world
     */
    public void step(float dt) {
        world.step(GameConstants.TIME_STEP * dt, 6, 2);
    }

    @Override
    public void dispose() {
        world.dispose();
    }

    /**
     * @return Returns true if a map is won
     */
    public boolean isMapWon() {
        int i = 0;
        for (GameObject o : gameObjects) {
            if (o instanceof Item) {
                i++;
            }
        }
        return i == 0;
    }

    /**
     * Updates the game cam position
     * @param camPos game cam position
     */
    public void setCamPos(Vector3 camPos) {
        this.camPos = new Vector2(camPos.x, camPos.y);
    }

    public int getItemCount() {
        return itemCount;
    }

    public int getCurrentItemCount() {
        return currentItemCount;
    }

    public boolean allPlayersAreAlive() {
        for (Player p : getPlayers()) {
            if (p.isDead()) {
                return false;
            }
        }
        return true;
    }
}
