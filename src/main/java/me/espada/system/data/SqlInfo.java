package me.espada.system.data;

import me.espada.Core;
import me.espada.system.utils.Config;

import java.util.function.Predicate;

public class SqlInfo {

    static {
        file = new Config(Core.getInstance().getDataFolder().getAbsolutePath() + "/data", "mysql");
    }

    private static Config file;

    public SqlCredentials getSqlCredentials() {
        if (exist.test(file)) {
            Core.getCoreLogger().log("Enabled successfully! please fill up the info required in the data folder!");
            file.set("mysql.username", "");
            file.set("mysql.password", "");
            file.set("mysql.database", "");
            file.set("mysql.hostname", "");
            file.set("mysql.port", "");
            return null;
        }

        String username = file.getString("mysql.username");
        String password = file.getString("mysql.password");
        String database = file.getString("mysql.database");
        String host = file.getString("mysql.hostname");
        int port = file.getInt("mysql.port");
        return new SqlCredentials(host, port, username, password, database);
    }

    public Predicate<Config> exist = (c) -> !c.exists() || c.getKeys(false).isEmpty();

}
