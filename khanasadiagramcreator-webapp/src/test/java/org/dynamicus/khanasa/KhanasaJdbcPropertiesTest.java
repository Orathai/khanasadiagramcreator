package org.dynamicus.khanasa;

import org.dynamicus.enumuration.DatabaseType;
import org.junit.Assert;
import org.junit.Test;

public class KhanasaJdbcPropertiesTest {

    @Test
    public void testGetJdbcDriver() throws Exception {

        Assert.assertEquals("com.mysql.jdbc.Driver", new KhanasaJdbcProperties().getJdbcDriver(DatabaseType.MYSQL));
        Assert.assertEquals("org.postgresql.Driver", new KhanasaJdbcProperties().getJdbcDriver(DatabaseType.POSTGRESQL));
    }

    @Test
    public void testGetJdbcUrl() throws Exception {

        Assert.assertEquals("jdbc:mysql://127.0.0.1/user?",
                new KhanasaJdbcProperties().getJdbcUrl(DatabaseType.MYSQL, "user", "127.0.0.1", null));

        Assert.assertEquals("jdbc:postgresql://127.0.0.1:3336/user?",
                new KhanasaJdbcProperties().getJdbcUrl(DatabaseType.POSTGRESQL, "user", "127.0.0.1", "3336"));
    }
}