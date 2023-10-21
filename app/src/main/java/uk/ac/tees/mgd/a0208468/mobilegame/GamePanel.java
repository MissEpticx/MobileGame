package uk.ac.tees.mgd.a0208468.mobilegame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Random;

import uk.ac.tees.mgd.a0208468.mobilegame.entities.GameCharacters;

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback{

    private GameLoop gameLoop;
    private Paint paint = new Paint();
    private SurfaceHolder holder;
    private Random rand = new Random();
    private float x, y;


    public GamePanel(Context context) {
        super(context);
        holder = getHolder();
        holder.addCallback(this);
        paint.setColor(Color.BLUE);
        gameLoop = new GameLoop(this);
    }

    public void render(){
        Canvas canvas = holder.lockCanvas();
        canvas.drawColor(Color.BLACK);
        canvas.drawBitmap(GameCharacters.PLAYER.getSprite(14, 0), x, y, null);

        holder.unlockCanvasAndPost(canvas);
    }

    public void update(double delta){

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            x = event.getX() - (24 * 8); //half of sprite size, multiplied by sprite scaleSize
            y = event.getY() - (24 * 8);
        }

        return true;
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
}
