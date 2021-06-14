package ch.hepia.covid_manager;

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
import java.util.LinkedHashMap;
import java.time.LocalDate;

public class Countries{

    private List < Country > countries = new ArrayList < > ();
    private LinkedHashMap < LocalDate, int[] > worldHistory = new LinkedHashMap < > ();

    public Countries() {}

    public Countries(List < Country > listOfCountries) {
        this.countries = listOfCountries;
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
            if (c.name().equals(name)) {
                return c;
            }
        }
        throw new RuntimeException("no country named " + name);
    }

    public List < Country > listOfCountries() {
        return this.countries;
    }

    public int totalCases(){
        return this.countries.stream()
                             .mapToInt(c -> c.playerTotalCases())
                             .sum();
    }

    public int totalActive(){
        return this.countries.stream()
                             .mapToInt(c -> c.playerTotalActive())
                             .sum();
    }

    public int totalRecovered(){
        return this.countries.stream()
                             .mapToInt(c -> c.playerTotalRecovered())
                             .sum();
    }

    public int totalDeaths(){
        return this.countries.stream()
                             .mapToInt(c -> c.playerTotalDeaths())
                             .sum();
    }

    public int totalCured(){
        return this.countries.stream()
                             .mapToInt(c -> c.playerTotalCured())
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

    public int totalDailyRecovered(){
        return this.countries.stream()
                             .mapToInt(c -> c.playerDailyRecovered())
                             .sum();    
    }

    public int totalDailyCured(){
        return this.countries.stream()
                             .mapToInt(c -> c.playerDailyCured())
                             .sum();    
    }

    // return the amount of points won by the player at the end of the day
    // depends on the new cases, the new deaths and the minimum to earn points
    public int getTotalDailyPoints(){
        return (this.totalDailyActive() + this.totalDailyDeaths()) / 5000000;
    }
    
    // return a list of coordinates for "num" country(s) as they will be used to
    // place a reward circle that will be earned by the player
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

    // update fields for each country at the end of the day
    public void elapseDayForAllCountries(){
        if(this.totalCases() > 500000){
            Cure.getInstance().updateCure();
            System.out.println(Cure.getInstance().progression());
        }
        System.out.println(this.totalCases());
        this.countries.forEach(c->c.elapseDay());
        //System.out.println(this.totalCases());
    }

    // global data for the world fields for each new day 
    // (store actives, deaths, recovered and cured that will be displayed on the left side bar)
    public void updateWorldHistory(LocalDate date){
        this.worldHistory.put(date, new int[] {this.totalActive(), this.totalDeaths(), this.totalRecovered(), this.totalCured()});
    }

    public LinkedHashMap<LocalDate, int[]> worldHistory(){
        return this.worldHistory;
    }

    // return a map that contains two circles for each country (one for the color and one for the outline)
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

    public List<Integer> listOfCasesByDay(){
        List<Integer> res = new ArrayList<>();
        for (Map.Entry<LocalDate, int[]> entry : this.worldHistory.entrySet()) {
            res.add(entry.getValue()[0]);
        }
        return res;
    }

    public List<Integer> listOfDeathsByDay(){
        List<Integer> res = new ArrayList<>();
        for (Map.Entry<LocalDate, int[]> entry : this.worldHistory.entrySet()) {
            res.add(entry.getValue()[1]);
        }
        return res;
    }

    public List<Integer> listOfRecoveredByDay(){
        List<Integer> res = new ArrayList<>();
        for (Map.Entry<LocalDate, int[]> entry : this.worldHistory.entrySet()) {
            res.add(entry.getValue()[2]);
        }
        return res;
    }

    public List<Integer> listOfCuredByDay(){
        List<Integer> res = new ArrayList<>();
        for (Map.Entry<LocalDate, int[]> entry : this.worldHistory.entrySet()) {
            res.add(entry.getValue()[3]);
        }
        return res;
    }
}