import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RBTreeTest {

    @Test
    void insertSimpleInteger() {
        RBTree<Integer> rb = new RBTree<>();
        rb.insert("First",1 );
        rb.insert("Second", 2);
        rb.insert("Third", 30);
        rb.insert("Fourth", 32);
        rb.insert("Fifth", 15);
        rb.traverseAll();
        System.out.println(rb.getRBHeight());

        assertEquals(new Integer(1), rb.get("First"));
    }


}
