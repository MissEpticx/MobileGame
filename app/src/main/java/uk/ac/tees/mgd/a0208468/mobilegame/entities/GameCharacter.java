package uk.ac.tees.mgd.a0208468.mobilegame.entities;

import static uk.ac.tees.mgd.a0208468.mobilegame.Utils.GameConstants.Sprite.DEFAULT_CHAR_SIZE;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import uk.ac.tees.mgd.a0208468.mobilegame.main.GameActivity;
import uk.ac.tees.mgd.a0208468.mobilegame.main.MainActivity;
import uk.ac.tees.mgd.a0208468.mobilegame.R;
import uk.ac.tees.mgd.a0208468.mobilegame.Utils.interfaces.BitmapMethods;

public enum GameCharacter implements BitmapMethods {
    PLAYER(R.drawable.player_sprite_sheet);

    private Bitmap spriteSheet;
    private Bitmap[][] sprites = new Bitmap[24][8]; //sprite size = 48x48

    GameCharacter(int resID) {
        options.inScaled = false;
        spriteSheet = BitmapFactory.decodeResource(GameActivity.getGameContext().getResources(), resID, options);
        for(int j = 0; j < sprites.length; j++){
            for(int i = 0; i < sprites[j].length; i++){
                sprites[j][i] = getScaledBitmap(Bitmap.createBitmap(spriteSheet, DEFAULT_CHAR_SIZE * i, DEFAULT_CHAR_SIZE * j, DEFAULT_CHAR_SIZE, DEFAULT_CHAR_SIZE));
            }
        }
    }

    public  Bitmap getSpriteSheet(){
        return spriteSheet;
    }

    public  Bitmap getSprite(int yPosition, int xPosition){
        return sprites[yPosition][xPosition];
    }
}
