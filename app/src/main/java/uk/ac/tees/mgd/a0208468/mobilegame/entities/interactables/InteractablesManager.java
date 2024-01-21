package uk.ac.tees.mgd.a0208468.mobilegame.entities.interactables;

import static uk.ac.tees.mgd.a0208468.mobilegame.Utils.GameConstants.Sprite.SELECTED_PLANT;
import static uk.ac.tees.mgd.a0208468.mobilegame.Utils.GameConstants.Sprite.TILE_SIZE;
import static uk.ac.tees.mgd.a0208468.mobilegame.entities.interactables.Plants.CARROT;
import static uk.ac.tees.mgd.a0208468.mobilegame.entities.interactables.Plants.EGGPLANT;
import static uk.ac.tees.mgd.a0208468.mobilegame.entities.interactables.Plants.PUMPKIN;
import static uk.ac.tees.mgd.a0208468.mobilegame.entities.interactables.Plants.TURNIP;

import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.PointF;
import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import uk.ac.tees.mgd.a0208468.mobilegame.entities.Player;
import uk.ac.tees.mgd.a0208468.mobilegame.entities.decorations.Decoration;
import uk.ac.tees.mgd.a0208468.mobilegame.environments.GameMap;
import uk.ac.tees.mgd.a0208468.mobilegame.environments.MapManager;
import uk.ac.tees.mgd.a0208468.mobilegame.gamestates.Playing;

public class InteractablesManager {
    private float cameraX, cameraY;
    private Set<Plant> plants;
    private MapManager mapManager;
    private Playing playing;
    private static Point lastTouchedTileSpace;
    private ArrayList<Decoration> decorationArrayList;
    private GameMap map;
    private Player player;
    public InteractablesManager(Playing playing, MapManager mapManager, Player player){
        this.mapManager = mapManager;
        this.decorationArrayList = mapManager.getCurrentMap().getDecorationArrayList();
        this.playing = playing;
        this.plants = new HashSet<>();
        this.player = player;
    }

    public void updatePlants(double delta){
        synchronized (plants) {
            for (Plant plant : plants) {
                plant.updatePlantStage(delta);
            }
        }
    }

    public void draw(Canvas canvas){
        if(plants != null){
            synchronized (plants) {
                for (Plant plant : plants) {
                    canvas.drawBitmap(plant.getPlantType().getImage(plant.getStage()), plant.getHitbox().left + cameraX, plant.getHitbox().top + cameraY, null);
                }
            }
        }
    }

    public void touchEvents(MotionEvent event){
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                if(canPlant(event.getX(), event.getY())){
                    spawnSapling((lastTouchedTileSpace.y * TILE_SIZE), (lastTouchedTileSpace.x * TILE_SIZE));
                }
        }
    }

    private synchronized void spawnSapling(float x, float y){
//        int randomVal = new Random().nextInt(4);
        switch (SELECTED_PLANT){
            case 0:
                plants.add(new Plant(new PointF(x, y), CARROT));
                break;
            case 1:
                plants.add(new Plant(new PointF(x, y), TURNIP));
                break;
            case 2:
                plants.add(new Plant(new PointF(x, y), EGGPLANT));
                break;
            case 3:
                plants.add(new Plant(new PointF(x, y), PUMPKIN));
                break;
        }
    }

    public void setCameraValues(float cameraX, float cameraY){
        this.cameraX = cameraX;
        this.cameraY = cameraY;
    }

    public boolean canPlant(float touchX, float touchY){
        GameMap map = mapManager.getCurrentMap();
        this.map = map;
        Point tileInGrid = GetTileInGrid(map, touchX, touchY, cameraX, cameraY);

        int dirtId = map.getMapLayer("Dirt").getSpriteId(tileInGrid.x, tileInGrid.y);
        int grassId = map.getMapLayer("Grass").getSpriteId(tileInGrid.x, tileInGrid.y);

        for(Decoration deco : decorationArrayList){
            if(deco.getHitbox().contains(touchX - cameraX, touchY - cameraY)){
                return false;
            }
        }

        synchronized (plants){
            for(Plant plant : plants){
                if(plant.getHitbox().contains(touchX - cameraX, touchY - cameraY)){
                    if(plant.getStage() >= 3){
                        HarvestPlant(plant);
                    }
                    return false;
                }
            }
        }

        if(dirtId == 12 && grassId == 10){
            return true;
        }
        return false;
    }

    private void HarvestPlant(Plant plant){
        plants.remove(plant);
        player.addXP(plant.getXp());
    }

    private static Point GetTileInGrid(GameMap map, float touchX, float touchY, float deltaX, float deltaY){
        int y = (int) ((touchX - deltaX) / TILE_SIZE);
        int x = (int) ((touchY - deltaY) / TILE_SIZE);
        lastTouchedTileSpace = new Point(x, y);
        return new  Point(x, y);
    }
}
