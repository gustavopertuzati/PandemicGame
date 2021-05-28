package ch.hepia.my_app;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import javafx.scene.paint.Color;

import java.lang.Math;
import javafx.scene.control.*;
import java.util.Optional;

public class Perks{

    private List < Perk > perks = new ArrayList < > ();

    public Perks() {}

    public Perks(List < Perk > listOfPerks) {
        perks = listOfPerks;
    }

    public void addPerk(Perk p) {
        this.perks.add(p);
    }

    public Optional<Perk> getPerkById(int id) {
        for (Perk p: this.perks) {
            if (p.id() == id) {
                return Optional.of(p);
            }
        }
        return Optional.empty();
    }

    public List < Perk > listOfPerks() {
        return this.perks;
    }

    public LinkedHashMap < Button, Perk > buttonsPerksMap() {
        LinkedHashMap< Button, Perk> perksMap = new LinkedHashMap<>();
        for(Perk p: this.perks){
            perksMap.put(new Button(p.name()), p);
        }
        return perksMap;
    }

    // méthode à remplacer par la bdd
    public void init(){
        // INFECTIFIVITY
        
    }
}