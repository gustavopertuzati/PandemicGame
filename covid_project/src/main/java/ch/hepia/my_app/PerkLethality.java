package ch.hepia.covid_manager;

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

    @Override
    public String toString(){
        return this.name() + ":\n  " + this.description() + "\n  +" + this.lethality + " of lethality\n  cost: " + this.cost() + " points";
    }
}