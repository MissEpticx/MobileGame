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
        public static final int DEFAULT_CHAR_SIZE = 48;
        public static final int DEFAULT_TILE_SIZE = 16;
        public static final int SCALE_MULTIPLIER = 8;
        public static final int TILE_SIZE = DEFAULT_TILE_SIZE * SCALE_MULTIPLIER;
    }

}
