package uk.ac.tees.mgd.a0208468.mobilegame.entities.decorations;

import static uk.ac.tees.mgd.a0208468.mobilegame.Utils.GameConstants.Sprite.CHAR_SIZE;
import static uk.ac.tees.mgd.a0208468.mobilegame.Utils.GameConstants.Sprite.DEFAULT_CHAR_SIZE;
import static uk.ac.tees.mgd.a0208468.mobilegame.Utils.GameConstants.Sprite.HITBOX_SIZE;
import static uk.ac.tees.mgd.a0208468.mobilegame.Utils.GameConstants.Sprite.SCALE_MULTIPLIER;
import static uk.ac.tees.mgd.a0208468.mobilegame.Utils.GameConstants.Sprite.TILE_SIZE;

import android.graphics.PointF;
import android.graphics.RectF;

import uk.ac.tees.mgd.a0208468.mobilegame.entities.Entity;
import uk.ac.tees.mgd.a0208468.mobilegame.entities.decorations.Decorations;

public class Decoration extends Entity {
    private Decorations decorationType;

    public Decoration(PointF pos, Decorations decorationType){
        super(pos, decorationType.getWidth() * SCALE_MULTIPLIER, decorationType.getHeight() * SCALE_MULTIPLIER);
        this.decorationType = decorationType;
    }

    public Decorations getDecorationType(){
        return decorationType;
    }

    public boolean hitPlayer(RectF playerHitbox, float deltaX, float deltaY){
        if(decorationType == Decorations.HOUSE || decorationType == Decorations.BOULDER){
            RectF hitbox = new RectF(getHitbox().left - deltaX + 10,
                    getHitbox().top - deltaY + 10,
                    getHitbox().right - deltaX - 10,
                    getHitbox().bottom - deltaY - 10);

            if(playerHitbox.intersects(hitbox.left, hitbox.top, hitbox.right, hitbox.bottom)){
                return true;
            }
        }
        return false;
    }
}