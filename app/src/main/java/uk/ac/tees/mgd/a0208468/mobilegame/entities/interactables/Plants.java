package uk.ac.tees.mgd.a0208468.mobilegame.entities.interactables;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import uk.ac.tees.mgd.a0208468.mobilegame.R;
import uk.ac.tees.mgd.a0208468.mobilegame.Utils.interfaces.BitmapMethods;
import uk.ac.tees.mgd.a0208468.mobilegame.main.MainActivity;

public enum Plants implements BitmapMethods {
    CARROT(0, 0, 15, 12),
    EGGPLANT(0, 13, 12, 14),
    TURNIP(0, 27, 13, 15),
    PUMPKIN(0, 42, 16, 16),
    WHEAT(0, 58, 10, 29);

    private Bitmap sapling, stage1, stage2, fullGrown;
    private int width, height;
    Plants(int x, int y, int width, int height){
        this.width = width;
        this.height = height;
        options.inScaled = false;
        Bitmap atlas = BitmapFactory.decodeResource(MainActivity.getGameContext().getResources(), R.drawable.plants, options);
        sapling = getScaledBitmap(Bitmap.createBitmap(atlas, x, y, width, height));
        stage1 = getScaledBitmap(Bitmap.createBitmap(atlas, width, y, width, height));
        stage2 = getScaledBitmap(Bitmap.createBitmap(atlas, width * 2, y, width, height));
        fullGrown = getScaledBitmap(Bitmap.createBitmap(atlas, width * 3, y, width, height));
    }

    public Bitmap getImage(int stage){
        switch (stage){
            case 0:
                return sapling;
            case 1:
                return stage1;
            case 2:
                return stage2;
            case 3:
                return fullGrown;
        }
        return sapling;
    }
    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
    }
}
