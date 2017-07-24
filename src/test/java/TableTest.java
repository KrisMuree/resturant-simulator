import org.junit.Assert;
import org.junit.Test;
import org.kc.rm.models.Table;

public class TableTest {

    @Test
    public void testTable() {
        Table table = new Table(1);
        Assert.assertTrue(table.getId() == 1);
    }
}
