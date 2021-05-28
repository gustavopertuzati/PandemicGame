package ch.hepia.my_app;

public class PerkLethality extends Perk{

    private double[] lethalityByLevel = new double[3];

    public PerkLethality(int id, String name, String description, double v1, double v2, double v3, int cost){
        super(id, name, description, cost);
        this.lethalityByLevel[0] = v1;
        this.lethalityByLevel[1] = v2;
        this.lethalityByLevel[2] = v3;
    }

    @Override
    public void update(Virus v){
        v.applyInfectivityBonus(lethalityByLevel[this.currentLevel()]);
        this.updateLevel();
    }
}