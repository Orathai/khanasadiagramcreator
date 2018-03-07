package org.dynamicus.khanasa;

import org.dynamicus.enumuration.DatabaseType;
import org.junit.Assert;
import org.junit.Test;

public class KhanasaJdbcComponentTest {

    @Test
    public void testGetJdbcDriver() throws Exception {

        Assert.assertEquals("com.mysql.jdbc.Driver", new KhanasaJdbcComponent().getJdbcDriver(DatabaseType.MYSQL));
        Assert.assertEquals("org.postgresql.Driver", new KhanasaJdbcComponent().getJdbcDriver(DatabaseType.POSTGRESQL));
    }

    @Test
    public void testGetJdbcUrl() throws Exception {

        Assert.assertEquals("jdbc:mysql://127.0.0.1:3306/database?",
                new KhanasaJdbcComponent().getJdbcUrl(DatabaseType.MYSQL, "127.0.0.1", "3306", "database"));

        Assert.assertEquals("jdbc:postgresql://127.0.0.1:5432/database?",
                new KhanasaJdbcComponent().getJdbcUrl(DatabaseType.POSTGRESQL, "127.0.0.1", "5432", "database"));
    }
}