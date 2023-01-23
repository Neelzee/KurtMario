package com.kaffekopp.game.game_object.terrain;

import com.kaffekopp.game.game_object.GameObjectIdentifier;

public record TerrainObject(TerrainType type) {

    /**
     * Returns a unique object instance identifier, so that a specific object can be referenced in WorldContactListener
     *
     * @return GameObjectIdentifier
     */
    public GameObjectIdentifier<?> getUserData() {
        return new GameObjectIdentifier<>(this);
    }
}
