package uk.ac.tees.mgd.a0208468.mobilegame.Utils.interfaces;

import android.graphics.Canvas;
import android.hardware.SensorEvent;
import android.view.MotionEvent;

public interface GameStateInterface {

    void update(double delta);
    void render(Canvas canvas);
    void touchEvents(MotionEvent event);
}
