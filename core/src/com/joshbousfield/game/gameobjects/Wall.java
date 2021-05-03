package com.joshbousfield.game.gameobjects;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.joshbousfield.game.GameScreen;

public class Wall extends GameObject {
    private boolean scoreZone;
    //needed for who to award point
    private Score score;

    public Wall(Rectangle rectangle, ShapeRenderer shapeRenderer, World world, GameObjectType type, boolean scoreZone, Score score) {
        super(rectangle, shapeRenderer, world, type);
        this.scoreZone = scoreZone;
        this.score = score;
        getBody().setType(BodyDef.BodyType.StaticBody);
        getBody().setUserData(this);
    }

    public void score() {
        //fill in what happens when scoring
        //game screen will check for a point scored
        if (scoreZone) {
            score.addPoints();
            GameScreen.getGameScreen().pointScored();
        }
    }

    @Override
    void update() {

    }

    @Override
    public void reset() {

    }
}
