package com.kaffekopp.game.game_object.actor.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.kaffekopp.game.constants.PlayerConstants;
import com.kaffekopp.game.constants.FilePaths;
import com.kaffekopp.game.game_object.GameObject;
import com.kaffekopp.game.game_object.GameObjectIdentifier;
import com.kaffekopp.game.game_object.actor.Actor;
import com.kaffekopp.game.game_object.actor.Effect;
import com.kaffekopp.game.game_object.item.Item;

/**
 * Player class controls what happens to the sprite on the game screen
 * It has several attributes, such as playerControls, which contains the key binds to move/control the player sprite.
 * @see PlayerInput
 *
 * The Player class is a subclass of the Actor class, and can therefore move and die, (except when immune),
 * except that it can change its sprite and therefore hit-box, depending on it laying down or not.
 *
 * @author Nils Michael
 */
public class Player extends Actor {

    /**
     * If its heading right (true) or left (false)
     */
    private boolean heading = true;

    /**
     * Is used in Player.move(), to see what keypress LibGDX should listen for.
     * @see PlayerInput
     */
    private PlayerInput playerControls;

    /**
     * Is true if the Player instance is prone.
     * When a Player is prone, it cannot jump, so it's used in Player.move()
     */
    private boolean isProne = false;

    /**
     * Toggled by WorldContactListener, to ensure no clipping happens when a Player is in a tunnel space that
     * is smaller than its original height
     */
    private boolean canStand = true;

    /**
     * What the Player can currently kick.
     * Is set by WorldContactListener once a GameObject enters the hit-box.
     * Is set to null once a GameObject leaves the hit-box
     */
    private GameObject kickTarget;

    /**
     * The amount of force applied to the Player.kickTarget, if kicked
     */
    private final Vector2 kickPower;

    /**
     * Is the World the physics of LibGDX is calculated.
     * Is needed to create new hit-boxes
     */
    private final World world;

    /**
     * Filepath to standing sprite
     */
    private String standingSprite;

    /**
     * Filepath to prone sprite
     */
    private String proneSprite;

    /**
     * Check for mute sound.
     */
    private boolean isMuted = false;

    /**
     * Checks for if player can jump
     */
    private boolean canJump = false;
    public Player(World world, float x, float y) {
        super(world, x, y);
        super.setJumpForce(PlayerConstants.JUMP_FORCE);
        super.setSpeedLimit(PlayerConstants.SPEED_LIMIT);
        super.setDamage(PlayerConstants.DAMAGE);
        super.setScore(0);
        this.playerControls = PlayerConstants.getPlayerOneControls();
        this.world = world;
        this.kickPower = PlayerConstants.KICK_FORCE;
    }

    /**
     * Testing instance
     */
    public Player(World world, Vector2 position) {
        super(world, position);
        super.setJumpForce(PlayerConstants.JUMP_FORCE);
        super.setSpeedLimit(PlayerConstants.SPEED_LIMIT);
        super.setDamage(PlayerConstants.DAMAGE);
        super.setScore(0);
        this.playerControls = PlayerConstants.getPlayerOneControls();
        this.world = world;
        this.kickPower = PlayerConstants.KICK_FORCE;
    }

    /**
     * Creates the feet hit-boxes used by the WorldContactListener to find out what GameObjects the Player can kick.
     * Is created everytime the player is standing up
     */
    private void createAppendages() {
        // Right foot, left foot
        FixtureDef fDef = new FixtureDef();
        PolygonShape rightFoot = new PolygonShape();
        PolygonShape leftFoot = new PolygonShape();
        GameObjectIdentifier<Player> playerAppendage = new GameObjectIdentifier<>(this);
        playerAppendage.setAppendage(true);
        rightFoot.setAsBox(getSprite().getWidth() / 3, getSprite().getHeight() / 6, new Vector2(getSprite().getWidth() * 5 / 6, getSprite().getHeight() * -1/3), 0);
        leftFoot.setAsBox(getSprite().getWidth() / 3, getSprite().getHeight() / 6, new Vector2((getSprite().getWidth() * 5 / 6) * (-1), getSprite().getHeight() * -1/3), 0);
        fDef.shape = rightFoot;
        fDef.isSensor = true;
        getB2body().createFixture(fDef).setUserData(playerAppendage);
        rightFoot.dispose();
        fDef = new FixtureDef();
        fDef.shape = leftFoot;
        fDef.isSensor = true;
        getB2body().createFixture(fDef).setUserData(playerAppendage);
        leftFoot.dispose();
    }

