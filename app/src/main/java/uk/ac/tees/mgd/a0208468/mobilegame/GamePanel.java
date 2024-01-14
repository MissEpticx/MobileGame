package uk.ac.tees.mgd.a0208468.mobilegame;

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

import java.util.Random;

import uk.ac.tees.mgd.a0208468.mobilegame.Utils.GameConstants;
import uk.ac.tees.mgd.a0208468.mobilegame.entities.Player;
import uk.ac.tees.mgd.a0208468.mobilegame.environments.MapManager;
import uk.ac.tees.mgd.a0208468.mobilegame.inputs.TouchEvents;

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback{

    private GameLoop gameLoop;
    private TouchEvents touchEvents;
    private Paint paint = new Paint();
    private SurfaceHolder holder;
    private Random rand = new Random();
    private  int waterAnimX;
    private int waterAniTick;
    private int waterAniSpeed = 4;
    private PointF lastTouchDiff;
    private boolean movePlayer;
    private float cameraX, cameraY;
    private MapManager mapManager;

    private Player player;

    public GamePanel(Context context) {
        super(context);
        holder = getHolder();
        holder.addCallback(this);
        paint.setColor(Color.BLUE);
        touchEvents = new TouchEvents(this);
        gameLoop = new GameLoop(this);
        mapManager = new MapManager();
        player = new Player();
    }

    public void render(){
        Canvas canvas = holder.lockCanvas();
        canvas.drawColor(Color.BLACK);
        mapManager.drawWater(canvas, waterAnimX);
        mapManager.draw(canvas);
        touchEvents.draw(canvas);
        drawPlayer(canvas);

        holder.unlockCanvasAndPost(canvas);
    }

    private void drawPlayer(Canvas canvas){
        canvas.drawBitmap(player.getGameCharType().getSprite(player.getFaceDir(), player.getAniIndex()),
                player.getHitbox().left - (TILE_SIZE + DEFAULT_CHAR_SIZE),
                player.getHitbox().top - (TILE_SIZE + DEFAULT_CHAR_SIZE),
                null);
    }

    public void update(double delta){
        updatePlayerMove(delta);
        player.update(delta, movePlayer);
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

        if(mapManager.canWalkHere(player.getHitbox().left + (cameraX * -1) + (deltaX * -1) + pWidth, player.getHitbox().top + (cameraY * -1) + (deltaY * -1) + pHeight)){
            cameraX += deltaX;
            cameraY += deltaY;
        }
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
