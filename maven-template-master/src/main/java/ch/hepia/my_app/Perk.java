package ch.hepia.my_app;

// classe bidon à mettre dans la bdd plus tard

public abstract class Perk {
    private int id;
    private String name;
    private String description;
    private int currentLevel;
    // peut être des coordonnées, des images...


    public Perk(int id, String name, String description){
        this.id = id;
        this.name = name;
        this.description = description;
        this.currentLevel = 0;
    }

    public abstract void update(Virus v);

    public int currentLevel(){
        return this.currentLevel;
    }

    public void updateLevel(){
        this.currentLevel += 1;
    }

    public String description(){
        return this.description;
    }

    public String name(){
        return this.name;
    }

    public int id(){
        return this.id;
    }
}