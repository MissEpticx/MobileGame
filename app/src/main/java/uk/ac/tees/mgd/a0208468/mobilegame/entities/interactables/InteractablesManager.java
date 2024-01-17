package uk.ac.tees.mgd.a0208468.mobilegame.entities.interactables;

import static uk.ac.tees.mgd.a0208468.mobilegame.Utils.GameConstants.Sprite.CHAR_SIZE;
import static uk.ac.tees.mgd.a0208468.mobilegame.Utils.GameConstants.Sprite.DEFAULT_TILE_SIZE;
import static uk.ac.tees.mgd.a0208468.mobilegame.Utils.GameConstants.Sprite.SCALE_MULTIPLIER;
import static uk.ac.tees.mgd.a0208468.mobilegame.Utils.GameConstants.Sprite.TILE_SIZE;
import static uk.ac.tees.mgd.a0208468.mobilegame.entities.interactables.Plants.CARROT;
import static uk.ac.tees.mgd.a0208468.mobilegame.main.MainActivity.GAME_HEIGHT;
import static uk.ac.tees.mgd.a0208468.mobilegame.main.MainActivity.GAME_WIDTH;

import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.RectF;
import android.view.MotionEvent;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import uk.ac.tees.mgd.a0208468.mobilegame.environments.GameMap;
import uk.ac.tees.mgd.a0208468.mobilegame.environments.MapLayer;
import uk.ac.tees.mgd.a0208468.mobilegame.environments.MapManager;
import uk.ac.tees.mgd.a0208468.mobilegame.gamestates.Playing;

public class InteractablesManager {
    private float cameraX, cameraY;
    private Set<Plant> plants;
    private MapManager mapManager;
    private Playing playing;
    public InteractablesManager(Playing playing, MapManager mapManager){
        this.mapManager = mapManager;
        this.playing = playing;
        this.plants = new HashSet<>();
    }

    public void draw(Canvas canvas){
        if(plants != null){
            for(Plant plant : plants){
                canvas.drawBitmap(plant.getPlantType().getImage(plant.getStage()), plant.getHitbox().left + cameraX, plant.getHitbox().top + cameraY, null);
            }
        }
    }

    public void touchEvents(MotionEvent event){
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                if(canPlant(event.getX(), event.getY())){
                    spawnSapling((event.getX() - cameraX), (event.getY() - cameraY));
                }
        }
    }

    private void spawnSapling(float x, float y){
        plants.add(new Plant(new PointF(x, y), CARROT));
    }

    public void setCameraValues(float cameraX, float cameraY){
        this.cameraX = cameraX;
        this.cameraY = cameraY;
    }

    public boolean canPlant(float touchX, float touchY){
        GameMap map = mapManager.getCurrentMap();

        Point tileInGrid = GetTileInGrid(map, touchX, touchY, cameraX, cameraY);

        int dirtId = map.getMapLayer("Dirt").getSpriteId(tileInGrid.x, tileInGrid.y);
        int grassId = map.getMapLayer("Grass").getSpriteId(tileInGrid.x, tileInGrid.y);

        if(dirtId == 12 && grassId == 10){
            return true;
        }
        return false;

//        System.out.println("Tile Co-ords 0: " + tileCoords[0].x + ", " + tileCoords[0].y);
//        System.out.println("Tile Co-ords 1: " + tileCoords[1].x + ", " + tileCoords[1].y);
//        System.out.println("Tile Co-ords 2: " + tileCoords[2].x + ", " + tileCoords[2].y);
//        System.out.println("Tile Co-ords 3: " + tileCoords[3].x + ", " + tileCoords[3].y);
//        int[] dirtTileIds = GetTileIds(map, tileCoords, 0);
//        int[] grassTileIds = GetTileIds(map, tileCoords, 1);

//        for (int i : dirtTileIds){
//            if (i == 12){
//                for (int j : grassTileIds){
//                    if(j == 10){
//                        return true;
//                    }
//                }
//            }
//        }
//        return false;
    }

    private static Point GetTileInGrid(GameMap map, float touchX, float touchY, float deltaX, float deltaY){
        int y = (int) ((touchX - deltaX) / TILE_SIZE);
        int x = (int) ((touchY - deltaY) / TILE_SIZE);
        return new  Point(x, y);
//        System.out.println("x: " + x + " || y: " + y);
//        MapLayer dirtLayer = map.getMapLayer("Dirt");
//        System.out.println("Sprite Id: " + dirtLayer.getSpriteId(x, y));
//        return dirtLayer.getSpriteId(x, y);
    }

    private int[] GetTileIds(GameMap map, Point[] tileCoords, int tileType){
        int[] tileIds = new int[4];
        for (int i = 0; i < tileCoords.length; i++) {
            if(tileCoords[i].x > 0 && tileCoords[i].y > 0){
                if(tileType == 0){
                    tileIds[i] = map.getSpriteId(map.getMapLayer("Dirt"), tileCoords[i].x, tileCoords[i].y);
                } else {
                    tileIds[i] = map.getSpriteId(map.getMapLayer("Grass"), tileCoords[i].x, tileCoords[i].y);
                }
            }
            System.out.println(tileIds[i]);
        }
        return tileIds;
    }
}
