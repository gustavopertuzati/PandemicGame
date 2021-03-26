package ch.hepia.my_app;
import javafx.scene.paint.Color;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class Country {
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
        int dailyDeaths, int totalRecovered, int dailyRecovered, int size, int totalPopulation) {
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

    // cas journaliers + cas totaux

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


    // morts journalières + morts totales

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


    // rétablissements journaliers + rétablissement totaux

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


    // cas actifs journaliers + totaux  

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

    public String slug() {
        return this.slug;
    }

    public String countryName() {
        return this.slug;
    }

    public int[] coordinates() {
        int[] coordinates = new int[2];

        coordinates[0] = this.latitude;
        coordinates[1] = this.longitude;

        return coordinates;
    }


    // données globales au joueur et à la "réalité"

    public int size() {
        return this.size;
    }

    public int totalPopulation() {
        return this.totalPopulation;
    }

    @Override
    public String toString() {
        return this.countryName + ": (" + this.latitude + ":" + this.longitude + ")" +
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
}