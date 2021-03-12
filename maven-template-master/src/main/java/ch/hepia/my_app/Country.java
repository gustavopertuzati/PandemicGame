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

    public String CountryName(){
        return this.countryName;
    }

    public double[] Coordinates(){
        double[] coordinates = new double[2];

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

    @Override
    public String toString(){
        return this.countryName + ": \n\tcases: " + this.totalCases + " (+" + this.dailyCases + ")" + 
                                  "\n\tdeaths: " + this.totalDeaths + " (+" + this.dailyDeaths + ")" + 
                                  "\n\trecovered: " + this.totalRecovered + " (+" + this.dailyRecovered + ")\n"; 
    }
}
