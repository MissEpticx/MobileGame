package uk.ac.tees.mgd.a0208468.mobilegame.gamestates;

import static uk.ac.tees.mgd.a0208468.mobilegame.Utils.GameConstants.Sprite.DEFAULT_CHAR_SIZE;
import static uk.ac.tees.mgd.a0208468.mobilegame.Utils.GameConstants.Sprite.TILE_SIZE;
import static uk.ac.tees.mgd.a0208468.mobilegame.Utils.GameConstants.Sprite.UI_SCALE_MULTIPLIER;
import static uk.ac.tees.mgd.a0208468.mobilegame.Utils.GameConstants.Sprite.X_DRAW_OFFSET;
import static uk.ac.tees.mgd.a0208468.mobilegame.Utils.GameConstants.Sprite.Y_DRAW_OFFSET;
import static uk.ac.tees.mgd.a0208468.mobilegame.entities.GameCharacter.PLAYER;
import static uk.ac.tees.mgd.a0208468.mobilegame.entities.interactables.Plants.CARROT;
import static uk.ac.tees.mgd.a0208468.mobilegame.entities.interactables.Plants.EGGPLANT;
import static uk.ac.tees.mgd.a0208468.mobilegame.entities.interactables.Plants.PUMPKIN;
import static uk.ac.tees.mgd.a0208468.mobilegame.entities.interactables.Plants.TURNIP;
import static uk.ac.tees.mgd.a0208468.mobilegame.main.GameActivity.GAME_HEIGHT;
import static uk.ac.tees.mgd.a0208468.mobilegame.main.GameActivity.GAME_WIDTH;
import static uk.ac.tees.mgd.a0208468.mobilegame.ui.ButtonImages.MENU_CLOSE;
import static uk.ac.tees.mgd.a0208468.mobilegame.ui.ButtonImages.MENU_PLAY;
import static uk.ac.tees.mgd.a0208468.mobilegame.ui.GameImages.MAIN_MENU_BOARD;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.view.MotionEvent;


import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import uk.ac.tees.mgd.a0208468.mobilegame.Utils.interfaces.GameStateInterface;
import uk.ac.tees.mgd.a0208468.mobilegame.entities.Player;
import uk.ac.tees.mgd.a0208468.mobilegame.entities.interactables.Plant;
import uk.ac.tees.mgd.a0208468.mobilegame.entities.interactables.Plants;
import uk.ac.tees.mgd.a0208468.mobilegame.environments.Floor;
import uk.ac.tees.mgd.a0208468.mobilegame.environments.GameMap;
import uk.ac.tees.mgd.a0208468.mobilegame.environments.MapLayer;
import uk.ac.tees.mgd.a0208468.mobilegame.main.Game;
import uk.ac.tees.mgd.a0208468.mobilegame.ui.CustomButton;

public class Menu extends BaseState implements GameStateInterface {
    private CustomButton buttonPlay;
    private CustomButton buttonClose;
    private int menuX = 250;
    private int menuY = 50;
    private int buttonPlayX = menuX + ((MAIN_MENU_BOARD.getImage().getWidth() / 2) - (MENU_PLAY.getWidth() * (UI_SCALE_MULTIPLIER / 2)));
    private int buttonPlayY = menuY + 350;
    private int buttonCloseX = menuX + ((MAIN_MENU_BOARD.getImage().getWidth() / 2) - (MENU_CLOSE.getWidth() * (UI_SCALE_MULTIPLIER / 2)));
    private int buttonCloseY = buttonPlayY + MENU_PLAY.getHeight() + 250;
    private GameMap menuMap;
    private  int waterAnimX;
    private int waterAniTick;
    private int waterAniSpeed = 10;
    private Player player;
    public Menu(Game game){
        super(game);
        buttonPlay = new CustomButton(buttonPlayX, buttonPlayY, MENU_PLAY.getWidth(), MENU_PLAY.getHeight());
        buttonClose = new CustomButton(buttonCloseX, buttonCloseY, MENU_CLOSE.getWidth(), MENU_CLOSE.getHeight());
        this.player = new Player();
        InitialiseMap();
    }
    @Override
    public void update(double delta) {
        player.update(delta, false);
        updateAnimation(delta);
    }

