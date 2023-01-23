package com.kaffekopp.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
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
import com.kaffekopp.game.scoreboard.MySQLCon;

import java.util.ArrayList;

public class HighScoreScreen implements Screen {

    private Stage stage;
    private final KurtMarioGame game;
    private final Texture menuButtonActive, menuButtonInactive, quitButtonActive, quitButtonInactive, background, highScoreBackground, highScoreTitle;
    private final String highScoreList;

    public HighScoreScreen(KurtMarioGame game) {
        MySQLCon con = new MySQLCon();
        ArrayList<ArrayList<Object>> dbInput = con.getScoreBoard();

        // Formatting the high score string for output
        StringBuilder result = new StringBuilder("    #  Name" + "        " + "Time" + "  " + "Score" + "\n    ");
        int limit = Math.min(dbInput.size(), 10);
        for (int i = 0; i < limit; i++) { // looping through top 10 entries in the database
            ArrayList<Object> list = dbInput.get(i);
            for(int j = 0; j < 3; j++) { // looping through name, time and score for each entry
                if (j == 0) {
                    if (i == 9) // need 1 less empty space when the # number is 2 digits(only occurs for 10 as its top 10)
                        result.insert(result.length() - 1, i + 1).append(" ");
                    else
                        result.append(i + 1).append("  ");
                    result.append(list.get(j));
                    result.append(" ".repeat(Math.max(0, 10 - ((String) list.get(j)).length())));
                } else {
                    result.append(" ".repeat(Math.max(0, 6 - String.valueOf(list.get(j)).length())));
                    result.append(list.get(j));
                }
            }
            result.append("\n    ");
        }
        highScoreList = result.toString();
        this.game = game;
        Viewport viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), new OrthographicCamera());
        stage = new Stage(viewport, game.getBatch());

        background = new Texture(FilePaths.SCREEN_BACKGROUND);
        menuButtonActive = new Texture(FilePaths.SCREEN_BUTTON_ACTIVE_MENU);
        menuButtonInactive = new Texture(FilePaths.SCREEN_BUTTON_INACTIVE_MENU);
        quitButtonActive = new Texture(FilePaths.SCREEN_BUTTON_ACTIVE_EXIT);
        quitButtonInactive = new Texture(FilePaths.SCREEN_BUTTON_INACTIVE_EXIT);
        highScoreBackground = new Texture(FilePaths.PARCHMENT);
        highScoreTitle = new Texture(FilePaths.HIGHSCORE_TITLE);
    }

    @Override
    public void show() {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        float BUTTON_WIDTH =  game.xScaler(0.3);
        float BUTTON_HEIGHT = game.yScaler(0.15);
        float HS_WIDTH = game.xScaler(1.3);
        float HS_HEIGHT = game.yScaler(0.9);

        TextButton menuButton, quitButton;
        Image backgroundImage, highScoreImage, hsTitle;
        Table table, table2, table3;
        BitmapFont font = new BitmapFont(Gdx.files.internal(FilePaths.INTELLIJ_FONT));
        font.getData().setScale((float)0.3); // set scale of text to 0.3 to fit with the default screen size(though hard to read)
        float defaultX = 480;
        float defaultY = 320;
        float newX = Gdx.graphics.getWidth();
        float newY = Gdx.graphics.getHeight();
        if (newX != defaultX || newY != defaultY) // sets scale of text to 1 if the screen size is changed(meant for fullscreen)
            font.getData().setScale(1);


        Label highScores;
        TextButton.TextButtonStyle quitStyle = new TextButton.TextButtonStyle();
        TextButton.TextButtonStyle menuStyle = new TextButton.TextButtonStyle();

        menuStyle.up = new TextureRegionDrawable(new TextureRegion(menuButtonInactive));
        menuStyle.over = new TextureRegionDrawable(new TextureRegion(menuButtonActive));
        quitStyle.up = new TextureRegionDrawable(new TextureRegion(quitButtonInactive));
        quitStyle.over = new TextureRegionDrawable(new TextureRegion(quitButtonActive));
        menuStyle.font = font;
        quitStyle.font = font;

        quitButton = new TextButton("", quitStyle);
        menuButton = new TextButton("", menuStyle);
        backgroundImage = new Image(background);
        highScoreImage = new Image(highScoreBackground);
        hsTitle = new Image(highScoreTitle);
        highScores = new Label(highScoreList, new Label.LabelStyle(font, Color.BLACK));

        table3 = new Table(); // Table used to show the background pixel art
        table2 = new Table(); // Table used to show the High Score background("parchment")
        table = new Table(); // Main table used for all the buttons
        table2.add(backgroundImage).fill();
        table2.setFillParent(true);
        table3.padBottom(BUTTON_HEIGHT).setFillParent(true);
        table3.add(highScoreImage).width(HS_WIDTH).height(HS_HEIGHT);
        table.setFillParent(true);
        // lot of numbers here, x/yScaler from game just gives a percentage of the screens width/height
        // also readjusting the sizes of the buttons by multiplying the preset BUTTON_HEIGHT/WIDTH by a small number
        table.add(hsTitle).height((float) (BUTTON_HEIGHT * 0.5)).width((float) (BUTTON_WIDTH * 0.5)).colspan(2).padTop(game.yScaler(0.08)).padBottom(game.yScaler(0.02)).row();
        table.add(highScores).colspan(2).expandY().fillX().padBottom(game.yScaler(0.1)).height(game.yScaler(0.6)).center().row();
        table.add(menuButton).height((float) (BUTTON_HEIGHT * 0.7)).width((float) (BUTTON_WIDTH * 0.7)).padBottom(game.yScaler(0.05));
        table.add(quitButton).height((float) (BUTTON_HEIGHT * 0.7)).width((float) (BUTTON_WIDTH * 0.7)).padBottom(game.yScaler(0.05));
        stage.addActor(table2);
        stage.addActor(table3);
        stage.addActor(table);

        // Quit button's functionality; exits the app
        quitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });

        // Menu button's functionality; disposes of this screen and changes screen to MenuScreen
        menuButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                dispose();
                game.setScreen(new MenuScreen(game));
            }
        });
    }

    @Override
    public void render(float v) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(v);
        stage.draw();
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
    public void hide() {}

    @Override
    public void dispose() {
        stage.dispose();
    }
}
