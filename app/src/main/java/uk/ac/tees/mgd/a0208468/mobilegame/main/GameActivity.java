package uk.ac.tees.mgd.a0208468.mobilegame.main;

import static uk.ac.tees.mgd.a0208468.mobilegame.Utils.GameConstants.Weather.IS_RAINING;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

import uk.ac.tees.mgd.a0208468.mobilegame.R;

public class GameActivity extends AppCompatActivity {
    private static Context gameContext;
    public static int GAME_WIDTH, GAME_HEIGHT;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gameContext = this;

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getRealMetrics(dm);
        GAME_WIDTH = dm.widthPixels;
        GAME_HEIGHT = dm.heightPixels;

        // Removes Navigation and Notification bars from screen, and allows you to swipe them in to see, and removes after no interaction
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY |
                View.SYSTEM_UI_FLAG_FULLSCREEN |
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        // Checks if current SDK version is level 28 or higher to check if draw space can go past front facing camera
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            getWindow().getAttributes().layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
        }

        setContentView(new GamePanel(this));
    }
    public static Context getGameContext(){
        return  gameContext;
    }
}