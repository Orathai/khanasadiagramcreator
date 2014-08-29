package org.dynamicus.bean;

import java.util.List;

public class DatabaseSchemaBean {

    private String databaseName;
    private String tableName;
    private List<String> tableNameList;
    private List<String> fkAndTableNameList;

    public List<String> getTableNameList() {
        return tableNameList;
    }

    public void setTableNameList(List<String> tableNameList) {
        this.tableNameList = tableNameList;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public List<String> getFkAndTableNameList() {
        return fkAndTableNameList;
    }

    public void setFkAndTableNameList(List<String> fkAndTableNameList) {
        this.fkAndTableNameList = fkAndTableNameList;
    }
}
