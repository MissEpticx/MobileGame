package uk.ac.tees.mgd.a0208468.mobilegame.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import uk.ac.tees.mgd.a0208468.mobilegame.R;
import uk.ac.tees.mgd.a0208468.mobilegame.Utils.interfaces.BitmapMethods;
import uk.ac.tees.mgd.a0208468.mobilegame.main.MainActivity;

public enum GameImages implements BitmapMethods {
    MAIN_MENU_BOARD(R.drawable.mainmenu_board),
    INVENTORY_BAR(R.drawable.inventory_bar);
    private final Bitmap image;
    GameImages(int resID){
        options.inScaled = false;
        if(resID == R.drawable.inventory_bar){
            image = getScaledBitmapUI(BitmapFactory.decodeResource(MainActivity.getGameContext().getResources(), resID, options), 6);
        } else{
            image = getScaledBitmapUI(BitmapFactory.decodeResource(MainActivity.getGameContext().getResources(), resID, options), 8);
        }
    }

    public Bitmap getImage(){
        return image;
    }

}
