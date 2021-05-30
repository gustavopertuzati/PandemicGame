package ch.hepia.my_app;

public class PerkLethality extends Perk{

    private double lethality;

    public PerkLethality(int id, String name, String description, double v1, int cost){
        super(id, name, description, cost);
        this.lethality = v1;
    }

    @Override
    public void update(Virus v){
        v.applyInfectivityBonus(this.lethality);
    }
}