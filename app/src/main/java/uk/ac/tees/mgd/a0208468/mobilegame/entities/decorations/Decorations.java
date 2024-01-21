package uk.ac.tees.mgd.a0208468.mobilegame.entities.decorations;

import static uk.ac.tees.mgd.a0208468.mobilegame.Utils.GameConstants.Sprite.SCALE_MULTIPLIER;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import uk.ac.tees.mgd.a0208468.mobilegame.R;
import uk.ac.tees.mgd.a0208468.mobilegame.Utils.interfaces.BitmapMethods;
import uk.ac.tees.mgd.a0208468.mobilegame.main.GameActivity;
import uk.ac.tees.mgd.a0208468.mobilegame.main.MainActivity;

public enum Decorations implements BitmapMethods{
    HOUSE(0, 11, 80, 59, R.drawable.house), // 3375, 400
    PEBBLE(5,24,5,4, R.drawable.ground_decorations),
    BOULDER(101, 27, 22, 19, R.drawable.ground_decorations),
    SUNFLOWER(49, 49, 14, 28, R.drawable.ground_decorations);

    private Bitmap image;
    private  int width, height;
    Decorations(int x, int y, int width, int height, int resID){
        this.width = width;
        this.height = height;
        options.inScaled = false;
        Bitmap atlas = BitmapFactory.decodeResource(GameActivity.getGameContext().getResources(), resID, options);
        image = getScaledBitmap(Bitmap.createBitmap(atlas, x, y, width, height));
    }

    public Bitmap getImage(){
        return image;
    }
    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
    }
}
