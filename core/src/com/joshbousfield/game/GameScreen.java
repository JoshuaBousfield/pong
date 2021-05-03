package com.joshbousfield.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.joshbousfield.game.collision.ContactListener;
import com.joshbousfield.game.controls.ControlScheme;
import com.joshbousfield.game.controls.ControlSchemeType;
import com.joshbousfield.game.gameobjects.*;

public class GameScreen implements Screen {
    public static final GameScreen gameScreen = new GameScreen();
    private GameObject playerOne;
    private GameObject playerTwo;
    private Ball ball;
    private Array<GameObject> walls;
    private Box2DDebugRenderer debug;
    private Score playerOneScore;
    private Score playerTwoScore;
    private float accumulator = 0;

    private FreeTypeFontGenerator fontGenerator;
    private FreeTypeFontGenerator.FreeTypeFontParameter fontParameter;

    private World world;

    private SpriteBatch batch;
    private OrthographicCamera camera;
    private ExtendViewport viewport;

    private ShapeRenderer shapeRenderer;

    public GameScreen() {
        Box2D.init();
        walls = new Array<>();
        world = new World(new Vector2(0, 0), true);
        world.setContactListener(new ContactListener());
        debug = new Box2DDebugRenderer();
        //contact listener here
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        viewport = new ExtendViewport(1920 * Constants.SCALE, 1080 * Constants.SCALE, camera);

        //font
        fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("font.ttf"));
        fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        fontParameter.size = 100;

        shapeRenderer = new ShapeRenderer();
        //add players
        //player 1
        ControlScheme s1 = new ControlScheme(ControlSchemeType.KEYBOARD_LEFT);
        playerOne = new Paddle(new Rectangle(1, viewport.getMinWorldHeight()/2, 1, 3), shapeRenderer, world, GameObjectType.PADDLE, s1);
        s1.setGameObject(playerOne);
        //player 2
        ControlScheme s2 = new ControlScheme(ControlSchemeType.KEYBOARD_RIGHT);
        playerTwo = new Paddle(new Rectangle(viewport.getMinWorldWidth() - 1, viewport.getMinWorldHeight()/2, 1, 3), shapeRenderer, world,
                GameObjectType.PADDLE, s2);
        s2.setGameObject(playerTwo);
        //create ball
        ball = new Ball(new Rectangle(viewport.getMinWorldWidth()/2, viewport.getMinWorldHeight()/2, 1, 1), shapeRenderer, world,
                GameObjectType.BALL);
        //add scores need to fix font
        BitmapFont font = fontGenerator.generateFont(fontParameter);
        font.getData().setScale(.1f);
        playerOneScore = new Score(0, new Vector2(10, viewport.getMinWorldHeight()-10), font, batch);
        playerTwoScore = new Score(0, new Vector2(viewport.getMinWorldWidth()-15, viewport.getMinWorldHeight()-10), font, batch);
        //add Walls top and bottom
        walls.add(new Wall(new Rectangle(0, 0, viewport.getMinWorldWidth(), 0), shapeRenderer, world, GameObjectType.WALL,
                false, null));
        walls.add(new Wall(new Rectangle(0, viewport.getMinWorldHeight(), viewport.getMinWorldWidth(), 0), shapeRenderer, world,
                GameObjectType.WALL, false, null));
        //add scoring walls
        walls.add(new Wall(new Rectangle(0, 0, 0, viewport.getMinWorldHeight()), shapeRenderer, world, GameObjectType.WALL,
                true, playerOneScore));
        walls.add(new Wall(new Rectangle(viewport.getMinWorldWidth(), 0, 0, viewport.getMinWorldHeight()), shapeRenderer, world, GameObjectType.WALL,
                true, playerTwoScore));
    }

    public void pointScored() {
        //check walls for point scored
        //render method
        playerOne.resetPosition();
        playerTwo.resetPosition();
        ball.resetPosition();
//        ball.reset();
    }

    private void stepWorld() {
        float delta = Gdx.graphics.getDeltaTime();
        accumulator += Math.min(delta, 0.25f);
        if (accumulator >= Constants.STEP_TIME) {
            accumulator -= Constants.STEP_TIME;
            world.step(Constants.STEP_TIME, Constants.VELOCITY_ITERATIONS, Constants.POSITION_ITERATIONS);
        }
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //sprite batch
        batch.begin();
        playerOneScore.render();
        playerTwoScore.render();
        batch.end();
        //shape renderer
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.rect((viewport.getMinWorldWidth()/2) - 0.5f, 0, 1, viewport.getMinWorldHeight());
        playerOne.render();
        playerTwo.render();
        ball.render();
        shapeRenderer.end();
//        debug.render(world, camera.combined);
        stepWorld();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        batch.setProjectionMatrix(camera.combined);
        shapeRenderer.setProjectionMatrix(camera.combined);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
        world.dispose();
        batch.dispose();
        ball.dispose();
    }

    public static GameScreen getGameScreen() {
        return gameScreen;
    }
}
