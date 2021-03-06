package io.github.thebusybiscuit.cscorelib2.database.implementation;

import org.bukkit.plugin.Plugin;

import io.github.thebusybiscuit.cscorelib2.database.AuthenticatedSQLDatabase;

public class MySQLDatabase extends AuthenticatedSQLDatabase<MySQLDatabase> {

    public MySQLDatabase(Plugin plugin, DatabaseLoader<MySQLDatabase> callback) {
        super(plugin, callback);
    }

    @Override
    public String getType() {
        return "MySQL";
    }

    @Override
    public int getDefaultPort() {
        return 3306;
    }

    @Override
    public String getDriver() {
        return "com.mysql.jdbc.Driver";
    }

    @Override
    public String getIP() {
        return "jdbc:mysql://" + this.host + ":" + this.port + "/" + this.database;
    }
}