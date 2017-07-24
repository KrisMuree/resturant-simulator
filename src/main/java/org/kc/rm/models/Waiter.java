package org.kc.rm.models;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Waiter {
    private int id;
    private String name;
    private int restaurantId;
    private List<Table> allottedTables;

    public Waiter(int id, String name) {
        this.id = id;
        this.name = name;
        this.restaurantId = -1;
        this.allottedTables = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
    }

    public List<Table> getAllottedTables() {
        return allottedTables;
    }

    public String getAllottedTablesAsString() {
        return allottedTables.stream()
                .map(Table::getTableName)
                .collect(Collectors.joining(","));
    }

    public void setAllottedTables(List<Table> allottedTables) {
        this.allottedTables = allottedTables;
    }
}
