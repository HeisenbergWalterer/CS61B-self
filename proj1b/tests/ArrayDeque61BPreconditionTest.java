import jh61b.utils.Reflection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

public class ArrayDeque61BPreconditionTest {

    @Test
    @DisplayName("ArrayDeque61B has no fields besides backing array and primitives") // ArrayDeque只能有数组和基本数据类型
    void noNonTrivialFields() {
        List<Field> badFields = Reflection.getFields(ArrayDeque61B.class)
                .filter(f -> !(f.getType().isPrimitive() || f.getType().equals(Object[].class) || f.isSynthetic()))
                .toList();

        assertWithMessage("Found fields that are not array or primitives").that(badFields).isEmpty();
    }

    @Test
    void addFirstAndAddLastTest() {
        Deque61B<Integer> ad1 = new ArrayDeque61B<>(); // ArratDeque61B是继承于Deque61B
        ad1.addFirst(3);
        ad1.addFirst(2);
        ad1.addFirst(1);
        assertThat(ad1.toList()).containsExactly(1, 2, 3).inOrder();

        ad1.removeFirst();
        assertThat(ad1.toList()).containsExactly(2, 3).inOrder();

        ad1.removeLast();
        assertThat(ad1.toList()).containsExactly(2).inOrder();
        ad1.removeFirst();

        ad1.addLast(3);
        ad1.addLast(2);
        ad1.addLast(1);
        assertThat(ad1.toList()).containsExactly(3, 2, 1).inOrder();
    }

    @Test
    public void isEmptyTest() {
        Deque61B<Integer> ad1 = new ArrayDeque61B<>();
        Deque61B<Integer> ad2 = new ArrayDeque61B<>();

        ad1.addFirst(1);

        assertThat(ad1.isEmpty()).isFalse();
        assertThat(ad2.isEmpty()).isTrue();
    }

    @Test
    public void sizeTest() {
        Deque61B<Integer> ad1 = new ArrayDeque61B<>();

        assertThat(ad1.size() == 0).isTrue();

        ad1.addFirst(0);
        ad1.addLast(1);

        assertThat(ad1.size() == 2).isTrue();
    }

    @Test
    public void getTest(){
        Deque61B<Integer> ad1 = new ArrayDeque61B<>();
        ad1.addLast(0);
        ad1.addLast(2);

        assertThat(ad1.get(0) == 0).isTrue();
        assertThat(ad1.get(-1)).isNull();
        assertThat(ad1.get(2)).isNull();
    }

    @Test
    public void expandTest() {
        Deque61B<Integer> ad1 = new ArrayDeque61B<>();
        // 初始size为8
        ad1.addLast(0);
        ad1.addLast(1);
        ad1.addLast(2);
        ad1.addLast(3);
        ad1.addLast(4);
        ad1.addLast(5);
        ad1.addLast(6);
        ad1.addLast(7);
        ad1.addLast(8);
        ad1.addLast(9); // index = 9

        assertThat(ad1.get(9) == 9).isTrue();
    }

    public void reduceTest() {
        Deque61B<Integer> ad1 = new ArrayDeque61B<>();
        // 数据类型为Deque61B，只是使用ArrayDeque61B。
        // 所以ad1能调用的仅包括Deque61B中定义的接口（只是这些接口是由ArrayDeque61B所实现的）

        ad1.addLast(0);
        ad1.addLast(1);
        ad1.addLast(2);
        ad1.addLast(3);
        ad1.addLast(4);
        ad1.addLast(5);
        ad1.addLast(6);
        ad1.addLast(7);
        ad1.addLast(8);
        ad1.addLast(9);
        ad1.addLast(10);
        ad1.addLast(11);
        ad1.addLast(12);
        ad1.addLast(13);
        ad1.addLast(14);
        ad1.addLast(15);
        ad1.addLast(16);
        ad1.addLast(17);
        ad1.addLast(18);
        ad1.addLast(19); // 此时length = 20,size = 32,内存利用率 = 0.625

        ad1.removeFirst();

        assertThat(ad1.size() == 19).isTrue();
        assertThat(ad1.get(19) == 18).isTrue();

    }
}
