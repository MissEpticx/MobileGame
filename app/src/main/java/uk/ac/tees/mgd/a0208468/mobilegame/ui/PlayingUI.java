package uk.ac.tees.mgd.a0208468.mobilegame.ui;

import static uk.ac.tees.mgd.a0208468.mobilegame.main.GameActivity.GAME_HEIGHT;
import static uk.ac.tees.mgd.a0208468.mobilegame.main.GameActivity.GAME_WIDTH;
import static uk.ac.tees.mgd.a0208468.mobilegame.ui.ButtonImages.CARROT_BUTTON;
import static uk.ac.tees.mgd.a0208468.mobilegame.ui.ButtonImages.EGGPLANT_BUTTON;
import static uk.ac.tees.mgd.a0208468.mobilegame.ui.ButtonImages.PLAYING_HOME;
import static uk.ac.tees.mgd.a0208468.mobilegame.ui.ButtonImages.PUMPKIN_BUTTON;
import static uk.ac.tees.mgd.a0208468.mobilegame.ui.ButtonImages.TURNIP_BUTTON;
import static uk.ac.tees.mgd.a0208468.mobilegame.ui.GameImages.FULL_STARS;
import static uk.ac.tees.mgd.a0208468.mobilegame.ui.GameImages.HALF_STAR;
import static uk.ac.tees.mgd.a0208468.mobilegame.ui.GameImages.INVENTORY_BAR;
import static uk.ac.tees.mgd.a0208468.mobilegame.Utils.GameConstants.Sprite.SELECTED_PLANT;
import static uk.ac.tees.mgd.a0208468.mobilegame.ui.GameImages.LEVEL_0;
import static uk.ac.tees.mgd.a0208468.mobilegame.ui.GameImages.LEVEL_1;
import static uk.ac.tees.mgd.a0208468.mobilegame.ui.GameImages.LEVEL_2;
import static uk.ac.tees.mgd.a0208468.mobilegame.ui.GameImages.LEVEL_3;
import static uk.ac.tees.mgd.a0208468.mobilegame.ui.GameImages.NO_STARS;
import static uk.ac.tees.mgd.a0208468.mobilegame.ui.GameImages.ONE_HALF_STAR;
import static uk.ac.tees.mgd.a0208468.mobilegame.ui.GameImages.ONE_STAR;
import static uk.ac.tees.mgd.a0208468.mobilegame.ui.GameImages.TWO_HALF_STAR;
import static uk.ac.tees.mgd.a0208468.mobilegame.ui.GameImages.TWO_STAR;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.view.MotionEvent;

import uk.ac.tees.mgd.a0208468.mobilegame.entities.Player;
import uk.ac.tees.mgd.a0208468.mobilegame.entities.interactables.InteractablesManager;
import uk.ac.tees.mgd.a0208468.mobilegame.gamestates.Playing;

public class PlayingUI {
    private final Playing playing;
    private Paint circlePaint;
    private float xCentre = (GAME_WIDTH / 12) * 10, yCentre = (GAME_HEIGHT / 8) * 6, radius = 100;
    private float xTouch, yTouch;
    private boolean touchDown;
    private CustomButton buttonHome;
    private CustomButton buttonCarrot;
    private CustomButton buttonTurnip;
    private CustomButton buttonEggplant;
    private CustomButton buttonPumpkin;
    private Player player;
    public PlayingUI(Playing playing, Player player){
        this.playing = playing;
        circlePaint = new Paint();
        circlePaint.setStyle(Paint.Style.STROKE);
        circlePaint.setStrokeWidth(6);
        circlePaint.setColor(Color.RED);
        this.player = player;
        buttonHome = new CustomButton(125, 50, PLAYING_HOME.getWidth(), PLAYING_HOME.getHeight());
        buttonHome.setIsLocked(false);
        buttonCarrot = new CustomButton(148, 335, CARROT_BUTTON.getWidth(), CARROT_BUTTON.getHeight());
        buttonCarrot.setIsLocked(false);
        buttonCarrot.setButtonPressed(true);
        buttonTurnip = new CustomButton(148, 485, TURNIP_BUTTON.getWidth(), TURNIP_BUTTON.getHeight());
        buttonEggplant = new CustomButton(148, 625, EGGPLANT_BUTTON.getWidth(), EGGPLANT_BUTTON.getHeight());
        buttonPumpkin = new CustomButton(148, 765, PUMPKIN_BUTTON.getWidth(), PUMPKIN_BUTTON.getHeight());
    }

    public void draw(Canvas canvas){
        canvas.drawCircle(xCentre, yCentre, radius, circlePaint);
        canvas.drawBitmap(PLAYING_HOME.getButtonImg(buttonHome.isButtonPressed(), buttonHome.isLocked()),
                buttonHome.getHitbox().left,
                buttonHome.getHitbox().top,
                null);
        drawInventory(canvas);
        drawXpBar(canvas);
        drawLevel(canvas);
    }

