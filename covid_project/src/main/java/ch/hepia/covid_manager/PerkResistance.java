package ch.hepia.covid_manager;

public class PerkResistance extends Perk{

    private double resistance;

    public PerkResistance(int id, String name, String description, double v1, int cost){
        super(id, name, description, cost);
        this.resistance = v1;
    }

    @Override
    public void update(Virus v){
        v.applyInfectivityBonus(this.resistance);
    }

    @Override
    public String toString(){
        return this.name() + ":\n  " + this.description() + "\n  +" + this.resistance + " of resistance\n  cost: " + this.cost() + " points";
    }
}