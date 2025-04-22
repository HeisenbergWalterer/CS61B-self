//package lec7_lists4.DIY;

/** This is a fill in the blanks version of the SLList class
 *  in case you want to try to figure out how to write it yourself.
 *  After writing your methods, you can run the AListTest file.
 */
public class AList {
    /** Creates an empty list. */
    private int length; // 当前存储内容部分的长度
    private int size = 100; // 初始大小为100
    private int[] item;

    public AList() {
        length = 0;
        item = new int[size];
    }

    /** Inserts X into the back of the list. */
    public void addLast(int x) {
        if (size == length) {
            size *= 2;
            int[] temp = item;
            this.item = new int[size];
            for (int i = 0; i < length; i++) {
                item[i] = temp[i];
            }
        }
        item[length] = x;
        length++;
    }

    /** Returns the item from the back of the list. */
    public int getLast() {
        if(length == 0){
            return length;
        } else {
            return item[length - 1];
        }
    }

    /** Gets the ith item in the list (0 is the front). */
    public int get(int i) {
        if(i < 0 || i >= length) {
            return 0;
        }
        return item[i];
    }

    /** Returns the number of items in the list. */
    public int size() {
        return length;
    }

    /** Deletes item from back of the list and
      * returns deleted item. */
    public int removeLast() {
        length--;
        return item[length];
    }
}
