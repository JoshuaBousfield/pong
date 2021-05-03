package com.joshbousfield.game.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.joshbousfield.game.Constants;

import java.util.Random;

public class Ball extends GameObject {
    private boolean isDocked;
    private float yDirection;
    private Sound blip;

    public Ball(Rectangle rectangle, ShapeRenderer shapeRenderer, World world, GameObjectType type) {
        super(rectangle, shapeRenderer, world, type);
        getBody().setType(BodyDef.BodyType.DynamicBody);
        getBody().getFixtureList().first().setRestitution(1f);
        getBody().setUserData(this);
        blip = Gdx.audio.newSound(Gdx.files.internal("blip.wav"));
        isDocked = true;
        yDirection = 0;
    }

    @Override
    void update() {
        if (isDocked) {
            Random r = new Random();
            int i = r.nextInt(2);
            if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
                System.out.println("update called");
                switch (i) {
                    case 0:
//                        getBody().applyLinearImpulse(Constants.IMPULSE, 0, getBody().getPosition().x, getBody().getPosition().y, true);
                        getBody().setLinearVelocity(Constants.IMPULSE, yDirection);
                        System.out.println("1");
                        isDocked = false;
                        break;
                    case 1:
                        getBody().setLinearVelocity(-Constants.IMPULSE, yDirection);
                        System.out.println("2");
                        isDocked = false;
                        break;
                }
            }
        }
    }

    @Override
    public void reset() {
        isDocked = true;
        getBody().setLinearVelocity(0, 0);
    }

    public void blip() {
        blip.play();
    }

    public boolean isDocked() {
        return isDocked;
    }

    public void setDocked(boolean docked) {
        isDocked = docked;
    }

    public void setyDirection(float yDirection) {
        this.yDirection = yDirection;
    }

    public void dispose() {
        blip.dispose();
    }
}
