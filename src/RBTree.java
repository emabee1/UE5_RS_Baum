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
     * If color of node´s parent is not BLACK or node is not root ->
     * If color of uncle is RED:
     * - change color of parent and uncle to BLACK
     * - change color of grandparent to RED
     * - node = grandparent and start balance from start (balanceTree1)
     * @param node Node to check
     */
    private void balanceTree2(Node<T> node) {
        if (node.getParent().getColor() != BLACK ||
                !node.equals(_root)) {

            if ((node.getUncle() != null) &&
                    (node.getUncle().getColor() == RED)) {

                node.getParent().setColor(BLACK);
                node.getUncle().setColor(BLACK);
                node.getGrandparent().setColor(RED);

                balanceTree1(node.getGrandparent());
            }
        }

        balanceTree3(node);
    }

    /**
     * If node´s color is BLACK there are four cases for node, its parent and grandparent:
     * - Left Left Case: parent is left child of grandparent and node is left child of parent
     * - Left Right Case: parent is left child of grandparent and node is right child of parent
     * - Right Left Case: parent is right child of grandparent and node is left child of parent
     * - Right Right Case: parent is right child of grandparent and node is right child of parent
     *
     * @param node Node to check
     */
    private void balanceTree3(Node<T> node) {
        if ((node.getUncle() != null) &&
                (node.getUncle().getColor() == BLACK)) {

            leftLeftCase(node);
            leftRightCase(node);
            rightRightCase(node);
            rightLeftCase(node);
        }
    }

    /**
     * Right Left Case:
     * - Right Rotate around parent
     * - execute Right Right case
     * @param node Node to check
     */
    private void rightLeftCase(Node<T> node) {
        if ((node.getGrandparent() != null) &&
                (node.getParent().equals(node.getGrandparent().getRightChild())) &&
                (node.equals(node.getParent().getLeftChild()))) {
            Node<T> parent = node.getParent();
            rotateRight(node.getParent());
            rightRightCase(parent);
        }
    }

    /**
     * Right Right Case:
     * - Left Rotate around grandparent
     * - Swap colors of grandparent and parent
     * @param node Node to check
     */
    private void rightRightCase(Node<T> node) {
        if ((node.getGrandparent() != null) &&
                node.getParent().equals(node.getGrandparent().getRightChild()) &&
                node.equals(node.getParent().getRightChild())) {
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
     * Left Right Case:
     * - Left Rotate around parent
     * - Execute Left Left case with old parent
     * @param node
     */
    private void leftRightCase(Node<T> node) {
        if ((node.getGrandparent() != null) &&
                node.getParent().equals(node.getGrandparent().getLeftChild()) &&
                node.equals(node.getParent().getRightChild())) {
            Node<T> parent = node.getParent();
            rotateLeft(parent);
            leftLeftCase(parent);
        }
    }

    /**
     * Left Left Case:
     * - Right Rotate around grandparent
     * - Swap colors of grandparent and parent
     * @param node Node to check
     */
    private void leftLeftCase(Node<T> node) {
        if ((node.getGrandparent() != null) &&
                node.getParent().equals(node.getGrandparent().getLeftChild()) &&
                node.equals(node.getParent().getLeftChild())) {
            Node<T> grandparent = node.getGrandparent();
            Node<T> parent = node.getParent();

            rotateRight(grandparent);

            boolean gColor = grandparent.getColor();
            grandparent.setColor(parent.getColor());
            parent.setColor(gColor);
        }
    }

    /**
     * Right Rotate around Node:
     * - left child of node is new parent of node
     * - right child of left child is new left child of node
     * - parent of node is new parent of left child
     * - if parent is null, left child is new root
     * @param node Node to rotate
     */
    private void rotateRight(Node<T> node) {
        Node<T> left = node.getLeftChild();
        Node<T> parent = node.getParent();

        node.setLeftChild(left.getRightChild());
        node.setParent(left);
        left.setRightChild(node);
        left.setParent(parent);

        if (parent == null) {
            _root = left;
        }
    }

    /**
     * Left Rotate around Node:
     * - right child of node is new parent of node
     * - left child of right child is new right child of node
     * - parent of node is new parent of right child
     * - if parent is null, right child is new root
     * @param node Node to rotate
     */
    private void rotateLeft(Node<T> node) {
        Node<T> parent = node.getParent();
        Node<T> right = node.getRightChild();

        node.setRightChild(right.getLeftChild());
        node.setParent(right);
        right.setParent(parent);
        right.setLeftChild(node);

        if (parent == null) {
            _root = right;
        }
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


    public void traverseAll() {
        traverse(_root);
    }

    private void traverse(Node<T> nextnode) {
        if (nextnode == NIL) {
            return;
        }

        traverse(nextnode.getLeftChild());
        traverse(nextnode.getRightChild());

        System.out.println(nextnode.toString());
    }

}
