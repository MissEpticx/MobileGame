package uk.ac.tees.mgd.a0208468.mobilegame.entities;

import static uk.ac.tees.mgd.a0208468.mobilegame.Utils.GameConstants.AMOUNT;
import static uk.ac.tees.mgd.a0208468.mobilegame.Utils.GameConstants.CHAR_SPEED;

import android.graphics.PointF;

import uk.ac.tees.mgd.a0208468.mobilegame.Utils.GameConstants;

public abstract class Character extends Entity{
    protected int aniTick, aniIndex;
    protected int faceDir = GameConstants.FaceDir.IDLE_DOWN;
    protected final GameCharacter gameCharType;
    public Character(PointF pos, GameCharacter gameCharType){
        super(pos, 1, 1);
        this.gameCharType = gameCharType;
    }

    protected void updateAnimation(double delta){
        aniTick++;

        if(aniTick >= CHAR_SPEED){
            aniTick = 0;
            aniIndex++;
            if(aniIndex >= AMOUNT){
                aniIndex = 0;
            }
        }
    }

    public int getAniIndex() {
        return aniIndex;
    }

    public int getFaceDir() {
        return faceDir;
    }

    public void setFaceDir(int faceDir) {
        this.faceDir = faceDir;
    }

    public GameCharacter getGameCharType() {
        return gameCharType;
    }
}
