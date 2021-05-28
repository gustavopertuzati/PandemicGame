package ch.hepia.my_app;

public class PerkResistance extends Perk{

    private double[] resistanceByLevel = new double[3];

    public PerkResistance(int id, String name, String description, double v1, double v2, double v3, int cost){
        super(id, name, description, cost);
        this.resistanceByLevel[0] = v1;
        this.resistanceByLevel[1] = v2;
        this.resistanceByLevel[2] = v3;
    }

    @Override
    public void update(Virus v){
        v.applyInfectivityBonus(resistanceByLevel[this.currentLevel()]);
        this.updateLevel();
    }
}