package ch.hepia.covid_manager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import ch.hepia.covid_manager.Countries;
import ch.hepia.covid_manager.Virus;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;

import java.time.LocalDate;



public class DataBaseCommunicator{
  private String dbDriver;
  private String url;
  private String user;
  private String pass;
  private Connection co;

  public DataBaseCommunicator(String driver, String url, String user, String pass) throws SQLException, ClassNotFoundException{
    this.dbDriver = driver;
    this.url = url;
    this.user = user;
    this.pass = pass;
    this.co = DriverManager.getConnection(this.url, this.user, this.pass);
  }

  //Class.forName("com.mysql.jdbc.Driver");

  public ResultSet executeQuery(String query) throws SQLException, ClassNotFoundException{
    Class.forName(this.dbDriver);
    PreparedStatement pSt = this.co.prepareStatement(query);
    ResultSet rs = pSt.executeQuery();
    return rs;
  }

  public int executeUpdate(String query) throws SQLException, ClassNotFoundException{
    Class.forName(this.dbDriver);
    PreparedStatement pSt = this.co.prepareStatement(query);
    int rs = pSt.executeUpdate();
    return rs;
  }



  
  public void closeConnection() throws SQLException, ClassNotFoundException{
    this.co.close();
  }


  public void save(Virus v, Countries c, User user, LocalDate ld){
    String req = "UPDATE `Virus` SET `infectivity`="+ v.infectivity() +",`lethality`=" + v.lethality() + ",`resistance`=" + v.resistance() + "WHERE `id`="+user.getUserId();
    try{
      this.executeUpdate(req);
    }catch (Exception e){
      throw new RuntimeException(e);
    }
    req = "UPDATE `Game` SET `current_date`= '"+ ld.toString() + "' WHERE `virus_id`="+user.getUserId();
    
    try{
      this.executeUpdate(req);
    }catch (Exception e){
      throw new RuntimeException(e);
    }
    v.getUnlockedPerks().forEach((Perk p) -> {
      try{
        this.executeUpdate("INSERT INTO `UnlockedPerk`(`perk_id`, `virus`) VALUES ("+ p.id() +"," + user.getUserId() + ") ON DUPLICATE KEY UPDATE `perk_id`=`perk_id`");
      }catch(Exception e){
        throw new RuntimeException(e);
      }
    });
    
    c.listOfCountries().forEach((Country co) ->{
        try{
          this.executeUpdate("INSERT INTO `State`(`game_id`, `slug`, `current_total_cases`, `current_total_active`, `current_total_deaths`) VALUES ("+user.getUserId()+",'"+ co.slug()+"',"+co.playerTotalCases()+"," + co.playerTotalActive() + ", "+ co.playerTotalDeaths() +") ON DUPLICATE KEY UPDATE `current_total_cases` = "+co.playerTotalCases()+", `current_total_active` = " + co.playerTotalActive() +", `current_total_deaths` = " + co.playerTotalDeaths());
        }catch(Exception e){
          throw new RuntimeException(e);
        }
      });
  }

  public Virus loadVirus(){ 
    throw new RuntimeException("Not implemeneted");
  }

  public Countries loadCountries(){
    throw new RuntimeException("Not implemeneted");
  }
}
