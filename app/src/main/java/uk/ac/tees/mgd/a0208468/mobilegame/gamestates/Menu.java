package uk.ac.tees.mgd.a0208468.mobilegame.gamestates;

import static uk.ac.tees.mgd.a0208468.mobilegame.main.MainActivity.GAME_HEIGHT;
import static uk.ac.tees.mgd.a0208468.mobilegame.main.MainActivity.GAME_WIDTH;
import static uk.ac.tees.mgd.a0208468.mobilegame.ui.ButtonImages.MENU_PLAY;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;

import uk.ac.tees.mgd.a0208468.mobilegame.Utils.interfaces.GameStateInterface;
import uk.ac.tees.mgd.a0208468.mobilegame.main.Game;
import uk.ac.tees.mgd.a0208468.mobilegame.ui.ButtonImages;
import uk.ac.tees.mgd.a0208468.mobilegame.ui.CustomButton;

public class Menu extends BaseState implements GameStateInterface {
    private Paint paint;
    private CustomButton buttonPlay;
    public Menu(Game game){
        super(game);
        paint = new Paint();
        paint.setTextSize(60);
        paint.setColor(Color.WHITE);
        buttonPlay = new CustomButton(300,200, MENU_PLAY.getWidth() * 3, MENU_PLAY.getHeight() * 3);
    }
    @Override
    public void update(double delta) {

    }

    @Override
    public void render(Canvas canvas) {
        canvas.drawText("Menu", 1110, 540, paint);
        canvas.drawBitmap(MENU_PLAY.getButtonImg(buttonPlay.isButtonPressed()),
                buttonPlay.getHitbox().left,
                buttonPlay.getHitbox().top,
                null);
    }

    @Override
    public void touchEvents(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            game.setCurrentGameState(Game.GameState.PLAYING);
        }
    }
}
