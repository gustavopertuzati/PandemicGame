package ch.hepia.my_app;

import java.util.List;
import java.util.ArrayList;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;


public class Virus{


  
  private double infectivity; // propagation du virus
  private double lethality; // a quel point ca tue
  private double resistance; //solidité du virus
  
  //Toutes les compétences
  private List<Perk> perkLst;
  
  private List<PropertyChangeListener> lstObservers;
  
  //currentPoints is the amount of points 
  private int currentPoints;
  
  //Design Pattern Singleton
  private static Virus virus;

  public static Virus getInstance(){
    if (Virus.virus == null){
      Virus.virus = new Virus();
    }
    return Virus.virus;
  }
  
  private Virus(){
    //Takes constants "similar" to the real covid-19's "stats"
    this.infectivity =  0.15; //Aucune idée du chiffre a mettre au début
    this.lethality = 0.005; //0.5% de mortalitlé
    this.resistance = 0.005;
    //this.perkLst = new ArrayList<Perk>();

    this.currentPoints = 0;
    this.perkLst = new ArrayList<>();
    this.lstObservers = new ArrayList<>();
  }

  /*public void learnPerk(Perk p){
    if (p == null){
      throw new RuntimeException("New perk is null!!");
    }
    this.perkLst.add(p);
  }*/

  public double infectivity(){
    return this.infectivity;
  }

  public void addPoint(){
    this.currentPoints += 1;
    this.updateObservers();
  }

  public void updateObservers(){
    this.lstObservers.forEach(i->
      i.propertyChange(new PropertyChangeEvent(this, "virus", new Integer(this.currentPoints - 1), new Integer(this.currentPoints)))
      );
  }

  public double lethality(){
    return this.lethality;
  }
  
  public double resistance(){
    return this.resistance;
  }

  public void applyInfectivityBonus(double bonus){
    this.infectivity *= (1 + bonus);
  }

  public void applyLethalityBonus(double bonus){
    this.lethality *= (1 + bonus);
  }

  public void applyResistanceBonus(double bonus){
    this.resistance *= (1 + bonus);
  }

  public void upgrade(Perk p){

    if (this.currentPoints < p.cost()){
      //Pas assez de points pour acheter l'amélioration
      //Normalement, ne devrait pas rentrer ici, vue que l'option d'améliorer
      //n'est disponnible que si on a assez de points
      System.out.println("Not enough points!");
      return;
    }
    if(this.hasPerk(p)){
      //Déjà recu cette amélioration
      //Pareil qu'en haut, ne devrait pas rentrer ici
      System.out.println("Already have this perk!");
      return;
    }
    this.currentPoints -= p.cost();
    this.perkLst.add(p);
    p.update(this);
    this.updateObservers();
  }

  public boolean hasEnoughPoints(Perk p){
    return p.cost() <= this.currentPoints;
  }


  public boolean hasPerk(Perk p){
    return this.perkLst.contains(p);
  }

  @Override
  public String toString(){
    String output = "Virus{\n";
    output +=  "\tInfectivity: "+ this.infectivity + "\n";
    output +=  "\tLethality: "+ this.lethality + "\n";
    output +=  "\tResistance: "+ this.resistance + "\n";
    output +=  "\tPoints: "+ this.currentPoints + "\n";
    output +=  "\tPerks: "+ this.perkLst + "\n}";
    return output;
  }

  public void addListener(PropertyChangeListener pcl){
    this.lstObservers.add(pcl);
  }


}