package org.dynamicus.model;

public class DbCredential {

    private String user;
    private String password;
    private String jdbcDriver;
    private String jdbcUrl;

    public DbCredential(String user,
                        String password,
                        String jdbcDriver,
                        String jdbcUrl) {
        this.user = user;
        this.password = password;
        this.jdbcDriver = jdbcDriver;
        this.jdbcUrl = jdbcUrl;
    }

    public String getJdbcDriver() {
        return jdbcDriver;
    }

    public String getJdbcUrl() {
        return jdbcUrl;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

}
