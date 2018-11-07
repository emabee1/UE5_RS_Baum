import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RBTreeTest {

    @Test
    void insertSimpleInteger() {
        RBTree<Integer> rb = new RBTree<>();
        rb.insert("First",1 );
        rb.insert("Second", 2);
        rb.insert("Third", 30);
        rb.insert("Fourth", 32);
        rb.insert("Fifth", 15);
        rb.traverse(rb.getRoot());

        assertEquals(new Integer(1), rb.get("First"));
    }


}
