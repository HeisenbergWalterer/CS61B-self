import java.util.List;
import java.util.ArrayList;

public class LinkedListDeque61B<T> implements Deque61B<T>{ // LinkedListDeque61B是对Deque61B接口的实现
    private Node sentinel;
    int size;

    public class Node{ // 节点内容
        public T item;
        public Node next;
        public Node prev;

        public Node(T item, Node prev, Node next){
            this.item = item;
            this.prev = prev;
            this.next = next;
        }
    }
    // 无参构造函数
    public LinkedListDeque61B(){
        sentinel = new Node(null, null, null);
        sentinel.next = sentinel; // DLList为空时，sentinel指向自身
        sentinel.prev = sentinel;
        int size = 0;
    }

    @Override
    // 头插法：将元素插入sentinel后
    public void addFirst(T x) {
        size++;
        Node current = new Node(x, sentinel, sentinel.next);
        // 空与非空的处理方式相同
        sentinel.next.prev = current;
        sentinel.next = current;

    }

    @Override
    public void addLast(T x) {
        size++;
        Node current = new Node(x, sentinel.prev, sentinel);
        sentinel.prev.next = current;
        sentinel.prev = current;
    }

    @Override
    public List<T> toList() {
        List<T> returnList = new ArrayList<>();
        Node current = sentinel.next;
        if(isEmpty()){
            return List.of();
        }
        while(current != sentinel){
            returnList.addLast(current.item);
            current = current.next;
        }
        return returnList;
    }

    @Override
    public boolean isEmpty() {
        if(size() == 0){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public int size() {
        int size = 0;
        Node current = sentinel.next;
        while(current != sentinel){
            size++;
            current = current.next;
        }
        return size;
    }

    @Override
    public T removeFirst() {
        if(isEmpty()){
            return null;
        } else{
            Node toDelete = sentinel.next;
            toDelete.next.prev = sentinel;
            sentinel.next = toDelete.next;

            return toDelete.item;
        }
    }

    @Override
    public T removeLast() {
        if(isEmpty()){
            return null;
        }else{
            Node toDelete = sentinel.prev;
            toDelete.prev.next = sentinel;
            sentinel.prev = toDelete.prev;

            return toDelete.item;
        }
    }

    @Override
    // 迭代
    public T get(int index) { // index从0开始计数
        if(index < 0 || index >= size()){
            return null;
        }else{
            int cur = 0;
            Node temp = sentinel.next; // sentinel指向的是实际存储数据的第一个节点
            while(cur != index) {
                cur++;
                temp = temp.next;
            }
            return temp.item;
        }
    }

    @Override
    // 递归
    public T getRecursive(int index) {
        if(index < 0 || index >= size()){
            return null;
        }else {
            return getRecursiveHelper(sentinel.next, index);
        }
    }

    public T getRecursiveHelper(Node current, int index){
        if(index == 0){
            return current.item;
        }else{
            return getRecursiveHelper(current.next, index - 1);
        }
    }


}
