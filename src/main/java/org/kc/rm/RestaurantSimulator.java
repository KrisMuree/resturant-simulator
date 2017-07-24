package org.kc.rm;

import org.kc.rm.exceptions.NotFoundException;
import org.kc.rm.models.Table;
import org.kc.rm.models.Waiter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RestaurantSimulator {
    private final static int MAXIMUM_RESTAURANTS = 2;
    private final static int MAXIMUM_TABLES = 20;

    private List<Waiter> waiters;
    private Map<Integer, List<Table>> allottedTableMap = new HashMap<>();

    public RestaurantSimulator(List<Waiter> waiters) {
        this.waiters = waiters;
;   }

    public Waiter getWaiter(int waiterId) throws NotFoundException {
        return this.waiters.stream()
                .filter(w -> w.getId() == waiterId)
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Waiter not found"));
    }

    private void assignTableToWaiter(int waiterId, Table table) throws Exception {
        Waiter waiter = getWaiter(waiterId);
       if (waiter.getAllottedTables().size() == 4) {
           throw new Exception("Maximum 4 tables can be allocated for a waiter.");
       }
        waiter.getAllottedTables().add(table);
    }

   public void managerView() {
        waiters.forEach(w -> {
                    System.out.println(w.getName());
                    System.out.println(w.getAllottedTablesAsString());
                });
   }

   public void waiterView(final int waiterId) throws NotFoundException {
        Waiter waiter = getWaiter(waiterId);
        String tableNames = waiter.getAllottedTables()
                .stream()
                .map(Table::getTableName)
                .collect(Collectors.joining(","));

        System.out.println(waiter.getName());
        System.out.println(tableNames);
   }

    public void allocateTable(final int restaurantId, final int tableId, final int waiterId)
            throws Exception {
        List<Table> allottedTables;

        if (restaurantId > MAXIMUM_RESTAURANTS) {
            throw new NotFoundException("Restaurant does not exist.");
        }

        if (tableId > MAXIMUM_TABLES) {
            throw new NotFoundException("Invalid table id");
        }

        allottedTables = allottedTableMap.get(restaurantId);

        if (isTableAllotted(allottedTables, tableId, waiterId)) {
            throw new NotFoundException("Table already allotted");
        } else {
            if (allottedTableMap.size() == 0) {
                allottedTables = new ArrayList<>();
            }
            Table table = new Table(tableId);
            assignTableToWaiter(waiterId, table);
            allottedTables.add(table);
            allottedTableMap.put(restaurantId, allottedTables);
        }
    }

    private boolean isTableAllotted(final List<Table> allottedTables, final int tableId, final int waiterId) throws NotFoundException {
        if (allottedTables == null) {
            return false;
        }
        Table table = getTable(allottedTables, tableId);
        return table != null;
    }

    private Table getTable(List<Table> tables, final int tableId) {
        return tables.stream()
                .filter(t -> t.getId() == tableId)
                .findFirst()
                .orElse(null);
    }

    public List<Waiter> getWaiters() {
        return waiters;
    }

    public Map<Integer, List<Table>> getAllottedTableMap() {
        return allottedTableMap;
    }
}
