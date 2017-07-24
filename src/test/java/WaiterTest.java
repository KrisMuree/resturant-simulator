import org.junit.Assert;
import org.junit.Test;
import org.kc.rm.models.Waiter;

public class WaiterTest {

    @Test
    public void testWaiter() {
        Waiter waiter = new Waiter(1, "Smith");
        Assert.assertNotNull(waiter);
        Assert.assertTrue(waiter.getId() == 1);
        Assert.assertTrue(waiter.getName().equals("Smith"));
    }

    @Test
    public void testForInvalidObject() {
        Waiter waiter = new Waiter(1, "Smith");
        Assert.assertFalse(waiter.getId() == 3);
        Assert.assertFalse(waiter.getName().equals("John"));
    }
}
