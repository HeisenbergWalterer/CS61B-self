import deque.ArrayDeque61B;
import deque.Deque61B;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Iterator;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * 全面测试ArrayDeque61B的功能
 */
public class ArrayDeque61BTest {

    @Test
    public void testAddFirstAndAddLast() {
        Deque61B<Integer> deque = new ArrayDeque61B<>();

        // 测试空队列
        assertThat(deque.isEmpty()).isTrue();
        assertThat(deque.size()).isEqualTo(0);

        // 添加元素到队首
        deque.addFirst(10);
        assertThat(deque.isEmpty()).isFalse();
        assertThat(deque.size()).isEqualTo(1);
        assertThat(deque.get(0)).isEqualTo(10);

        // 继续添加元素到队首
        deque.addFirst(20);
        assertThat(deque.size()).isEqualTo(2);
        assertThat(deque.get(0)).isEqualTo(20);
        assertThat(deque.get(1)).isEqualTo(10);

        // 添加元素到队尾
        deque.addLast(30);
        assertThat(deque.size()).isEqualTo(3);
        assertThat(deque.get(0)).isEqualTo(20);
        assertThat(deque.get(1)).isEqualTo(10);
        assertThat(deque.get(2)).isEqualTo(30);

        // 继续添加元素到队尾
        deque.addLast(40);
        assertThat(deque.size()).isEqualTo(4);
        assertThat(deque.get(3)).isEqualTo(40);
    }

    @Test
    public void testToList() {
        Deque61B<String> deque = new ArrayDeque61B<>();

        // 空队列转为列表
        List<String> emptyList = deque.toList();
        assertThat(emptyList).isEmpty();

        // 添加元素后转为列表
        deque.addLast("a");
        deque.addLast("b");
        deque.addLast("c");

        List<String> list = deque.toList();
        assertThat(list).containsExactly("a", "b", "c").inOrder();
    }

    @Test
    public void testRemoveFirstAndRemoveLast() {
        Deque61B<Integer> deque = new ArrayDeque61B<>();

        // 空队列的删除操作
        assertThat(deque.removeFirst()).isNull();
        assertThat(deque.removeLast()).isNull();

        // 添加元素
        deque.addLast(10);
        deque.addLast(20);
        deque.addLast(30);
        deque.addLast(40);

        // 从队首删除
        assertThat(deque.removeFirst()).isEqualTo(10);
        assertThat(deque.size()).isEqualTo(3);
        assertThat(deque.get(0)).isEqualTo(20);

        // 从队尾删除
        assertThat(deque.removeLast()).isEqualTo(40);
        assertThat(deque.size()).isEqualTo(2);
        assertThat(deque.get(1)).isEqualTo(30);

        // 继续删除
        assertThat(deque.removeFirst()).isEqualTo(20);
        assertThat(deque.removeLast()).isEqualTo(30);
        assertThat(deque.isEmpty()).isTrue();
    }

    @Test
    public void testGet() {
        Deque61B<String> deque = new ArrayDeque61B<>();

        // 空队列的获取操作
        assertThat(deque.get(0)).isNull();
        assertThat(deque.get(-1)).isNull();

        // 添加元素
        deque.addLast("a");
        deque.addLast("b");
        deque.addLast("c");

        // 有效索引获取
        assertThat(deque.get(0)).isEqualTo("a");
        assertThat(deque.get(1)).isEqualTo("b");
        assertThat(deque.get(2)).isEqualTo("c");

        // 无效索引获取
        assertThat(deque.get(3)).isNull();
        assertThat(deque.get(-1)).isNull();
    }

    @Test
    public void testIterator() {
        Deque61B<Integer> deque = new ArrayDeque61B<>();

        // 测试空队列的迭代器
        Iterator<Integer> emptyIterator = deque.iterator();
        assertThat(emptyIterator.hasNext()).isFalse();
        assertThrows(java.util.NoSuchElementException.class, emptyIterator::next);

        // 添加元素
        deque.addLast(10);
        deque.addLast(20);
        deque.addLast(30);

        // 测试迭代器
        Iterator<Integer> iterator = deque.iterator();
        assertThat(iterator.hasNext()).isTrue();
        assertThat(iterator.next()).isEqualTo(10);
        assertThat(iterator.next()).isEqualTo(20);
        assertThat(iterator.next()).isEqualTo(30);
        assertThat(iterator.hasNext()).isFalse();

        // 迭代结束后再调用next()应抛出异常
        assertThrows(java.util.NoSuchElementException.class, iterator::next);

        // 测试增强型for循环
        int index = 0;
        int[] expected = {10, 20, 30};
        for (int item : deque) {
            assertThat(item).isEqualTo(expected[index++]);
        }
    }

