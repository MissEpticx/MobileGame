package uk.ac.tees.mgd.a0208468.mobilegame.environments;

import static uk.ac.tees.mgd.a0208468.mobilegame.Utils.GameConstants.Sprite.TILE_SIZE;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import uk.ac.tees.mgd.a0208468.mobilegame.entities.decorations.Decoration;

public class GameMap {
    private Map<String, MapLayer> mapLayers;
    private ArrayList<Decoration> decorationArrayList;
    public GameMap(Map<String, MapLayer> mapLayers, ArrayList<Decoration> decorationArrayList){
        this.mapLayers = mapLayers;
        this.decorationArrayList = decorationArrayList;
    }

    public Set<String> getKeys(){
        return mapLayers.keySet();
    }

    public MapLayer getMapLayer(String layerName){
        return mapLayers.get(layerName);
    }

    public ArrayList<Decoration> getDecorationArrayList(){
        return decorationArrayList;
    }

    public int getSpriteId(MapLayer layer, int xIndex, int yIndex){
        return layer.getSpriteIds()[yIndex][xIndex];
    }

    public int getAnimSpriteId(MapLayer layer, int xIndex, int yIndex, int waterAnimX){
        if(layer.getSpriteIds()[yIndex][xIndex] != 4){
            return waterAnimX;
        }
        return layer.getSpriteIds()[yIndex][xIndex];
    }

    public int getArrayWidth(String layerName){
        return mapLayers.get(layerName).getSpriteIds()[0].length;
    }

    public int getArrayHeight(String layerName){
        return mapLayers.get(layerName).getSpriteIds().length;
    }

    public int getMapWidth(String layerName){
        return getArrayWidth(layerName) * TILE_SIZE;
    }

    public int getMapHeight(String layerName){
        return getArrayHeight(layerName) * TILE_SIZE;
    }
}
