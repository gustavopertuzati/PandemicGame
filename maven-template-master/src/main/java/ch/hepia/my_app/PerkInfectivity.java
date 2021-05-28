package ch.hepia.my_app;

public class PerkInfectivity extends Perk{

    private double[] infectivityByLevel = new double[3];

    public PerkInfectivity(int id, String name, String description, double v1, double v2, double v3){
        super(id, name, description);
        this.infectivityByLevel[0] = v1;
        this.infectivityByLevel[1] = v2;
        this.infectivityByLevel[2] = v3;
    }

    @Override
    public void update(Virus v){
        v.applyInfectivityBonus(infectivityByLevel[this.currentLevel()]);
        this.updateLevel();
    }
}