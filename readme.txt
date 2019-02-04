PluginUtils
version: 2.0.0
website: http://zzzubec.no-ip.org
author: ZZZubec (Salamandr)
description:
Wrapper for Mysql and Sqlite, Configuration (yml) for plugins craftbukkit and just Java

Changelog:
2.0.1
add BitsArray class

2.0.0
Finish version

0.0.2
Adding create and save configuration file
Adding Description to the save configuration file
Modifing read file, for the create plugin folder if not exists


example BitsArray:
System.out.println("-----------------------");
byte meta = 23;
BitsArray bits = new BitsArray(meta);
int rotateAngle = bits.GetInt(1,2, 0); //0,1,2,3 * 90
int growth = bits.GetInt(3,7, 2);
System.out.println("rotateAngle:" + rotateAngle);
System.out.println("growth:" + growth);

growth++;

BitsArray ba_growth = new BitsArray(growth);
BitsArray ba_rotate = new BitsArray(rotateAngle);
BitsArray result = BitsArray.join(ba_rotate, 2, ba_growth );
meta = (byte)result.GetInt();
System.out.println("meta:" + result.GetInt());

growth++;

BitsArray newbits = new BitsArray(Integer.toBinaryString(growth) + Integer.toBinaryString(rotateAngle));
meta = (byte)newbits.GetInt();
System.out.println("meta:" + meta);

example:
public class MyClass {
  public SQLSystem sql;
  private FileConfigTree plugin_options;

  public MyClass() {
    logSystem = new LogSystem(getLogger(), path + "/../../logs/" + getClass().getSimpleName() + "_log.txt");
    logSystem.prefix = getDescription().getPrefix();

    initFileDirsAndConfig();
    initMySQL();

    logSystem.debug = plugin_options.getNumber("debug.level");
    logSystem.writeOut = plugin_options.getBoolean("debug.write_to_console");
  }

  private void initMySQL() {
        db_config = new DataBaseConfig(
                plugin_options.getString("mysql.host"),
                plugin_options.getNumber("mysql.port"),
                plugin_options.getString("mysql.dbname"),
                plugin_options.getString("mysql.user"),
                plugin_options.getString("mysql.password"));
        sql = new SQLSystem(db_config, SQLSystem.EnumSQL.MySQL);
        sql.connectSQL();

        int count = 0;
        ResultSet res = sql.query("SELECT * FROM players");
        try {
            while (res != null && res.next()) {
                count++;
            }
            log("initMySQL", "register players in DB: " + count);
        }catch (SQLException ex) {
            error("initMySQL", ex.getMessage());
        }
    }

  private void initFileDirsAndConfig() {
        utils = new Utils();

        File f = new File(path + "/config.cfg");
        if(f.exists())
        {
            plugin_options = new FileConfigTree();
            plugin_options.readFromFile(path, "config.cfg");
        } else {
            plugin_options = new FileConfigTree();
            plugin_options.addKeyValue("debug.level", 2);
            plugin_options.addKeyValue("debug.write_to_console", "yes");
            plugin_options.addKeyValue("mysql.host", "localhost");
            plugin_options.addKeyValue("mysql.port", 3306);
            plugin_options.addKeyValue("mysql.dbname", "scrypto_mine");
            plugin_options.addKeyValue("mysql.user", "root");
            plugin_options.addKeyValue("mysql.password", "12345678");
            plugin_options.addKeyValue("ore.diamond.chance", "1/1000");
            plugin_options.addKeyValue("ore.emerald.chance", "1/1000");
            plugin_options.addKeyValue("ore.lapis.chance", "1/1000");
            plugin_options.addKeyValue("ore.coal.chance", "1/1000");
            plugin_options.writeToFile(path, "config.cfg");
        }
    }
}


example select from BD:
ResultSet res = sql.query("SELECT * FROM players WHERE login='" + player.getDisplayName() + "' LIMIT 1;");
try {
  while (res != null && res.next()) {
    String passwd = res.getString("password")
    int player_id = res.getInt("id");
  }
} catch (SQLException ex) {
  error("AuthPlayer", ex.getMessage());
}

public void log(String method, String message) {
  logSystem.showMessage(this.getClass().getSimpleName(), method, message);
}

public void error(String method, String message) {
  logSystem.errorMessage(this.getClass().getSimpleName(), method, message);
}


example INSERT, UPDATE:
if (sql.update("INSERT INTO players (login, password, uuid) "
  + "VALUES('" + player.getDisplayName() + "', '" + paswd + "', '" + player.getUniqueId() + "'"
  + ");")) 
{
  log("INSERT complete!");
}

if(sql.update("UPDATE players SET money=" + 999999 + " WHERE id=" + playerInfo.bd_id ))
{
  log("UPDATE finish succesfful!");
}