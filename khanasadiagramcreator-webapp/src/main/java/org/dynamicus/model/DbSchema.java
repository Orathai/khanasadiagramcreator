package org.dynamicus.model;

import java.util.List;

public class DbSchema {

    private String databaseName;
    private String tableName;
    private List<String> tableNameList;
    private List<String> fkAndTableNameList;

    public List<String> getFkAndTableNameList() {
        return fkAndTableNameList;
    }

    public void setFkAndTableNameList(List<String> fkAndTableNameList) {
        this.fkAndTableNameList = fkAndTableNameList;
    }
}
