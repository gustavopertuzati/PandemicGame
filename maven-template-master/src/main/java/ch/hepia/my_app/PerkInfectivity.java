package ch.hepia.my_app;

public class PerkInfectivity extends Perk{

    private double infectivity;

    public PerkInfectivity(int id, String name, String description, double v1, int cost){
        super(id, name, description, cost);
        this.infectivity = v1;
    }

    @Override
    public void update(Virus v){
        v.applyInfectivityBonus(infectivity);
    }
}