    private void drawInventory(Canvas canvas){
        canvas.drawBitmap(INVENTORY_BAR.getImage(), 125, 300, null);

        canvas.drawBitmap(CARROT_BUTTON.getButtonImg(buttonCarrot.isButtonPressed(),
                        buttonCarrot.isLocked()),
                buttonCarrot.getHitbox().left,
                buttonCarrot.getHitbox().top,
                null);

        canvas.drawBitmap(TURNIP_BUTTON.getButtonImg(buttonTurnip.isButtonPressed(),
                        buttonTurnip.isLocked()),
                buttonTurnip.getHitbox().left,
                buttonTurnip.getHitbox().top,
                null);

        canvas.drawBitmap(EGGPLANT_BUTTON.getButtonImg(buttonEggplant.isButtonPressed(),
                        buttonEggplant.isLocked()),
                buttonEggplant.getHitbox().left,
                buttonEggplant.getHitbox().top,
                null);

        canvas.drawBitmap(PUMPKIN_BUTTON.getButtonImg(buttonPumpkin.isButtonPressed(),
                        buttonPumpkin.isLocked()),
                buttonPumpkin.getHitbox().left,
                buttonPumpkin.getHitbox().top,
                null);
    }

    private void drawXpBar(Canvas canvas){
        int xp = player.getXP();
        int level = player.getLevel();
        int maxXP = player.getMaxXp();
        int thirdMaxXp = maxXP / 3;
        Bitmap draw;
//        if(xp < 50){
//            draw = NO_STARS.getImage();
//        } else
        if (level == 3 && xp == maxXP) {
            draw = FULL_STARS.getImage();
        }else if (level != 3 && xp >= thirdMaxXp / 2 && xp < thirdMaxXp){
            draw = HALF_STAR.getImage();
        } else if (level != 3 && xp >= thirdMaxXp && xp < thirdMaxXp * 1.5f) {
            draw = ONE_STAR.getImage();
        } else if (level != 3 && xp >= thirdMaxXp * 1.5f && xp < thirdMaxXp * 2){
            draw = ONE_HALF_STAR.getImage();
        } else if (level != 3 && xp >= thirdMaxXp * 2 && xp < thirdMaxXp * 2.5f) {
            draw = TWO_STAR.getImage();
        } else if (level != 3 && xp >= thirdMaxXp * 2.5f && xp < maxXP) {
            draw = TWO_HALF_STAR.getImage();
        } else{
            draw = NO_STARS.getImage();
        }

        canvas.drawBitmap(draw, (GAME_WIDTH / 4) * 3, 50, null);
    }

    private void drawLevel(Canvas canvas){
        int level = player.getLevel();
        Bitmap draw;
        if(level == 0){
            draw = LEVEL_0.getImage();
        } else if (level == 1){
            draw = LEVEL_1.getImage();
            player.setMaxXp(270);
            buttonTurnip.setIsLocked(false);
        } else if (level == 2) {
            draw = LEVEL_2.getImage();
            player.setMaxXp(420);
            buttonEggplant.setIsLocked(false);
        } else {
            draw = LEVEL_3.getImage();
            player.setMaxXp(600);
            buttonPumpkin.setIsLocked(false);
        }
        canvas.drawBitmap(draw, (GAME_WIDTH / 4) * 3 + (NO_STARS.getImage().getWidth() + 30), 50, null);
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
                    } else if (isIn(event, buttonCarrot) && !buttonCarrot.isLocked()) {
                        SELECTED_PLANT = 0;
                        buttonCarrot.setButtonPressed(true);
                        buttonTurnip.setButtonPressed(false);
                        buttonEggplant.setButtonPressed(false);
                        buttonPumpkin.setButtonPressed(false);
                    } else if (isIn(event, buttonTurnip) && !buttonTurnip.isLocked()) {
                        SELECTED_PLANT = 1;
                        buttonTurnip.setButtonPressed(true);
                        buttonCarrot.setButtonPressed(false);
                        buttonEggplant.setButtonPressed(false);
                        buttonPumpkin.setButtonPressed(false);
                    } else if (isIn(event, buttonEggplant) && !buttonEggplant.isLocked()) {
                        SELECTED_PLANT = 2;
                        buttonEggplant.setButtonPressed(true);
                        buttonCarrot.setButtonPressed(false);
                        buttonTurnip.setButtonPressed(false);
                        buttonPumpkin.setButtonPressed(false);
                    } else if (isIn(event, buttonPumpkin) && !buttonPumpkin.isLocked()) {
                        SELECTED_PLANT = 3;
                        buttonPumpkin.setButtonPressed(true);
                        buttonCarrot.setButtonPressed(false);
                        buttonTurnip.setButtonPressed(false);
                        buttonEggplant.setButtonPressed(false);
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
