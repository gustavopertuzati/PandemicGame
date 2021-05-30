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

  public DataBaseCommunicator(String driver, String url, String user, String pass){
    this.dbDriver = driver;
    this.url = url;
    this.user = user;
    this.pass = pass;
  }

  public ResultSet executeQuery(String query) throws SQLException, ClassNotFoundException{
    Class.forName(this.dbDriver);
    String connection = this.url + "user=" + this.user  +"&password="+this.pass;
    Connection co = DriverManager.getConnection(connection);
    PreparedStatement pSt = co.prepareStatement(query);
    co.close();
    return pSt.executeQuery();
  }
  


}