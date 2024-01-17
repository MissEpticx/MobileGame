package uk.ac.tees.mgd.a0208468.mobilegame.ui;

import static uk.ac.tees.mgd.a0208468.mobilegame.ui.ButtonImages.PLAYING_HOME;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.view.MotionEvent;

import uk.ac.tees.mgd.a0208468.mobilegame.entities.interactables.InteractablesManager;
import uk.ac.tees.mgd.a0208468.mobilegame.gamestates.Playing;
import uk.ac.tees.mgd.a0208468.mobilegame.main.Game;
import uk.ac.tees.mgd.a0208468.mobilegame.main.MainActivity;

public class PlayingUI {
    private final Playing playing;
    private Paint circlePaint;
    private float xCentre = (MainActivity.GAME_WIDTH / 12) * 10, yCentre = (MainActivity.GAME_HEIGHT / 8) * 6, radius = 100;
    private float xTouch, yTouch;
    private boolean touchDown;
    private CustomButton buttonHome;
    public PlayingUI(Playing playing){
        this.playing = playing;
        circlePaint = new Paint();
        circlePaint.setStyle(Paint.Style.STROKE);
        circlePaint.setStrokeWidth(6);
        circlePaint.setColor(Color.RED);

        buttonHome = new CustomButton(50, 50, PLAYING_HOME.getWidth(), PLAYING_HOME.getHeight());
    }

    public void draw(Canvas canvas){
        canvas.drawCircle(xCentre, yCentre, radius, circlePaint);
        canvas.drawBitmap(PLAYING_HOME.getButtonImg(buttonHome.isButtonPressed()),
                buttonHome.getHitbox().left,
                buttonHome.getHitbox().top,
                null);
    }

    public void touchEvents(MotionEvent event, InteractablesManager interactManager){
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN :
                //  Using Trigonometry to determine if touch down is within the joystick circle or not.
                float x = event.getX();
                float y = event.getY();

                float a = Math.abs(x - xCentre);
                float b = Math.abs(y - yCentre);
                float c = (float) Math.hypot(a, b);

                if(c <= radius){
                    touchDown = true;
                    xTouch = x;
                    yTouch = y;
                } else{
                    if(isIn(event, buttonHome)){
                        buttonHome.setButtonPressed(true);
                    } else{
                        interactManager.touchEvents(event);
                    }
                }
                break;
            case MotionEvent.ACTION_MOVE :
                if(touchDown){
                    xTouch = event.getX();
                    yTouch = event.getY();
                    float xDiff = xTouch - xCentre;
                    float yDiff = yTouch - yCentre;
                    playing.setPlayerMoveTrue(new PointF(xDiff, yDiff));
                }
                break;
            case MotionEvent.ACTION_UP :
                if(isIn(event, buttonHome)){
                    if(buttonHome.isButtonPressed()){
                        playing.setGameStateToMenu();
                    }
                }
                buttonHome.setButtonPressed(false);
                touchDown = false;
                playing.setPlayerMoveFalse();
                break;
        }
    }

    private boolean isIn(MotionEvent event, CustomButton button){
        return button.getHitbox().contains(event.getX(), event.getY());
    }
}
