package deque;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.lang.Math;

public class ArrayDeque61B<T> implements Deque61B<T> {
    private int length;
    private int size = 8; // 数组大小
    private T[] item;
    private int head, tail;

    @SuppressWarnings("unchecked")
    public ArrayDeque61B() {
        item = (T[]) new Object[size];
        length = 0;
        head = 0; // 指向头元素位置
        tail = 0; // 指向末元素位置
    }

    @SuppressWarnings("unchecked")
    T[] expand(T[] item, int size) {
        T[] newItem = (T[]) new Object[size * 2];
        for(int i = 0; i < item.length; i++) { // item已满，所以此时size = length
            newItem[i] = get(i);
        }
        head = 0;
        tail = (length == 0) ? 0 : length - 1;

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

    @SuppressWarnings("unchecked")
    T[] reduce(T[] item, int size) {
        // 计算新的数组大小，确保它至少为16，或者是当前长度的2倍
        int newSize = Math.max(16, length * 2);

        // 创建新数组
        T[] newItem = (T[]) new Object[newSize];

        // 正确处理循环数组：将所有实际元素按顺序复制到新数组的开头
        for(int i = 0; i < length; i++) {
            // 使用get方法获取有效元素，它会处理循环索引的计算
            newItem[i] = get(i);
        }

        // 重置head和tail指针，使数组中不再有"空洞"
        head = 0;
        tail = (length == 0) ? 0 : length - 1;

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

    // equals重载
    @Override
    public boolean equals(Object other) {
        if(this == other) {
            return true;
        }
        if(other instanceof ArrayDeque61B<?>) {
            ArrayDeque61B ad = (ArrayDeque61B) other;

            if(this.size() != ad.size()) {
                return false;
            }

            Iterator<T> Iter1 = this.iterator();
            Iterator<T> Iter2 = ad.iterator();
            while(Iter1.hasNext() && Iter2.hasNext()) {
                if(Iter1.next() != Iter2.next()) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    // 迭代器实现
    @Override
    public Iterator<T> iterator() {
        return new ArrayDeque61BIterator();
    }
    private class ArrayDeque61BIterator implements Iterator<T> {
        int index = 0;

        @Override
        public boolean hasNext() {
            return index < length;
        }

        @Override
        public T next() {
            if(!hasNext()) {
                throw new java.util.NoSuchElementException();
            }
            T returnItem = get(index);
            index++;

            return returnItem;
        }
    }

    // toString重载
    @Override
    public String toString() {
        return this.toList().toString();
    }
}
