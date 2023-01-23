package com.kaffekopp.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.kaffekopp.game.constants.MapConstants;
import com.kaffekopp.game.screens.MenuScreen;
import com.kaffekopp.game.screens.hud.Hud;

public class KurtMarioGame extends Game {
    private SpriteBatch batch;
    private boolean isMultiPlayer;
    private TiledMap map;
    private int level = 0;
    private Hud hud;
    private boolean restart = false;
    private boolean gameWon = false;

    private boolean muted;

    //Storing last map's score and time to carry over into next
    private Integer time = 0;
    private Integer score = 0;


    public boolean isGameWon() {
        return (level + 1) == (isMultiPlayer ? MapConstants.multiPlayerMaps.size() : MapConstants.singlePlayerMaps.size());
    }

    /**
     * Gets the Game ready for loading a map
     * Checks if the game is won
     */
    public void initialize() {
            initializeMap();
            initializeHud();
    }

    /**
     * Gets map ready for loading
     */
    private void initializeMap() {
        String mapPath = isMultiPlayer ? MapConstants.multiPlayerMaps.get(level) : MapConstants.singlePlayerMaps.get(level);
        this.map = new TmxMapLoader().load(mapPath);
    }

    /**
     * Gets the hud ready
     * Checks if it should be multiplayer or single player
     * Keeps the time from last map going into next map
     */
    private void initializeHud() {
        if(hud != null){ // null before first map
            try {
                if(restart) { // is true if the user has gone back to main menu after playing
                    time = 0;
                    score = 0;
                    restart = false;
                }
                else  {
                    setTime(hud.getTime());
                    setScore(hud.getScore());
                }
            }
            catch (NullPointerException e) {
                setTime(0);
                setScore(0);
            }
        } else restart = false;
        this.hud = new Hud(getBatch());
        this.hud.initialiseHud(this);
        this.hud.createStage();
    }

    /**
     * Updates the hud
     * @param gameLogic used to update player stats, such as score and hp
     * @param dt used for time keeping
     */
    public void updateHud(GameLogic gameLogic, float dt) {
        if (isMultiPlayer) {
            hud.playerTwoHp = gameLogic.getPlayers().get(1).getCurrentHp();
            hud.playerTwoMaxHp = gameLogic.getPlayers().get(1).getMaxHp();
            hud.playerScore = gameLogic.getPlayers().get(1).getScore();
        } else {
            hud.playerScore = 0;
        }
        hud.playerScore += gameLogic.getPlayers().get(0).getScore() + score;
        hud.playerOneHp = gameLogic.getPlayers().get(0).getCurrentHp();
        hud.playerOneMaxHp = gameLogic.getPlayers().get(0).getMaxHp();
        hud.currentCount = gameLogic.getCurrentItemCount();
        hud.itemCount = gameLogic.getItemCount();
        hud.update(dt);
    }

    @Override
    public void create() {
        setBatch(new SpriteBatch());
        setScreen(new MenuScreen(this));
    }

    public SpriteBatch getBatch() {
        return batch;
    }

    public void setBatch(SpriteBatch batch) {
        this.batch = batch;
    }

    public boolean isMultiPlayer() {
        return isMultiPlayer;
    }

    public void setMultiPlayer(boolean multiPlayer) {
        isMultiPlayer = multiPlayer;
    }

    public TiledMap getMap() {
        return map;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setTime(int time)  { this.time = time;}

    public void setScore(int score) {this.score = score;}

    public int getTime() { return time;}

    /**
     * Increases the level by one
     */
    public void increaseLevel() {
        level++;
    }

    public Hud getHud() {
        return hud;
    }

    public void doRestart() { restart = true;}

    /**
     * Method used to give a scalable size for buttons etc on screens. Scale 0.4 = 40% of the screen's height
     * @param scale, a double that is multiplied by the screen's height
     * @return returns the pixels that is scale multiplied by the total screen height
     */
    public float yScaler(double scale) {
        return (float) (Gdx.graphics.getHeight() * scale);
    }

    /**
     * Method used to give a scalable size for buttons etc on screens. Scale 0.4 = 40% of the screen's width
     * @param scale, a double that is multiplied by the screen's width
     * @return returns the pixels that is scale multiplied by the total screen width
     */
    public float xScaler(double scale) {
        return (float) (Gdx.graphics.getWidth() * scale);
    }

    public void setGameWon(boolean b) { gameWon = b; }

    public boolean getGameWon() { return gameWon; }

    public boolean isMuted() {
        return muted;
    }

    public void setMuted(boolean muted) {
        this.muted = muted;
    }


}
