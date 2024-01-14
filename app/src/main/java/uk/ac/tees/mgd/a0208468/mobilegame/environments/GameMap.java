package uk.ac.tees.mgd.a0208468.mobilegame.environments;

import static uk.ac.tees.mgd.a0208468.mobilegame.Utils.GameConstants.Sprite.TILE_SIZE;

import android.graphics.Canvas;

public class GameMap {
    private int[][] spriteIds;

    public GameMap(int[][] spriteIds){
        this.spriteIds = spriteIds;
    }

    public int getSpriteId(int xIndex, int yIndex){
        return spriteIds[yIndex][xIndex];
    }

    public int getAnimSpriteId(int xIndex, int yIndex, int waterAnimX){
        if(spriteIds[yIndex][xIndex] != 4){
            return waterAnimX;
        }
        return spriteIds[yIndex][xIndex];
    }


    public int getArrayWidth(){
        return spriteIds[0].length;
    }

    public int getArrayHeight(){
        return spriteIds.length;
    }
}
