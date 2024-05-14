package syncPlugin;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class LevelData {
  private String name;
  
  private LevelData(String name) {
    this.name = name;
    Main.INSTANCE.executeSQL("CREATE TABLE IF NOT EXISTS " + name + " (Uuid VARCHAR(32),Level INTEGER);");
  }
  
  public int getLevel(UUID u) {
    ResultSet result = Main.INSTANCE.executeSQLwithReturn("SELECT Level FROM " + this.name + " WHERE Uuid=\"" + u.toString().replace("-", "") + "\";");
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
  
  public void setLevel(UUID u, int mines) {
    ResultSet result = Main.INSTANCE.executeSQLwithReturn("select count(*) from " + this.name + " WHERE Uuid=\"" + u.toString().replace("-", "") + "\";");
    try {
      if (result.getInt(1) > 0) {
        Main.INSTANCE.executeSQL("UPDATE " + this.name + " SET Level = " + mines + " WHERE Uuid = \"" + u.toString().replace("-", "") + "\";");
      } else {
        Main.INSTANCE.executeSQL("INSERT INTO " + this.name + " (Uuid,Level) VALUES('" + u.toString().replace("-", "") + "'," + mines + ");");
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
  
  public void nextLevel(UUID u) {
    setLevel(u, getLevel(u) + 1);
  }
  
  public void prevousLevel(UUID u) {
    setLevel(u, Math.max(getLevel(u) - 1, 0));
  }
  
  public String getDataName() {
    return this.name;
  }
  
  public static LevelData getLevelData(String dataName) {
    return new LevelData(dataName);
  }
}
