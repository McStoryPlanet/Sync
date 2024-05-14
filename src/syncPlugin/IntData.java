package syncPlugin;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class IntData {
  private String name;
  
  private IntData(String name) {
    this.name = name;
    Main.INSTANCE.executeSQL("CREATE TABLE IF NOT EXISTS " + name + " (Uuid VARCHAR(32),Value INTEGER);");
  }
  
  public int getData(UUID u) {
    ResultSet result = Main.INSTANCE.executeSQLwithReturn("SELECT Value FROM " + this.name + " WHERE Uuid=\"" + u.toString().replace("-", "") + "\";");
    if (result == null)
      return 0; 
    int return0 = 0;
    try {
      return0 = result.getInt(1);
    } catch (SQLException sQLException) {}
    try {
      result.close();
    } catch (SQLException e) {
      e.printStackTrace();
    } 
    return return0;
  }
  
  public void setData(UUID u, int mines) {
    ResultSet result = Main.INSTANCE.executeSQLwithReturn("select count(*) from " + this.name + " WHERE Uuid=\"" + u.toString().replace("-", "") + "\";");
    try {
      if (result.getInt(1) > 0) {
        Main.INSTANCE.executeSQL("UPDATE " + this.name + " SET Value = " + mines + " WHERE Uuid = \"" + u.toString().replace("-", "") + "\";");
      } else {
        Main.INSTANCE.executeSQL("INSERT INTO " + this.name + " (Uuid,Value) VALUES('" + u.toString().replace("-", "") + "'," + mines + ");");
      } 
    } catch (SQLException e) {
      e.printStackTrace();
    } 
    try {
      result.close();
    } catch (SQLException e) {
      e.printStackTrace();
    } 
  }
  
  public String getDataName() {
    return this.name;
  }
  
  public static IntData getData(String dataName) {
    return new IntData(dataName);
  }
}
