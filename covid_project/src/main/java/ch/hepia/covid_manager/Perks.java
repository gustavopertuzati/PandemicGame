package ch.hepia.covid_manager;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import javafx.scene.paint.Color;

import java.lang.Math;
import java.sql.ResultSet;

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
        this.perks = getPerksFromDb();
    }

    public static List<Perk> getPerksFromDb(){
        List<Perk> res = new ArrayList<>();

        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost/";
        String req = "SELECT * FROM Perk";

        try{
            DataBaseCommunicator dbc = new DataBaseCommunicator(driver, url, "root", "root");
            dbc.executeQuery("USE covid");
            // faire une transaction si on veut insert
            ResultSet rs = dbc.executeQuery(req);
            System.out.println(rs);
            while (rs.next()){
                //selon le type du resultat, instancier une classe diff
                res.add(Perk.perkFactory(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), (double)rs.getFloat(5), rs.getString(6)));
            }
        }catch(Exception e){
            throw new RuntimeException(e);
        }
        return res;
    }
}