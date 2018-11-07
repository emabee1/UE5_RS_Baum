public class Node<T> {

    private Node<T> _parent;
    private Node<T> _leftChild;
    private Node<T> _rightChild;
    private boolean _color;
    private String _key;
    private T _object;

    public Node(Node<T> leftChild, Node<T> rightChild,Node<T> parent, boolean color, String key, T object) {
        _parent = parent;
        _leftChild = leftChild;
        _rightChild = rightChild;
        _color = color;
        _key = key;
        _object = object;
    }

    public Node<T> getParent() {
        return _parent;
    }

    public Node<T> getGrandparent(){
        return _parent.getParent();
    }

    public Node<T> getUncle(){
        if(getParent() != null && getGrandparent() != null) {
            if (getParent().equals(getGrandparent()._leftChild)) {
                return getGrandparent()._rightChild;
            } else {
                return getGrandparent()._leftChild;
            }
        }else {
            return null;
        }
    }

    public Node<T> getLeftChild() {
        return _leftChild;
    }

    public Node<T> getRightChild() {
        return _rightChild;
    }

    public boolean getColor() {
        return _color;
    }

    public String getKey() {
        return _key;
    }

    public T getObject() {
        return _object;
    }

    public void setParent(Node<T> parent) {
        _parent = parent;
    }

    public void setLeftChild(Node<T> leftChild) {
        _leftChild = leftChild;
    }

    public void setRightChild(Node<T> rightChild) {
        _rightChild = rightChild;
    }

    public void setColor(boolean color) {
        _color = color;
    }

    public void setKey(String key) {
        _key = key;
    }

    public void setObject(T object) {
        _object = object;
    }

    @Override
    public String toString(){
        return "Key: " + _key  + " Color: " + (_color ? "Black":"Red");
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Node){
            Node in = (Node) obj;
            if(in.getKey() == _key){
                return true;
            }
        }
        return false;
    }
}