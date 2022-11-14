package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;


public class GameScreen implements Screen {
    public void clearScreen(){
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    public void loseScreen(){
        Gdx.gl.glClearColor(1,0,0,0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    private static final float WORLD_WIDTH = 800;
    private static final float WORLD_HEIGHT = 600;

    private static final int XGUTTER = 10;

    private static final int YGUTTER = 35;

    //Object that allows us to draw all our graphics
    private SpriteBatch spriteBatch;

    //Object that allwos us to draw shapes
    private ShapeRenderer shapeRenderer;

    //Camera to view our virtual world
    private Camera camera;

    //control how the camera views the world
    //zoom in/out? Keep everything scaled?
    private Viewport viewport;

    GameBoard board = new GameBoard();

    //x and y of last mouse click
    private int mouseX =-10;
    private int mouseY = -10;



    BitmapFont tempFont = new BitmapFont();

    @Override
    public void show() {
        camera = new OrthographicCamera(); //2D camera
        camera.position.set(WORLD_WIDTH/2, WORLD_HEIGHT/2,0);//move the camera
        camera.update();

        //freeze my view to 800x600, no matter the window size
        viewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);

        spriteBatch = new SpriteBatch();

        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setAutoShapeType(true); //???, I just know that this was the solution to an annoying problem


    }

    public void handleClick() {

        if(Gdx.input.isButtonJustPressed(((Input.Buttons.LEFT)))){
            mouseX = Gdx.input.getX();
            mouseY = Gdx.input.getY();
            board.handleClick(mouseX, mouseY);
        }
    }

    public void handleRightClick() {
        if (Gdx.input.isButtonJustPressed(((Input.Buttons.RIGHT)))){
            mouseX = Gdx.input.getX();
            mouseY = Gdx.input.getY();
            board.handleRightClick(mouseX, mouseY);
        }
    }

    @Override
    public void render(float delta) {
        //clears the screen
        clearScreen();

        //handle player input
        handleClick();
        handleRightClick();

        //A.I. updates

        //draw textures
        spriteBatch.begin();
        board.draw(spriteBatch);
        tempFont.draw(spriteBatch, "Clicked at (" + mouseX + " , " + mouseY + " )", 400, 100);
        tempFont.draw(spriteBatch, "row: " + (mouseY-10)/25, 400, 50);
        tempFont.draw(spriteBatch, "col: " + (mouseX-10)/25, 500, 50);
        tempFont.draw(spriteBatch, "MINESWEEPER", 100, 95);
        if (GameBoard.isGameLost) {
            tempFont.draw(spriteBatch, "You Lost!", 350, 500);
            this.loseScreen();
        }
        spriteBatch.end();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
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
        spriteBatch.dispose();
        shapeRenderer.dispose();
    }
}