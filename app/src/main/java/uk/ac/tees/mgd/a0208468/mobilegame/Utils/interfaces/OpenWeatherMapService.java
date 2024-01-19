package uk.ac.tees.mgd.a0208468.mobilegame.Utils.interfaces;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import uk.ac.tees.mgd.a0208468.mobilegame.Utils.weather.WeatherData;

public interface OpenWeatherMapService {
    @GET("weather")
    Call<WeatherData> getCurrentWeather(@Query("lat") double latitude, @Query("lon") double longitude, @Query("appid") String apiKey);
}
