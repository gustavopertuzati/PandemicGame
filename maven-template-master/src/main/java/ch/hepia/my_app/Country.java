package ch.hepia.my_app;
import javafx.scene.paint.Color;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Random; 


public class Country {
    // données communes
    private String countryName;
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
    private int totalActive;

    // données réelles
    private Map < LocalDate, Integer[] > countryHistory = new HashMap < > ();

    public Country(String countryName, int latitude, int longitude, int totalCases, int dailyCases, int totalDeaths,
        int dailyDeaths, int totalRecovered, int dailyRecovered, int size, int totalPopulation, String slug) {
        this.slug = countryName;
        this.latitude = latitude;
        this.longitude = longitude;
        this.totalCases = totalCases;
        this.dailyCases = dailyCases;
        this.totalDeaths = totalDeaths;
        this.dailyDeaths = dailyDeaths;
        this.totalRecovered = totalRecovered;
        this.dailyRecovered = dailyRecovered;
        this.totalActive = totalCases - (totalRecovered + totalDeaths);
        this.size = size;
        this.totalPopulation = totalPopulation;
    }

    // cas journaliers et cas totaux
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
            //En théorie devrait jamais arriver
            throw new RuntimeException("Date is not in the country's history");
        }
    }

    public int getDailyCasesByDate(LocalDate date) {
        LocalDate date2 = date.minusDays(1);
        return this.getTotalCasesByDate(date) - this.getTotalCasesByDate(date2);
    }


    // morts journalières et morts totales
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
            //En théorie devrait jamais arriver
            throw new RuntimeException("Date is not in the country's history");
        }
    }

    public int getDailyDeathsByDate(LocalDate date) {
        LocalDate date2 = date.minusDays(1);
        return this.getTotalDeathsByDate(date) - this.getTotalDeathsByDate(date2);
    }


    // rétablissements journaliers et rétablissement totaux
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
            //En théorie devrait jamais arriver
            throw new RuntimeException("Date is not in the country's history");
        }
    }

    public int getDailyRecoveredByDate(LocalDate date) {
        LocalDate date2 = date.minusDays(1);
        return this.getTotalRecoveredByDate(date) - this.getTotalRecoveredByDate(date2);
    }


    // cas actifs journaliers et totaux  
    public int playerTotalActive() {
        return this.totalActive;
    }

    public int getTotalActiveByDate(LocalDate date) {
        if (this.countryHistory.keySet().contains(date)) {
            return this.countryHistory.get(date)[3];
        } else {
            //En théorie devrait jamais arriver
            throw new RuntimeException("Date is not in the country's history");
        }
    }

    
    // données globales
    public int size() {
        return this.size;
    }

    public int totalPopulation() {
        return this.totalPopulation;
    }
    
    public String slug() {
        return this.slug;
    }

    public String countryName() {
        return this.countryName;
    }

    public int[] coordinates() {
        int[] coordinates = new int[2];

        coordinates[0] = this.latitude;
        coordinates[1] = this.longitude;

        return coordinates;
    }

    @Override
    public String toString() {
        return this.slug + ":" +
            "\n\tcases: " + this.totalCases + " (+" + this.dailyCases + ")" +
            "\n\tactive: " + this.totalActive +
            "\n\tdeaths: " + this.totalDeaths + " (+" + this.dailyDeaths + ")" +
            "\n\trecovered: " + this.totalRecovered + " (+" + this.dailyRecovered + ")\n";
    }

    public Color getColorFromCountry() {
        double ratio = (double)this.playerTotalActive() / (double)this.totalPopulation();
        ratio += (double)this.playerDailyDeaths() / (double)this.totalPopulation();
        //System.out.println(this.countryName() + ": " + ratio);
        if (ratio <0.0015){
            return Color.GREEN;
        } else if (ratio < 0.0065){
            return Color.ORANGE;
        } else {
            return Color.RED;
        }
    }


    // update de l'historique du pays
    public void updateCountryHistory() {
        //Use API to read history
        if(this.countryHistory.keySet().size() == 0){
            APICountryManager ap = new APICountryManager("https://api.covid19api.com");
            this.countryHistory = ap.getCountryHistory(this);
        }
    }

    public Map<LocalDate, Integer[]> getCountryHistory(){
        return this.countryHistory;
    }


    //Formules pour les trois méthodes new à changer en fonction des résultats
    private void newCases(){

        //This depends on the measures taken by the country ( i.e. confinement or remote work etc...)
        int nbPpl = 12; 
        //Chance to infect depends on whether or not masks are enforced etc...
        double chanceToInfect = 0.15;

        int newCases = (int) Math.pow(nbPpl*chanceToInfect, this.totalActive);

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
        double randDouble =  rand.nextGaussian()*0.1;
        int newRecov = (int)randDouble * this.totalActive;

        this.totalActive -= newRecov;
        this.totalRecovered += newRecov;
    }

    private void newDeaths(){
        Random rand = new Random();
        //This should be about a .5% death rate everytime this method is called
        double randDouble =  rand.nextGaussian()*0.5;
        int newDead = (int)randDouble * this.totalActive;

        this.totalActive -= newDead;
        this.totalDeaths += newDead;
    }

    private void updateState(){

    }

    public void updateDisease(){
        this.newRecoveries();
        this.newCases();
        this.newDeaths();

        

    }
}