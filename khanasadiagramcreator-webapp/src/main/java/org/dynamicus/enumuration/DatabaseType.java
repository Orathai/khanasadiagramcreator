package org.dynamicus.enumuration;


public enum DatabaseType {

    MYSQL(1, "mysql"),
    POSTGRESQL(2, "postgresql");

    private int numberValue = 0;
    private String textValue = null;

    DatabaseType(int numberValue, String textValue) {
        this.numberValue = numberValue;
        this.textValue = textValue;
    }

    public String toTextValue() {
        return this.textValue;
    }

    public int toNumberValue() {
        return this.numberValue;
    }

    public static DatabaseType fromNumberValue(int value) {
        for (DatabaseType i : DatabaseType.values()) {
            if (i.toNumberValue() == value) {
                return i;
            }
        }
        return null;
    }

}
