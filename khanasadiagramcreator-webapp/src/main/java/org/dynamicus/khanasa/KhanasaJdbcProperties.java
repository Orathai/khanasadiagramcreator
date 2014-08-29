package org.dynamicus.khanasa;

import org.dynamicus.enumuration.DatabaseType;
import com.mysql.jdbc.StringUtils;

public class KhanasaJdbcProperties {

    public String getJdbcDriver(DatabaseType dbType) {
        switch (dbType) {
            case MYSQL:
                return "com.mysql.jdbc.Driver";
            case POSTGRESQL:
                return "org.postgresql.Driver";
            default:
                return "unknown database type";
        }

    }

    public String getJdbcUrl(DatabaseType dbType,
                             String user,
                             String ip,
                             String port) {
        String host = ip;
        if (!StringUtils.isNullOrEmpty(port)) {
            host += ":" + port;
        }
        switch (dbType) {
            case MYSQL:
                return "jdbc:mysql://" + host + "/" + user + "?";
            case POSTGRESQL:
                return "jdbc:postgresql://" + host + "/" + user + "?";
            default:
                return "unknown database type";
        }
    }
}
