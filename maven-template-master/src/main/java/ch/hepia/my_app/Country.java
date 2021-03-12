package ch.hepia.my_app;

public class Country {

    private String countryName;
    private Double latitude;
    private Double longitude;
    private int totalCases;
    private int dailyCases;
    private int totalDeaths;
    private int dailyDeaths;
    private int totalRecovered;
    private int dailyRecovered;

    public Country(String countryName, Double latitude, Double longitude, int totalCases, int dailyCases, int totalDeaths, int dailyDeaths, int totalRecovered, int dailyRecovered){
        this.countryName = countryName;
        this.latitude = latitude;
        this.longitude = longitude;
        this.totalCases = totalCases;
        this.dailyCases = dailyCases;
        this.totalDeaths = totalDeaths;
        this.dailyDeaths = dailyDeaths;
        this.totalRecovered = totalRecovered;
        this.dailyRecovered = dailyRecovered;
    }

    @Override
    public String toString(){
        return this.countryName + "(" + this.latitude + "," + this.longitude + ")" + ":"  + this.totalCases+  "\n";
    }
}
