package uk.ac.tees.mgd.a0208468.mobilegame.Utils.weather;

import com.google.gson.annotations.SerializedName;

public class Rain {
    @SerializedName("1h")
    private String hour;

    @SerializedName("3h")
    private String threeHours;

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getThreeHours() {
        return threeHours;
    }

    public void setThreeHours(String threeHours) {
        this.threeHours = threeHours;
    }
}
