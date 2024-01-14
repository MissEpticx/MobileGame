package uk.ac.tees.mgd.a0208468.mobilegame.gamestates;

import static uk.ac.tees.mgd.a0208468.mobilegame.Utils.GameConstants.Sprite.DEFAULT_CHAR_SIZE;
import static uk.ac.tees.mgd.a0208468.mobilegame.Utils.GameConstants.Sprite.TILE_SIZE;

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

    //UI
    private Paint circlePaint;
    private float xCentre = (MainActivity.GAME_WIDTH / 12) * 10, yCentre = (MainActivity.GAME_HEIGHT / 8) * 6, radius = 100;
    private float xTouch, yTouch;
    private boolean touchDown;

    public Playing(Game game){
        super(game);
        mapManager = new MapManager();
        player = new Player();
        paint.setColor(Color.BLUE);
        circlePaint = new Paint();
        circlePaint.setStyle(Paint.Style.STROKE);
        circlePaint.setStrokeWidth(6);
        circlePaint.setColor(Color.RED);
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
        drawUI(canvas);
        drawPlayer(canvas);
    }

    @Override
    public void touchEvents(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN :
                //  Using Trigonometry to determine if touch down is within the joystick circle or not.
                float x = event.getX();
                float y = event.getY();

                float a = Math.abs(x - xCentre);
                float b = Math.abs(y - yCentre);
                float c = (float) Math.hypot(a, b);

                if(c <= radius){
                    touchDown = true;
                    xTouch = x;
                    yTouch = y;
                } else{
                    touchDown = false;
                    game.setCurrentGameState(Game.GameState.MENU);
                }
                break;
            case MotionEvent.ACTION_MOVE :
                if(touchDown){
                    xTouch = event.getX();
                    yTouch = event.getY();
                    float xDiff = xTouch - xCentre;
                    float yDiff = yTouch - yCentre;
                    setPlayerMoveTrue(new PointF(xDiff, yDiff));
                }
                break;
            case MotionEvent.ACTION_UP :
                touchDown = false;
                setPlayerMoveFalse();
                break;
        }
    }

    private void drawPlayer(Canvas canvas){
        canvas.drawBitmap(player.getGameCharType().getSprite(player.getFaceDir(), player.getAniIndex()),
                player.getHitbox().left - (TILE_SIZE + DEFAULT_CHAR_SIZE),
                player.getHitbox().top - (TILE_SIZE + DEFAULT_CHAR_SIZE),
                null);
    }

    private void drawUI(Canvas canvas){
        canvas.drawCircle(xCentre, yCentre, radius, circlePaint);
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
