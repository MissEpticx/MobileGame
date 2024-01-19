package uk.ac.tees.mgd.a0208468.mobilegame.Utils.weather;

import java.util.List;

public class WeatherData {
    private String base;
    private List<Weather> weather;
    private Rain rain;

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public List<Weather> getWeather() {
        return weather;
    }

    public void setWeather(List<Weather> weather) {
        this.weather = weather;
    }

    public Rain getRain() {
        return rain;
    }

    public void setRain(Rain rain) {
        this.rain = rain;
    }
}

