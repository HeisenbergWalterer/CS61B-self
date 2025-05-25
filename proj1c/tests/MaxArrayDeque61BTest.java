import org.junit.jupiter.api.*;

import java.util.Comparator;
import deque.MaxArrayDeque61B;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

public class MaxArrayDeque61BTest {
    // 按字符串长度排序
    private static class StringLengthComparator implements Comparator<String> {
        public int compare(String a, String b) {
            return a.length() - b.length();
        }
    }

    // 按字符串字典序排序
    private static class StringAlphabeticalComparator implements Comparator<String> {
        public int compare(String a, String b) {
            return a.compareTo(b);
        }
    }

    // 按整数大小排序
    private static class IntegerComparator implements Comparator<Integer> {
        public int compare(Integer a, Integer b) {
            return a - b;
        }
    }

    @Test
    public void basicTest() {
        MaxArrayDeque61B<String> mad = new MaxArrayDeque61B<>(new StringLengthComparator());
        mad.addFirst("");
        mad.addFirst("2");
        mad.addFirst("fury road");
        assertThat(mad.max()).isEqualTo("fury road");
    }

    @Test
    public void testMaxWithDefaultComparator() {
        MaxArrayDeque61B<String> mad = new MaxArrayDeque61B<>(new StringLengthComparator());
        mad.addLast("a");
        mad.addLast("abc");
        mad.addLast("ab");

        // 使用默认比较器（按长度）
        assertThat(mad.max()).isEqualTo("abc");

        // 添加更长的字符串
        mad.addLast("abcde");
        assertThat(mad.max()).isEqualTo("abcde");

        // 移除最长的字符串
        mad.removeLast();
        assertThat(mad.max()).isEqualTo("abc");
    }

    @Test
    public void testMaxWithCustomComparator() {
        MaxArrayDeque61B<String> mad = new MaxArrayDeque61B<>(new StringLengthComparator());
        mad.addLast("abc");
        mad.addLast("xyz");
        mad.addLast("def");

        // 使用默认比较器（按长度）
        assertThat(mad.max()).isEqualTo("abc"); // 所有字符串长度相同，返回第一个

        // 使用自定义比较器（按字典序）
        assertThat(mad.max(new StringAlphabeticalComparator())).isEqualTo("xyz");
    }

    @Test
    public void testEmptyDeque() {
        MaxArrayDeque61B<String> mad = new MaxArrayDeque61B<>(new StringLengthComparator());

        // 空队列应该返回null
        assertThat(mad.max()).isNull();
        assertThat(mad.max(new StringAlphabeticalComparator())).isNull();
    }

    @Test
    public void testIntegerMaxArrayDeque() {
        MaxArrayDeque61B<Integer> mad = new MaxArrayDeque61B<>(new IntegerComparator());
        mad.addLast(5);
        mad.addLast(10);
        mad.addLast(3);

        // 使用默认比较器
        assertThat(mad.max()).isEqualTo(10);

        // 添加更大的值
        mad.addLast(15);
        assertThat(mad.max()).isEqualTo(15);

        // 移除最大值
        mad.removeLast();
        assertThat(mad.max()).isEqualTo(10);
    }

    @Test
    public void testMaxWithMultipleOperations() {
        MaxArrayDeque61B<String> mad = new MaxArrayDeque61B<>(new StringLengthComparator());

        // 测试空队列
        assertThat(mad.max()).isNull();

        // 添加元素
        mad.addFirst("medium");  // 长度为6
        assertThat(mad.max()).isEqualTo("medium");

        mad.addLast("longest string"); // 长度为14
        assertThat(mad.max()).isEqualTo("longest string");

        mad.addFirst("short"); // 长度为5
        assertThat(mad.max()).isEqualTo("longest string");

        // 移除最长的元素
        mad.removeLast();
        assertThat(mad.max()).isEqualTo("medium");

        // 使用不同的比较器
        assertThat(mad.max(new StringAlphabeticalComparator())).isEqualTo("short");
    }
}
