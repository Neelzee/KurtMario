package com.kaffekopp.game.listener;

import com.badlogic.gdx.physics.box2d.*;
import com.kaffekopp.game.game_object.GameObjectIdentifier;
import com.kaffekopp.game.game_object.actor.Actor;
import com.kaffekopp.game.game_object.actor.platform.Platform;
import com.kaffekopp.game.game_object.actor.player.Player;

/**
 * LibGDX ContactListener is called when two Fixtures collide. This happens outside the normal physics calculations.
 * Therefor specific scenarios can be created, such as when a Player collides with an Item, with great amount of speed,
 * the Item is destroyed.
 *
 * The ContactListener or in KurtMarioGame, the GameContactListener, can't determine what Fixture collided with whom,
 * just that two Fixtures, Fixture A and Fixture B, now are touching. So to determine what GameObject collides with what,
 * LibGDX has implemented Fixture.getUserData(), which returns an Object, that is set by Fixture.setUserData(Object object).
 * In KurtMarioGame, GameObjectIdentifier is used to identify what Object crashes with what,
 * And also what specific instance is involved in the collision.
 * @see GameObjectIdentifier
 *
 * A Fixture contains null, if Fixture.setUserData(Object object) has not been used.
 *
 * @author Nils Michael Fitjar
 */
public class GameContactListener implements ContactListener {

    private final PlayerContact playerContact;

    private final ActorContact actorContact;

    private final PlatformContact platformContact;

    public GameContactListener() {
        this.playerContact = new PlayerContact();
        this.actorContact = new ActorContact();
        this.platformContact = new PlatformContact();
    }

    /**
     * Is called when two Fixtures begin touching.
     *
     * To determine what Fixture is what, use Fixture.getUserData()
     * Which returns a GameObjectIdentifier for all GameObjects.
     * Then one can determine what kind of class one is working with,
     * and also the specific instance of that class
     *
     * @param contact contains the two fixtures that are colliding
     */
    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        // Platform interactions
        if ((((GameObjectIdentifier<?>) fixA.getUserData()).getGameObject() instanceof Platform) || (((GameObjectIdentifier<?>) fixB.getUserData()).getGameObject() instanceof Platform)) {
            Fixture platformFix = (((GameObjectIdentifier<?>) fixA.getUserData()).getGameObject() instanceof Platform) ? fixA : fixB;
            Fixture objectFix = platformFix.equals(fixA) ? fixB : fixA;
            platformContact.beginContact(platformFix, objectFix);
        }

        // Actor interactions
        if ((((GameObjectIdentifier<?>) fixA.getUserData()).getGameObject() instanceof Actor) || (((GameObjectIdentifier<?>) fixB.getUserData()).getGameObject() instanceof Actor)) {
            Fixture actorFix = (((GameObjectIdentifier<?>) fixA.getUserData()).getGameObject() instanceof Actor) ? fixA : fixB;
            Fixture objectFix = actorFix.equals(fixA) ? fixB : fixA;
            actorContact.beginContact(actorFix, objectFix);
        }

        // Player and Object interaction
        if (((GameObjectIdentifier<?>) fixA.getUserData()).getGameObject().getClass() == Player.class || ((GameObjectIdentifier<?>) fixB.getUserData()).getGameObject().getClass() == Player.class) {
            Fixture plrFix = ((GameObjectIdentifier<?>) fixA.getUserData()).getGameObject() instanceof Player ? fixA : fixB;
            Fixture objFix = plrFix.equals(fixA) ? fixB : fixA;
            playerContact.beginContact(plrFix, objFix);
        }

    }

    /**
     * Is called when two Fixtures move away from each other
     *
     * @param contact contains the two fixtures that are moving away
     */
    @Override
    public void endContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        // Platform interactions
        if ((((GameObjectIdentifier<?>) fixA.getUserData()).getGameObject() instanceof Platform) || (((GameObjectIdentifier<?>) fixB.getUserData()).getGameObject() instanceof Platform)) {
            Fixture platformFix = (((GameObjectIdentifier<?>) fixA.getUserData()).getGameObject() instanceof Platform) ? fixA : fixB;
            Fixture objectFix = platformFix.equals(fixA) ? fixB : fixA;
            platformContact.endContact(platformFix, objectFix);
        }

        // Actor interactions
        if ((((GameObjectIdentifier<?>) fixA.getUserData()).getGameObject() instanceof Actor) || (((GameObjectIdentifier<?>) fixB.getUserData()).getGameObject() instanceof Actor)) {
            Fixture actorFix = (((GameObjectIdentifier<?>) fixA.getUserData()).getGameObject() instanceof Actor) ? fixA : fixB;
            Fixture objectFix = actorFix.equals(fixA) ? fixB : fixA;
            actorContact.endContact(objectFix);
        }

        // Player and object interaction
        if (((GameObjectIdentifier<?>) fixA.getUserData()).getGameObject().getClass() == Player.class || ((GameObjectIdentifier<?>) fixB.getUserData()).getGameObject().getClass() == Player.class) {
            Fixture playerFix = ((GameObjectIdentifier<?>) fixA.getUserData()).getGameObject().getClass() == Player.class ? fixA : fixB;
            Fixture objectFix = playerFix.equals(fixA) ? fixB : fixA;

            playerContact.endContact(playerFix, objectFix);
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold manifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse contactImpulse) {

    }
}
