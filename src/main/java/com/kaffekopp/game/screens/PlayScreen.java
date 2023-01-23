package com.kaffekopp.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.kaffekopp.game.constants.GameConstants;
import com.kaffekopp.game.constants.ScreenConstants;
import com.kaffekopp.game.B2WorldCreator;
import com.kaffekopp.game.GameLogic;
import com.kaffekopp.game.KurtMarioGame;
import com.kaffekopp.game.factory.GameObjectSpawner;
import com.kaffekopp.game.game_object.actor.player.Player;
import com.kaffekopp.game.game_object.GameObject;

import java.util.*;

import static com.badlogic.gdx.math.MathUtils.clamp;

public class PlayScreen implements Screen {

    private final float SCALED_VIEWPORT_WIDTH_HALF = ScreenConstants.SCALED_VIEWPORT_WIDTH_HALF;
    private final float SCALED_VIEWPORT_HEIGHT_HALF = ScreenConstants.SCALED_VIEWPORT_HEIGHT_HALF;
    private final float MAGIC_FACTOR = ScreenConstants.MAGIC_FACTOR; //The magic factor which makes the camera stop at the end of maps

    /**
     * Game instance
     */
    private final KurtMarioGame game;

    /**
     * Game Camera
     */
    private final OrthographicCamera gameCam;

    /***
     * Window that the user views the game
     */
    private final Viewport gamePort;

    /**
     * Current map's width
     */
    private float mapWidth;

    /**
     * Current map's height
     */
    private float mapHeight;

    /**
     * Where the camera needs to stop at the end of the current map
     */
    private float camStopRightX;

    /**
     * Where the camera needs to stop at the top of the current map
     */
    private float camStopTopY;

    /**
     * Map renderer
     */
    private final OrthogonalTiledMapRenderer renderer;


    public GameLogic gameLogic;

    private final GameObjectSpawner spawner;



    public PlayScreen(KurtMarioGame game) {
        this.game = game;
        if (game.getMap() == null) {
            game.initialize();
        }

        gameCam = new OrthographicCamera();
        // Ensures everyone gets the same aspect ratio, no matter screen size
        int v_HEIGHT = ScreenConstants.V_HEIGHT;
        // Height and Width ratio of the screen
        int v_WIDTH = ScreenConstants.V_WIDTH;
        gamePort = new FitViewport(v_WIDTH / GameConstants.PPM, v_HEIGHT / GameConstants.PPM, gameCam);

        mapWidth = game.getMap().getProperties().get("width", Integer.class);
        mapHeight = game.getMap().getProperties().get("height", Integer.class);
        camStopRightX = (mapWidth * MAGIC_FACTOR) - SCALED_VIEWPORT_WIDTH_HALF;
        camStopTopY = (mapHeight * MAGIC_FACTOR) - SCALED_VIEWPORT_HEIGHT_HALF;

        renderer = new OrthogonalTiledMapRenderer(game.getMap(), 1 / GameConstants.PPM);
        gameCam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);

        // Keeps track of who should be rendered or removed
        this.gameLogic = new GameLogic(new World(GameConstants.EARTH_GRAVITY, true));

        gameLogic.setCamPos(gameCam.position);
        // Spawns GameObjects in the world
        this.spawner = new GameObjectSpawner();

        gameLogic.setGameObjects(spawner.constructGameObjects(gameLogic.getWorld(), game.getMap()));

