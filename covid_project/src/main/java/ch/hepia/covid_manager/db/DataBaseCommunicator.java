package ch.hepia.covid_manager;

import java.util.List;
import java.util.ArrayList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.CompletableFuture;
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

  public void save(Virus virus, Countries countries, int userId){
    /////////////////////////////////////////////////////
    /////////////////////////////////////////////////////
    /////////////////////////////////////////////////////
    /////////////////////////////////////////////////////
    /////////////////////////////////////////////////////
    /////////////////////////////////////////////////////
  }

  public CompletableFuture<Countries> loadCountries(){

    return CompletableFuture.supplyAsync(() -> {
      Countries countries = new Countries();
      try{
        this.executeQuery("USE covid");
        // faire une transaction si on veut insert
        ResultSet rs = this.executeQuery("SELECT * FROM Country;");
        //System.out.println(rs.getFetchSize());
        while (rs.next()){
          int totalPop = rs.getInt(6);
          int totalCases = rs.getInt(7);
          int totalActive = rs.getInt(8);
          int totalDeaths = rs.getInt(9);
          int totalRecovered = totalCases - totalActive - totalDeaths;
          int size = rs.getInt(3);
          int lat = rs.getInt(4);
          int longi = rs.getInt(5);
          countries.addCountry(new Country(rs.getString(2), lat, longi, totalCases,0, 
                                                  totalDeaths, 0, totalRecovered,0,0, 0, size, // added 0,0 -> totalCured / dailyCured
                                                  totalPop ,rs.getString(1)));
        }
      }catch(Exception e){
          throw new RuntimeException(e);
      }
      return countries;
    });
  }
}