package uk.ac.tees.mgd.a0208468.mobilegame.environments;

import static uk.ac.tees.mgd.a0208468.mobilegame.Utils.GameConstants.Sprite.TILE_SIZE;

import android.graphics.Canvas;

public class MapManager {
    private GameMap currentDirt;
    private GameMap currentGrass;
    private float cameraX;
    private float cameraY;
    public MapManager(){
        InitialiseMap();
    }

    public void InitialiseMap(){
        int[][] dirtSpriteIds = {
                {0, 1, 1, 1, 1, 2, 33, 34, 35, 10, 0, 1, 1, 1, 1, 1, 2},
                {11, 12, 12, 12, 12, 27, 1, 2, 10, 10, 22, 17, 12, 12, 12, 12, 13},
                {11, 12, 12, 12, 12, 12, 12, 13, 10, 36, 10, 11, 12, 12, 12, 12, 13},
                {22, 23, 23, 23, 17, 12, 12, 27, 1, 1, 1, 28, 12, 12, 12, 16, 24},
                {10, 10, 3, 10, 11, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 13, 10},
                {36, 10, 25, 10, 11, 16, 23, 23, 23, 17, 12, 12, 12, 12, 12, 13, 10},
                {0, 1, 1, 1, 28, 13, 10, 10, 36, 11, 12, 12, 12, 12, 16, 24, 10},
                {22, 23, 23, 23, 23, 24, 33, 35, 10, 22, 23, 23, 23, 23, 24, 10, 10}
        };

        System.out.println("MapArrayTest: " + dirtSpriteIds.length);
        currentDirt = new GameMap(dirtSpriteIds);

        int[][] grassSpriteIds = {
                {10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10},
                {10, 0, 1, 1, 1, 1, 1, 2, 10, 10, 10, 10, 0, 1, 1, 1, 2},
                {10, 11, 12, 12, 12, 12, 12, 13, 10, 10, 10, 0, 28, 12, 12, 12, 13},
                {10, 22, 23, 23, 17, 12, 12, 27, 1, 1, 1, 28, 12, 12, 12, 16, 24},
                {10, 10, 10, 10, 11, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 13, 10},
                {10, 10, 10, 10, 11, 16, 23, 23, 23, 17, 12, 12, 12, 12, 12, 13, 10},
                {0, 1, 1, 1, 28, 13, 10, 10, 36, 11, 12, 12, 12, 12, 16, 24, 10},
                {22, 23, 23, 23, 23, 24, 10, 10, 10, 22, 23, 23, 23, 23, 24, 10, 10}
        };
        currentGrass = new GameMap(grassSpriteIds);
    }

    public void draw(Canvas canvas){
        for (int j = 0; j < currentDirt.getArrayHeight(); j++) {
            for (int i = 0; i < currentDirt.getArrayWidth(); i++) {
                canvas.drawBitmap(Floor.DIRT.getSprite(currentDirt.getSpriteId(i, j)), (i * TILE_SIZE) + cameraX, (j * TILE_SIZE) + cameraY, null);
            }
        }

        for (int j = 0; j < currentGrass.getArrayHeight(); j++) {
            for (int i = 0; i < currentGrass.getArrayWidth(); i++) {
                canvas.drawBitmap(Floor.GRASS.getSprite(currentGrass.getSpriteId(i, j)), (i * TILE_SIZE) + cameraX, (j * TILE_SIZE) + cameraY, null);
            }
        }
    }

    public void setCameraValues(float cameraX, float cameraY){
        this.cameraX = cameraX;
        this.cameraY = cameraY;
    }

    public boolean canWalkHere(float x, float y){
        if(x < 0 || y < 0){
            return false;
        }

        if(x > getMapMaxWidth() || y > getMapMaxHeight()){
            return false;
        }

        return true;
    }

    public int getMapMaxWidth(){
        return currentDirt.getArrayWidth() * TILE_SIZE;
    }

    public int getMapMaxHeight(){
        return currentDirt.getArrayHeight() * TILE_SIZE;
    }
}
