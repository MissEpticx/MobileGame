package uk.ac.tees.mgd.a0208468.mobilegame.entities.interactables;

import static uk.ac.tees.mgd.a0208468.mobilegame.Utils.GameConstants.Sprite.SCALE_MULTIPLIER;
import static uk.ac.tees.mgd.a0208468.mobilegame.Utils.GameConstants.Sprite.TILE_SIZE;

import android.graphics.PointF;
import android.graphics.RectF;

import uk.ac.tees.mgd.a0208468.mobilegame.entities.Entity;
import uk.ac.tees.mgd.a0208468.mobilegame.entities.decorations.Decorations;

public class Plant extends Entity {
    private Plants plantType;
    private int stage;
    public Plant(PointF pos, Plants plantType){
        super(pos, plantType.getWidth() * TILE_SIZE, plantType.getHeight() * TILE_SIZE);
        this.plantType = plantType;
        this.stage = 0;
    }

    public Plants getPlantType(){
        return plantType;
    }

    public boolean isObstructed(RectF playerHitbox){//, float deltaX, float deltaY
        RectF hitbox = new RectF(getHitbox().left, // - deltaX + 10,
                getHitbox().top, // - deltaY + 10,
                getHitbox().right, // - deltaX - 10,
                getHitbox().bottom); // - deltaY - 10);

        if(playerHitbox.intersects(hitbox.left, hitbox.top, hitbox.right, hitbox.bottom)){
            return true;
        }

        return false;
    }

    public void setStage(int stage) {
        this.stage = stage;
    }

    public int getStage() {
        return stage;
    }
}
