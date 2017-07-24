import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.kc.rm.RestaurantSimulator;
import org.kc.rm.models.Table;
import org.kc.rm.models.Waiter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RestaurantSimulatorTest {
    private List<Waiter> waiters;

    @Before
    public void setup() {
        createWaiterList();
    }
    private void createWaiterList() {
        waiters = new ArrayList<>(8);
        waiters.add(createWaiter(1, "John"));
        waiters.add(createWaiter(2, "Anna"));
        waiters.add(createWaiter(3, "Chris"));
        waiters.add(createWaiter(4, "Mac"));
        waiters.add(createWaiter(5, "Tia"));
        waiters.add(createWaiter(6, "Richard"));
        waiters.add(createWaiter(7, "Susan"));
        waiters.add(createWaiter(8, "Mike"));
    }

    private Waiter createWaiter(final int id, final String name) {
        return new Waiter(id, name);
    }

    @Test
    public void testForValidAllocation() throws Exception {
        RestaurantSimulator simulator = new RestaurantSimulator(waiters);
        Assert.assertTrue(simulator.getWaiters().size() == 8);
        simulator.allocateTable(1, 1, 1);
        simulator.allocateTable(1, 2, 1);
        simulator.allocateTable(1, 3, 2);
        simulator.allocateTable(1, 4, 3);

        Map<Integer, List<Table>> allottedTableMap = simulator.getAllottedTableMap();
        Assert.assertTrue(allottedTableMap.size() > 0);
        Assert.assertTrue(allottedTableMap.containsKey(1));
        Assert.assertFalse(allottedTableMap.containsKey(2));
        Assert.assertTrue(allottedTableMap.get(1).size() == 4);
        Waiter waiter = simulator.getWaiter(1);
        Assert.assertTrue(waiter.getId() == 1);
        Assert.assertTrue(waiter.getAllottedTables().size() == 2);
    }

    @Test
    public void testForMangerView() throws Exception {
        RestaurantSimulator simulator = new RestaurantSimulator(waiters);
        simulator.allocateTable(1, 1, 1);
        simulator.allocateTable(1, 2, 1);
        simulator.allocateTable(1, 3, 2);
        simulator.allocateTable(1, 4, 3);
        simulator.managerView();
    }

    @Test
    public void invalidRestaurantId() {
        RestaurantSimulator simulator = new RestaurantSimulator(waiters);
        try {
            simulator.allocateTable(13, 1, 1);
        } catch (Exception e) {
            Assert.assertEquals("Restaurant does not exist.", e.getMessage());
        }
    }

    @Test
    public void invalidTableId() {
        RestaurantSimulator simulator = new RestaurantSimulator(waiters);
        try {
            simulator.allocateTable(1, 21, 1);
        } catch (Exception e) {
            Assert.assertEquals("Invalid table id", e.getMessage());
        }
    }

    @Test
    public void invalidWaiter() {
        RestaurantSimulator simulator = new RestaurantSimulator(waiters);
        try {
            simulator.allocateTable(1, 1, 21);
        } catch (Exception e) {
            Assert.assertEquals("Waiter not found", e.getMessage());
        }
    }

    @Test
    public void waiterTableAllocationLimitExceeded() {
        RestaurantSimulator simulator = new RestaurantSimulator(waiters);
        try {
            simulator.allocateTable(1, 1, 1);
            simulator.allocateTable(1, 2, 1);
            simulator.allocateTable(1, 3, 1);
            simulator.allocateTable(1, 4, 1);
            simulator.allocateTable(1, 5, 1);
        } catch (Exception e) {
            Assert.assertEquals("Maximum 4 tables can be allocated for a waiter.", e.getMessage());
        }
    }

    @Test
    public void assigingTableToMultipleWaiterIsInvalid() {
        RestaurantSimulator simulator = new RestaurantSimulator(waiters);
        try {
            simulator.allocateTable(1, 1, 1);
            simulator.allocateTable(1, 1, 2);
        } catch (Exception e) {
            Assert.assertEquals("Table already allotted", e.getMessage());
        }
    }
}
