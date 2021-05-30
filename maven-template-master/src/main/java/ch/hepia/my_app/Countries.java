package ch.hepia.my_app;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.input.MouseEvent;




import java.lang.Math;
import java.time.LocalDate;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.Map;
import java.util.HashMap;


import java.time.LocalDate;


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

    public double totalCases(){
        return this.countries.stream()
                   .mapToDouble(c -> c.playerTotalCases())
                   .sum();
    }

    public int totalActive(){
        return this.countries.stream()
                             .mapToInt(c -> c.playerTotalActive())
                             .sum();
    }

    public double totalDeaths(){
        return this.countries.stream()
                             .mapToDouble(c -> c.playerTotalDeaths())
                             .sum();
    }

    public double totalPop(){
        return this.countries.stream()
                                .mapToDouble(c -> c.totalPopulation())
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

    public int getTotalDailyPoints(){
        return (this.totalDailyActive() + this.totalDailyDeaths()) / 1000;
    }
    

    public List<int[]> getRandomCountryCoordinates(int num){
        List<int[]> lstCoords = new ArrayList<>();
        for(int i = 0; i < num; i+=1){
            int randIndex = (int)Math.floor(Math.random()*(this.countries.size()));
            Country crt = this.countries.get(randIndex);
            int[] tmp = new int[2];
            tmp[0] = crt.coordinates()[0];
            tmp[1] = crt.coordinates()[1];
            lstCoords.add(tmp);
        }
        return lstCoords;
    }


    //Function that is called for 
    public void elapseDayForAllCountries(){
        this.countries.forEach(c->c.elapseDay());
    }



    public Map<Country, Circle[]> getCountryCirclesMap(BiConsumer<MouseEvent,Country> cons){
        Map<Country, Circle[]> map = new HashMap<>();
        this.countries.forEach(c->{
            map.put(c, c.getCountryCircles());
            map.get(c)[1].setOnMouseClicked( e ->{
                cons.accept(e, c);
            });
        });
        return map;
    }

}