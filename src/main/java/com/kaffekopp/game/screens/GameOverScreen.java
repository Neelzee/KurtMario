package com.kaffekopp.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.kaffekopp.game.KurtMarioGame;
import com.kaffekopp.game.constants.FilePaths;
import com.kaffekopp.game.constants.MapConstants;
import com.kaffekopp.game.scoreboard.MySQLCon;
import com.kaffekopp.game.scoreboard.UserSRInfo;
import com.kaffekopp.game.screens.hud.Hud;

import java.util.ArrayList;

public class GameOverScreen implements Screen {

    private Stage stage;
    private final KurtMarioGame game;
    private final Texture menuButtonActive;
    private final Texture menuButtonInactive;
    private final Texture quitButtonActive;
    private final Texture quitButtonInactive;
    private final Texture background;
    private final Texture retryButtonActive;
    private final Texture retryButtonInactive;
    private final Texture inactiveSubmitButton;
    private final Texture activeSubmitButton;
    private final Hud hud;
    private UserSRInfo user;
    private final MySQLCon mySQL;


    public GameOverScreen (KurtMarioGame game, boolean win, Hud hud) {
        mySQL = new MySQLCon();
        this.game = game;
        this.hud = hud;
        Viewport viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), new OrthographicCamera());
        stage = new Stage(viewport, game.getBatch());

        if(win) {
            background = new Texture(FilePaths.SCREEN_BACKGROUND_GAME_OVER_WIN);
        }
        else {
            background = new Texture(FilePaths.SCREEN_BACKGROUND_GAME_OVER_LOSE);
        }
        menuButtonActive = new Texture(FilePaths.SCREEN_BUTTON_ACTIVE_MENU);
        menuButtonInactive = new Texture(FilePaths.SCREEN_BUTTON_INACTIVE_MENU);
        quitButtonActive = new Texture(FilePaths.SCREEN_BUTTON_ACTIVE_EXIT);
        quitButtonInactive = new Texture(FilePaths.SCREEN_BUTTON_INACTIVE_EXIT);
        retryButtonInactive = new Texture(FilePaths.SCREEN_BUTTON_INACTIVE_RETRY);
        retryButtonActive = new Texture(FilePaths.SCREEN_BUTTON_ACTIVE_RETRY);
        inactiveSubmitButton = new Texture(FilePaths.INACTIVE_SUBMIT);
        activeSubmitButton = new Texture(FilePaths.ACTIVE_SUBMIT);
    }

    @Override
    public void show() {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        float BUTTON_WIDTH =  (float) (Gdx.graphics.getWidth() * 0.3);
        float BUTTON_HEIGHT = (float) (Gdx.graphics.getHeight() * 0.15);

        TextButton menuButton, quitButton, retryButton, submitButton;
        Image backgroundImage;
        Table table;
        Table table2;
        BitmapFont white = new BitmapFont();

        table2 = new Table();
        table = new Table();
        table.center().setFillParent(true);

        TextButton.TextButtonStyle tbsQuit = new TextButton.TextButtonStyle();
        TextButton.TextButtonStyle tbsMenu = new TextButton.TextButtonStyle();
        TextButton.TextButtonStyle retry = new TextButton.TextButtonStyle();
        TextButton.TextButtonStyle submit = new TextButton.TextButtonStyle();

        tbsMenu.up = new TextureRegionDrawable(new TextureRegion(menuButtonInactive));
        tbsMenu.over = new TextureRegionDrawable(new TextureRegion(menuButtonActive));
        tbsQuit.up = new TextureRegionDrawable(new TextureRegion(quitButtonInactive));
        tbsQuit.over = new TextureRegionDrawable(new TextureRegion(quitButtonActive));
        retry.up = new TextureRegionDrawable(new TextureRegion(retryButtonInactive));
        retry.over = new TextureRegionDrawable(new TextureRegion(retryButtonActive));
        submit.up = new TextureRegionDrawable(new TextureRegion(inactiveSubmitButton));
        submit.over = new TextureRegionDrawable(new TextureRegion(activeSubmitButton));

        tbsMenu.font = white;
        tbsQuit.font = white;
        retry.font = white;
        submit.font = white;

        Skin skin = new Skin(Gdx.files.internal(FilePaths.SKIN));
        white.getData().setScale(5);
        TextField txfUsername = new TextField("", skin);
        txfUsername.setSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        txfUsername.setMaxLength(8);

        quitButton = new TextButton("", tbsQuit);
        menuButton = new TextButton("", tbsMenu);
        retryButton = new TextButton("", retry);
        submitButton = new TextButton("", submit);

        backgroundImage = new Image(background);

        table2.add(backgroundImage).fillX().fillY();
        table2.setFillParent(true);
        table.padTop(game.yScaler(0.4));
        table.add(menuButton).height((float) (BUTTON_HEIGHT * 0.7)).width((float) (BUTTON_WIDTH * 0.7)).padBottom(game.yScaler(0.05)).padRight(game.yScaler(0.05));
        table.add(retryButton).height((float) (BUTTON_HEIGHT * 0.7)).width((float) (BUTTON_WIDTH * 0.7)).padBottom(game.yScaler(0.05)).row();
        table.add(quitButton).height((float) (BUTTON_HEIGHT * 0.7)).width((float) (BUTTON_WIDTH * 0.7)).colspan(2).padBottom(game.yScaler(0.05)).padRight(game.yScaler(0.05)).row();
        if((game.getLevel() + 1) == (game.isMultiPlayer() ? MapConstants.multiPlayerMaps.size() : MapConstants.singlePlayerMaps.size()) && game.getGameWon()){
            table.add(txfUsername).height((float) (BUTTON_HEIGHT * 0.7)).width((float) (BUTTON_WIDTH * 0.4)).center();
            table.add(submitButton).height((float) (BUTTON_HEIGHT * 0.7)).width((float) (BUTTON_WIDTH * 0.7));
        }
        stage.addActor(table2);
        stage.addActor(table);

        quitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });

        menuButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                dispose();
                game.setScreen(new MenuScreen(game));
            }
        });
        retryButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setGameWon(false);
                game.setScreen(new PlayScreen(game));
                game.getHud().setTime(game.getTime());
                dispose();
            }
        });
        submitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) { // Sending User info to database
                if(txfUsername.getText().length() >= 2) {
                    user = new UserSRInfo();
                    user.username = txfUsername.getText();
                    user.time = hud.worldTimer;
                    user.score = hud.playerScore;
                    ArrayList<ArrayList<Object>> list = mySQL.getScoreBoard(); // get current list of entries from database
                    // Checking if the username has already been used, if so, updates the entry with the same name
                    boolean updated = false;
                    for(ArrayList<Object> innerList : list) {
                        if(user.username.equals(innerList.get(0))) {
                            if((user.time < (float)innerList.get(1)) || (user.time == (float)innerList.get(1) && user.score > (int)innerList.get(2))) {
                                System.out.println("Updated the time for user " + user.username + "triggers: " + (user.time < (float)innerList.get(1)) + " | " + (user.time == (float)innerList.get(1) && user.score > (int)innerList.get(2)) +
                                        "\nOld: " + innerList.get(0) + " " + innerList.get(1) + " " + innerList.get(2)
                                        + "\nNew: " + user.username + " " + user.time + " " + user.score); //TODO remove this
                                mySQL.updateScore(user);
                            }
                            else
                                System.out.println("Failed to update the user " + user.username + "| triggers: " + (user.time < (float)innerList.get(1)) + " | " + (user.time == (float)innerList.get(1) && user.score > (int)innerList.get(2)) +
                                        "\nOld: " + innerList.get(0) + " " + innerList.get(1) + " " + innerList.get(2)
                                        + "\nNew: " + user.username + " " + user.time + " " + user.score);
                            updated = true;
                        }
                    }

                    //Check if the new run is a new top 10 or if total entries is below 10, if so, submits the new entry
                    int limit;
                    boolean newTopTen = false;
                    if(!updated) {
                        ArrayList<Object> subList;
                        if (list.size() > 10) { // loop through current top 10
                            limit = list.size() - 1;
                            for (int i = limit; i >= 0; i--) {
                                subList = list.get(i);
                                float oldTime = (float) subList.get(1);
                                if (user.time < oldTime) {
                                    newTopTen = true;
                                    break;
                                }
                            }
                        } else newTopTen = true;
                    }

                    if(newTopTen)
                        mySQL.submitScore(user);
                    dispose();
                    game.setScreen(new MenuScreen(game));
                }
                else {
                    txfUsername.setText("2+ chars");
                }

            }
        });
    }

    @Override
    public void render(float v) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(v);
        stage.draw();
        hud.stage.draw();

    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
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
        stage.dispose();
    }
}

