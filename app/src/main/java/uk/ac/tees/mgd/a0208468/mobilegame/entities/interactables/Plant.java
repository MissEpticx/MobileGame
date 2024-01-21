package uk.ac.tees.mgd.a0208468.mobilegame.entities.interactables;

import static uk.ac.tees.mgd.a0208468.mobilegame.Utils.GameConstants.Sprite.DEFAULT_TILE_SIZE;
import static uk.ac.tees.mgd.a0208468.mobilegame.Utils.GameConstants.Sprite.SCALE_MULTIPLIER;
import static uk.ac.tees.mgd.a0208468.mobilegame.Utils.GameConstants.Weather.IS_RAINING;

import android.graphics.PointF;
import android.graphics.RectF;

import uk.ac.tees.mgd.a0208468.mobilegame.entities.Entity;
import uk.ac.tees.mgd.a0208468.mobilegame.entities.decorations.Decorations;

public class Plant extends Entity {
    private Plants plantType;
    private int stage;
    private float growTimer;
    private float wateringSap = 0;
    private float wateringStg1 = 0;
    private float wateringStg2 = 0;
    public Plant(PointF pos, Plants plantType){
        super(pos, plantType.getWidth() * SCALE_MULTIPLIER, plantType.getHeight() * SCALE_MULTIPLIER);
        this.plantType = plantType;
        this.stage = 0;
        this.growTimer = 0f;
    }

    public void updatePlantStage(double delta){
        growTimer++;

        // If rain is active, decrease the grow time for each stage
        if(IS_RAINING){
            System.out.println("Raining");
            wateringSap = 300f;
            wateringStg1 = 800f;
            wateringStg2 = 1500f;
        } else{
            wateringSap = 0f;
            wateringStg1 = 0f;
            wateringStg2 = 0f;
        }
        if(growTimer + delta>= (900f - wateringSap) && stage == 0){
            setStage(1);
        } else if (growTimer + delta>= (2000f - wateringStg1) && stage == 1) {
            setStage(2);
        } else if (growTimer + delta>= (3500f - wateringStg2) && stage == 2) {
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