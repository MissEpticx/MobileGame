package uk.ac.tees.mgd.a0208468.mobilegame.environments;

import static uk.ac.tees.mgd.a0208468.mobilegame.Utils.GameConstants.Sprite.DEFAULT_TILE_SIZE;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import uk.ac.tees.mgd.a0208468.mobilegame.main.MainActivity;
import uk.ac.tees.mgd.a0208468.mobilegame.R;
import uk.ac.tees.mgd.a0208468.mobilegame.Utils.interfaces.BitmapMethods;

public enum Floor implements BitmapMethods {
    DIRT(R.drawable.dirt_tileset, 11, 7),
    GRASS(R.drawable.grass_tileset, 11, 7),
    HILL(R.drawable.grass_hill_tileset, 11, 7),
    SLOPES(R.drawable.grass_hill_slopes_tileset, 6, 3),
    BUSHES(R.drawable.bush_tileset, 11, 11),
    WATER(R.drawable.water_sprite_sheet, 5, 1),
    BASE_RAIN(R.drawable.rain_long, 8, 1),
    QUICK_RAIN(R.drawable.rain_short, 8, 1);

    private Bitmap[] sprites;
    private boolean isOccupied;
    Floor(int resID, int tilesInWidth, int tilesInHeight){
        options.inScaled = false;
        sprites = new Bitmap[tilesInHeight * tilesInWidth];
        Bitmap spriteSheet = BitmapFactory.decodeResource(MainActivity.getGameContext().getResources(), resID, options);
        if(resID == R.drawable.rain){
            for(int j = 0; j < tilesInHeight; j++){
                for(int i = 0; i < tilesInWidth; i++){
                    int index = j * tilesInWidth + i;
                    sprites[index] = getScaledBitmap(Bitmap.createBitmap(spriteSheet, DEFAULT_TILE_SIZE * i, DEFAULT_TILE_SIZE * j, DEFAULT_TILE_SIZE, DEFAULT_TILE_SIZE));
                }
            }
        } else{
            for(int j = 0; j < tilesInHeight; j++){
                for(int i = 0; i < tilesInWidth; i++){
                    int index = j * tilesInWidth + i;
                    sprites[index] = getScaledBitmap(Bitmap.createBitmap(spriteSheet, DEFAULT_TILE_SIZE * i, DEFAULT_TILE_SIZE * j, DEFAULT_TILE_SIZE, DEFAULT_TILE_SIZE));
                }
            }
        }
    }

    public Bitmap getSprite(int id){
        return sprites[id];
    }
}
