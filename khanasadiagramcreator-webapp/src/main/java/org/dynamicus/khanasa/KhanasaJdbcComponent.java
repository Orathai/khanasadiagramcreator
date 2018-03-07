package org.dynamicus.khanasa;

import org.dynamicus.enumuration.DatabaseType;
import com.mysql.jdbc.StringUtils;

public class KhanasaJdbcComponent {

    String getJdbcDriver(DatabaseType dbType) {
        switch (dbType) {
            case MYSQL:
                return "com.mysql.jdbc.Driver";
            case POSTGRESQL:
                return "org.postgresql.Driver";
            default:
                return "unknown database type";
        }

    }

    String getJdbcUrl(DatabaseType dbType,
                      String ip,
                      String port,
                      String database) {
        String host = ip;
        if (!StringUtils.isNullOrEmpty(port)) {
            host += ":" + port;
        }
        switch (dbType) {
            case MYSQL:
                return "jdbc:mysql://" + host + "/" + database + "?";
            case POSTGRESQL:
                return "jdbc:postgresql://" + host + "/" + database + "?";
            default:
                return "unknown database type";
        }
    }
}