    @Test
    public void testEquals() {
        Deque61B<String> deque1 = new ArrayDeque61B<>();
        Deque61B<String> deque2 = new ArrayDeque61B<>();
        Deque61B<String> deque3 = new ArrayDeque61B<>();

        // 空队列相等
        assertThat(deque1.equals(deque2)).isTrue();

        // 添加相同元素
        deque1.addLast("a");
        deque1.addLast("b");
        deque1.addLast("c");

        deque2.addLast("a");
        deque2.addLast("b");
        deque2.addLast("c");

        deque3.addLast("x");
        deque3.addLast("y");
        deque3.addLast("z");

        // 相同内容的队列相等
        assertThat(deque1.equals(deque2)).isTrue();

        // 不同内容的队列不相等
        assertThat(deque1.equals(deque3)).isFalse();

        // 与null比较
        assertThat(deque1.equals(null)).isFalse();

        // 与自身比较
        assertThat(deque1.equals(deque1)).isTrue();
    }

    @Test
    public void testToString() {
        Deque61B<Integer> deque = new ArrayDeque61B<>();

        // 空队列的字符串表示
        assertThat(deque.toString()).isEqualTo("[]");

        // 添加元素
        deque.addLast(10);
        assertThat(deque.toString()).isEqualTo("[10]");

        deque.addLast(20);
        deque.addLast(30);
        assertThat(deque.toString()).isEqualTo("[10, 20, 30]");

        // 删除元素
        deque.removeFirst();
        assertThat(deque.toString()).isEqualTo("[20, 30]");
    }

    @Test
    public void testResizing() {
        Deque61B<Integer> deque = new ArrayDeque61B<>();

        // 添加许多元素测试扩容
        for (int i = 0; i < 20; i++) {
            deque.addLast(i);
        }

        // 验证所有元素都正确
        for (int i = 0; i < 20; i++) {
            assertThat(deque.get(i)).isEqualTo(i);
        }

        // 删除大部分元素测试缩容
        for (int i = 0; i < 15; i++) {
            deque.removeFirst();
        }

        // 验证剩余元素正确
        for (int i = 0; i < 5; i++) {
            assertThat(deque.get(i)).isEqualTo(i + 15);
        }
    }

    @Test
    public void testCircularBehavior() {
        Deque61B<String> deque = new ArrayDeque61B<>();

        // 测试循环行为 - 从前面添加再从后面删除
        for (int i = 0; i < 8; i++) {
            deque.addFirst("item" + i);
        }

        for (int i = 0; i < 8; i++) {
            assertThat(deque.removeLast()).isEqualTo("item" + i);
        }

        // 测试循环行为 - 从后面添加再从前面删除
        for (int i = 0; i < 8; i++) {
            deque.addLast("item" + i);
        }

        for (int i = 0; i < 8; i++) {
            assertThat(deque.removeFirst()).isEqualTo("item" + i);
        }

        // 交替添加删除
        deque.addFirst("a");
        deque.addLast("b");
        deque.addFirst("c");
        deque.addLast("d");

        assertThat(deque.removeFirst()).isEqualTo("c");
        assertThat(deque.removeLast()).isEqualTo("d");
        assertThat(deque.removeFirst()).isEqualTo("a");
        assertThat(deque.removeLast()).isEqualTo("b");
    }

    @Test
    public void testMixedOperations() {
        Deque61B<String> deque = new ArrayDeque61B<>();

        // 混合操作测试
        deque.addFirst("first");
        assertThat(deque.get(0)).isEqualTo("first");

        deque.addLast("last");
        assertThat(deque.get(0)).isEqualTo("first");
        assertThat(deque.get(1)).isEqualTo("last");

        deque.addFirst("new_first");
        assertThat(deque.get(0)).isEqualTo("new_first");

        assertThat(deque.removeFirst()).isEqualTo("new_first");
        assertThat(deque.get(0)).isEqualTo("first");

        assertThat(deque.removeLast()).isEqualTo("last");
        assertThat(deque.size()).isEqualTo(1);

        deque.addFirst("a");
        deque.addFirst("b");
        deque.addLast("c");

        // 验证顺序：b, a, first, c
        assertThat(deque.get(0)).isEqualTo("b");
        assertThat(deque.get(1)).isEqualTo("a");
        assertThat(deque.get(2)).isEqualTo("first");
        assertThat(deque.get(3)).isEqualTo("c");
    }
}
