package ch.hepia.covid_manager;

// classe bidon à mettre dans la bdd plus tard

public abstract class Perk {
    private int id;
    private String name;
    private String description;
    private int costToUnlock;
    // peut être des coordonnées, des images...

    public static Perk perkFactory(int id, String name, String description, int costToUnlock, double val, String type){
        switch(type){
            case "infecicty":
                return new PerkInfectivity(id, name, description, val, costToUnlock);
            case "resistance":
                return new PerkResistance(id, name, description, val, costToUnlock);
            case "lethatlity":
                return new PerkLethality(id, name, description, val, costToUnlock);
            default:
                throw new RuntimeException("Type is invalid!!");
        }
    }

    public Perk(int id, String name, String description, int costToUnlock){
        this.id = id;
        this.name = name;
        this.description = description;
        this.costToUnlock = costToUnlock;
    }

    public abstract void update(Virus v);

    public String description(){
        return this.description;
    }

    public String name(){
        return this.name;
    }

    public int id(){
        return this.id;
    }

    public int cost(){
        return this.costToUnlock;
    }



}