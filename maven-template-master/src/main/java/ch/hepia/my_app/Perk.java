package ch.hepia.my_app;

// classe bidon à mettre dans la bdd plus tard

public class Perk {
    private String name;
    private String description;
    private boolean status;
    private double[] infectivityByLevel = new double[3];
    private double[] lethalityByLevel = new double[3];
    private double[] resistanceByLevel = new double[3];
    // peut être des coordonnées, des images...


    public Perk(String name, String description){
        this.name = name;
        this.description = description;
        this.status = false;
    }

    public void updateInfectivity(double valueRank1, double valueRank2, double valueRank3){
        this.infectivityByLevel[0] = valueRank1;
        this.infectivityByLevel[1] = valueRank2;
        this.infectivityByLevel[2] = valueRank3;
    }

    public void updateLethality(double valueRank1, double valueRank2, double valueRank3){
        this.lethalityByLevel[0] = valueRank1;
        this.lethalityByLevel[1] = valueRank2;
        this.lethalityByLevel[2] = valueRank3;
    }

    public void updateResistance(double valueRank1, double valueRank2, double valueRank3){
        this.resistanceByLevel[0] = valueRank1;
        this.resistanceByLevel[1] = valueRank2;
        this.resistanceByLevel[2] = valueRank3;
    }

    public boolean status(){
        return this.status;
    }

    public String description(){
        return this.description;
    }

    public String name(){
        return this.name;
    }
}