package uk.ac.tees.mgd.a0208468.mobilegame.entities;

import android.graphics.PointF;
import android.graphics.RectF;

public abstract class Entity {
    protected RectF hitbox;
    public Entity(){

    }
    public Entity(PointF pos, float width, float height){
        this.hitbox = new RectF(pos.x, pos.y, pos.x + width, pos.y + height);

    }
    public RectF getHitbox(){
        return hitbox;
    }

}
