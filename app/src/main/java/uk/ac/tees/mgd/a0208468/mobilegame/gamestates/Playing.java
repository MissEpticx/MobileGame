package uk.ac.tees.mgd.a0208468.mobilegame.gamestates;

import static uk.ac.tees.mgd.a0208468.mobilegame.Utils.GameConstants.Sprite.DEFAULT_CHAR_SIZE;
import static uk.ac.tees.mgd.a0208468.mobilegame.Utils.GameConstants.Sprite.TILE_SIZE;
import static uk.ac.tees.mgd.a0208468.mobilegame.Utils.GameConstants.Sprite.X_DRAW_OFFSET;
import static uk.ac.tees.mgd.a0208468.mobilegame.Utils.GameConstants.Sprite.Y_DRAW_OFFSET;
import static uk.ac.tees.mgd.a0208468.mobilegame.Utils.GameConstants.Weather.IS_RAINING;
import static uk.ac.tees.mgd.a0208468.mobilegame.Utils.GameConstants.Weather.RAIN_FRAME;
import static uk.ac.tees.mgd.a0208468.mobilegame.Utils.GameConstants.Weather.RAIN_QUICK_FRAME;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.view.MotionEvent;

import java.util.ConcurrentModificationException;

import uk.ac.tees.mgd.a0208468.mobilegame.Utils.GameConstants;
import uk.ac.tees.mgd.a0208468.mobilegame.Utils.interfaces.GameStateInterface;
import uk.ac.tees.mgd.a0208468.mobilegame.entities.Player;
import uk.ac.tees.mgd.a0208468.mobilegame.entities.interactables.InteractablesManager;
import uk.ac.tees.mgd.a0208468.mobilegame.environments.MapManager;
import uk.ac.tees.mgd.a0208468.mobilegame.main.Game;
import uk.ac.tees.mgd.a0208468.mobilegame.ui.PlayingUI;

public class Playing extends BaseState implements GameStateInterface {
    private MapManager mapManager;
    private InteractablesManager interactManager;
    private Player player;
    private  int waterAnimX;
    private int waterAniTick, rainAniTick, rainQuickAniTick;
    private int waterAniSpeed = 5, rainAniSpeed = 2, rainQuickAniSpeed = 0;
    private boolean movePlayer;
    private float cameraX, cameraY;
    private PointF lastTouchDiff;
    private Paint paint = new Paint();
    private PlayingUI playingUI;

    public Playing(Game game){
        super(game);
        mapManager = new MapManager(this);
        player = new Player();
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);
        interactManager = new InteractablesManager(this, mapManager, player);
        playingUI = new PlayingUI(this, player);
    }

    @Override
    public void update(double delta) {
        updatePlayerMove(delta);
        player.update(delta, movePlayer);
        mapManager.setCameraValues(cameraX, cameraY);
        interactManager.setCameraValues(cameraX, cameraY);
        updateAnimation(delta);
        updatePlants(delta);
    }

    @Override
    public void render(Canvas canvas) {
        mapManager.draw(canvas, waterAnimX);
        try{
            interactManager.draw(canvas);
        } catch(ConcurrentModificationException e){
            System.out.println("ConcurrentModificationException thrown");
        }

        drawPlayer(canvas);
        if(IS_RAINING){
            mapManager.drawRain(canvas);
        }
        playingUI.draw(canvas);
    }

    @Override
    public void touchEvents(MotionEvent event) {
        playingUI.touchEvents(event, interactManager);
    }

    private void drawPlayer(Canvas canvas){
        canvas.drawBitmap(player.getGameCharType().getSprite(player.getFaceDir(), player.getAniIndex()),
                player.getHitbox().left - (TILE_SIZE + DEFAULT_CHAR_SIZE/2 - X_DRAW_OFFSET),
                player.getHitbox().top - (TILE_SIZE + DEFAULT_CHAR_SIZE/2 + Y_DRAW_OFFSET),
                null);

//        canvas.drawRect(player.getHitbox(), paint);
    }

    public void updatePlayerMove(double delta){
        if (!movePlayer){
            return;
        }

        float baseSpeed = (float) delta * 300;
        float ratio = Math.abs(lastTouchDiff.y / Math.abs(lastTouchDiff.x));
        double angle = Math.atan(ratio);

        float xSpeed = (float) Math.cos(angle);
        float ySpeed = (float) Math.sin(angle);

        if (ySpeed > xSpeed){
            if(lastTouchDiff.y > 0){
                player.setFaceDir(GameConstants.FaceDir.WALK_DOWN);
            } else{
                player.setFaceDir(GameConstants.FaceDir.WALK_UP);
            }
        } else{
            if (lastTouchDiff.x > 0){
                player.setFaceDir(GameConstants.FaceDir.WALK_RIGHT);
            } else{
                player.setFaceDir(GameConstants.FaceDir.WALK_LEFT);
            }
        }

        if (lastTouchDiff.x < 0){
            xSpeed *= -1;
        }
        if (lastTouchDiff.y < 0){
            ySpeed *= -1;
        }

        float deltaX = xSpeed * baseSpeed * -1;
        float deltaY = ySpeed * baseSpeed * -1;

        float cameraDeltaX = cameraX * -1 + deltaX * -1;
        float cameraDeltaY = cameraY * -1 + deltaY * -1;


        if(mapManager.canWalkHere(player.getHitbox(), cameraDeltaX, cameraDeltaY)){
            cameraX += deltaX;
            cameraY += deltaY;
        }
    }

    public void setGameStateToMenu(){
        game.setCurrentGameState(Game.GameState.MENU);
    }

    public  void setPlayerMoveTrue(PointF lastTouchDiff){
        movePlayer = true;
        this.lastTouchDiff = lastTouchDiff;
    }

    public void setPlayerMoveFalse(){
        movePlayer = false;
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

        rainAniTick++;

        if(rainAniTick >= rainAniSpeed){
            rainAniTick = 0;
            RAIN_FRAME++;
            if(RAIN_FRAME >= 8){
                RAIN_FRAME = 0;
            }
        }

        rainQuickAniTick++;

        if(rainQuickAniTick >= rainQuickAniSpeed){
            rainQuickAniTick = 0;
            RAIN_QUICK_FRAME++;
            if(RAIN_QUICK_FRAME >= 8){
                RAIN_QUICK_FRAME = 0;
            }
        }
    }

    private void updatePlants(double delta){
        try{
            interactManager.updatePlants(delta);
        } catch (ConcurrentModificationException e){
            System.out.println("Concurrent Modification Exception thrown");
        }
    }
}