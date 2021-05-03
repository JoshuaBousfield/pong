package com.joshbousfield.game.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public abstract class GameObject {
    abstract void update();
    public abstract void reset();

    private Body body;
    private ShapeRenderer shapeRenderer;
    private World world;
    private Rectangle rectangle;
    private GameObjectType type;

    public GameObject(Rectangle rectangle, ShapeRenderer shapeRenderer, World world, GameObjectType type) {
        this.shapeRenderer = shapeRenderer;
        this.rectangle = rectangle;
        this.world = world;
        this.type = type;
        buildBody();
        Sprite sprite = new Sprite();
    }

    public void resetPosition() {
        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                body.setTransform(rectangle.x, rectangle.y, 0);
                reset();
            }
        });
//        body.setTransform(rectangle.x, rectangle.y, 0);
    }

    private void buildBody() {
        BodyDef bodyDef = new BodyDef();

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.friction = 0f;

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(rectangle.width, rectangle.height);
        fixtureDef.shape = shape;

        body = world.createBody(bodyDef);
        body.createFixture(fixtureDef);
        body.setTransform(rectangle.x, rectangle.y, 0);
        body.setUserData(type);
    }

    public void render() {
        update();
        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.rect(body.getPosition().x - rectangle.width, body.getPosition().y - rectangle.height, rectangle.width*2, rectangle.height*2);
        //body.getPosition().x, body.getPosition().y,
    }

    public Body getBody() {
        return body;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }
}
