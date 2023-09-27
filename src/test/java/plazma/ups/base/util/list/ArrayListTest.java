package plazma.ups.base.util.list;

import org.junit.Test;
import static org.junit.Assert.*;

public class ArrayListTest extends AbstractListTest {

    @Override
    List createList() {
        return new ArrayList();
    }

    @Test
    public void testDefaultCapacity() {
        ArrayList list = new ArrayList();
        assertEquals(10, list.getCapacity());
    }

    @Test
    public void testGrowCapacity() {
        ArrayList list = new ArrayList(5);
        assertEquals(5, list.getCapacity());

        list.add("Element 0");
        list.add("Element 1");
        list.add("Element 2");
        list.add("Element 3");
        list.add("Element 4");

        assertEquals(5, list.getCapacity());

        list.add("Element 6");

        assertEquals((int) (5 * 1.5), list.getCapacity());
    }

}
