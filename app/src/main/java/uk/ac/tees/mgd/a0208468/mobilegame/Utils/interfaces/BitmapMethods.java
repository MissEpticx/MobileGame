package uk.ac.tees.mgd.a0208468.mobilegame.Utils.interfaces;

import static uk.ac.tees.mgd.a0208468.mobilegame.Utils.GameConstants.Sprite.SCALE_MULTIPLIER;
import static uk.ac.tees.mgd.a0208468.mobilegame.Utils.GameConstants.Sprite.UI_SCALE_MULTIPLIER;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public interface BitmapMethods {
    BitmapFactory.Options options = new BitmapFactory.Options();

    default Bitmap getScaledBitmap(Bitmap bitmap) {
        return Bitmap.createScaledBitmap(bitmap, bitmap.getWidth() * SCALE_MULTIPLIER, bitmap.getHeight() * SCALE_MULTIPLIER, false);
    }

    default Bitmap getScaledBitmapUI(Bitmap bitmap, int scale){
        return Bitmap.createScaledBitmap(bitmap, bitmap.getWidth() * scale, bitmap.getHeight() * scale, false);
    }

}
