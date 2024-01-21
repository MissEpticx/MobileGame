package uk.ac.tees.mgd.a0208468.mobilegame.ui;

import static uk.ac.tees.mgd.a0208468.mobilegame.Utils.GameConstants.Sprite.SCALE_MULTIPLIER;
import static uk.ac.tees.mgd.a0208468.mobilegame.main.MainActivity.GAME_HEIGHT;
import static uk.ac.tees.mgd.a0208468.mobilegame.main.MainActivity.GAME_WIDTH;
import static uk.ac.tees.mgd.a0208468.mobilegame.ui.ButtonImages.CARROT_BUTTON;
import static uk.ac.tees.mgd.a0208468.mobilegame.ui.ButtonImages.EGGPLANT_BUTTON;
import static uk.ac.tees.mgd.a0208468.mobilegame.ui.ButtonImages.PLAYING_HOME;
import static uk.ac.tees.mgd.a0208468.mobilegame.ui.ButtonImages.PUMPKIN_BUTTON;
import static uk.ac.tees.mgd.a0208468.mobilegame.ui.ButtonImages.TURNIP_BUTTON;
import static uk.ac.tees.mgd.a0208468.mobilegame.ui.GameImages.INVENTORY_BAR;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.view.MotionEvent;

import uk.ac.tees.mgd.a0208468.mobilegame.entities.interactables.InteractablesManager;
import uk.ac.tees.mgd.a0208468.mobilegame.gamestates.Playing;
import uk.ac.tees.mgd.a0208468.mobilegame.main.MainActivity;

public class PlayingUI {
    private final Playing playing;
    private Paint circlePaint;
    private float xCentre = (GAME_WIDTH / 12) * 10, yCentre = (MainActivity.GAME_HEIGHT / 8) * 6, radius = 100;
    private float xTouch, yTouch;
    private boolean touchDown;
    private CustomButton buttonHome;
    private CustomButton buttonCarrot;
    private CustomButton buttonTurnip;
    private CustomButton buttonEggplant;
    private CustomButton buttonPumpkin;
    public PlayingUI(Playing playing){
        this.playing = playing;
        circlePaint = new Paint();
        circlePaint.setStyle(Paint.Style.STROKE);
        circlePaint.setStrokeWidth(6);
        circlePaint.setColor(Color.RED);

        buttonHome = new CustomButton(125, 50, PLAYING_HOME.getWidth(), PLAYING_HOME.getHeight());
        buttonCarrot = new CustomButton(150, 320, CARROT_BUTTON.getWidth(), CARROT_BUTTON.getHeight());
        buttonTurnip = new CustomButton(150, 470, TURNIP_BUTTON.getWidth(), TURNIP_BUTTON.getHeight());
        buttonEggplant = new CustomButton(150, 610, EGGPLANT_BUTTON.getWidth(), EGGPLANT_BUTTON.getHeight());
        buttonPumpkin = new CustomButton(150, 750, PUMPKIN_BUTTON.getWidth(), PUMPKIN_BUTTON.getHeight());
    }

    public void draw(Canvas canvas){
        canvas.drawCircle(xCentre, yCentre, radius, circlePaint);
        canvas.drawBitmap(PLAYING_HOME.getButtonImg(buttonHome.isButtonPressed(), false),
                buttonHome.getHitbox().left,
                buttonHome.getHitbox().top,
                null);

        int barX = (GAME_WIDTH / 2) - (INVENTORY_BAR.getImage().getWidth() / 2);
        int barY = GAME_HEIGHT - (50 + INVENTORY_BAR.getImage().getHeight());
        canvas.drawBitmap(INVENTORY_BAR.getImage(), 125, 300, null);

        canvas.drawBitmap(CARROT_BUTTON.getButtonImg(false,
                buttonCarrot.isLocked()),
                buttonCarrot.getHitbox().left,
                buttonCarrot.getHitbox().top,
                null);

        canvas.drawBitmap(TURNIP_BUTTON.getButtonImg(false,
                        buttonTurnip.isLocked()),
                buttonTurnip.getHitbox().left,
                buttonTurnip.getHitbox().top,
                null);

        canvas.drawBitmap(EGGPLANT_BUTTON.getButtonImg(false,
                        buttonEggplant.isLocked()),
                buttonEggplant.getHitbox().left,
                buttonEggplant.getHitbox().top,
                null);

        canvas.drawBitmap(PUMPKIN_BUTTON.getButtonImg(false,
                        buttonPumpkin.isLocked()),
                buttonPumpkin.getHitbox().left,
                buttonPumpkin.getHitbox().top,
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
