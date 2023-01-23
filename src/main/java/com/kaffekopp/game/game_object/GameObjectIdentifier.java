package com.kaffekopp.game.game_object;

import com.kaffekopp.game.listener.GameContactListener;

/**
 * Is used to identify what hit-box belongs to what object.
 * All hit-boxes (Fixtures in LibGDX) can contain an Object.
 * In KurtMarioGame this Object is a GameObjectIdentifier,
 * which contains the specific instance that "owns" that hit-box.
 * Therefor one can reference the instance when it collides with something
 * @see GameContactListener
 *
 * @author Nils Michael
 */
public class GameObjectIdentifier <T> {
    private final T gameObject;

    /**
     * Used to identity Player specific hit-boxes, such as "kicks"
     */
    private boolean isAppendage = false;

    public GameObjectIdentifier(T gameObject) {
        this.gameObject = gameObject;
    }

    public T getGameObject() {
        return gameObject;
    }

    public boolean isAppendage() {
        return isAppendage;
    }

    public void setAppendage(boolean appendage) {
        isAppendage = appendage;
    }
}
