package me.espada.system.data;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import me.espada.Core;
import me.espada.system.utils.LogMessage;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class SqlAcces {

    private SqlCredentials credentials;
    private HikariDataSource hikariDataSource;

    public boolean init() {
        credentials = new SqlInfo().getSqlCredentials();
        if (credentials == null) return false;

        final HikariConfig CFG = new HikariConfig();

        CFG.setJdbcUrl(credentials.toURL());
        CFG.setUsername(credentials.getUsername());
        CFG.setPassword(credentials.getPassword());
        CFG.setMaximumPoolSize(10);
        CFG.setMaxLifetime(600000L);
        CFG.setIdleTimeout(300000L);
        CFG.setLeakDetectionThreshold(300000L);
        CFG.setConnectionTimeout(10000L);

        hikariDataSource = new HikariDataSource(CFG);
        Core.getCoreLogger().log("&aConnected to mysql!");

        return true;
    }

    public boolean shutdown() {
        if (hikariDataSource != null) {
            hikariDataSource.close();
            Core.getCoreLogger().log("&aDisconnected from mysql!");
            return true;
        }

        return false;
    }

    public Connection getConnection() {
        try {
            validateConnection.accept(hikariDataSource.getConnection());
            return hikariDataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Predicate<Connection> verifyConnection = (c) -> {
        try {
            return c != null && !c.isClosed();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    };

    public Consumer<Connection> validateConnection = (c) -> {
        if (verifyConnection.test(c)) {
            Core.getCoreLogger().log("Hikari Connection was not found!", LogMessage.LogType.ERROR);
        }
    };

}
