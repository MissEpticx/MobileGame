package uk.ac.tees.mgd.a0208468.mobilegame.main;

import static uk.ac.tees.mgd.a0208468.mobilegame.Utils.GameConstants.Weather.IS_RAINING;
import static uk.ac.tees.mgd.a0208468.mobilegame.Utils.GameConstants.Weather.WEATHER_SET;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

import uk.ac.tees.mgd.a0208468.mobilegame.R;
import uk.ac.tees.mgd.a0208468.mobilegame.gamestates.Menu;
import uk.ac.tees.mgd.a0208468.mobilegame.gamestates.Playing;

public class Game {
    private GameLoop gameLoop;
    private SurfaceHolder holder;
    private Menu menu;
    private Playing playing;
    private GameState currentGameState = GameState.MENU;
    private MediaPlayer mediaPlayer;
    private boolean musicPlaying;

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

        if(WEATHER_SET && !musicPlaying){
            startMusic();
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

    public void startGameLoop(Context context) {
        gameLoop.startGameLoop();
    }
    private void startMusic(){
        musicPlaying = true;
        int resId;
        if(IS_RAINING){
            System.out.println("raining music");
            resId = R.raw.raining_music;

        } else {
            resId = R.raw.standard_music;
            System.out.println("standard music");
        }

        mediaPlayer = MediaPlayer.create(GameActivity.getGameContext(), resId);
        System.out.println("play music");
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
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
