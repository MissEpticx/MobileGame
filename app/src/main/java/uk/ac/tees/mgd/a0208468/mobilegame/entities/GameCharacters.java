package uk.ac.tees.mgd.a0208468.mobilegame.entities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import uk.ac.tees.mgd.a0208468.mobilegame.MainActivity;
import uk.ac.tees.mgd.a0208468.mobilegame.R;

public enum GameCharacters {
    PLAYER(R.drawable.player_sprite_sheet);

    private Bitmap spriteSheet;
    private Bitmap[][] sprites = new Bitmap[24][8]; //sprite size = 48x48
    private BitmapFactory.Options options = new BitmapFactory.Options();
    private int scaleSize = 8;

    GameCharacters(int redID) {
        options.inScaled = false;
        spriteSheet = BitmapFactory.decodeResource(MainActivity.getGameContext().getResources(), redID, options);
        for(int j = 0; j < sprites.length; j++){
            for(int i = 0; i < sprites[j].length; i++){
                sprites[j][i] = getScaledBitmap(Bitmap.createBitmap(spriteSheet, 48 * i, 48 * j, 48, 48));
            }
        }
    }

    public  Bitmap getSpriteSheet(){
        return spriteSheet;
    }

    public  Bitmap getSprite(int yPosition, int xPosition){
        return sprites[yPosition][xPosition];
    }

    private  Bitmap getScaledBitmap(Bitmap bitmap) {
        return Bitmap.createScaledBitmap(bitmap, bitmap.getWidth() * scaleSize, bitmap.getHeight() * scaleSize, false);
    }
}
