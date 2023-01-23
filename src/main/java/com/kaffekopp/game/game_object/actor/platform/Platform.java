package com.kaffekopp.game.game_object.actor.platform;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.kaffekopp.game.game_object.GameObject;
import com.kaffekopp.game.game_object.GameObjectIdentifier;
import com.kaffekopp.game.game_object.actor.Actor;

import java.util.ArrayList;

public class Platform extends Actor {

    /**
     * Contains the list of all the riders on the platform,
     * was to be used to ensure riders did not fall off.
     */
    public final ArrayList<GameObject> riders = new ArrayList<>();

    private PlatformAI ai;

    /**
     * Unique identifier that is used in construction of the Platform,
     * ensuring it has stops.
     */
    private int PID;

    public Platform(World world, float x, float y) {
        super(world, x, y);
        getB2body().setGravityScale(0); // Ensures the platform instance is not affected by Gravity
    }

    @Override
    public void move() {
        Vector2 force = ai.move();
        getB2body().setLinearVelocity(force);
    }

    @Override
    public BodyDef.BodyType getBodyType() {
        return BodyDef.BodyType.KinematicBody;
    }


    @Override
    public GameObjectIdentifier<Platform> getCenterUserData() {
        return new GameObjectIdentifier<>(this);
    }

    public void addStops(Vector2[] stops) {
        getAi().addStops(stops);
    }

    public PlatformAI getAi() {
        return ai;
    }

    public void setAi(PlatformMovement movement) {
        this.ai = new PlatformAI(movement, this);
    }

    public int getPID() {
        return PID;
    }

    public void setPID(int PID) {
        this.PID = PID;
    }
}
