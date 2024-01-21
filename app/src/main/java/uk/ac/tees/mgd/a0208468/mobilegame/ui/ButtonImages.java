package uk.ac.tees.mgd.a0208468.mobilegame.ui;

import static uk.ac.tees.mgd.a0208468.mobilegame.Utils.GameConstants.Sprite.UI_SCALE_MULTIPLIER;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import uk.ac.tees.mgd.a0208468.mobilegame.R;
import uk.ac.tees.mgd.a0208468.mobilegame.Utils.interfaces.BitmapMethods;
import uk.ac.tees.mgd.a0208468.mobilegame.main.GameActivity;
import uk.ac.tees.mgd.a0208468.mobilegame.main.MainActivity;

public enum ButtonImages implements BitmapMethods {
    MENU_PLAY(R.drawable.mainmenu_button_play, 90, 27, UI_SCALE_MULTIPLIER),
    MENU_CLOSE(R.drawable.mainmenu_button_close, 90, 27, UI_SCALE_MULTIPLIER),
    PLAYING_HOME(R.drawable.playing_home_button, 22, 24, 7),
    CARROT_BUTTON(R.drawable.button_carrot, 18, 18, UI_SCALE_MULTIPLIER),
    TURNIP_BUTTON(R.drawable.button_turnip, 18, 18, UI_SCALE_MULTIPLIER),
    EGGPLANT_BUTTON(R.drawable.button_eggplant, 18, 18, UI_SCALE_MULTIPLIER),
    PUMPKIN_BUTTON(R.drawable.button_pumpkin, 18, 18, UI_SCALE_MULTIPLIER);
    private int width, height;
    private Bitmap standard, pressed, locked;

    ButtonImages(int resID, int width, int height, int scale_multiplier){
        options.inScaled = false;
        this.width = width;
        this.height = height;
        Bitmap buttonAtlas = BitmapFactory.decodeResource(GameActivity.getGameContext().getResources(), resID, options);
        System.out.println("Bitmap Width: " + buttonAtlas.getWidth());
        standard = getScaledBitmapUI(Bitmap.createBitmap(buttonAtlas, 0, 0, width, height), scale_multiplier);
        pressed = getScaledBitmapUI(Bitmap.createBitmap(buttonAtlas, width, 0, width, height), scale_multiplier);
        if(width == 18){
            locked = getScaledBitmapUI(Bitmap.createBitmap(buttonAtlas, width * 2, 0, width, height), scale_multiplier);
        }
    }

    public int getWidth(){
        return width;
    }

    public  int getHeight(){
        return height;
    }

    public Bitmap getButtonImg(boolean buttonPressed, boolean isLocked){
        if(isLocked){
            return  locked;
        } else if (buttonPressed) {
            return pressed;
        }
        return standard;
    }
}
