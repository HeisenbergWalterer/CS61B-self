import deque.Deque61B;
import deque.LinkedListDeque61B;
import deque.ArrayDeque61B;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class Test_Iterator_Equals_ToString {

    @Test
    public void testLinkedListDequeIterator() {
        Deque61B<String> lld1 = new LinkedListDeque61B<>();
        lld1.addLast("front");
        lld1.addLast("middle");
        lld1.addLast("back");

        int index = 0;
        for(String s : lld1) {
            assertThat(lld1.get(index)).isEqualTo(s);
            index++;
        }

        Iterator<String> iterator = lld1.iterator();
        assertThat(iterator.hasNext()).isTrue();
        assertThat(iterator.next()).isEqualTo("front");
        assertThat(iterator.next()).isEqualTo("middle");
        assertThat(iterator.next()).isEqualTo("back");
        assertThat(iterator.hasNext()).isFalse();

        // 测试迭代结束后调用 next() 抛出异常
        assertThrows(java.util.NoSuchElementException.class, iterator::next);
    }

    // 测试Iterator
    @Test
    public void testArrayDequeIterator() {
        Deque61B<String> ad1 = new ArrayDeque61B<>();
        ad1.addLast("front");
        ad1.addLast("middle");
        ad1.addLast("back");

        int index = 0;
        for(String s : ad1) {
            assertThat(ad1.get(index)).isEqualTo(s);
            index++;
        }

        Iterator<String> iterator = ad1.iterator();
        assertThat(iterator.hasNext()).isTrue();
        assertThat(iterator.next()).isEqualTo("front");
        assertThat(iterator.next()).isEqualTo("middle");
        assertThat(iterator.next()).isEqualTo("back");
        assertThat(iterator.hasNext()).isFalse();

        // 测试迭代结束后调用 next() 抛出异常
        assertThrows(java.util.NoSuchElementException.class, iterator::next);
    }

    @Test
    public void testEmptyDequeIterator() {
        Deque61B<String> lld1 = new LinkedListDeque61B<>();
        Iterator<String> iterator1 = lld1.iterator();
        assertThat(iterator1.hasNext()).isFalse();
        assertThrows(java.util.NoSuchElementException.class, iterator1::next);

        Deque61B<String> ad1 = new ArrayDeque61B<>();
        Iterator<String> iterator2 = ad1.iterator();
        assertThat(iterator2.hasNext()).isFalse();
        assertThrows(java.util.NoSuchElementException.class, iterator2::next);
    }

    @Test
    public void testIteratorAfterModification() {
        // 测试在修改Deque后，迭代器是否正确反映变化
        Deque61B<String> lld = new LinkedListDeque61B<>();
        lld.addLast("a");
        lld.addLast("b");

        Iterator<String> iterator = lld.iterator();
        assertThat(iterator.next()).isEqualTo("a");

        // 添加新元素
        lld.addLast("c");

        // 获取新的迭代器
        Iterator<String> newIterator = lld.iterator();
        assertThat(newIterator.next()).isEqualTo("a");
        assertThat(newIterator.next()).isEqualTo("b");
        assertThat(newIterator.next()).isEqualTo("c");

        // 测试ArrayDeque
        Deque61B<String> ad = new ArrayDeque61B<>();
        ad.addLast("a");
        ad.addLast("b");

        Iterator<String> adIterator = ad.iterator();
        assertThat(adIterator.next()).isEqualTo("a");

        // 添加新元素
        ad.addLast("c");

        // 获取新的迭代器
        Iterator<String> newAdIterator = ad.iterator();
        assertThat(newAdIterator.next()).isEqualTo("a");
        assertThat(newAdIterator.next()).isEqualTo("b");
        assertThat(newAdIterator.next()).isEqualTo("c");
    }

    // 测试Equals
    @Test
    public void testLinkedListDequeEquals() {
        Deque61B<String> lld1 = new LinkedListDeque61B<>();
        Deque61B<String> lld2 = new LinkedListDeque61B<>();
        Deque61B<String> lld3 = new LinkedListDeque61B<>();

        lld1.addLast("a");
        lld1.addLast("b");
        lld1.addLast("c");

        lld2.addLast("a");
        lld2.addLast("b");
        lld2.addLast("c");

        lld3.addLast("x");
        lld3.addLast("y");
        lld3.addLast("z");

        assertThat(lld1.equals(lld2)).isTrue();
        assertThat(lld1.equals(lld3)).isFalse();
        assertThat(lld1.equals(null)).isFalse();
    }

    @Test
    public void testArrayDequeEquals() {
        Deque61B<String> ad1 = new ArrayDeque61B<>();
        Deque61B<String> ad2 = new ArrayDeque61B<>();
        Deque61B<String> ad3 = new ArrayDeque61B<>();

        ad1.addLast("a");
        ad1.addLast("b");
        ad1.addLast("c");

        ad2.addLast("a");
        ad2.addLast("b");
        ad2.addLast("c");

        ad3.addLast("x");
        ad3.addLast("y");
        ad3.addLast("z");

        assertThat(ad1.equals(ad2)).isTrue();
        assertThat(ad1.equals(ad3)).isFalse();
        assertThat(ad1.equals(null)).isFalse();
    }

    @Test
    public void testDifferentTypesEquals() {
        // 测试不同类型的Deque之间的equals方法
        Deque61B<String> lld = new LinkedListDeque61B<>();
        Deque61B<String> ad = new ArrayDeque61B<>();

        lld.addLast("a");
        lld.addLast("b");
        lld.addLast("c");

        ad.addLast("a");
        ad.addLast("b");
        ad.addLast("c");

        // 虽然内容相同，但类型不同，根据equals的实现可能返回true或false
        // 这里只是测试方法不会抛出异常
        lld.equals(ad);
        ad.equals(lld);
    }

    @Test
    public void testEqualsWithDifferentSizes() {
        Deque61B<String> lld1 = new LinkedListDeque61B<>();
        Deque61B<String> lld2 = new LinkedListDeque61B<>();

        lld1.addLast("a");
        lld1.addLast("b");

        lld2.addLast("a");
        lld2.addLast("b");
        lld2.addLast("c");

        assertThat(lld1.equals(lld2)).isFalse();

        Deque61B<String> ad1 = new ArrayDeque61B<>();
        Deque61B<String> ad2 = new ArrayDeque61B<>();

        ad1.addLast("a");
        ad1.addLast("b");

        ad2.addLast("a");
        ad2.addLast("b");
        ad2.addLast("c");

        assertThat(ad1.equals(ad2)).isFalse();
    }

    // 测试toString
    @Test
    public void testLinkedListDequeToString() {
        Deque61B<String> lld = new LinkedListDeque61B<>();
        lld.addLast("a");
        lld.addLast("b");
        lld.addLast("c");

        assertThat(lld.toString()).isEqualTo("[a, b, c]");

        lld.removeFirst();
        assertThat(lld.toString()).isEqualTo("[b, c]");

        lld.removeLast();
        assertThat(lld.toString()).isEqualTo("[b]");
    }

    @Test
    public void testArrayDequeToString() {
        Deque61B<String> ad = new ArrayDeque61B<>();
        ad.addLast("a");
        ad.addLast("b");
        ad.addLast("c");

        assertThat(ad.toString()).isEqualTo("[a, b, c]");

        ad.removeFirst();
        assertThat(ad.toString()).isEqualTo("[b, c]");

        ad.removeLast();
        assertThat(ad.toString()).isEqualTo("[b]");
    }

    @Test
    public void testEmptyDequeToString() {
        Deque61B<String> lld = new LinkedListDeque61B<>();
        assertThat(lld.toString()).isEqualTo("[]");

        Deque61B<String> ad = new ArrayDeque61B<>();
        assertThat(ad.toString()).isEqualTo("[]");
    }

    @Test
    public void testToStringWithOneElement() {
        Deque61B<String> lld = new LinkedListDeque61B<>();
        lld.addLast("single");
        assertThat(lld.toString()).isEqualTo("[single]");

        Deque61B<String> ad = new ArrayDeque61B<>();
        ad.addLast("single");
        assertThat(ad.toString()).isEqualTo("[single]");
    }

    @Test
    public void testToStringWithNonStringElements() {
        Deque61B<Integer> lld = new LinkedListDeque61B<>();
        lld.addLast(1);
        lld.addLast(2);
        lld.addLast(3);
        assertThat(lld.toString()).isEqualTo("[1, 2, 3]");

        Deque61B<Integer> ad = new ArrayDeque61B<>();
        ad.addLast(1);
        ad.addLast(2);
        ad.addLast(3);
        assertThat(ad.toString()).isEqualTo("[1, 2, 3]");
    }
}
