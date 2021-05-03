package com.joshbousfield.game.gameobjects;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Score {
    private int score;
    private Vector2 position;
    private BitmapFont font;
    private SpriteBatch batch;
    //attach to player

    public Score(int score, Vector2 position, BitmapFont font, SpriteBatch batch) {
        this.score = score;
        this.position = position;
        this.font = font;
        this.batch = batch;
    }

    public void render() {
        font.draw(batch, String.valueOf(score), position.x, position.y);
    }

    public void addPoints() {
        score++;
    }
}
