package uk.ac.tees.mgd.a0208468.mobilegame.Utils.weather;

public class Weather {
    private String id;
    private String main;
    private String description;
    private String icon;

    Weather(String id, String main, String description, String icon){
        this.icon = icon;
        this.description = description;
        this.main = main;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
