package ch.hepia.my_app;

public class Country {

    private String countryName;
    private int latitude;
    private int longitude;
    private int totalCases;
    private int dailyCases;
    private int totalDeaths;
    private int dailyDeaths;
    private int totalRecovered;
    private int dailyRecovered;
    private int size;

    public Country(String countryName, int latitude, int longitude, int totalCases, int dailyCases, int totalDeaths, int dailyDeaths, int totalRecovered, int dailyRecovered, int size){
        this.countryName = countryName;
        this.latitude = latitude;
        this.longitude = longitude;
        this.totalCases = totalCases;
        this.dailyCases = dailyCases;
        this.totalDeaths = totalDeaths;
        this.dailyDeaths = dailyDeaths;
        this.totalRecovered = totalRecovered;
        this.dailyRecovered = dailyRecovered;
        this.size = size;
    }

    public String CountryName(){
        return this.countryName;
    }

    public int[] coordinates(){
        int[] coordinates = new int[2];

        coordinates[0] = this.latitude;
        coordinates[1] = this.longitude;

        return coordinates;
    }

    public int totalCases(){
        return this.totalCases;
    }

    public int dailyCases(){
        return this.dailyCases;
    }

    public int totalDeaths(){
        return this.totalDeaths;
    }

    public int dailyDeaths(){
        return this.dailyDeaths;
    }

    public int totalRecovered(){
        return this.totalRecovered;
    }

    public int dailyRecovered(){
        return this.dailyRecovered;
    }

    public int size(){
        return this.size;
    }

    @Override
    public String toString(){
        return this.countryName + ": (" + this.latitude + ":" + this.longitude + ")" +   
        "\n\tcases: " + this.totalCases + " (+" + this.dailyCases + ")" + 
        "\n\tdeaths: " + this.totalDeaths + " (+" + this.dailyDeaths + ")" + 
        "\n\trecovered: " + this.totalRecovered + " (+" + this.dailyRecovered + ")\n"; 
    }
}
