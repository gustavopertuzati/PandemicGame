package ch.hepia.covid_manager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
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
}