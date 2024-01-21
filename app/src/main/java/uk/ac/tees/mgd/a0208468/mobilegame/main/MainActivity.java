package uk.ac.tees.mgd.a0208468.mobilegame.main;

import static uk.ac.tees.mgd.a0208468.mobilegame.Utils.GameConstants.Weather.API_KEY;
import static uk.ac.tees.mgd.a0208468.mobilegame.Utils.GameConstants.Weather.IS_RAINING;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.Iterator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.ac.tees.mgd.a0208468.mobilegame.Utils.weather.WeatherAPIClient;
import uk.ac.tees.mgd.a0208468.mobilegame.Utils.weather.WeatherData;
import uk.ac.tees.mgd.a0208468.mobilegame.Utils.interfaces.OpenWeatherMapService;

public class MainActivity extends AppCompatActivity {

    private static Context gameContext;
    public static int GAME_WIDTH, GAME_HEIGHT;

    // Google location services
    private FusedLocationProviderClient fusedLocationProviderClient;
    private static final int REQUEST_CODE = 101;
    private double latitude, longitude;
    private String cityName;
    private OpenWeatherMapService openWeatherMapService;


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

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        getLocation();
    }

    private void getLocation(){
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED &&  ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
            return;
        }

        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location != null){
//                    currentLocation = location;
                    latitude = location.getLatitude();
                    longitude = location.getLongitude();
                    System.out.println("Current lat: " + latitude + " || long: " + longitude);
                    openWeatherMapService = WeatherAPIClient.getInstance().create(OpenWeatherMapService.class);
                    loadWeatherInfo();
                } else{
                    System.out.println("No Location Found!");
                }
            }
        });
    }

    private void reverseGeocode(){
        Geocoder geo = new Geocoder(this);
        String locationInfo;
        try{
            Iterator<Address> addresses = geo.getFromLocation(latitude, longitude, 3).iterator();
            if(addresses != null){
                while (addresses.hasNext()){
                    Address namedLocation = addresses.next();

                    if(namedLocation.getLocality() != null){
                        cityName = namedLocation.getLocality();
                    } else if (namedLocation.getSubLocality() != null) {
                        cityName = namedLocation.getSubLocality();
                    } else if (namedLocation.getSubAdminArea() != null) {
                        cityName = namedLocation.getSubAdminArea();
                    } else{
                        cityName = namedLocation.getAdminArea();
                    }

                    // Remove later
                    String adminArea = namedLocation.getAdminArea();
                    String subAdminArea = namedLocation.getSubAdminArea();
                    String cityName = namedLocation.getLocality();
                    String subLocality = namedLocation.getSubLocality();

                    locationInfo = String.format("[%s][%s][%s][%s]", adminArea, subAdminArea, cityName, subLocality);
                }
            }
        } catch (IOException e){
            Log.e("GPS", "Failed to get address", e);
        }

    }

    private void loadWeatherInfo(){
        openWeatherMapService.getCurrentWeather(latitude, longitude, API_KEY).enqueue(new Callback<WeatherData>() {
            // WeatherData class created to parse Json information to a usable object (contains objects within to match the API fields
            // that I desire to collect and hold from OpenWeatherMap.org)
            @Override
            public void onResponse(Call<WeatherData> call, Response<WeatherData> response) {
                if(response.isSuccessful()){
                    System.out.println("Response: " + response.body());
                    WeatherData data = response.body();
                    System.out.println("Weather Data: ");
                    System.out.println("Base: " + data.getBase());
                    System.out.println("id: " + data.getWeather().get(0).getId());
                    System.out.println("main: " + data.getWeather().get(0).getMain());
                    System.out.println("description: " + data.getWeather().get(0).getDescription());
                    System.out.println("icon: " + data.getWeather().get(0).getIcon());
                    if(data.getRain() != null){
                        IS_RAINING = true;
                        System.out.println("Is Raining: " + IS_RAINING);
                        System.out.println("rain: " + data.getRain().getHour());
                        System.out.println("rain 3h: " + data.getRain().getThreeHours());
                    } else{
                        IS_RAINING = false;
                        System.out.println("Is Raining: " + IS_RAINING);
                    }
                } else{
                    System.out.println("Failed to obtain weather information");
                }
            }

            @Override
            public void onFailure(Call<WeatherData> call, Throwable t) {
                System.out.println("Failed to obtain weather information");
            }
        });
    }

    public static Context getGameContext(){
        return  gameContext;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == REQUEST_CODE){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                getLocation();
            }
        }
    }
}