package com.kaffekopp.game.listener;

import com.badlogic.gdx.physics.box2d.Fixture;
import com.kaffekopp.game.game_object.GameObject;
import com.kaffekopp.game.game_object.GameObjectIdentifier;
import com.kaffekopp.game.game_object.actor.platform.Platform;

public class PlatformContact implements IGameObjectListener {
    public void beginContact(Fixture platformFixture, Fixture objectFixture) {
        Platform platform = (Platform) ((GameObjectIdentifier<?>) platformFixture.getUserData()).getGameObject();
        platform.riders.add((GameObject) ((GameObjectIdentifier<?>) objectFixture.getUserData()).getGameObject());
    }

    public void endContact(Fixture platformFixture, Fixture objectFixture) {
        Platform platform = (Platform) ((GameObjectIdentifier<?>) platformFixture.getUserData()).getGameObject();
        platform.riders.remove((GameObject) ((GameObjectIdentifier<?>) objectFixture.getUserData()).getGameObject());
    }
}
