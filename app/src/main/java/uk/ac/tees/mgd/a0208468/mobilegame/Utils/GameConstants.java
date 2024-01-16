package uk.ac.tees.mgd.a0208468.mobilegame.Utils;

public final class GameConstants {
    public static final class FaceDir{
        public static final int WALK_RIGHT = 6;
        public static final int WALK_LEFT = 7;
        public static final int WALK_UP = 5;
        public static final int WALK_DOWN = 4;

        public static final int IDLE_RIGHT = 3;
        public static final int IDLE_LEFT = 2;
        public static final int IDLE_UP = 1;
        public static final int IDLE_DOWN = 0;
    }

    public static final class Sprite{
        public static final int SCALE_MULTIPLIER = 8;
        public static final int DEFAULT_CHAR_SIZE = 48;
        public static final int CHAR_SIZE = DEFAULT_CHAR_SIZE * SCALE_MULTIPLIER;
        public static final int HITBOX_SIZE = 12 * SCALE_MULTIPLIER;
        public static final int DEFAULT_TILE_SIZE = 16;
        public static final int UI_SCALE_MULTIPLIER = 6;
        public static final int TILE_SIZE = DEFAULT_TILE_SIZE * SCALE_MULTIPLIER;
        public static final int X_DRAW_OFFSET = 1 * SCALE_MULTIPLIER;
        public static final int Y_DRAW_OFFSET = 1 * SCALE_MULTIPLIER;
    }

    public static final class Animation{}
    public static int CHAR_SPEED = 2;
    public static final int WATER_SPEED = 4;
    public static final int AMOUNT = 8;
}
