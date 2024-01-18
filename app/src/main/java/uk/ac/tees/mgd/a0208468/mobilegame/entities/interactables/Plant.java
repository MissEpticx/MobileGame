package uk.ac.tees.mgd.a0208468.mobilegame.entities.interactables;

import static uk.ac.tees.mgd.a0208468.mobilegame.Utils.GameConstants.Sprite.DEFAULT_TILE_SIZE;
import static uk.ac.tees.mgd.a0208468.mobilegame.Utils.GameConstants.Sprite.SCALE_MULTIPLIER;
import static uk.ac.tees.mgd.a0208468.mobilegame.Utils.GameConstants.Sprite.TILE_SIZE;

import android.graphics.PointF;
import android.graphics.RectF;

import uk.ac.tees.mgd.a0208468.mobilegame.entities.Entity;
import uk.ac.tees.mgd.a0208468.mobilegame.entities.decorations.Decorations;

public class Plant extends Entity {
    private Plants plantType;
    private int stage;
    private float growTimer;
    public Plant(PointF pos, Plants plantType){
        super(pos, plantType.getWidth() * SCALE_MULTIPLIER, plantType.getHeight() * SCALE_MULTIPLIER);
        this.plantType = plantType;
        this.stage = 0;
        this.growTimer = 0f;
    }

    public void updatePlantStage(double delta){
        growTimer++;
        if(growTimer + delta >= 400f && stage == 0){
            setStage(1);
        } else if (growTimer + delta >= 1200f && stage == 1) {
            setStage(2);
        } else if (growTimer + delta >= 2500f && stage == 2) {
            setStage(3);
        }
    }

    public Plants getPlantType(){
        return plantType;
    }

    public void setStage(int stage) {
        this.stage = stage;
    }

    public int getStage() {
        return stage;
    }
}
