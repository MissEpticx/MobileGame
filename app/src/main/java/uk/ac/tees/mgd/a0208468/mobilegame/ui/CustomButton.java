package uk.ac.tees.mgd.a0208468.mobilegame.ui;

import android.graphics.RectF;

public class CustomButton {
    private final RectF hitbox;
    private boolean buttonPressed;
    public CustomButton(float x, float y, float width, float height){
        hitbox = new RectF(x, y, x + width, y + height);

    }

    public RectF getHitbox(){
        return hitbox;
    }

    public boolean isButtonPressed(){
        return buttonPressed;
    }

    public void setButtonPressed(boolean buttonPressed){
        this.buttonPressed = buttonPressed;
    }
}
