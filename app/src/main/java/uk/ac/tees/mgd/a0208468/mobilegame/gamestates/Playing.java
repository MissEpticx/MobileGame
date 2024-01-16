package uk.ac.tees.mgd.a0208468.mobilegame.gamestates;

import static uk.ac.tees.mgd.a0208468.mobilegame.Utils.GameConstants.Sprite.DEFAULT_CHAR_SIZE;
import static uk.ac.tees.mgd.a0208468.mobilegame.Utils.GameConstants.Sprite.TILE_SIZE;
import static uk.ac.tees.mgd.a0208468.mobilegame.Utils.GameConstants.Sprite.X_DRAW_OFFSET;
import static uk.ac.tees.mgd.a0208468.mobilegame.Utils.GameConstants.Sprite.Y_DRAW_OFFSET;
import static uk.ac.tees.mgd.a0208468.mobilegame.ui.ButtonImages.PLAYING_HOME;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.view.MotionEvent;

import uk.ac.tees.mgd.a0208468.mobilegame.Utils.GameConstants;
import uk.ac.tees.mgd.a0208468.mobilegame.Utils.interfaces.GameStateInterface;
import uk.ac.tees.mgd.a0208468.mobilegame.entities.Player;
import uk.ac.tees.mgd.a0208468.mobilegame.environments.MapManager;
import uk.ac.tees.mgd.a0208468.mobilegame.main.Game;
import uk.ac.tees.mgd.a0208468.mobilegame.main.MainActivity;
import uk.ac.tees.mgd.a0208468.mobilegame.ui.CustomButton;
import uk.ac.tees.mgd.a0208468.mobilegame.ui.PlayingUI;

public class Playing extends BaseState implements GameStateInterface {
    //Game
    private MapManager mapManager;
    private Player player;
    private  int waterAnimX;
    private int waterAniTick;
    private int waterAniSpeed = 4;
    private boolean movePlayer;
    private float cameraX, cameraY;
    private PointF lastTouchDiff;
    private Paint paint = new Paint();
    private PlayingUI playingUI;

    public Playing(Game game){
        super(game);
        mapManager = new MapManager();
        player = new Player();
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);
        playingUI = new PlayingUI(this);
    }
    @Override
    public void update(double delta) {
        updatePlayerMove(delta);
        player.update(delta, movePlayer);
        mapManager.setCameraValues(cameraX, cameraY);
        updateAnimation(delta);
    }

    @Override
    public void render(Canvas canvas) {
        mapManager.drawWater(canvas, waterAnimX);
        mapManager.draw(canvas);

        drawPlayer(canvas);
        playingUI.draw(canvas);
    }

    @Override
    public void touchEvents(MotionEvent event) {
        playingUI.touchEvents(event);
    }

    private void drawPlayer(Canvas canvas){
        canvas.drawBitmap(player.getGameCharType().getSprite(player.getFaceDir(), player.getAniIndex()),
                player.getHitbox().left - (TILE_SIZE + DEFAULT_CHAR_SIZE/2 - X_DRAW_OFFSET),
                player.getHitbox().top - (TILE_SIZE + DEFAULT_CHAR_SIZE/2 + Y_DRAW_OFFSET),
                null);

        canvas.drawRect(player.getHitbox(), paint);
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

//        int pWidth = TILE_SIZE;
//        int pHeight = TILE_SIZE;
//
//        if(xSpeed <= 0){
//            pWidth = 0;
//        }
//        if(ySpeed <= 0){
//            pHeight = 0;
//        }

        float deltaX = xSpeed * baseSpeed * -1;
        float deltaY = ySpeed * baseSpeed * -1;

        float cameraDeltaX = cameraX * -1 + deltaX * -1;
        float cameraDeltaY = cameraY * -1 + deltaY * -1;

        if(mapManager.canWalkHere(player.getHitbox(), cameraDeltaX, cameraDeltaY)){
            cameraX += deltaX;
            cameraY += deltaY;
        }
//        if(mapManager.canWalkHere(player.getHitbox().left + (cameraX * -1) + (deltaX * -1) + pWidth, player.getHitbox().top + (cameraY * -1) + (deltaY * -1) + pHeight)){
//            cameraX += deltaX;
//            cameraY += deltaY;
//        }
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
    }
}
