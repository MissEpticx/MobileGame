package uk.ac.tees.mgd.a0208468.mobilegame.ui;

import static uk.ac.tees.mgd.a0208468.mobilegame.Utils.GameConstants.Sprite.UI_SCALE_MULTIPLIER;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import uk.ac.tees.mgd.a0208468.mobilegame.R;
import uk.ac.tees.mgd.a0208468.mobilegame.Utils.interfaces.BitmapMethods;
import uk.ac.tees.mgd.a0208468.mobilegame.main.MainActivity;

public enum ButtonImages implements BitmapMethods {
    MENU_PLAY(R.drawable.mainmenu_button_play, 90, 27),
    MENU_CLOSE(R.drawable.mainmenu_button_close, 90, 27),
    PLAYING_HOME(R.drawable.playing_home_button, 22, 24);
    private int width, height;
    private Bitmap standard, pressed;

    ButtonImages(int resID, int width, int height){
        options.inScaled = false;
        this.width = width;
        this.height = height;

        Bitmap buttonAtlas = BitmapFactory.decodeResource(MainActivity.getGameContext().getResources(), resID, options);
        standard = getScaledBitmapUI(Bitmap.createBitmap(buttonAtlas, 0, 0, width, height), UI_SCALE_MULTIPLIER);
        pressed = getScaledBitmapUI(Bitmap.createBitmap(buttonAtlas, width, 0, width, height), UI_SCALE_MULTIPLIER);
    }

    public int getWidth(){
        return width;
    }

    public  int getHeight(){
        return height;
    }

    public Bitmap getButtonImg(boolean buttonPressed){
        return buttonPressed ? pressed : standard;
    }
}
