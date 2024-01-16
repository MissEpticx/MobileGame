package uk.ac.tees.mgd.a0208468.mobilegame.gamestates;

import static uk.ac.tees.mgd.a0208468.mobilegame.Utils.GameConstants.Sprite.UI_SCALE_MULTIPLIER;
import static uk.ac.tees.mgd.a0208468.mobilegame.ui.ButtonImages.MENU_CLOSE;
import static uk.ac.tees.mgd.a0208468.mobilegame.ui.ButtonImages.MENU_PLAY;
import static uk.ac.tees.mgd.a0208468.mobilegame.ui.GameImages.MAIN_MENU_BOARD;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;

import uk.ac.tees.mgd.a0208468.mobilegame.Utils.interfaces.GameStateInterface;
import uk.ac.tees.mgd.a0208468.mobilegame.main.Game;
import uk.ac.tees.mgd.a0208468.mobilegame.ui.CustomButton;

public class Menu extends BaseState implements GameStateInterface {
    private CustomButton buttonPlay;
    private CustomButton buttonClose;
    private int menuX = 200;
    private int menuY = 50;
    private int buttonPlayX = menuX + ((MAIN_MENU_BOARD.getImage().getWidth() / 2) - (MENU_PLAY.getWidth() * (UI_SCALE_MULTIPLIER / 2)));
    private int buttonPlayY = menuY + 350;
    private int buttonCloseX = menuX + ((MAIN_MENU_BOARD.getImage().getWidth() / 2) - (MENU_PLAY.getWidth() * (UI_SCALE_MULTIPLIER / 2)));
    private int buttonCloseY = buttonPlayY + MENU_PLAY.getHeight() + 250;
    public Menu(Game game){
        super(game);
        buttonPlay = new CustomButton(buttonPlayX, buttonPlayY, MENU_PLAY.getWidth(), MENU_PLAY.getHeight());
        buttonClose = new CustomButton(buttonCloseX, buttonCloseY, MENU_CLOSE.getWidth(), MENU_CLOSE.getHeight());
    }
    @Override
    public void update(double delta) {

    }

    @Override
    public void render(Canvas canvas) {
        canvas.drawBitmap(MAIN_MENU_BOARD.getImage(),
                menuX,
                menuY,
                null);

        canvas.drawBitmap(MENU_PLAY.getButtonImg(buttonPlay.isButtonPressed()),
                buttonPlay.getHitbox().left,
                buttonPlay.getHitbox().top,
                null);
        canvas.drawBitmap(MENU_CLOSE.getButtonImg(buttonClose.isButtonPressed()),
                buttonClose.getHitbox().left,
                buttonClose.getHitbox().top,
                null);
    }

    @Override
    public void touchEvents(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            if(isIn(event, buttonPlay)){
                buttonPlay.setButtonPressed(true);
            } else if (isIn(event, buttonClose)) {
                buttonClose.setButtonPressed(true);
            }
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            if(isIn(event, buttonPlay)) {
                if(buttonPlay.isButtonPressed()) {
                    game.setCurrentGameState(Game.GameState.PLAYING);
                }
            } else if (isIn(event, buttonClose)) {
                if (buttonClose.isButtonPressed()) {
                    game.setCurrentGameState(Game.GameState.CLOSE);
                }
            }
            buttonPlay.setButtonPressed(false);
            buttonClose.setButtonPressed(false);
        }
    }

    private boolean isIn(MotionEvent event, CustomButton button){
        return button.getHitbox().contains(event.getX(), event.getY());
    }
}
