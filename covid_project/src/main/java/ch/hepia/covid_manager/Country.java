package ch.hepia.covid_manager;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Random; 

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Country {
    // données communes
    private String name;
    private String slug;
    private int latitude;
    private int longitude;
    private int size;
    private int totalPopulation;

    // données joueur
    private int totalCases;
    private int dailyCases;
    private int totalDeaths;
    private int dailyDeaths;
    private int totalRecovered;
    private int dailyRecovered;
    private int totalCured;
    private int dailyCured;
    private int totalActive;

    // données réelles
    private Map < LocalDate, Integer[] > countryHistory = new HashMap < > ();

    public Country(String name, int latitude, int longitude,
                   int totalCases, int dailyCases, 
                   int totalDeaths, int dailyDeaths, 
                   int totalRecovered, int dailyRecovered,
                   int totalCured, int dailyCured, 
                   int size, int totalPopulation, String slug){

        this.slug = slug;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.totalCases = totalCases;
        this.dailyCases = dailyCases;
        this.totalDeaths = totalDeaths;
        this.dailyDeaths = dailyDeaths;
        this.totalRecovered = totalRecovered;
        this.dailyRecovered = dailyRecovered;
        this.totalCured = totalCured;
        this.dailyCured = dailyCured;
        this.totalActive = totalCases - (totalRecovered + totalDeaths + totalCured);
        this.size = size;
        this.totalPopulation = totalPopulation;
    }


    public int playerTotalCases() {
        return this.totalCases;
    }

    public int playerDailyCases() {
        return this.dailyCases;
    }

    public int getTotalCasesByDate(LocalDate date) {
        if (this.countryHistory.keySet().contains(date)) {
            return this.countryHistory.get(date)[0];
        } else {
            // Should never be reached
            throw new RuntimeException("Date is not in the country's history");
        }
    }

    public int getDailyCasesByDate(LocalDate date) {
        return this.getTotalCasesByDate(date) - this.getTotalCasesByDate(date.minusDays(1));
    }

    public int playerTotalDeaths() {
        return this.totalDeaths;
    }

    public int playerDailyDeaths() {
        return this.dailyDeaths;
    }

    public int getTotalDeathsByDate(LocalDate date) {
        if (this.countryHistory.keySet().contains(date)) {
            return this.countryHistory.get(date)[1];
        } else {
            // Should never be reached
            throw new RuntimeException("Date is not in the country's history");
        }
    }

    public int getDailyDeathsByDate(LocalDate date) {
        return this.getTotalDeathsByDate(date) - this.getTotalDeathsByDate(date.minusDays(1));
    }

    public int playerTotalRecovered() {
        return this.totalRecovered;
    }

    public int playerDailyRecovered() {
        return this.dailyRecovered;
    }

    public int getTotalRecoveredByDate(LocalDate date) {
        if (this.countryHistory.keySet().contains(date)) {
            return this.countryHistory.get(date)[2];
        } else {
            // Should never be reached
            throw new RuntimeException("Date is not in the country's history");
        }
    }

    public int getDailyRecoveredByDate(LocalDate date) {
        return this.getTotalRecoveredByDate(date) - this.getTotalRecoveredByDate(date.minusDays(1));
    }

    public int playerTotalCured() {
        return this.totalCured;
    }

    public int playerDailyCured() {
        return this.dailyCured;
    }

    public int getTotalCuredByDate(LocalDate date) {
        if (this.countryHistory.keySet().contains(date)) {
            return this.countryHistory.get(date)[3];
        } else {
            // Should never be reached
            throw new RuntimeException("Date is not in the country's history");
        }
    }

    public int getDailyCuredByDate(LocalDate date) {
        return this.getTotalCuredByDate(date) - this.getTotalCuredByDate(date.minusDays(1));
    }
 
    public int playerTotalActive() {
        return this.totalActive;
    }

    public int getTotalActiveByDate(LocalDate date) {
        if (this.countryHistory.keySet().contains(date)) {
            return this.countryHistory.get(date)[3];
        } else {
            // Should never be reached
            throw new RuntimeException("Date is not in the country's history");
        }
    }

    public int size() {
        return this.size;
    }

    public int totalPopulation() {
        return this.totalPopulation;
    }
    
    public String slug() {
        return this.slug;
    }

    public String name() {
        return this.name;
    }

    public int[] coordinates() {
        return new int[] {this.latitude, this.longitude};
    }

    @Override
    public String toString() {
        return this.name + ":" +
            "\n\tcases: " + this.totalCases + " (+" + this.dailyCases + ")" +
            "\n\tactive: " + this.totalActive +
            "\n\tdeaths: " + this.totalDeaths + " (+" + this.dailyDeaths + ")" +
            "\n\trecovered: " + this.totalRecovered + " (+" + this.dailyRecovered + ")\n";
    }

    // display the data for a specific date (took from the api)
    public String realData(LocalDate date){
        return "Real data for the " + date.toString() + ":" +
            "\n\tcases: " + this.getTotalCasesByDate(date) + " (+" + this.getDailyCasesByDate(date) + ")" +
            "\n\tactive: " + this.getTotalActiveByDate(date) +
            "\n\tdeaths: " + this.getTotalDeathsByDate(date) + " (+" + this.getDailyDeathsByDate(date) + ")" +
            "\n\trecovered: " + this.getTotalRecoveredByDate(date) + " (+" + this.getDailyRecoveredByDate(date) + ")\n";
    }

    // update the color to show the current situatio of the country
    public Color getColorFromCountry() {
        double ratio = (double)this.playerTotalActive() / (double)this.totalPopulation();
        ratio += (double)this.playerDailyDeaths() / (double)this.totalPopulation();
        if (ratio <0.0015){
            return Color.GREEN;
        } else if (ratio < 0.0065){
            return Color.ORANGE;
        } else {
            return Color.RED;
        }
    }

    public double getCircleWidth() {
        double ratio = (double)this.playerTotalActive() / (double)this.totalPopulation();
        ratio += (double)this.playerDailyDeaths() / (double)this.totalPopulation();
        if (ratio <0.0015){
            return 15;
        } else if (ratio < 0.0065){
            return 20;
        } else {
            return 25;
        }
    }

    // call once to get history of the real situation for the country
    public void updateCountryHistory() {
        //Use API to read history
        if(this.countryHistory.keySet().size() == 0){
            APICountryManager ap = new APICountryManager("https://api.covid19api.com");
            try{
                this.countryHistory = ap.getCountryHistory(this).get();
            }catch(Exception e){
                throw new RuntimeException(e);
            }
        }
    }

    public Map<LocalDate, Integer[]> getCountryHistory(){
        return this.countryHistory;
    }

    // update country's data from the real ones at a specific date
    public void goToDate(LocalDate date){
        Integer[] data = this.countryHistory.get(date);
        this.totalActive =  this.getTotalActiveByDate(date);
        this.totalCases =  this.getTotalCasesByDate(date);
        this.totalDeaths =  this.getTotalDeathsByDate(date);
    }

    private void newCases(){
        //This depends on the measures taken by the country ( i.e. confinement or remote work etc...)
        int nbPpl = 12; 
        //Chance to infect depends on whether or not masks are enforced etc...
        double chanceToInfect = Virus.getInstance().infectivity();

        int newCases = (int) Math.round(this.totalActive*nbPpl*chanceToInfect)+2;

        if (newCases > this.totalPopulation - this.totalDeaths - this.totalActive - this.totalRecovered){
            this.dailyCases = newCases;
            this.totalActive += newCases;
        }else{
            //Player has infected every infectible person
        }
    }

    private void newRecoveries(){
        Random rand = new Random();
        //This should be about a 5% recovery everytime this method is called
        double randDouble =  rand.nextGaussian()*0.0001*(1-Virus.getInstance().resistance());
        int newRecov = (int)randDouble * this.totalActive+2;

        this.totalActive -= newRecov;
        this.totalRecovered += newRecov;
    }

    private void newDeaths(){
        Random rand = new Random();
        //This should be about a .5% death rate everytime this method is called
        double randDouble =  rand.nextGaussian()*Virus.getInstance().lethality() ;
        int newDead = (int)randDouble * this.totalActive+2;

        this.totalActive -= newDead;
        this.totalDeaths += newDead;
    }

    private void newCured(){
        System.out.println("State updates not implemented yet");
    }

    private void updateState(){
        System.out.println("State updates not implemented yet");
    }

    public void elapseDay(){
        this.newRecoveries();
        this.newCases();
        this.newDeaths();
        this.newCured();
    }

    public Circle[] getCountryCircles() {
        return new Circle[] {
            new Circle(this.latitude, this.longitude, this.getCircleWidth() + 2 , Color.BLACK ),
            new Circle(this.latitude, this.longitude, this.getCircleWidth() , this.getColorFromCountry())
        };
    }
}