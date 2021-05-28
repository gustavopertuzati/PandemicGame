package ch.hepia.my_app;

import java.util.List;
import java.util.ArrayList;

public class Virus{

  private double infectivity; // propagation du virus
  private double lethality; // a quel point ca tue
  private double resistance; //solidité du virus
  //private List<Perk> perkLst;

  //Level is the amount of points 
  private int level;

  public Virus(){
    //Takes constants "similar" to the real covid-19's "stats"
    this.infectivity =  0.15; //Aucune idée du chiffre a mettre au début
    this.lethality = 0.005; //0.5% de mortalitlé
    this.lethality = 0.005;
    //this.perkLst = new ArrayList<Perk>();

    this.level = 0;
  }

  /*public void learnPerk(Perk p){
    if (p == null){
      throw new RuntimeException("New perk is null!!");
    }
    this.perkLst.add(p);
  }*/


  public void addPoint(){
    this.level += 1;
  }



}