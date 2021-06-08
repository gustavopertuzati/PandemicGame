package ch.hepia.covid_manager;

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
            Button b = new Button(p.name());
            b.setPrefWidth(120);
            b.setPrefHeight(60);
            perksMap.put(b, p);
        }
        return perksMap;
    }

    // méthode à remplacer par la bdd
    public void init(){
        this.addPerk(new PerkInfectivity(0, "Blood I", "kek", 1, 1));
        this.addPerk(new PerkInfectivity(1, "Blood II", "kek", 2, 5));
        this.addPerk(new PerkInfectivity(2, "Blood III", "kek", 3, 10));
        
        this.addPerk(new PerkInfectivity(3, "Animals I", "kek", 1, 1));
        this.addPerk(new PerkInfectivity(4, "Animals II", "kek", 2, 5));
        this.addPerk(new PerkInfectivity(5, "Animals III", "kek", 3, 10));

        this.addPerk(new PerkInfectivity(6, "Air I", "kek", 1, 1));
        this.addPerk(new PerkInfectivity(7, "Air II", "kek", 2, 5));
        this.addPerk(new PerkInfectivity(8, "Air III", "kek", 3, 10));

        this.addPerk(new PerkInfectivity(9, "Water I", "kek", 1, 1));
        this.addPerk(new PerkInfectivity(10, "Water II", "kek", 2, 5));
        this.addPerk(new PerkInfectivity(11, "Water III", "kek", 3, 10));

        // INFECTIFIVITY
        this.addPerk(new PerkLethality(12, "Nausea I", "kek", 1, 1));
        this.addPerk(new PerkLethality(13, "Nausea II", "kek", 2, 5));
        this.addPerk(new PerkLethality(14, "Nausea III", "kek", 3, 10));
        
        this.addPerk(new PerkLethality(15, "Cough I", "kek", 1, 1));
        this.addPerk(new PerkLethality(16, "Cough II", "kek", 2, 5));
        this.addPerk(new PerkLethality(17, "Cough III", "kek", 3, 10));

        this.addPerk(new PerkLethality(18, "Anemia I", "kek", 1, 1));
        this.addPerk(new PerkLethality(19, "Anemia II", "kek", 2, 5));
        this.addPerk(new PerkLethality(20, "Anemia III", "kek", 3, 10));

        this.addPerk(new PerkLethality(21, "Insomnia I", "kek", 1, 1));
        this.addPerk(new PerkLethality(22, "Insomnia II", "kek", 2, 5));
        this.addPerk(new PerkLethality(23, "Insomnia III", "kek", 3, 10));

        // RESITANCE
        this.addPerk(new PerkResistance(24, "Bacterial resistance I", "kek", 1, 1));
        this.addPerk(new PerkResistance(25, "Bacterial resistance II", "kek", 2, 5));
        this.addPerk(new PerkResistance(26, "Bacterial resistance III", "kek", 3, 10));
        
        this.addPerk(new PerkResistance(27, "Drug resistance I", "kek", 1, 1));
        this.addPerk(new PerkResistance(28, "Drug resistance II", "kek", 2, 5));
        this.addPerk(new PerkResistance(29, "Drug resistance III", "kek", 3, 10));

        this.addPerk(new PerkResistance(30, "Vaccine resistance I", "kek", 1, 1));
        this.addPerk(new PerkResistance(31, "Vaccine resistance II", "kek", 2, 5));
        this.addPerk(new PerkResistance(32, "Vaccine resistance III", "kek", 3, 10));
        
        this.addPerk(new PerkResistance(33, "kek I", "kek", 1, 1));
        this.addPerk(new PerkResistance(34, "kek II", "kek", 2, 5));
        this.addPerk(new PerkResistance(35, "kek III", "kek", 3, 10));
    }
}