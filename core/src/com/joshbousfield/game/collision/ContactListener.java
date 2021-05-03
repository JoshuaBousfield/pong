package com.joshbousfield.game.collision;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.joshbousfield.game.Constants;
import com.joshbousfield.game.gameobjects.Ball;
import com.joshbousfield.game.gameobjects.Paddle;
import com.joshbousfield.game.gameobjects.Wall;

public class ContactListener implements com.badlogic.gdx.physics.box2d.ContactListener {
    Fixture a;
    Fixture b;

    @Override
    public void beginContact(Contact contact) {
        a = contact.getFixtureA();
        b = contact.getFixtureB();
        if (a.getBody().getUserData() instanceof Paddle) {
            //change ball direction
            b.getBody().setLinearVelocity(b.getBody().getLinearVelocity().x, (b.getBody().getPosition().y - a.getBody().getPosition().y) * Constants.BOUNCE);
            ((Ball)b.getBody().getUserData()).blip();
        } else if (b.getBody().getUserData() instanceof Paddle) {
            a.getBody().setLinearVelocity(a.getBody().getLinearVelocity().x, (a.getBody().getPosition().y - b.getBody().getPosition().y) * Constants.BOUNCE);
            ((Ball)a.getBody().getUserData()).blip();
        }
        if (a.getBody().getUserData() instanceof Wall) {
            ((Wall)a.getBody().getUserData()).score();
            ((Ball)b.getBody().getUserData()).blip();
        } else if (b.getBody().getUserData() instanceof Wall) {
            ((Wall)b.getBody().getUserData()).score();
            ((Ball)a.getBody().getUserData()).blip();
        }
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
