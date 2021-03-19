package ch.hepia.my_app;
import javafx.scene.paint.Color;

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
    private int totalPopulation;

    public Country(String countryName, int latitude, int longitude, int totalCases, int dailyCases, int totalDeaths, int dailyDeaths, int totalRecovered, int dailyRecovered, int size, int totalPopulation){
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
        this.totalPopulation = totalPopulation;
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

    public int totalPopulation(){
        //return this.totalPopulation;
        return 67000000;
    }

    @Override
    public String toString(){
        return this.countryName + ": (" + this.latitude + ":" + this.longitude + ")" +   
        "\n\tcases: " + this.totalCases + " (+" + this.dailyCases + ")" + 
        "\n\tdeaths: " + this.totalDeaths + " (+" + this.dailyDeaths + ")" + 
        "\n\trecovered: " + this.totalRecovered + " (+" + this.dailyRecovered + ")\n"; 
    }

    // modèle SIR
    public Color getColorFromCountry(){

        double durationOfInfectivity = 7.0;
        /* durée d'incubation du virus (entre 2 et 14 jours):
        en moyenne -> 5*/

        double dailyContacts = 20;
        /* nombre de personne croisées par jour :
        aucunes mesures prises -> 30
        couvre feux -> 15
        confinement -> 5 */

        double transmissionRate = 0.025;
        /* taux de transmission du virus (fixe et dépend du virus) */

        double sanitaryMeasures = 0.5;
        /* facteur des mesures sanitaires. Il dépend :
        de la distance sociale,
        des barrières médicales (masques + gels),
        du nombre de personnes téstées (donc on part du principe qu'elles s'isolent si elles ont le covid),
        des transits inter-communautés (sortie limitée à un périmètre, frontières closes, avions/trains diminués...)*/

        int popInfectible = this.totalPopulation() - this.totalCases();
        /* population pouvant encore attraper le virus 
        on part du principe qu'une personne guérie / morte ne peut pas re-avoir le virus */
        
        // FAUX CAR ON SE BASE UNIQUEMENT SUR LE NOMBRE DE PERSONNE QU'IL RESTE A INFECTER
        // ON DEVRAIT AUSSI SE BASER SUR LE NOMBRE DE PERSONNE QUI SONT NOUVELLEMENT TOUCHEES ET LES MORTS / RECOVERY
        double probability =  transmissionRate * ((double)popInfectible/(double)this.totalPopulation()) * (1 - sanitaryMeasures);


        double R0 = probability * dailyContacts * durationOfInfectivity;
        System.out.println("R0: " + R0);

        // pourcentage de la population qui doit se faire vacciner pour que l'épidémie soit sure de s'arreter
        //double persistance = 1 - (1 / R0); 

        if(R0 < 1.0){
            return Color.GREEN;
        } else if(R0 < 2.5){
            return Color.ORANGE;
        } else {
            return Color.RED;
        }
    }
}
