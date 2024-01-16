package uk.ac.tees.mgd.a0208468.mobilegame.ui;

import static uk.ac.tees.mgd.a0208468.mobilegame.Utils.GameConstants.Sprite.UI_SCALE_MULTIPLIER;

import android.graphics.RectF;

public class CustomButton {
    private final RectF hitbox;
    private boolean buttonPressed = false;
    public CustomButton(float x, float y, float width, float height){
        float scaledWidth = width * UI_SCALE_MULTIPLIER;
        float scaledHeight = height * UI_SCALE_MULTIPLIER;
        hitbox = new RectF(x, y, x + scaledWidth, y + scaledHeight);
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
