package ch.hepia.my_app;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.paint.Color;
import java.lang.Math;

import java.util.Optional;

public class Countries {

    private List < Country > countries = new ArrayList < > ();

    public Countries() {}

    public Countries(List < Country > listOfCountries) {
        countries = listOfCountries;
    }

    public void addCountry(Country c) {
        this.countries.add(c);
    }

    public Optional<Country> getCountryByCoordinates(double x, double y) {
        for (Country c: this.countries) {
            if ((Math.abs(c.coordinates()[0] - x) <= c.size() * 6.5) && (Math.abs(c.coordinates()[1] - y) <= c.size() * 6.5)) {
                return Optional.of(c);
            }
        }
        return Optional.empty();
    }

    public Country getCountryDataByName(String name) {
        for (Country c: this.countries) {
            if (c.countryName().equals(name)) {
                return c;
            }
        }
        throw new RuntimeException("no country named " + name);
    }

    public List < Country > listOfCountries() {
        return this.countries;
    }

    public int totalActive(){
        return this.countries.stream()
                             .mapToInt(c -> c.playerTotalActive())
                             .sum();
    }

    public int totalDeaths(){
        return this.countries.stream()
                             .mapToInt(c -> c.playerTotalDeaths())
                             .sum();
    }

    public int totalPop(){
        return this.countries.stream()
                                .mapToInt(c -> c.totalPopulation())
                                .sum();
    }

    public int totalDailyActive(){
        return this.countries.stream()
                                .mapToInt(c -> c.playerDailyCases())
                                .sum();
    }

    public int totalDailyDeaths(){
        return this.countries.stream()
                                .mapToInt(c -> c.playerDailyDeaths())
                                .sum();        
    }
}