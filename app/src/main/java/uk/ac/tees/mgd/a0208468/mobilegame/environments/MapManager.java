package uk.ac.tees.mgd.a0208468.mobilegame.environments;

import static uk.ac.tees.mgd.a0208468.mobilegame.Utils.GameConstants.Sprite.TILE_SIZE;
import static uk.ac.tees.mgd.a0208468.mobilegame.entities.decorations.Decorations.BOULDER;
import static uk.ac.tees.mgd.a0208468.mobilegame.entities.decorations.Decorations.HOUSE;
import static uk.ac.tees.mgd.a0208468.mobilegame.entities.decorations.Decorations.PEBBLE;
import static uk.ac.tees.mgd.a0208468.mobilegame.entities.decorations.Decorations.SUNFLOWER;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.RectF;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import uk.ac.tees.mgd.a0208468.mobilegame.entities.decorations.Decoration;
import uk.ac.tees.mgd.a0208468.mobilegame.gamestates.Playing;

public class MapManager {
    private GameMap currentMap;
    private float cameraX, cameraY;
    private Playing playing;
    private Paint paint = new Paint();
    public MapManager(Playing playing){
        this.playing = playing;
        InitialiseMap();
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);
    }

    public GameMap getCurrentMap(){
        return currentMap;
    }
    public void drawTiles(Canvas canvas, int waterAnimX){
        for(String key : currentMap.getKeys()){
            for (int i = 0; i < currentMap.getArrayHeight(key); i++) {
                for (int j = 0; j < currentMap.getArrayWidth(key); j++) {
                    MapLayer layer = currentMap.getMapLayer(key);
                    if(j > 0 && i > 0){
                        if(key.contains("Water")){

                            Bitmap draw = layer.getFloorType().getSprite(currentMap.getAnimSpriteId(layer, j, i, waterAnimX));
                            canvas.drawBitmap(draw, (j * TILE_SIZE) + cameraX, (i * TILE_SIZE) + cameraY, null);
                        } else{
                            canvas.drawBitmap(layer.getFloorType().getSprite(currentMap.getSpriteId(layer, j, i)), (j * TILE_SIZE) + cameraX, (i * TILE_SIZE) + cameraY, null);
                        }
                    }
                }
            }
        }
    }

    public void drawDecorations(Canvas canvas){
        if(currentMap.getDecorationArrayList() != null){
            for(Decoration deco : currentMap.getDecorationArrayList()){
                RectF hitbox = deco.getHitbox();
                canvas.drawBitmap(deco.getDecorationType().getImage(), hitbox.left + cameraX, hitbox.top + cameraY, null);

//                canvas.drawRect(hitbox.left + cameraX, hitbox.top + cameraY, hitbox.right + cameraX, hitbox.bottom + cameraY, paint);
            }
        }
    }

    public void draw(Canvas canvas, int waterAnimX){
        drawTiles(canvas, waterAnimX);
        drawDecorations(canvas);
    }

    public void setCameraValues(float cameraX, float cameraY){
        this.cameraX = cameraX;
        this.cameraY = cameraY;
    }

    public boolean canWalkHere(RectF playerHitbox, float deltaX, float deltaY){
        Point[] tileCoords = GetTileCoords(playerHitbox, deltaX, deltaY);
        int[] waterTileIds = GetTileIds(tileCoords, 0);
        int[] hillTileIds = GetTileIds(tileCoords, 1);

        if(!IsTilesWalkable(waterTileIds, 0)){
            return false;
        }
        if(!IsTilesWalkable(hillTileIds, 1)){
            return false;
        }
        for (Decoration deco : currentMap.getDecorationArrayList()){
            if(deco.hitPlayer(playerHitbox, deltaX, deltaY)){
                System.out.println("Collision detected");
                return false;
            }
        }
        return true;
    }

    private static Point[] GetTileCoords(RectF hitbox, float deltaX, float deltaY){
        Point[] tileCoords = new Point[4];

        int left = (int) ((hitbox.left + deltaX) / TILE_SIZE);
        int right = (int) ((hitbox.right + deltaX) / TILE_SIZE);
        int top = (int) ((hitbox.top + deltaY) / TILE_SIZE);
        int bottom = (int) ((hitbox.bottom + deltaY) / TILE_SIZE);

        tileCoords[0] = new Point(left, top);
        tileCoords[1] = new Point(right, top);
        tileCoords[2] = new Point(left, bottom);
        tileCoords[3] = new Point(right, bottom);

        return tileCoords;
    }

    private int[] GetTileIds(Point[] tileCoords, int tileType){
        int[] tileIds = new int[4];
        for (int i = 0; i < tileCoords.length; i++) {
            if(tileType == 0){
                tileIds[i] = currentMap.getSpriteId(currentMap.getMapLayer("Water"), tileCoords[i].x, tileCoords[i].y);
            } else{
                tileIds[i] = currentMap.getSpriteId(currentMap.getMapLayer("Hills"), tileCoords[i].x, tileCoords[i].y);
            }
        }
        return tileIds;
    }

    public static boolean IsTilesWalkable(int[] tileIds, int tileType){
        for(int i : tileIds){
            if(!IsTileWalkable(i, tileType)){
                return false;
            }
        }
        return true;
    }

    public static boolean IsTileWalkable(int tileID, int tileType){
        if(tileType == 0){
            if(tileID != 4){
                return false;
            }
        } else{
            if(tileID == 0 || tileID == 1 || tileID == 2
                || tileID == 11 || tileID == 13 || tileID == 22
                || tileID == 23 || tileID == 24){
                return false;
            }
        }
        return true;
    }

    public void InitialiseMap(){
        ArrayList<Decoration> decorationArrayList;

        decorationArrayList = new ArrayList<>();
        decorationArrayList.add(new Decoration(new PointF(3375, 400), HOUSE));
        decorationArrayList.add(new Decoration(new PointF(1000, 800), PEBBLE));
        decorationArrayList.add(new Decoration(new PointF(2000, 1500), BOULDER));
        decorationArrayList.add(new Decoration(new PointF(4100, 1000), SUNFLOWER));

        int[][] bushSpriteIds = {
                { 0,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  2},
                {11, 16, 23, 23, 23, 23, 23, 23, 23, 23, 23, 23, 23, 23, 23, 17, 78, 78, 78, 78, 78, 78, 78, 78, 78, 78, 78, 78, 78, 78, 78, 78, 78, 13},
                {11, 13, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 11, 78, 78, 78, 78, 78, 78, 78, 78, 78, 78, 78, 78, 78, 78, 78, 78, 78, 13},
                {11, 13, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 11, 78, 78, 78, 78, 78, 78, 78, 16, 23, 23, 23, 23, 23, 23, 23, 23, 23, 18},
                {11, 13, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 11, 78, 78, 78, 78, 78, 78, 78, 13, 10, 10, 10, 10, 10, 10, 10, 10, 10, 14},
                {11, 13, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 22, 23, 23, 23, 23, 23, 23, 23, 24, 10, 10, 10, 10, 10, 10, 10, 10, 10, 14},
                {11, 13, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 14},
                {22, 24, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 14},
                {10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 14},
                {10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 14},
                {10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 14},
                {10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 14},
                {10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 25},
                {10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10},
                {10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10},
                {10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10},
                {10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10},
                {10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10},
                {10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10},
                {10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10}
        };

        int[][] waterSpriteIds = {
                { 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},
                { 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  0},
                { 0,  0,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  0,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  0},
                { 0,  0,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  0,  4,  4,  4,  4,  4,  4,  4,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},
                { 0,  0,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  0,  4,  4,  4,  4,  4,  4,  4,  0,  4,  4,  4,  4,  4,  4,  4,  4,  4,  0},
                { 0,  0,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  0,  0,  0,  0,  0,  0,  0,  0,  0,  4,  4,  4,  4,  4,  4,  4,  4,  4,  0},
                { 0,  0,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  0},
                { 0,  0,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  0},
                { 0,  0,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  0},
                { 0,  0,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  0},
                { 0,  0,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  0},
                { 0,  0,  0,  0,  0,  0,  0,  0,  0,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  0},
                { 0,  0,  0,  4,  4,  0,  0,  0,  0,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  0},
                { 0,  0,  4,  4,  4,  4,  0,  0,  0,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  0},
                { 0,  0,  4,  4,  4,  4,  4,  4,  0,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  0,  0,  0,  0,  0,  0},
                { 0,  4,  4,  4,  4,  4,  4,  4,  0,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  0,  0,  0,  0,  0,  0},
                { 0,  4,  4,  4,  4,  4,  4,  4,  0,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  0,  0,  0,  0,  0,  0},
                { 0,  4,  4,  4,  4,  4,  4,  4,  0,  0,  0,  0,  0,  0,  0,  0,  0,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  0,  0,  0,  0,  0,  0},
                { 0,  4,  4,  4,  4,  4,  4,  4,  4,  0,  0,  0,  0,  0,  0,  0,  0,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  0,  0,  0,  0,  0,  0},
                { 0,  0,  4,  4,  4,  4,  4,  4,  4,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0}
        };

        int[][] waterLedgeSpriteIds = {
                { 4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4},
                { 4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4},
                { 4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4},
                { 4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4},
                { 4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4},
                { 4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4},
                { 4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4},
                { 4,  0,  0,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4},
                { 4,  4,  0,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4},
                { 4,  4,  0,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4},
                { 4,  4,  0,  0,  0,  0,  0,  0,  0,  0,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4},
                { 4,  4,  4,  4,  4,  4,  4,  4,  4,  0,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4},
                { 4,  4,  0,  0,  0,  0,  4,  4,  4,  0,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4},
                { 4,  4,  0,  4,  4,  0,  4,  4,  4,  0,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  0,  0,  0,  0,  0,  0,  4},
                { 4,  0,  0,  4,  4,  0,  0,  0,  4,  0,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  0,  4,  4,  4,  4,  4,  4},
                { 4,  0,  4,  4,  4,  4,  4,  0,  4,  0,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  0,  4,  4,  4,  4,  4,  4},
                { 4,  0,  4,  4,  4,  4,  4,  0,  4,  0,  0,  0,  0,  0,  0,  0,  0,  0,  4,  4,  4,  4,  4,  4,  4,  4,  4,  0,  4,  4,  4,  4,  4,  4},
                { 4,  0,  4,  4,  4,  4,  4,  0,  4,  4,  4,  4,  4,  4,  4,  4,  4,  0,  4,  4,  4,  4,  4,  4,  4,  4,  4,  0,  4,  4,  4,  4,  4,  4},
                { 4,  0,  4,  4,  4,  4,  4,  4,  0,  4,  4,  4,  4,  4,  4,  4,  4,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  4,  4,  4,  4,  4,  4},
                { 4,  0,  0,  0,  0,  0,  0,  0,  0,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4,  4}
        };

        int[][] dirtSpriteIds = {
                {10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10},
                {10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10},
                {10, 10,  0,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  2, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10},
                {10, 10, 11, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 13, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10},
                {10, 10, 11, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 13, 10, 10, 10, 10, 10, 10, 10, 10, 10,  0,  1,  1,  1,  1,  1,  1,  1,  2, 10},
                {10, 10, 11, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 13, 10, 10, 10, 10, 10, 10, 10, 10, 10, 11, 12, 12, 12, 12, 12, 12, 12, 13, 10},
                {10, 10, 11, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 27,  1,  1,  1,  1,  1,  1,  1,  1 , 1, 28, 12, 12, 12, 12, 12, 12, 12, 13, 10},
                {22, 23, 17, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 13, 10},
                { 3, 10, 11, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 13, 10},
                {14, 10, 11, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 13, 10},
                {14, 10, 22, 23, 23, 23, 23, 23, 23, 17, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 13, 10},
                {48,  8, 34, 35, 10, 10, 10, 10, 10, 11, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 13, 10},
                {14, 25,  0,  1,  1,  2, 10, 36, 10, 11, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 16, 24},
                {25, 10, 11, 12, 12, 13, 10, 10, 10, 11, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 16, 23, 23, 23, 23, 24, 10},
                {10,  0, 28, 12, 12, 27,  1,  2, 10, 11, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 13, 10, 10, 10, 10, 36, 10},
                {10, 11, 12, 12, 12, 12, 12, 13, 10, 11, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 13, 10, 33,  7, 10, 10, 10},
                {10, 11, 12, 12, 12, 12, 12, 13, 10, 22, 23, 23, 23, 23, 23, 23, 23, 17, 12, 12, 12, 12, 12, 12, 12, 12, 12, 13, 10, 10, 14, 10,  3, 10},
                {10, 11, 12, 12, 12, 12, 12, 27,  2, 10, 10, 10, 10,  3, 10, 10, 10, 11, 12, 12, 12, 12, 12, 12, 12, 12, 12, 13, 10, 10, 37, 34, 51, 10},
                {36, 11, 12, 12, 12, 12, 12, 12, 27,  2, 10,  3, 10, 37, 34, 35, 10, 22, 23, 23, 23, 23, 23, 23, 23, 23, 23, 24, 10, 36, 10, 10, 14, 10},
                {10, 22, 23, 23, 23, 23, 23, 23, 23, 24, 10, 25, 10, 10, 10, 10, 36, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 36, 10, 10, 10, 25, 10}
        };

        int[][] grassSpriteIds = {
                { 0, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10,  2},
                {11, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 13},
                {11, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 10, 10, 10, 10, 10, 10, 10, 12, 12, 12, 10, 10, 10, 10, 10, 10, 10, 13},
                {11, 12, 12, 16, 23, 23, 23, 23, 23, 23, 23, 23, 23, 17, 12, 12, 10, 10, 10, 10, 10, 10, 10, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 13},
                {11, 12, 12, 13, 10, 10, 10, 10, 10, 10, 10, 10, 10, 11, 12, 12, 10, 10, 10, 10, 10, 10, 10, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 13},
                {11, 12, 12, 13, 10, 10, 10, 10, 10, 10, 10, 10, 10, 11, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 13},
                {11, 12, 12, 13, 10, 10, 10, 10, 10, 10,  0,  1,  1, 28, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 13},
                {22, 23, 17, 13, 10, 10, 10, 10, 10, 10, 11, 16, 23, 23, 23, 23, 23, 23, 23, 23, 17, 16, 23, 23, 17, 12, 12, 12, 12, 12, 12, 12, 12, 13},
                {10, 10, 11, 13, 10, 10, 10, 10, 10, 10, 11, 13, 10, 10, 10, 10, 10, 10, 10, 10, 11, 13, 10, 10, 11, 12, 12, 12, 12, 12, 12, 12, 12, 13},
                {10, 10, 11, 27,  1,  1,  1,  1,  1,  1, 28, 13, 10, 10, 10, 10, 10, 10, 10, 10, 11, 13, 10, 10, 11, 12, 12, 12, 12, 12, 12, 12, 12, 13},
                {10, 10, 22, 23, 23, 23, 23, 23, 23, 17, 12, 27,  1,  1,  1,  1,  1,  1,  1,  1, 28, 27,  1,  1, 28, 12, 12, 12, 12, 12, 12, 12, 12, 13},
                {36,  3, 33, 35, 10, 10, 10, 10, 10, 11, 16, 23, 23, 23, 23, 23, 23, 23, 23, 23, 23, 23, 17, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 13},
                {10, 25, 10, 33, 34,  7, 10, 10, 10, 11, 13, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 11, 12, 12, 12, 12, 12, 12, 12, 12, 12, 16, 24},
                {10, 10, 10, 10, 36, 14, 10, 10, 10, 11, 13, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 22, 23, 23, 23, 17, 16, 23, 23, 23, 23, 24, 10},
                {10, 10, 10, 10, 10, 37,  6,  2, 10, 11, 13, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 11, 13, 10, 10, 10, 10, 10, 10},
                {10, 10, 10, 10, 10, 10, 11, 13, 10, 11, 27,  1,  1,  1,  1,  1,  1,  1,  2, 10, 10, 10, 10, 10, 10, 10, 11, 13, 10, 10,  3, 10, 10, 10},
                {10, 10, 10, 10, 10, 10, 11, 13, 10, 22, 23, 23, 23, 23, 23, 23, 23, 17, 13, 10, 10, 10, 10, 10, 10, 10, 11, 13, 10, 10, 14, 10, 36, 10},
                {10, 10, 10, 10, 10, 10, 22, 38,  7, 10, 10, 10, 10, 10, 10, 10, 10, 11, 27,  1,  1,  1,  1,  1,  1,  1, 28, 13, 10, 10, 37, 35, 10, 10},
                {10, 10, 10, 10, 10, 10, 10, 10, 25, 10, 10, 10, 10, 33, 35, 10, 10, 22, 23, 23, 23, 23, 23, 23, 23, 23, 23, 24, 10, 10, 10, 10, 10, 10},
                {10, 10, 10, 10, 10, 10, 10, 10, 33, 35, 10, 10, 10, 10, 10, 10, 36, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 36, 10, 10, 10, 10, 10}
        };

        int[][] hillSpriteIds = {
                {10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10},
                {10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10},
                {10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10},
                {10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10},
                {10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10,  0,  1,  1,  1,  1,  2, 10, 10},
                {10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 11, 12, 12, 12, 12, 13, 10, 10},
                {10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 11, 12, 12, 12, 12, 13, 10, 10},
                {10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 11, 12, 12, 12, 12, 13, 10, 10},
                {10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 22, 23, 12, 12, 23, 24, 10, 10},
                {10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10},
                {10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10},
                {10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10},
                {10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10},
                {10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10},
                {10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10},
                {10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10},
                {10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10},
                {10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10},
                {10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10},
                {10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10}
        };

        int[][] slopeSpriteIds = {
                {12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12},
                {12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12},
                {12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12},
                {12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12},
                {12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12},
                {12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12},
                {12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12},
                {12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12},
                {12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12,  4,  5, 12, 12, 12, 12},
                {12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 10, 11, 12, 12, 12, 12},
                {12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 16, 17, 12, 12, 12, 12},
                {12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12},
                {12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12},
                {12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12},
                {12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12},
                {12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12},
                {12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12},
                {12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12},
                {12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12},
                {12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12}
        };

        MapLayer water = new MapLayer(waterSpriteIds, Floor.WATER);
        MapLayer waterEdges = new MapLayer(waterLedgeSpriteIds, Floor.WATER);
        MapLayer dirt = new MapLayer(dirtSpriteIds, Floor.DIRT);
        MapLayer grass = new MapLayer(grassSpriteIds, Floor.GRASS);
        MapLayer hills = new MapLayer(hillSpriteIds, Floor.HILL);
        MapLayer slope = new MapLayer(slopeSpriteIds, Floor.SLOPES);
        MapLayer bushes = new MapLayer(bushSpriteIds, Floor.BUSHES);

        Map<String, MapLayer> mapLayers = new LinkedHashMap<>();
        mapLayers.put("Water", water);
        mapLayers.put("WaterEdge", waterEdges);
        mapLayers.put("Dirt", dirt);
        mapLayers.put("Grass", grass);
        mapLayers.put("Hills", hills);
        mapLayers.put("Slope", slope);
        mapLayers.put("Bushes", bushes);

        currentMap = new GameMap(mapLayers, decorationArrayList);
    }
}
