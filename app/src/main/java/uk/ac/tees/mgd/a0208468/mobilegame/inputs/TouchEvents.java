package uk.ac.tees.mgd.a0208468.mobilegame.inputs;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.view.MotionEvent;

import uk.ac.tees.mgd.a0208468.mobilegame.GamePanel;
import uk.ac.tees.mgd.a0208468.mobilegame.MainActivity;

public class TouchEvents {
    private GamePanel gamePanel;
    private float xCentre = (MainActivity.GAME_WIDTH / 12) * 10, yCentre = (MainActivity.GAME_HEIGHT / 8) * 6, radius = 100;
    private Paint circlePaint;
    private Paint yellowPaint;
    private float xTouch, yTouch;
    private boolean touchDown;

    public TouchEvents(GamePanel gamePanel){
        this.gamePanel = gamePanel;
        circlePaint = new Paint();
        circlePaint.setStyle(Paint.Style.STROKE);
        circlePaint.setStrokeWidth(6);
        circlePaint.setColor(Color.RED);
        yellowPaint = new Paint();
        yellowPaint.setColor(Color.YELLOW);
    }

    public  void draw(Canvas c){
        c.drawCircle(xCentre, yCentre, radius, circlePaint);

        if(touchDown){
            c.drawLine(xCentre, yCentre, xTouch, yTouch, yellowPaint);
            c.drawLine(xCentre, yCentre, xTouch, yCentre, yellowPaint);
            c.drawLine(xTouch, yTouch, xTouch, yCentre, yellowPaint);
        }
    }

    public boolean touchEvent(MotionEvent event){
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
                }
                break;
            case MotionEvent.ACTION_MOVE :
                if(touchDown){
                    xTouch = event.getX();
                    yTouch = event.getY();
                    float xDiff = xTouch - xCentre;
                    float yDiff = yTouch - yCentre;
                    gamePanel.setPlayerMoveTrue(new PointF(xDiff, yDiff));
                }
                break;
            case MotionEvent.ACTION_UP :
                touchDown = false;
                gamePanel.setPlayerMoveFalse();
                break;
        }
        return true;
    }
}
