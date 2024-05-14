package syncPlugin;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class Economy {
  private String name;
  
  private Economy(String name) {
    this.name = name;
    Main.INSTANCE.executeSQL("CREATE TABLE IF NOT EXISTS " + name + " (Uuid VARCHAR(32)," + name + " INTEGER);");
  }
  
  public int getEconomy(UUID u) {
    ResultSet result = Main.INSTANCE.executeSQLwithReturn("SELECT " + this.name + " FROM " + this.name + " WHERE Uuid=\"" + u.toString().replace("-", "") + "\";");
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
  
  public void setEconomy(UUID u, int money) {
    ResultSet result = Main.INSTANCE.executeSQLwithReturn("select count(*) from " + this.name + " WHERE Uuid=\"" + u.toString().replace("-", "") + "\";");
    try {
      if (result.getInt(1) > 0) {
        Main.INSTANCE.executeSQL("UPDATE " + this.name + " SET " + this.name + " = " + money + " WHERE Uuid = \"" + u.toString().replace("-", "") + "\";");
      } else {
        Main.INSTANCE.executeSQL("INSERT INTO " + this.name + " (Uuid," + this.name + ") VALUES('" + u.toString().replace("-", "") + "'," + money + ");");
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
  
  public void addEconomy(UUID u, int moneyAdd) {
    setEconomy(u, getEconomy(u) + moneyAdd);
  }
  
  public String getEconomyName() {
    return this.name;
  }
  
  public static Economy getEconomy(String economyName) {
    return new Economy(economyName);
  }
}
