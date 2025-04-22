import java.util.ArrayList;
import java.util.List;
import java.lang.Math;

public class ArrayDeque61B<T> implements Deque61B<T> {
    private int length;
    private int size = 8; // 数组大小
    private T[] item;
    private int head, tail;

    ArrayDeque61B() {
        item = (T[]) new Object[size];
        length = 0;
        head = 0; // 指向头元素位置
        tail = 0; // 指向末元素位置
    }

    // 需添加扩大尺寸的功能

    T[] expand(T[] item, int size) {
        T[] newItem = (T[]) new Object[size * 2];
        for(int i = 0; i < item.length; i++) { // item已满，所以此时size = length
            newItem[i] = get(i);
        }
        return newItem;
    }

    @Override
    public void addFirst(T x) {
        if(length == 0) {
            item[head] = x;
            length++;
            return;
        } else if(length == size) {
            item = expand(item, size);
            size *= 2;
        }
        head = Math.floorMod(head - 1, size);
        item[head] = x; // head 指向的是头元素的位置
        length++;
    }

    @Override
    public void addLast(T x) {
        if(length == 0){
            item[tail] = x;
            length++;
            return;
        } else if(length == size) {
            item = expand(item, size);
            size *= 2;
        }
        tail = Math.floorMod(tail + 1, size);
        item[tail] = x;
        length++;
    }

    @Override
    public List<T> toList() {
        if(isEmpty()) {
            return List.of();
        }
        List<T> returnList = new ArrayList<>(); // 使用 List 接口作为变量类型，具体实现类根据需求选择
        for(int i = 0; i < length; i++) {
            returnList.addLast(get(i));
        }
        return returnList;
    }

    @Override
    public boolean isEmpty() {
        if(length == 0){
            return true;
        } else{
            return false;
        }
    }

    @Override
    public int size() {
        return length;
    }


    // 需添加缩小尺寸的功能

    T[] reduce(T[] item, int size) {
        int newSize = item.length * 4; // newSize = length * 4 : 内存利用率始终为25%
        T[] newItem = (T[]) new Object[newSize];

        for(int i = 0; i < item.length; i++) {
            newItem[i] = get(i);
        }
        return newItem;
    }


    @Override
    public T removeFirst() {
        if(isEmpty()) {
            return null;
        }

        T returnT = item[head];
        if(length != 1) {
            head = Math.floorMod(head + 1, size);
        }
        length--;
        item[Math.floorMod(head - 1, size)] = null; // 移除对元素的引用

        // remove之后，检查内存利用率
        if (size > length * 4 && size > 15) {
            item = reduce(item, size);
            size = length * 4;
        }

        return returnT;
    }

    @Override
    public T removeLast() {
        if(isEmpty()) {
            return null;
        }

        T returnT = item[tail];
        if(length != 1) {
            tail = Math.floorMod(tail - 1, size);
        }
        length--;
        item[Math.floorMod(tail + 1, size)] = null;

        // remove之后，检查内存利用率
        if (size > length * 4 && size > 15) {
            item = reduce(item, size);
            size = length * 4;
        }

        return returnT;
    }

    @Override
    public T get(int index) { // index 从0开始计数
        if(index < 0 || index >= length) {
            return null;
        }
        return item[Math.floorMod(head + index, size)];
    }

    @Override
    public T getRecursive(int index) {
        throw new UnsupportedOperationException("No need to implement getRecursive for proj 1b");
    }
}
