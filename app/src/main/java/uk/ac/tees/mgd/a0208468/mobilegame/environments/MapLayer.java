package uk.ac.tees.mgd.a0208468.mobilegame.environments;

public class MapLayer {
    private int[][] spriteIds;
    private Floor floorType;
    public MapLayer(int[][] spriteIds, Floor floorType){
        this.spriteIds = spriteIds;
        this.floorType = floorType;
    }

    public void setFloorType(Floor floorType) {
        this.floorType = floorType;
    }

    public Floor getFloorType() {
        return floorType;
    }

    public void setSpriteIds(int[][] spriteIds) {
        this.spriteIds = spriteIds;
    }

    public int[][] getSpriteIds() {
        return spriteIds;
    }
}