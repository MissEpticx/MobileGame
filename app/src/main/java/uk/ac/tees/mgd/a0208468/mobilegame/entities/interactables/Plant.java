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
    private float growTimer;
    public Plant(PointF pos, Plants plantType){
        super(pos, plantType.getWidth() * TILE_SIZE, plantType.getHeight() * TILE_SIZE);
        this.plantType = plantType;
        this.stage = 0;
        this.growTimer = 0f;
    }

    public void updatePlantStage(double delta){
        growTimer++;
        if(growTimer + delta >= 500f && stage == 0){
            setStage(1);
        } else if (growTimer + delta >= 1500f && stage == 1) {
            setStage(2);
        } else if (growTimer + delta >= 3000f && stage == 2) {
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