    /**
     * Overrides GameObject's setSprite(), to initiate color for Player.originalColor and Player.currentColor
     * if they are null. After this, calls on GameObjects.setSprite, as well as Player.createAppendages().
     * @param sprite new Sprite
     */
    @Override
    public void setSprite(Sprite sprite) {
        super.setSprite(sprite);
        createAppendages();
    }


    @Override
    public GameObjectIdentifier<Player> getCenterUserData() {
        return new GameObjectIdentifier<>(this);
    }

    /**
     * Listens to key presses made by the user, and performs different actions depending on those key presses.
     * Uses Player.PlayerControls to find out which key to listen for.
     * @see PlayerKeyControls
     *
     * Since each key press is in its own if-statement, a Player can perform multiple actions at the same time.
     * For example, jump and then move left or right at the same time, aka "strafing".
     * This makes the movement seem more "fluid".
     *
     */
    @Override
    public void move() {
       // System.out.println(canJump + " canJump");
        //System.out.println(canStandUp()+ " canStandUp");
        //System.out.println(isProne + " isProne");
        // Listens for the key press that is bound to jumping
        if (Gdx.input.isKeyJustPressed(getKey(PlayerKeyControls.JUMP))) { // Player is jumping
          /*
            If the player is prone, and the user presses the jump key,
            no jumping occurs, but the player is now standing up
             */

            if (isProne() && canStandUp()) {
                setProne(false);
                standUp();
            }
            /*
            Checks that the player is not moving in the Y direction, i.e. jumping,
            and ensures that a player cannot jump infinitely and therefor fly
             */
            //if (getB2body().getLinearVelocity().y == 0) { // TODO: Improve the checking for jumping, as this is true even if the player is just moving up a hill
             if(canJump || getB2body().getLinearVelocity().y == 0){
                if(!isMuted)
                    Gdx.audio.newSound(Gdx.files.internal(FilePaths.JUMP_SOUND)).play(0.05f);
                getB2body().applyLinearImpulse(new Vector2(0, getJumpForce()), getB2body().getWorldCenter(), true);
            }
        }

        /*
         Listens for the key press that is bound to moving right or left
         Also checks that the player is moving below the "speed limit"
         so that the player does not accelerate to infinite speed
         */
        if (Gdx.input.isKeyPressed(getKey(PlayerKeyControls.RIGHT)) && getB2body().getLinearVelocity().x <= getSpeedLimit()) {
            if (!heading) {
                heading = true;
                getSprite().flip(true, false);
            }
            getB2body().applyLinearImpulse(new Vector2(getVelocity(), 0), getB2body().getWorldCenter(), true);
        }
        // Moving left, is the same as moving right, but with a negative X value
        if (Gdx.input.isKeyPressed(getKey(PlayerKeyControls.LEFT)) && getB2body().getLinearVelocity().x >= getSpeedLimit() * -1) {
            if (heading) {
                heading = false;
                getSprite().flip(true, false);
            }
            getB2body().applyLinearImpulse(new Vector2(getVelocity() * (-1), 0), getB2body().getWorldCenter(), true);
        }

        /*
         Listens for the key press that is bound to crouching
         If the player is already prone, nothing happens
         */
        if (Gdx.input.isKeyPressed(getKey(PlayerKeyControls.DOWN)) && ! isProne()) {
            layDown(); // Player's sprite and hit-box is changed to one laying down
            setProne(true);
        }

        /*
         Listens for the key press that is bound to kicking
         also ensures that the player has a target to kick
         */
        if (Gdx.input.isKeyPressed(getKey(PlayerKeyControls.KICK)) && getKickTarget() != null) {
            kick(getKickTarget());
        }
    }

