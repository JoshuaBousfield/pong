package com.joshbousfield.game.gameobjects;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.joshbousfield.game.controls.ControlScheme;

public class Paddle extends GameObject {

    private final ControlScheme controlScheme;

    public Paddle(Rectangle rectangle, ShapeRenderer shapeRenderer, World world, GameObjectType type, ControlScheme controlScheme) {
        super(rectangle, shapeRenderer, world, type);
        getBody().setType(BodyDef.BodyType.KinematicBody);
        getBody().setUserData(this);
        this.controlScheme = controlScheme;
    }

    @Override
    void update() {
        controlScheme.updateControl();
    }

    @Override
    public void reset() {

    }
}
