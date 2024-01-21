package uk.ac.tees.mgd.a0208468.mobilegame.entities;

import static uk.ac.tees.mgd.a0208468.mobilegame.Utils.GameConstants.Sprite.DEFAULT_CHAR_SIZE;
import static uk.ac.tees.mgd.a0208468.mobilegame.main.GameActivity.GAME_HEIGHT;
import static uk.ac.tees.mgd.a0208468.mobilegame.main.GameActivity.GAME_WIDTH;
import static uk.ac.tees.mgd.a0208468.mobilegame.Utils.GameConstants.Animation.CHAR_SPEED;

import android.graphics.PointF;

import uk.ac.tees.mgd.a0208468.mobilegame.Utils.GameConstants;

public class Player extends Character{
    private int level;
    private int xp;
    private int maxXp;
    public Player(){
        super(new PointF(GAME_WIDTH/2 - DEFAULT_CHAR_SIZE/2, GAME_HEIGHT/2 + DEFAULT_CHAR_SIZE/2), GameCharacter.PLAYER);
        level = 0;
        xp = 0;
        maxXp = 120;
    }

    public void update(double delta, boolean movePlayer){
        if(!movePlayer) {
            CHAR_SPEED = 7;
            switch (faceDir) {
                case GameConstants.FaceDir.WALK_RIGHT:
                    setFaceDir(GameConstants.FaceDir.IDLE_RIGHT);
                    break;
                case GameConstants.FaceDir.WALK_LEFT:
                    setFaceDir(GameConstants.FaceDir.IDLE_LEFT);
                    break;
                case GameConstants.FaceDir.WALK_UP:
                    setFaceDir(GameConstants.FaceDir.IDLE_UP);
                    break;
                case GameConstants.FaceDir.WALK_DOWN:
                    setFaceDir(GameConstants.FaceDir.IDLE_DOWN);
                    break;
            }
        } else{
            CHAR_SPEED = 4;
        }

        updateAnimation(delta);
    }

    public void addXP(int addedXp){
        System.out.println("XP: " + xp + " || Added XP: " + addedXp + " || Max XP: " + maxXp + "|| Level: " + level);
        if(xp + addedXp < maxXp){
            xp += addedXp;
        } else if (xp + addedXp == maxXp) {
            if(level != 3){
                xp = 0;
                level ++;
            }
        } else{
            int remainingXp = (xp + addedXp) - maxXp;
            if(level != 3){
                xp = remainingXp;
                level ++;
            }
        }
        System.out.println("XP: " + xp + " || Max XP: " + maxXp + " || Level: " + level);
    }

    public int getXP(){
        return xp;
    }

    public int getLevel(){
        return level;
    }

    public int getMaxXp(){
        return maxXp;
    }

    public void setMaxXp(int xp){
        maxXp = xp;
    }

}