    /**
     * Changes the sprite and hit-box to one standing up
     * Also creates the hit-boxes used for kicking
     */
    public void standUp() {
        Vector2 force = getB2body().getLinearVelocity();
        super.setSpeedLimit(PlayerConstants.SPEED_LIMIT);
        super.setSprite(new Sprite(new Texture(getStandingSprite())));
        // Default is facing right
        if (!heading) {
            getSprite().flip(true, false);
        }
        super.disposeOldBody(world);
        createAppendages();
        getB2body().applyLinearImpulse(force, getB2body().getWorldCenter(), true);
    }

    /**
     * Changes the sprite and hit-box to one laying down
     * Also halves the movement speed of the Player
     */
    public void layDown() {
        Vector2 force = getB2body().getLinearVelocity();
        super.setSpeedLimit(PlayerConstants.SPEED_LIMIT / 2);
        super.setSprite(new Sprite(new Texture(getProneSprite())));
        if (!heading) {
            getSprite().flip(true, false);
        }
        super.disposeOldBody(world);
        getB2body().applyLinearImpulse(force, getB2body().getWorldCenter(), true);
    }

    /**
     * Returns the key that is defined in playerControls, if its bound, else returns 0
     * @see PlayerInput
     *
     * @param key key bind one wants
     * @return correct Input.KEYS.
     */
    private int getKey(PlayerKeyControls key) {
        if (playerControls.getPlayerControls().containsKey(key)) return playerControls.getPlayerControls().get(key);
        return 0;
    }

    /**
     * Picks up the given item, and adds it to the Player instance score.
     * returns true if successfully
     * Since the Item has an effect, applies it to the player
     * @param item closest item
     */
    public void pickUp(Item item) {
        addScore(item.getScore()); // Adds score to player score
        if (item.getEffect().equals(Effect.NONE)) {
            return;
        }
        addStatusEffect(item.getStatusEffect());
    }

    /**
     * Sets the playerControls to the new given one
     * @param playerControls new keybinding layout
     */
    public void setPlayerControls(PlayerInput playerControls){
        this.playerControls = playerControls;
    }

    /**
     * Player "kicks" the given object
     * Applies a kick force to it
     * @param object GameObject to be kicked
     */
    public void kick(GameObject object) {
        Vector2 kickPower = this.kickPower;
        if (object.getB2body().getPosition().x < getB2body().getPosition().x) {
            kickPower.x = kickPower.x * (-1);
        }
        object.applyForce(kickPower);
    }
    @Override
    public void takeDamage(int damage) {
        if(!isMuted)
            Gdx.audio.newSound(Gdx.files.internal(FilePaths.TAKING_DAMAGE)).play(0.1f);
        if (statusEffects.containsKey(Effect.IMMUNE)) {
            isDead();
            return;
        }
        setCurrentHp(getCurrentHp() - damage);
        isDead();
    }
    @Override
    public boolean isDead() {
        if(!isMuted)
            Gdx.audio.newSound(Gdx.files.internal(FilePaths.DEATH)).play(0.1f);
        return getCurrentHp() <= 0;
    }

    public boolean isProne() {
        return isProne;
    }

    public void setProne(boolean prone) {
        isProne = prone;
    }

    public boolean canStandUp() {
        return canStand;
    }

    public void setStanding(boolean canStand) {
        this.canStand = canStand;
    }

    public Vector2 getKickPower() {
        return kickPower;
    }

    public GameObject getKickTarget() {
        return kickTarget;
    }

    public void setKickTarget(GameObject kickTarget) {
        this.kickTarget = kickTarget;
    }

    public String getStandingSprite() {
        if (standingSprite == null) {
            standingSprite = FilePaths.PLAYER_SPRITE;
        }
        return standingSprite;
    }

    public void setStandingSprite(String standingSprite) {
        this.standingSprite = standingSprite;
    }

    public String getProneSprite() {
        if (proneSprite == null) {
            proneSprite = FilePaths.PLAYER_PRONE_SPRITE;
        }
        return proneSprite;
    }

    public void setProneSprite(String proneSprite) {
        this.proneSprite = proneSprite;
    }

    public boolean getHeading() {
        return heading;
    }

    public boolean getIsMuted() {
        return !isMuted;
    }

    public void setMuted(boolean muted) {
        isMuted = muted;
    }

    public void setCanJump(boolean canJump) {
        this.canJump = canJump;
    }
}
