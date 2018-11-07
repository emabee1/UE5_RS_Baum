/**
 * A Red Black Tree implementation
 *
 * @param <T>
 * @author Emanuel Beer
 */
public class RBTree<T> {
    private static final boolean BLACK = true;
    private static final boolean RED = false;

    private Node<T> NIL = new Node<>(null, null, null, BLACK, null, null);
    private Node<T> _root;

    private int _height;

    public RBTree() {
        _root = NIL;
        _height = 0;
    }

    public boolean insert(String key, T object) {
        Node<T> current = _root;
        Node<T> parent = null;


        //Traverse to an empty leave
        while (current != NIL) {
            if (current.getKey().equals(key)) {
                return false;
            } else if (current.getKey().compareTo(key) < 0) {
                parent = current;
                current = parent.getRightChild();
            } else {
                parent = current;
                current = parent.getLeftChild();
            }
        }

        //create new Node from Input
        current = new Node<>(NIL, NIL, parent, RED, key, object);

        //Set new Child for parent Node if new Node is not Root
        if (parent != null) {
            if (parent.getKey().compareTo(key) < 0) {
                parent.setRightChild(current);
            } else {
                parent.setLeftChild(current);
            }
        } else {
            _root = current;
        }

        balanceTree1(current);
        return true;
    }

    /**
     * If node is root, color is changed to BLACK
     *
     * @param node Node to check
     */
    private void balanceTree1(Node<T> node) {
        if (node.getParent() == null) {
            node.setColor(BLACK);
            _height++;
        } else {
            balanceTree2(node);
        }
    }

    /**
     * @param node
     */
    private void balanceTree2(Node<T> node) {
        if (node.getParent().getColor() != BLACK || !node.equals(_root)) {
            if ((node.getUncle() != null) && (node.getUncle().getColor() == RED)) {
                node.getParent().setColor(BLACK);
                node.getUncle().setColor(BLACK);
                node.getGrandparent().setColor(RED);

                balanceTree1(node.getGrandparent());
            }
        } else {
            balanceTree3(node);
        }
    }

    private void balanceTree3(Node<T> node) {
        if ((node.getUncle() != null) && (node.getUncle().getColor() == BLACK)) {
            leftLeftCase(node);
            leftRightCase(node);
            rightRightCase(node);
            rightLeftCase(node);
        }
    }

    /**
     * Left Right Case (parent is right child of grandparent and node is left child of parent)
     *
     * @param node
     */
    private void rightLeftCase(Node<T> node) {
        if (node.getParent().equals(node.getGrandparent().getRightChild()) && node.equals(node.getParent().getLeftChild())) {
            Node parent = node.getParent();
            rotateRight(node.getParent());
            rightRightCase(parent);
        }
    }

    /**
     * Right Right Case (parent is right child of grandparent and node is right child of parent)
     *
     * @param node
     */
    private void rightRightCase(Node<T> node) {
        if (node.getParent().equals(node.getGrandparent().getRightChild()) && node.equals(node.getParent().getRightChild())) {
            Node grandparent = node.getGrandparent();
            Node parent = node.getParent();

            rotateLeft(grandparent);

            //Swap colors of grandparent and parent
            boolean gColor = grandparent.getColor();
            grandparent.setColor(parent.getColor());
            parent.setColor(gColor);
        }
    }

    /**
     * Left Right Case (parent is left child of grandparent and node is right child of parent)
     *
     * @param node
     */
    private void leftRightCase(Node<T> node) {
        if (node.getParent().equals(node.getGrandparent().getLeftChild()) && node.equals(node.getParent().getRightChild())) {
            Node parent = node.getParent();
            rotateLeft(parent);
            leftLeftCase(parent);
        }
    }

    /**
     * //Left Left Case (parent is left child of grandparent and node is left child of parent)
     *
     * @param node
     */
    private void leftLeftCase(Node<T> node) {
        if (node.getParent().equals(node.getGrandparent().getLeftChild()) && node.equals(node.getParent().getLeftChild())) {
            Node<T> grandparent = node.getGrandparent();
            Node<T> parent = node.getParent();

            rotateRight(grandparent);

            boolean gColor = grandparent.getColor();
            grandparent.setColor(parent.getColor());
            parent.setColor(gColor);
        }
    }

    private void rotateRight(Node<T> node) {
        Node left = node.getLeftChild();
        Node parent = node.getParent();

        node.setLeftChild(left.getRightChild());
        node.setParent(left);
        left.setRightChild(node);
        left.setParent(parent);
    }

    private void rotateLeft(Node<T> node) {
        Node parent = node.getParent();
        Node right = node.getRightChild();

        node.setRightChild(right.getLeftChild());
        node.setParent(right);
        right.setParent(parent);
        right.setLeftChild(node);
    }

    public T get(String key) {
        Node current = _root;
        while (current != NIL) {
            if (current.getKey().compareTo(key) < 0) {
                current = current.getRightChild();
            } else if (current.getKey().compareTo(key) > 0) {
                current = current.getLeftChild();
            } else {
                return (T) current.getObject();
            }
        }
        return null;
    }

    public int getRBHeight() {
        return _height;
    }

    public Node<T> getRoot() {
        return _root;
    }

    public void debugPrint() {
        Node current = _root;
    }

    public void traverse(Node nextnode) {
        if (nextnode == NIL) {
            return;
        }

        traverse(nextnode.getLeftChild());
        traverse(nextnode.getRightChild());

        System.out.println(nextnode.toString());
    }


}
