package uk.ac.tees.mgd.a0208468.mobilegame.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import uk.ac.tees.mgd.a0208468.mobilegame.R;
import uk.ac.tees.mgd.a0208468.mobilegame.Utils.interfaces.BitmapMethods;
import uk.ac.tees.mgd.a0208468.mobilegame.main.MainActivity;

public enum ButtonImages implements BitmapMethods {
    MENU_PLAY(R.drawable.mainmenu_button_play, 247, 74);
    private int width, height;
    private Bitmap standard, pressed;

    ButtonImages(int resId, int width, int height){
        this.width = width;
        this.height = height;

        Bitmap buttonAtlas = BitmapFactory.decodeResource(MainActivity.getGameContext().getResources(), resId, options);
        standard = Bitmap.createBitmap(buttonAtlas, 0, 0, width, height);
        pressed = Bitmap.createBitmap(buttonAtlas, width, 0, width, height);
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