    @Override
    public void render(Canvas canvas) {
        drawTiles(canvas, waterAnimX);
        drawPlants(canvas);
        canvas.drawBitmap(player.getGameCharType().getSprite(player.getFaceDir(), player.getAniIndex()),
                (GAME_WIDTH / 3) * 1.5f,
                (GAME_HEIGHT / 3) * 1.5f,
                null);
        canvas.drawBitmap(MAIN_MENU_BOARD.getImage(),
                menuX,
                menuY,
                null);

        canvas.drawBitmap(MENU_PLAY.getButtonImg(buttonPlay.isButtonPressed(), false),
                buttonPlay.getHitbox().left,
                buttonPlay.getHitbox().top,
                null);
        canvas.drawBitmap(MENU_CLOSE.getButtonImg(buttonClose.isButtonPressed(), false),
                buttonClose.getHitbox().left,
                buttonClose.getHitbox().top,
                null);
    }

    private void updateAnimation(double delta){
        waterAniTick++;

        if(waterAniTick >= waterAniSpeed){
            waterAniTick = 0;
            waterAnimX++;
            if(waterAnimX >= 4){
                waterAnimX = 0;
            }
        }
    }

    @Override
    public void touchEvents(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            if(isIn(event, buttonPlay)){
                buttonPlay.setButtonPressed(true);
            } else if (isIn(event, buttonClose)) {
                buttonClose.setButtonPressed(true);
            }
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            if(isIn(event, buttonPlay)) {
                if(buttonPlay.isButtonPressed()) {
                    game.setCurrentGameState(Game.GameState.PLAYING);
                }
            } else if (isIn(event, buttonClose)) {
                if (buttonClose.isButtonPressed()) {
                    game.setCurrentGameState(Game.GameState.CLOSE);
                }
            }
            buttonPlay.setButtonPressed(false);
            buttonClose.setButtonPressed(false);
        }
    }

    private boolean isIn(MotionEvent event, CustomButton button){
        return button.getHitbox().contains(event.getX(), event.getY());
    }

    public void drawTiles(Canvas canvas, int waterAnimX){
        for(String key : menuMap.getKeys()){
            for (int i = 0; i < menuMap.getArrayHeight(key); i++) {
                for (int j = 0; j < menuMap.getArrayWidth(key); j++) {
                    MapLayer layer = menuMap.getMapLayer(key);
                    if(j > 0 && i > 0){
                        if(key.contains("Water")){
                            Bitmap draw = layer.getFloorType().getSprite(menuMap.getAnimSpriteId(layer, j, i, waterAnimX));
                            canvas.drawBitmap(draw, (j * TILE_SIZE) - (TILE_SIZE * 2), (i * TILE_SIZE) - (TILE_SIZE * 2), null);
                        } else{
                            canvas.drawBitmap(layer.getFloorType().getSprite(menuMap.getSpriteId(layer, j, i)), (j * TILE_SIZE) - (TILE_SIZE * 2), (i * TILE_SIZE) - (TILE_SIZE * 2), null);
                        } // (j * TILE_SIZE) - (TILE_SIZE * 2), (i * TILE_SIZE) - (TILE_SIZE * 2)
                    }
                }
            }
        }
    }

    public void drawPlants(Canvas canvas){
        List<Plant> plants = new ArrayList<>();
        Plant pumpkin = new Plant(new PointF(GAME_WIDTH/6 * 4 - PUMPKIN.getImage(3).getWidth(), GAME_HEIGHT/4 * 3 - (PUMPKIN.getImage(3).getWidth() * 1.5f)), PUMPKIN);
        pumpkin.setStage(3);
        Plant eggplant = new Plant(new PointF(GAME_WIDTH/6 * 3 + (EGGPLANT.getImage(3).getWidth() * 1.5f), GAME_HEIGHT/4 * 2 - (EGGPLANT.getImage(3).getWidth())), EGGPLANT);
        eggplant.setStage(2);
        Plant carrot = new Plant(new PointF(GAME_WIDTH/6 * 5 - CARROT.getImage(3).getWidth(), GAME_HEIGHT/4 * 3 - (CARROT.getImage(3).getWidth() * 2.5f)), CARROT);
        carrot.setStage(3);
        Plant turnip = new Plant(new PointF(GAME_WIDTH/6 * 3 + TURNIP.getImage(3).getWidth() * 4f, GAME_HEIGHT/4 * 3 - (TURNIP.getImage(3).getWidth() * 1.5f)), TURNIP);
        turnip.setStage(1);
        plants.add(pumpkin);
        plants.add(eggplant);
        plants.add(carrot);
        plants.add(turnip);

        for(Plant plant : plants){
            canvas.drawBitmap(plant.getPlantType().getImage(plant.getStage()), plant.getHitbox().left, plant.getHitbox().top, null );
        }
    }

    public void InitialiseMap(){
        // Smaller Grid Map for Menu cover
        int[][] menuWaterSpriteIds = {
                { 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},
                { 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},
                { 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},
                { 0,  0,  0,  0,  0,  4,  4,  4,  4,  4,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},
                { 0,  0,  0,  0,  0,  4,  4,  4,  4,  4,  0,  4,  4,  4,  4,  4,  0,  0,  0,  0,  0},
                { 0,  0,  0,  0,  0,  4,  4,  4,  4,  4,  0,  4,  4,  4,  4,  4,  4,  4,  4,  0,  0},
                { 0,  0,  0,  0,  0,  4,  4,  4,  4,  4,  0,  4,  4,  4,  4,  4,  4,  4,  4,  0,  0},
                { 0,  0,  0,  0,  0,  4,  4,  4,  4,  4,  0,  4,  4,  4,  4,  4,  4,  4,  4,  0,  0},
                { 0,  0,  0,  0,  0,  0,  0,  4,  4,  4,  0,  4,  4,  4,  4,  4,  4,  4,  4,  0,  0},
                { 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},
                { 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0}
        };

        int[][] menuHillSpriteIds = {
                {10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10},
                {10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10},
                {10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10},
                {10, 10, 10, 10, 10, 10, 10,  0,  1,  1,  1,  1,  1,  1,  1,  1,  2, 10, 10, 10, 10},
                {10, 10, 10, 10, 10, 10, 10, 11, 10, 10, 12, 16, 23, 23, 23, 17, 27,  1,  1,  2, 10},
                {10, 10, 10, 10, 10, 10, 10, 11, 10, 10, 12, 13, 10, 10, 10, 22, 23, 23, 17, 13, 10},
                {10, 10, 10, 10, 10, 10, 10, 11, 10, 10, 12, 13, 10, 10, 10, 10, 10, 10, 11, 13, 10},
                {10, 10, 10, 10, 10, 10, 10, 11, 10, 10, 12, 13, 10, 10, 10, 10, 10, 10, 11, 13, 10},
                {10, 10, 10, 10, 10, 10, 10, 11, 10, 10, 12, 27,  1,  1,  1,  1,  1,  1, 28, 13, 10},
                {10, 10, 10, 10, 10, 10, 10, 22, 23, 23, 23, 23, 23, 23, 23, 23, 23, 23, 23, 24, 10},
                {10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10}
        };

        int[][] menuDirtSpriteIds = {
                {10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10},
                {10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10,  2, 10, 10, 10, 10},
                {10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10},
                {10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10},
                {10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 12, 12, 12, 12, 12, 27,  1,  1,  2, 10},
                {10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 12, 12, 12, 12, 12, 12, 12, 12, 13, 10},
                {10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 12, 12, 12, 12, 12, 12, 12, 12, 13, 10},
                {10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 12, 12, 12, 12, 12, 12, 12, 12, 13, 10},
                {10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 12, 12, 12, 12, 12, 12, 12, 12, 13, 10},
                {10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 23, 23, 23, 23, 23, 23, 23, 23, 24, 10},
                {10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10}
        };

        MapLayer menuWater = new MapLayer(menuWaterSpriteIds, Floor.WATER);
        MapLayer menuDirt = new MapLayer(menuDirtSpriteIds, Floor.DIRT);
        MapLayer menuHill = new MapLayer(menuHillSpriteIds, Floor.HILL);
        Map<String, MapLayer> menuLayers = new LinkedHashMap<>();
        menuLayers.put("Water", menuWater);
        menuLayers.put("Dirt", menuDirt);
        menuLayers.put("Hill", menuHill);
        menuMap = new GameMap(menuLayers);
    }

}
