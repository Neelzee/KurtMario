package com.kaffekopp.game.constants;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.kaffekopp.game.game_object.GameObject;
import com.kaffekopp.game.game_object.actor.platform.PlatformMovement;

import java.util.ArrayList;

/**
 * Contains functions that are used in several classes
 *
 * @author Nils Michael
 */
public class Functions {
    /**
     * Returns the absolute distance between two points
     * @param a start point
     * @param b end point
     * @return distance
     */
    private static float distance(Vector2 a, Vector2 b) {
        return (float) Math.sqrt(Math.pow((a.x - b.x), 2) + Math.pow((a.y - b.y), 2));
    }

    /**
     * Returns distance between two vectors
     * @param a Start
     * @param b End
     * @return distance
     */
    public static float distance(GameObject a, GameObject b) {
        return distance(a.getB2body().getWorldCenter(), b.getB2body().getWorldCenter());
    }

    public static <T extends GameObject> T closestObject(ArrayList<T> objects, GameObject target) {
        float distance = Functions.distance(objects.get(0).getB2body().getPosition(), target.getB2body().getPosition());
        T closestItem = objects.get(0);
        for (T i : objects) {
            if (Functions.distance(i.getB2body().getPosition(), target.getB2body().getPosition()) < distance) {
                closestItem = i;
                distance = Functions.distance(i.getB2body().getPosition(), target.getB2body().getPosition());
            }
        }
        return closestItem;
    }

    /**
     * Scales the Vector with PPM
     * @param v Vector
     * @return Game scaled vector
     */
    public static Vector2 gameScaleVector(Vector2 v) {
        return scalar(v, 1 / GameConstants.PPM);
    }

    /**
     * Checks whether the distance between the position and target is less than or equal to the margin
     * @param position Start vector
     * @param target End vector
     * @param margin Radius
     * @return true if within radius
     */
    public static boolean isNearPoint(Vector2 position, Vector2 target, float margin) {
        return distance(position, target) <= margin;
    }

    public static Vector2 getPosition(Rectangle rect) {
        return new Vector2((rect.getX() + rect.getWidth() / 2), (rect.getY() + rect.getHeight() / 2));
    }

    public static Vector2 inverseVector(Vector2 force) {
        return new Vector2(force.x * -1, force.y * -1);
    }

    /**
     * Creates a Vector between two points
     * @param a point A
     * @param b point B
     * @return new Vector
     */
    public static Vector2 getVector2(Vector2 a, Vector2 b) {
        return new Vector2(a.x - b.x, a.y - b.y);
    }

    public static PlatformMovement parsePlatformAI(String s) {
        if (s == null) {
            return PlatformMovement.XY_MOVEMENT;
        }
        PlatformMovement platformMovement = PlatformMovement.XY_MOVEMENT;
        switch (s) {
            case "YX" -> platformMovement =  PlatformMovement.YX_MOVEMENT;
            case "DIRECT" -> platformMovement =  PlatformMovement.DIRECT_MOVEMENT;
        }
        return platformMovement;
    }

    public static boolean isWithinRange(float result, float value, float margin) {
        return !(value - margin < result) || !(result < value + margin);
    }

    public static float getLength(Vector2 v) {
        return (float) Math.sqrt(v.x * v.x + v.y * v.y);
    }

    public static Vector2 scalar(Vector2 v, float s) {
        return new Vector2(s * v.x, s * v.y);
    }
}
