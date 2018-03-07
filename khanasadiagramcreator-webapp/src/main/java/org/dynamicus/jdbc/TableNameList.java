package org.dynamicus.jdbc;

import org.dynamicus.model.DbCredential;
import org.dynamicus.model.DbSchema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TableNameList {
    private static final Logger log = LoggerFactory.getLogger(TableNameList.class);

    public DbSchema getTableNameList(DbCredential dbCredential) {

        try {
            Class.forName(dbCredential.getJdbcDriver()).newInstance();
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            log.error("Trouble with JdbcDriver", e);
            throw new RuntimeException("Trouble with JdbcDriver", e);
        }

        try (Connection connection = DriverManager.getConnection(
                dbCredential.getJdbcUrl(),
                dbCredential.getUser(),
                dbCredential.getPassword())) {

            DatabaseMetaData databaseMetaData = connection.getMetaData();
            String[] types = {"TABLE"};

            DbSchema dbBean = new DbSchema();

            //get table name list
            List<String> tableNameList = new ArrayList<>();
            getTableNameList(databaseMetaData, types, tableNameList);

            //get foreign key table
            List<String> fkTableList = new ArrayList<>();
            getForeignKeyTable(databaseMetaData, tableNameList, fkTableList);

            dbBean.setFkAndTableNameList(fkTableList);
            return dbBean;

        } catch (SQLException e) {
            throw new RuntimeException("Could not read DB", e);
        }

    }

    private void getTableNameList(DatabaseMetaData databaseMetaData,
                                  String[] types,
                                  List<String> tableNameList){
        ResultSet resultSet = null;
        try {
            resultSet = databaseMetaData.getTables(null, null, "%", types);
            while (resultSet.next()) {
                String tableName = resultSet.getString(3);
                log.debug("..reading table : " + tableName);

                tableNameList.add(tableName);
            }
        } catch (SQLException e) {
            log.error("Could not read data table", e);
            throw new RuntimeException("Could not read data table", e);
        }

    }

    private void getForeignKeyTable(DatabaseMetaData databaseMetaData,
                                    List<String> tableNameList,
                                    List<String> fkTableList) {
        tableNameList.forEach(tableName -> {
            try {
                ResultSet foreignKeyResultSet = databaseMetaData.getExportedKeys(null, null, tableName);
                while (foreignKeyResultSet.next()) {
                    String fkTableName = foreignKeyResultSet.getString("FKTABLE_NAME");
                    String fkColumnName = foreignKeyResultSet.getString("FKCOLUMN_NAME");
                    log.debug("FKTABLE_NAME : " + fkTableName + ", FKCOLUMN_NAME : " + fkColumnName);

                    fkTableList.add(tableName + " -> " + fkTableName);
                }
                //No foreign Key
                fkTableList.add(tableName);

            } catch (SQLException e) {
                log.error("Could not read foreign key table", e);
                throw new RuntimeException("Could not read foreign key table", e);
            }

        });
    }
}
