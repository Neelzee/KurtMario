package com.kaffekopp.game.game_object.actor.platform;

import com.badlogic.gdx.math.Vector2;
import com.kaffekopp.game.constants.Functions;

import java.util.ArrayList;
import java.util.List;


public class PlatformAI {

    private final PlatformMovement movement;

    private final Platform platform;

    private final ArrayList<Vector2> stops = new ArrayList<>();

    private int stopCount = 0;

    public PlatformAI(PlatformMovement movement, Platform platform) {
        this.movement = movement;
        this.platform = platform;
    }

    public Vector2 move() {
        float margin = 0.01f;
        Vector2 force = new Vector2(0, 0);
        if (stops.isEmpty()) {
            return force;
        }

        if (Functions.isNearPoint(platform.getB2body().getPosition(), stops.get(stopCount), margin)) {
            increaseCount();
        }

        Vector2 goalPosition = stops.get(stopCount);
        Vector2 currentPosition = platform.getB2body().getPosition();

        switch (movement) {
            case XY_MOVEMENT -> {
                // Moves first in X direction, til it's within margins
                if (Functions.isWithinRange(currentPosition.x, goalPosition.x, margin)) {
                    // It's not within bounds, so finds needs to move more along the X-axis.
                    force.x = currentPosition.x < goalPosition.x ? platform.getVelocity() : platform.getVelocity() * -1;
                } else {
                    // It's within bounds on the X-axis, so needs to move along the Y-axis.
                    force.y = currentPosition.y < goalPosition.y ? platform.getVelocity() : platform.getVelocity() * -1;
                }
            }
            case YX_MOVEMENT -> {
                // Moves first in Y direction, til it's within margins
                if (Functions.isWithinRange(currentPosition.y, goalPosition.y, margin)) {
                    // It's within bounds on the X-axis, so needs to move along the Y-axis.
                    force.y = currentPosition.y < goalPosition.y ? platform.getVelocity() : platform.getVelocity() * -1;
                } else {
                    // It's not within bounds, so finds needs to move more along the X-axis.
                    force.x = currentPosition.x < goalPosition.x ? platform.getVelocity() : platform.getVelocity() * -1;
                }
            }
            case DIRECT_MOVEMENT -> {
                /*
                Creates a vector between current position and goal position.
                If two vectors are parallel, P | Q -> P = Q * t, where t is some constant
                We don't want any random vector, but a vector that utilizes the full velocity of the Platform,
                without going faster.
                So we need to set a limit to t, which we do with this formula:
                X^2 + Y^2 = velocity
                Since the vector we want is just Vector2((P.x - Q.y) * t, (P.y - Q.y) * t), we use the formula above,
                substituting the X and Y, with the X and Y components of the vector, giving us:
                (P.x - Q.y)^2 * t^2 +(P.y - Q.y)^2 * t^2 = velocity
                Doing some tricks to the formula we get:
                velocity / t^2 = (P.x - Q.y)^2 + (P.y - Q.y)^2
                velocity / t^2 / (P.x - Q.y)^2 + (P.y - Q.y)^2 = 1
                t^2 = velocity / (P.x - Q.y)^2 + (P.y - Q.y)^2
                t = sqrt(velocity / (P.x - Q.y)^2 + (P.y - Q.y)^2)
                 */
                Vector2 totalVector = Functions.getVector2(currentPosition, goalPosition);
                double t = Math.sqrt(platform.getVelocity() / (Math.pow(totalVector.x, 2) + Math.pow(totalVector.y, 2)));
                force.x = (float) (totalVector.x / t);
                force.y = (float) (totalVector.y / t);
                force = Functions.inverseVector(force);

                /*
                Now we have the correct direction, we want it to have the correct speed.
                Using different ideas, we get the similar formula.
                A vector has a length (speed), which we calculate with |V| = sqrt(x^2 + y^2)
                We increase this speed by multiplying (scaling) the vector with a constant s.
                using s, we can scale the vector to the wanted speed, like so: V -> [s*x, s*y]
                We can find s, since we know the length of the vector should be equal to the speed, like so:
                speed = sqrt((s*x)^2 + (s*y)^2), doing some tricks we get:
                s = sqrt(speed^2 / x^2 + y^2)
                 */
                if (Functions.getLength(force) != platform.getVelocity()) {
                    float s = (float) Math.sqrt(Math.pow(platform.getVelocity(), 2) / (Math.pow(force.x, 2) + Math.pow(force.y, 2)));
                    force = Functions.scalar(force, s);
                }
            }
        }
        return force;
    }

    public void addStops(Vector2[] stops) {
        this.stops.addAll(List.of(stops));
    }

    private void increaseCount() {
        stopCount++;
        if (stopCount == stops.size()) {
            stopCount = 0;
        }
    }

}
