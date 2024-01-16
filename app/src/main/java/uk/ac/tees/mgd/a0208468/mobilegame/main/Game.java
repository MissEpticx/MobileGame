package uk.ac.tees.mgd.a0208468.mobilegame.main;

import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

import uk.ac.tees.mgd.a0208468.mobilegame.gamestates.Menu;
import uk.ac.tees.mgd.a0208468.mobilegame.gamestates.Playing;

public class Game {
    private GameLoop gameLoop;
    private SurfaceHolder holder;
    private Menu menu;
    private Playing playing;
    private GameState currentGameState = GameState.MENU;

    public Game(SurfaceHolder holder){
        this.holder = holder;
        gameLoop = new GameLoop(this);
        initGameStates();

    }

    public boolean touchEvent(MotionEvent event){
        switch (currentGameState){
            case MENU:
                menu.touchEvents(event);
                break;
            case PLAYING:
                playing.touchEvents(event);
                break;
        }
        return true;
    }

    public void update(double delta){
        switch (currentGameState){
            case MENU:
                menu.update(delta);
                break;
            case PLAYING:
                playing.update(delta);
                break;
            case CLOSE:
                System.exit(0);
        }
    }

    public void render(){
        Canvas canvas = holder.lockCanvas();
        canvas.drawColor(Color.BLACK);

        switch (currentGameState){
            case MENU:
                menu.render(canvas);
                break;
            case PLAYING:
                playing.render(canvas);
                break;
        }

        holder.unlockCanvasAndPost(canvas);
    }

    private void initGameStates(){
        menu = new Menu(this);
        playing = new Playing(this);
    }

    public void startGameLoop() {
        gameLoop.startGameLoop();
    }

    public enum GameState{
        MENU,
        PLAYING,
        CLOSE;
    }

    public GameState getCurrentGameState(){
        return currentGameState;
    }

    public void setCurrentGameState(GameState currentGameState){
        this.currentGameState = currentGameState;
    }
}
