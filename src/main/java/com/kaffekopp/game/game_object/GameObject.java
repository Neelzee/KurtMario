package com.kaffekopp.game.game_object;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.kaffekopp.game.listener.GameContactListener;
import com.kaffekopp.game.constants.FilePaths;
import com.kaffekopp.game.constants.GameConstants;

/**
 *
 * All instances of GameObjects are objects that has physics and should be rendered in the game world
 *
 * @author Nils Michael
 */
public abstract class GameObject {

    private boolean shouldRender = true;

    private Body b2Body;

    private Sprite sprite;

    /**
     * Contains the original color of the sprite.
     * Used to turn the player sprite "back" to its original color
     */
    private Color originalColor;

    /**
     * Contains the current color of the sprite
     * Is used to maintain color between different sprites
     */
    private Color currentColor;

    /**
     * Constructor for GameObjects
     * @param world world the GameObject is created in
     * @param x X Position
     * @param y Y Position
     */
    public GameObject(World world, float x, float y) {
        BodyDef bDef = new BodyDef();
        bDef.position.set(x, y);
        // Probably not needed for all GameObjects, but means we can have interesting physics in the future
        bDef.type = getBodyType();
        b2Body = world.createBody(bDef);
        originalColor = getSprite().getColor();
        currentColor = originalColor;
    }

    /**
     * Used for testing
     */
    public GameObject(World world, Vector2 position) {
        BodyDef bDef = new BodyDef();
        bDef.position.set(position.x, position.y);
        // Probably not needed for all GameObjects, but means we can have interesting physics in the future
        bDef.type = getBodyType();
        b2Body = world.createBody(bDef);
    }

    /**
     * Removes the old body, and sets it to a new one
     * @param world where the new body is created
     */
    public void disposeOldBody(World world) {
        BodyDef bDef = new BodyDef();
        bDef.position.set(getB2body().getPosition().x, getB2body().getPosition().y);
        bDef.type = getBodyType();
        world.destroyBody(getB2body());
        b2Body = world.createBody(bDef);
        setShape();
    }

    public abstract BodyDef.BodyType getBodyType();

    /**
     * Sets the hit-box for the GameObject, is called when setSprite() is called
     * Used by LibGDX for physics calculations
     */
    protected void setShape() {
        FixtureDef fDef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        // Doesn't need to be scaled, as Sprite is always scaled
        shape.setAsBox(getSprite().getWidth() / 2, getSprite().getHeight() / 2);
        fDef.shape = shape;
        getB2body().createFixture(fDef).setUserData(getCenterUserData());
        shape.dispose();
    }

    /**
     * Returns the Sprite of the GameObject instance
     * @return Sprite
     */
    public Sprite getSprite() {
        if (sprite == null) { // Returns a default sprite, ensures game work even if paths are not functioning
            sprite = new Sprite(new Texture(FilePaths.DEFAULT_SPRITE));
            sprite.setSize(sprite.getWidth() / GameConstants.PPM, sprite.getHeight() / GameConstants.PPM);
        }
        return sprite;
    }

    /**
     * Sets GameObject' Sprite to the given Sprite
     * Scales it with PPM as well
     * @param sprite new Sprite
     */
    public void setSprite(Sprite sprite) {
        if (originalColor == null) {
            originalColor = sprite.getColor();
        }
        if (currentColor != null) {
            sprite.setColor(currentColor);
        } else {
            currentColor = originalColor;
        }
        sprite.setSize(sprite.getWidth() / GameConstants.PPM, sprite.getHeight() / GameConstants.PPM);
        this.sprite = sprite;
        setShape();
    }

    /**
     * Returns a unique object instance identifier, so that a specific object can be referenced in GameContactListener.
     * Specifically for the hit-box in the center, the hit-box created in GameObject.setShape()
     *
     * @see GameContactListener
     *
     * @return GameObjectIdentifier for the Center of the GameObject
     */
    public abstract GameObjectIdentifier<?> getCenterUserData();

    /**
     * Applies a force to the GameObject instance
     * @param force force to be applied to the instance
     */
    public void applyForce(Vector2 force) {
        getB2body().applyLinearImpulse(force, getB2body().getWorldCenter(), true);
    }

    /**
     * Sets height and width of the GameObject,
     * overwrites height and width of the sprite
     * @param width new width
     * @param height new height
     */
    public void setSize(float width, float height) {
        getSprite().setSize(width, height);
        setShape();
    }

    public Body getB2body() {
        return b2Body;
    }

    public void setCurrentColor(Color currentColor) {
        getSprite().setColor(currentColor);
        this.currentColor = currentColor;
    }

    public Color getOriginalColor() {
        return originalColor;
    }

    public boolean shouldRender() {
        return shouldRender;
    }

    public void setShouldRender(boolean shouldRender) {
        this.shouldRender = shouldRender;
    }
}