        // Create walls
        new B2WorldCreator(gameLogic.getWorld(), game.getMap());
    }


    /**
     * Method which positions the camera in-game
     *
     * @author Thomas Børdal
     */
    public void gameCamPositioner() {
        //The player ahead controls the camera:
        if (gameLogic.allPlayersAreAlive()) {
            if (gameLogic.getPlayers().size() != 0) {
                ArrayList<Float> maxX = new ArrayList<>();
                ArrayList<Float> maxY = new ArrayList<>();
                for (Player p : gameLogic.getPlayers()) {
                    maxX.add(p.getB2body().getPosition().x);
                    maxY.add(p.getB2body().getPosition().y);
                }
                gameCam.position.x = Collections.max(maxX);
                gameCam.position.y = Collections.max(maxY);
            }
        } else {
            for (Player p : gameLogic.getPlayers()) {
                if (!p.isDead()) {
                    gameCam.position.x = p.getB2body().getPosition().x;
                    gameCam.position.y = p.getB2body().getPosition().y;
                }
            }
        }

        //Logic for making camera stop at the end of the maps:
        if (SCALED_VIEWPORT_WIDTH_HALF < camStopRightX) {
            gameCam.position.x = clamp(gameCam.position.x, SCALED_VIEWPORT_WIDTH_HALF, camStopRightX);
        } else { //The map-width is small, so it doesn't need to move x-wise
            gameCam.position.x = SCALED_VIEWPORT_WIDTH_HALF;
        }
        if (SCALED_VIEWPORT_HEIGHT_HALF < camStopTopY) {
            gameCam.position.y = clamp(gameCam.position.y, SCALED_VIEWPORT_HEIGHT_HALF, camStopTopY);
        } else { //The map-height is small, so it doesn't need to move y-wise
            gameCam.position.y = SCALED_VIEWPORT_HEIGHT_HALF;
        }
    }

    public void update(float dt) {

        game.updateHud(gameLogic, dt);

        gameLogic.setCamPos(gameCam.position);
        gameLogic.update();

        // Stepper
        gameLogic.step(dt);
        //world.step(GameConstants.TIME_STEP, 6, 2);

        gameCamPositioner();
        gameCam.update();
        renderer.setView(gameCam);
    }

    @Override
    public void show() {}

    @Override
    public void render(float v) {
        update(v);
        Gdx.gl.glClearColor(0, 0, 0, 1); // Clears colour
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Clears the screen

        boolean gameOver = false;
        if(gameLogic.allPlayersDead() && !gameOver) {
            game.setScreen(new GameOverScreen(game, false, game.getHud()));
        }

        if (gameLogic.isMapWon()) {
            if(game.isGameWon()) {
                game.setGameWon(true);
                game.setScreen(new GameOverScreen(game, true, game.getHud()));
            }else
                nextMap();
        }

        renderer.render();


        // debug lines
        //b2dr.render(gameLogic.getWorld(), gameCam.combined);

        // Camera
        game.getBatch().setProjectionMatrix(gameCam.combined);

        game.getBatch().begin();
        // Loops through all GameObjects that should be rendered
        for (GameObject object : gameLogic.getGameObjects()) {
            if (!object.shouldRender()) {
                continue;
            }
            if(object instanceof Player){
                ((Player) object).setMuted(game.isMuted());
            }
            object.getSprite().setPosition(object.getB2body().getPosition().x - object.getSprite().getWidth() / 2, object.getB2body().getPosition().y - object.getSprite().getHeight() / 2);
            object.getSprite().draw(game.getBatch());
        }
        game.getBatch().end();
        game.getBatch().setProjectionMatrix(game.getHud().stage.getCamera().combined);
        game.getHud().stage.draw();

    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    /**
     * sets the next map of the game to active map.
     */
    private void nextMap(){

        game.increaseLevel();

        // Dispose
        game.getMap().dispose();
        renderer.getMap().dispose();
        gameLogic.dispose();

        spawner.cleanUp();

        // Initialises next map
        game.initialize();

        // Positions camera
        mapWidth = game.getMap().getProperties().get("width", Integer.class);
        mapHeight = game.getMap().getProperties().get("height", Integer.class);
        camStopRightX = (mapWidth * MAGIC_FACTOR) - SCALED_VIEWPORT_WIDTH_HALF;
        camStopTopY = (mapHeight * MAGIC_FACTOR) - SCALED_VIEWPORT_HEIGHT_HALF;


        renderer.setMap(game.getMap());
        gameLogic = new GameLogic(new World(GameConstants.EARTH_GRAVITY, true));
        new B2WorldCreator(gameLogic.getWorld(), game.getMap());

        gameLogic.setGameObjects(spawner.constructGameObjects(gameLogic.getWorld(), game.getMap()));

    }
    @Override
    public void dispose() {
        game.getMap().dispose();
        renderer.dispose();
        gameLogic.dispose();
    }
}
