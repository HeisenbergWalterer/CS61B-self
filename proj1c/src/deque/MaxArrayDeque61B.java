package deque;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class MaxArrayDeque61B<T> implements Deque61B<T> {
    private int length;
    private int size = 8; // 数组大小
    private T[] item;
    private int head, tail;
    Comparator<T> comparator;

    @SuppressWarnings("unchecked")
//    public MaxArrayDeque61B() {
//        item = (T[]) new Object[size];
//        length = 0;
//        head = 0; // 指向头元素位置
//        tail = 0; // 指向末元素位置
//    }

    // Comparator用于存储自定义排序规则
    // 而该接口下的compare即是用于按自定义规则比较两元素
    public MaxArrayDeque61B(Comparator<T> c) {
        item = (T[]) new Object[size];
        length = 0;
        head = 0; // 指向头元素位置
        tail = 0; // 指向末元素位置
        comparator = c;
    }

    public T max() {
        if(this.isEmpty()) {
            return null;
        }
        T maxItem = this.get(0);
        for(int i = 1; i < length; i++) {
            T currentItem = this.get(i);
            if(comparator.compare(currentItem, maxItem) > 0) {
                maxItem = currentItem;
            }
        }
        return maxItem;
    }

    public T max(Comparator<T> c) {
        if(this.isEmpty()) {
            return null;
        }
        T maxItem = this.get(0);
        for(int i = 1; i < length; i++) {
            T currentItem = this.get(i);
            if(c.compare(currentItem, maxItem) > 0) {
                maxItem = currentItem;
            }
        }
        return maxItem;
    }

    @SuppressWarnings("unchecked")
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

    @SuppressWarnings("unchecked")
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

    // equals重载
    @Override
    public boolean equals(Object other) {
        if(this == other) {
            return true;
        }
        if(other instanceof MaxArrayDeque61B<?>) {
            MaxArrayDeque61B ad = (MaxArrayDeque61B) other;

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
        return new MaxArrayDeque61B.MaxArrayDeque61BIterator();
    }
    private class MaxArrayDeque61BIterator implements Iterator<T> {
        int index;

        @Override
        public boolean hasNext() {
            return index < size;
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
