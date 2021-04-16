import java.util.List;
import java.util.ArrayList;

public class Virus{

  private double infectivity;
  private double lethality;
  private double resistance;
  //private List<Perk> perkLst;
  private int level;

  public Virus(){
    //Takes constants "similar" to the real covid-19's "stats"
    this.infectivity =  0.15; //Aucune idée du chiffre a mettre au début
    this.lethality = 0.005; //0.5% de mortalitlé
    this.lethality = 0.005;
    //this.perkLst = new ArrayList<Perk>();
  }

  /*public void learnPerk(Perk p){
    if (p == null){
      throw new RuntimeException("New perk is null!!");
    }
    this.perkLst.add(p);
  }*/



}