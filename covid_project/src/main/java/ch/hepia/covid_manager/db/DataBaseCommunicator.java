package ch.hepia.covid_manager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import ch.hepia.covid_manager.Countries;
import ch.hepia.covid_manager.Virus;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;



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


  public void save(Virus v, Countries c, User user){
    String req = "UPDATE `Virus` SET `id`="+user.getUserId()+",`infectivity`="+ v.infectivity() +",`lethality`=" + v.lethality() + ",`resistance`=" + v.resistance() + ",`player_name`=" + user.getUsername();
    
    try{
      this.executeUpdate(req);
    }catch (Exception e){
      throw new RuntimeException(e);
    }

    c.listOfCountries().forEach((Country co) ->{
        try{
          this.executeQuery("UPDATE `State` SET `game_id`="+user.getUserId()+",`slug`=" + co.slug() + ",`current_total_cases`=" + co.playerTotalCases() + ",`current_total_active`=" + co.playerTotalActive() + ",`current_total_deaths`=" + co.playerTotalDeaths() +")");
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
