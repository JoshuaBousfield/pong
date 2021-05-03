package com.joshbousfield.game.controls;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.joshbousfield.game.Constants;
import com.joshbousfield.game.gameobjects.GameObject;

public class ControlScheme {
    private ControlSchemeType type;
    private GameObject gameObject;
//TODO add check for null gameObject
    public ControlScheme(ControlSchemeType type) {
        this.type = type;
    }

    public ControlScheme() {
    }

    public void setGameObject(GameObject gameObject) {
        this.gameObject = gameObject;
    }

    public void updateControl() {
        switch (type) {
            case KEYBOARD_LEFT:
                if (Gdx.input.isKeyPressed(Input.Keys.W)) {
                    moveDirection(Direction.UP);
                    break;
                }
                if (Gdx.input.isKeyPressed(Input.Keys.S)) {
                    moveDirection(Direction.DOWN);
                    break;
                }
                moveDirection(Direction.NONE);
                break;
            case KEYBOARD_RIGHT:
                if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
                    moveDirection(Direction.UP);
                    break;
                }
                if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
                    moveDirection(Direction.DOWN);
                    break;
                }
                moveDirection(Direction.NONE);
                break;
            case CONTROLLER:
                break;
        }
    }

    private void moveDirection(Direction direction) {
        switch (direction) {
            case UP:
                gameObject.getBody().setLinearVelocity(0, Constants.MOVEMENT);
                break;
            case DOWN:
                gameObject.getBody().setLinearVelocity(0, -Constants.MOVEMENT);
                break;
            case NONE:
                gameObject.getBody().setLinearVelocity(0, 0);
                break;
        }
    }
}
