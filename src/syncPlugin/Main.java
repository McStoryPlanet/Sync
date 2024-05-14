package syncPlugin;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
  public static Main INSTANCE;
  
  private Connection connection;
  
  private Statement statement;
  
  public void onEnable() {
    INSTANCE = this;
    try {
      Class.forName("org.sqlite.JDBC");
    } catch (ClassNotFoundException e) {
      getLogger().log(Level.WARNING, e, () -> "Could not find SQLite.");
    } 
    try {
      initSQL();
    } catch (SQLException e) {
      getLogger().log(Level.WARNING, e, () -> "SQL Error during init.");
    } 
  }
  
  public void onDisable() {
    try {
      this.statement.close();
      this.connection.close();
    } catch (SQLException e) {
      e.printStackTrace();
    } 
  }
  
  public void initSQL() throws SQLException {
    getDataFolder().mkdirs();
    this.connection = DriverManager.getConnection("jdbc:sqlite:" + getDataFolder() + File.separator + "testdb.db");
    this.statement = this.connection.createStatement();
  }
  
  public boolean executeSQL(String sqlCommand) {
    boolean result = false;
    try {
      result = this.statement.execute(sqlCommand);
    } catch (SQLException e) {
      e.printStackTrace();
    } 
    return result;
  }
  
  public ResultSet executeSQLwithReturn(String sqlCommand) {
    ResultSet result = null;
    try {
      result = this.statement.executeQuery(sqlCommand);
    } catch (SQLException sQLException) {}
    return result;
  }
}
