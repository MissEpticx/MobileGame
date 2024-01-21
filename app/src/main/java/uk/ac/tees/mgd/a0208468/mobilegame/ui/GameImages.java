package uk.ac.tees.mgd.a0208468.mobilegame.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import uk.ac.tees.mgd.a0208468.mobilegame.R;
import uk.ac.tees.mgd.a0208468.mobilegame.Utils.interfaces.BitmapMethods;
import uk.ac.tees.mgd.a0208468.mobilegame.main.GameActivity;
import uk.ac.tees.mgd.a0208468.mobilegame.main.MainActivity;

public enum GameImages implements BitmapMethods {
    MAIN_MENU_BOARD(R.drawable.mainmenu_board),
    INVENTORY_BAR(R.drawable.inventory_bar),
    NO_STARS(R.drawable.no_stars),
    HALF_STAR(R.drawable.half_star),
    ONE_STAR(R.drawable.one_star),
    ONE_HALF_STAR(R.drawable.one_half_star),
    TWO_STAR(R.drawable.two_stars),
    TWO_HALF_STAR(R.drawable.two_half_stars),
    FULL_STARS(R.drawable.full_stars),
    LEVEL_0(R.drawable.level0),
    LEVEL_1(R.drawable.level1),
    LEVEL_2(R.drawable.level2),
    LEVEL_3(R.drawable.level3);
    private final Bitmap image;
    GameImages(int resID){
        int scale = 4;
        options.inScaled = false;
        if(resID == R.drawable.mainmenu_board){
            scale = 8;
        } else if(resID == R.drawable.inventory_bar){
            scale = 6;
        }
        image = getScaledBitmapUI(BitmapFactory.decodeResource(GameActivity.getGameContext().getResources(), resID, options), scale);
    }

    public Bitmap getImage(){
        return image;
    }

}
