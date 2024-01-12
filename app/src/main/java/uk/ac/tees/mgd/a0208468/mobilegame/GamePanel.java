package uk.ac.tees.mgd.a0208468.mobilegame;

import static uk.ac.tees.mgd.a0208468.mobilegame.MainActivity.GAME_HEIGHT;
import static uk.ac.tees.mgd.a0208468.mobilegame.MainActivity.GAME_WIDTH;
import static uk.ac.tees.mgd.a0208468.mobilegame.Utils.GameConstants.Sprite.DEFAULT_CHAR_SIZE;
import static uk.ac.tees.mgd.a0208468.mobilegame.Utils.GameConstants.Sprite.TILE_SIZE;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import java.util.Arrays;
import java.util.Random;

import uk.ac.tees.mgd.a0208468.mobilegame.Utils.GameConstants;
import uk.ac.tees.mgd.a0208468.mobilegame.entities.GameCharacter;
import uk.ac.tees.mgd.a0208468.mobilegame.environments.GameMap;
import uk.ac.tees.mgd.a0208468.mobilegame.environments.MapManager;
import uk.ac.tees.mgd.a0208468.mobilegame.inputs.TouchEvents;

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback{

    private GameLoop gameLoop;
    private TouchEvents touchEvents;
    private Paint paint = new Paint();
    private SurfaceHolder holder;
    private Random rand = new Random();
    private float playerX = GAME_WIDTH/2, playerY = GAME_HEIGHT/2;
    private int playerAnimX, playerFaceDir = GameConstants.FaceDir.WALK_DOWN;
    private int aniTick;
    private int aniSpeed = 6;
    private PointF lastTouchDiff;
    private boolean movePlayer;
    private float cameraX, cameraY;
    
    // Map Test
    private MapManager mapManager;

    public GamePanel(Context context) {
        super(context);
        holder = getHolder();
        holder.addCallback(this);
        paint.setColor(Color.BLUE);
        touchEvents = new TouchEvents(this);
        gameLoop = new GameLoop(this);
        mapManager = new MapManager();
    }

    public void render(){
        Canvas canvas = holder.lockCanvas();
        canvas.drawColor(Color.BLACK);
        mapManager.draw(canvas);

        touchEvents.draw(canvas);

        canvas.drawBitmap(GameCharacter.PLAYER.getSprite(playerFaceDir, playerAnimX), playerX-(TILE_SIZE + DEFAULT_CHAR_SIZE), playerY-(TILE_SIZE + DEFAULT_CHAR_SIZE), null);

        holder.unlockCanvasAndPost(canvas);
    }

    public void update(double delta){
        updatePlayerMove(delta);

        mapManager.setCameraValues(cameraX, cameraY);


        updateAnimation(delta);



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
                if(!movePlayer){
                    playerFaceDir = GameConstants.FaceDir.IDLE_DOWN;
                } else {
                    playerFaceDir = GameConstants.FaceDir.WALK_DOWN;
                }
            } else{
                playerFaceDir = GameConstants.FaceDir.WALK_UP;
            }
        } else{
            if (lastTouchDiff.x > 0){
                playerFaceDir = GameConstants.FaceDir.WALK_RIGHT;
            } else{
                playerFaceDir = GameConstants.FaceDir.WALK_LEFT;
            }
        }

        if (lastTouchDiff.x < 0){
            xSpeed *= -1;
        }
        if (lastTouchDiff.y < 0){
            ySpeed *= -1;
        }

        int pWidth = TILE_SIZE;
        int pHeight = TILE_SIZE;

        if(xSpeed <= 0){
            pWidth = 0;
        }
        if(ySpeed <= 0){
            pHeight = 0;
        }

        float deltaX = xSpeed * baseSpeed * -1;
        float deltaY = ySpeed * baseSpeed * -1;

        if(mapManager.canWalkHere(playerX + (cameraX * -1) + (deltaX * -1) + pWidth, playerY + (cameraY * -1) + (deltaY * -1) + pHeight)){
            cameraX += deltaX;
            cameraY += deltaY;
        }
    }

    private void updateAnimation(double delta){
        aniTick++;
        if(!movePlayer){
            switch (playerFaceDir){
                case GameConstants.FaceDir.WALK_RIGHT:
                    playerFaceDir = GameConstants.FaceDir.IDLE_RIGHT;
                    break;
                case GameConstants.FaceDir.WALK_LEFT:
                    playerFaceDir = GameConstants.FaceDir.IDLE_LEFT;
                    break;
                case GameConstants.FaceDir.WALK_UP:
                    playerFaceDir = GameConstants.FaceDir.IDLE_UP;
                    break;
                case GameConstants.FaceDir.WALK_DOWN:
                    playerFaceDir = GameConstants.FaceDir.IDLE_DOWN;
                    break;
            }
            if(aniTick >= aniSpeed){
                aniTick = 0;
                playerAnimX++;
                if(playerAnimX >= 8) {
                    playerAnimX = 0;
                }
            }
        }

        if(aniTick >= aniSpeed){
            aniTick = 0;
            playerAnimX++;
            if(playerAnimX >= 8){
                playerAnimX = 0;
            }
        }
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return touchEvents.touchEvent(event);
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        gameLoop.startGameLoop();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {

    }

    public  void setPlayerMoveTrue(PointF lastTouchDiff){
        movePlayer = true;
        this.lastTouchDiff = lastTouchDiff;
    }

    public void setPlayerMoveFalse(){
        movePlayer = false;
    }


}
