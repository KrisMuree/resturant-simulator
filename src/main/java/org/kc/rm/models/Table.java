package org.kc.rm.models;

public class Table {
    private final static String TABLE_NAME = "Table ";

    private int id;
    private String tableName;

    public Table(int id) {
        this.id = id;
        this.tableName = TABLE_NAME + id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTableName() {
        return tableName;
    }
}
