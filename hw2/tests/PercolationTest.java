import org.junit.jupiter.api.Test;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Random;

public class PercolationTest {

    /**
     * Enum to represent the state of a cell in the grid. Use this enum to help you write tests.
     * <p>
     * (0) CLOSED: isOpen() returns true, isFull() return false
     * <p>
     * (1) OPEN: isOpen() returns true, isFull() returns false
     * <p>
     * (2) INVALID: isOpen() returns false, isFull() returns true
     *              (This should not happen! Only open cells should be full.)
     * <p>
     * (3) FULL: isOpen() returns true, isFull() returns true
     * <p>
     */
    private enum Cell {
        CLOSED, OPEN, INVALID, FULL
    }

    /**
     * Creates a Cell[][] based off of what Percolation p returns.
     * Use this method in your tests to see if isOpen and isFull are returning the
     * correct things.
     */
    private static Cell[][] getState(int N, Percolation p) {
        Cell[][] state = new Cell[N][N];
        for (int r = 0; r < N; r++) {
            for (int c = 0; c < N; c++) {
                int open = p.isOpen(r, c) ? 1 : 0;
                int full = p.isFull(r, c) ? 2 : 0;
                state[r][c] = Cell.values()[open + full];
            }
        }
        return state;
    }

    @Test
    public void basicTest() {
        int N = 5;
        Percolation p = new Percolation(N);
        // open sites at (r, c) = (0, 1), (2, 0), (3, 1), etc. (0, 0) is top-left
        int[][] openSites = {
                {0, 1},
                {2, 0},
                {3, 1},
                {4, 1},
                {1, 0},
                {1, 1}
        };
        Cell[][] expectedState = {
                {Cell.CLOSED, Cell.FULL, Cell.CLOSED, Cell.CLOSED, Cell.CLOSED},
                {Cell.FULL, Cell.FULL, Cell.CLOSED, Cell.CLOSED, Cell.CLOSED},
                {Cell.FULL, Cell.CLOSED, Cell.CLOSED, Cell.CLOSED, Cell.CLOSED},
                {Cell.CLOSED, Cell.OPEN, Cell.CLOSED, Cell.CLOSED, Cell.CLOSED},
                {Cell.CLOSED, Cell.OPEN, Cell.CLOSED, Cell.CLOSED, Cell.CLOSED}
        };
        for (int[] site : openSites) {
            p.open(site[0], site[1]);
        }
        assertThat(getState(N, p)).isEqualTo(expectedState);
        assertThat(p.percolates()).isFalse();
    }

    @Test
    public void oneByOneTest() {
        int N = 1;
        Percolation p = new Percolation(N);
        p.open(0, 0);
        Cell[][] expectedState = {
                {Cell.FULL}
        };
        assertThat(getState(N, p)).isEqualTo(expectedState);
        assertThat(p.percolates()).isTrue();
    }

    @Test
    public void testPercolation() {
        int N = 5;
        Percolation p = new Percolation(N);
        // 创建一条从顶部到底部的开放路径
        for (int i = 0; i < N; i++) {
            p.open(i, 2);  // 在中间列创建一条路径
        }

        Cell[][] expectedState = {
                {Cell.CLOSED, Cell.CLOSED, Cell.FULL, Cell.CLOSED, Cell.CLOSED},
                {Cell.CLOSED, Cell.CLOSED, Cell.FULL, Cell.CLOSED, Cell.CLOSED},
                {Cell.CLOSED, Cell.CLOSED, Cell.FULL, Cell.CLOSED, Cell.CLOSED},
                {Cell.CLOSED, Cell.CLOSED, Cell.FULL, Cell.CLOSED, Cell.CLOSED},
                {Cell.CLOSED, Cell.CLOSED, Cell.FULL, Cell.CLOSED, Cell.CLOSED}
        };

        Cell[][] nowState = getState(N, p);
        assertThat(getState(N, p)).isEqualTo(expectedState);
        assertThat(p.percolates()).isTrue();
    }

    @Test
    public void testLargeGrid() {
        int N = 10;
        Percolation p = new Percolation(N);

        // 开放左侧边界的所有位置
        for (int i = 0; i < N; i++) {
            p.open(i, 0);
        }

        // 检查是否渗透
        assertThat(p.percolates()).isTrue();

        // 验证第一列的所有位置都应该被填满
        for (int i = 0; i < N; i++) {
            assertThat(p.isFull(i, 0)).isTrue();
        }
    }

    @Test
    public void testZigZagPercolation() {
        int N = 5;
        Percolation p = new Percolation(N);

        // 创建一条之字形的渗透路径
        p.open(0, 0);  // 顶部
        p.open(1, 0);
        p.open(1, 1);
        p.open(2, 1);
        p.open(2, 2);
        p.open(3, 2);
        p.open(3, 3);
        p.open(4, 3);  // 底部

        Cell[][] expectedState = {
                {Cell.FULL, Cell.CLOSED, Cell.CLOSED, Cell.CLOSED, Cell.CLOSED},
                {Cell.FULL, Cell.FULL, Cell.CLOSED, Cell.CLOSED, Cell.CLOSED},
                {Cell.CLOSED, Cell.FULL, Cell.FULL, Cell.CLOSED, Cell.CLOSED},
                {Cell.CLOSED, Cell.CLOSED, Cell.FULL, Cell.FULL, Cell.CLOSED},
                {Cell.CLOSED, Cell.CLOSED, Cell.CLOSED, Cell.FULL, Cell.CLOSED}
        };

        assertThat(getState(N, p)).isEqualTo(expectedState);
        assertThat(p.percolates()).isTrue();
    }

    @Test
    public void testNoPercolation() {
        int N = 6;
        Percolation p = new Percolation(N);

        // 开放顶部的一些单元格
        p.open(0, 1);
        p.open(0, 2);
        p.open(0, 3);

        // 开放底部的一些单元格，但中间没有连接
        p.open(5, 0);
        p.open(5, 4);
        p.open(5, 5);

        // 开放中间的一些单元格，但不形成连接
        p.open(2, 2);
        p.open(2, 3);
        p.open(3, 1);
        p.open(3, 4);

        // 验证没有渗透
        assertThat(p.percolates()).isFalse();

        // 顶部的单元格应该是FULL
        assertThat(p.isFull(0, 1)).isTrue();
        assertThat(p.isFull(0, 2)).isTrue();
        assertThat(p.isFull(0, 3)).isTrue();

        // 底部的单元格应该是OPEN而不是FULL
        assertThat(p.isOpen(5, 0)).isTrue();
        assertThat(p.isFull(5, 0)).isFalse();
    }

    @Test
    public void testBackwash() {
        int N = 5;
        Percolation p = new Percolation(N);

        // 创建一条从顶部到底部的路径
        for (int i = 0; i < N; i++) {
            p.open(i, 1);
        }

        // 在底部开放一个额外的单元格，但它不应该被填满
        p.open(N-1, 3);

        // 验证单元格(N-1, 3)是开放的但不是填满的
        assertThat(p.isOpen(N-1, 3)).isTrue();
        assertThat(p.isFull(N-1, 3)).isFalse();

        // 验证渗透确实发生了
        assertThat(p.percolates()).isTrue();
    }

    @Test
    public void testComplexBackwash() {
        int N = 6;
        Percolation p = new Percolation(N);

        // 创建一条从顶部到底部的主渗透路径
        for (int i = 0; i < N; i++) {
            p.open(i, 2);
        }

        // 在底部创建一个"T"形连接，可能导致回流问题
        p.open(N-1, 0);
        p.open(N-1, 4);

        // 检查渗透状态
        assertThat(p.percolates()).isTrue();

        // 验证中间列是满的（与顶部相连）
        for (int i = 0; i < N; i++) {
            assertThat(p.isFull(i, 2)).isTrue();
        }

        // 关键测试：验证底部的"T"形连接点不应该是满的
        // 如果实现存在回流问题，这些点会错误地被标记为满
        assertThat(p.isOpen(N-1, 0)).isTrue();
        assertThat(p.isFull(N-1, 0)).isFalse(); // 应该为false，如果为true则存在回流问题

        assertThat(p.isOpen(N-1, 4)).isTrue();
        assertThat(p.isFull(N-1, 4)).isFalse(); // 应该为false，如果为true则存在回流问题

        // 创建从顶部到这些单元格的连接，现在它们应该变成满的
        for (int i = 0; i < N; i++) {
            p.open(i, 0);
        }

        // 现在验证左侧的单元格应该变成满的
        assertThat(p.isFull(N-1, 0)).isTrue();

        // 右侧单元格仍然不应该是满的
        assertThat(p.isFull(N-1, 4)).isFalse();
    }

    @Test
    public void testBackwashWithMultipleComponents() {
        int N = 8;
        Percolation p = new Percolation(N);

        // 创建第一条渗透路径
        for (int i = 0; i < N; i++) {
            p.open(i, 1);
        }

        // 创建第二个不相连的组件在底部
        for (int j = 3; j < 6; j++) {
            p.open(N-1, j);
            p.open(N-2, j);
        }
        p.open(N-3, 4);

        // 验证渗透状态
        assertThat(p.percolates()).isTrue();

        // 验证第一列是满的
        for (int i = 0; i < N; i++) {
            assertThat(p.isFull(i, 1)).isTrue();
        }

        // 验证第二个组件的单元格是开放的但不是满的
        for (int j = 3; j < 6; j++) {
            assertThat(p.isOpen(N-1, j)).isTrue();
            assertThat(p.isFull(N-1, j)).isFalse(); // 不应该是满的，否则存在回流

            assertThat(p.isOpen(N-2, j)).isTrue();
            assertThat(p.isFull(N-2, j)).isFalse(); // 不应该是满的，否则存在回流
        }

        assertThat(p.isOpen(N-3, 4)).isTrue();
        assertThat(p.isFull(N-3, 4)).isFalse(); // 不应该是满的，否则存在回流

        // 现在创建从顶部到第二个组件的连接
        for (int i = 0; i < N-3; i++) {
            p.open(i, 4);
        }

        // 验证现在第二个组件应该是满的
        assertThat(p.isFull(N-3, 4)).isTrue();
        for (int j = 3; j < 6; j++) {
            assertThat(p.isFull(N-2, j)).isTrue();
            assertThat(p.isFull(N-1, j)).isTrue();
        }
    }

    @Test
    public void testBackwashWithVirtualSites() {
        // 这个测试特别适用于使用虚拟站点实现的Percolation类
        int N = 5;
        Percolation p = new Percolation(N);

        // 开放顶部的一些单元格
        p.open(0, 1);
        p.open(0, 3);

        // 创建一条从(0,1)到底部的路径
        for (int i = 1; i < N; i++) {
            p.open(i, 1);
        }

        // 在底部开放一些额外的单元格
        p.open(N-1, 0);
        p.open(N-1, 2);
        p.open(N-1, 4);

        // 验证渗透状态
        assertThat(p.percolates()).isTrue();

        // 验证连接到顶部的路径是满的
        for (int i = 0; i < N; i++) {
            assertThat(p.isFull(i, 1)).isTrue();
        }

        // 验证顶部的另一个开放单元格也是满的
        assertThat(p.isFull(0, 3)).isTrue();

        // 关键测试：验证底部的其他开放单元格不应该是满的
        assertThat(p.isOpen(N-1, 4)).isTrue();
        assertThat(p.isFull(N-1, 4)).isFalse(); // 不应该是满的
    }

    @Test
    public void testInvalidIndices() {
        int N = 4;
        Percolation p = new Percolation(N);

        // 测试负索引
        assertThrows(IndexOutOfBoundsException.class, () -> p.open(-1, 0));
        assertThrows(IndexOutOfBoundsException.class, () -> p.open(0, -1));

        // 测试超出范围的索引
        assertThrows(IndexOutOfBoundsException.class, () -> p.open(N, 0));
        assertThrows(IndexOutOfBoundsException.class, () -> p.open(0, N));

        // 检查isOpen和isFull方法也应处理无效索引
        assertThrows(IndexOutOfBoundsException.class, () -> p.isOpen(-1, 0));
        assertThrows(IndexOutOfBoundsException.class, () -> p.isFull(0, N));
    }

    @Test
    public void testRandomOpenings() {
        int N = 10;
        Percolation p = new Percolation(N);
        Random random = new Random(42); // 使用固定种子以便测试结果可重现

        // 随机开放大约50%的单元格
        int totalCells = N * N;
        int openedCells = 0;

        while (openedCells < totalCells / 2) {
            int row = random.nextInt(N);
            int col = random.nextInt(N);

            if (!p.isOpen(row, col)) {
                p.open(row, col);
                openedCells++;
            }
        }

        // 只是验证已经开放了预期数量的单元格
        int countOpen = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (p.isOpen(i, j)) {
                    countOpen++;
                }
            }
        }

        assertThat(countOpen).isEqualTo(openedCells);

        // 注意：这个测试不断言渗透结果，因为随机开放可能导致渗透也可能不导致
    }

    @Test
    public void testEdgeCases() {
        // 测试最小尺寸
        int N = 1;
        Percolation p1 = new Percolation(N);
        assertThat(p1.percolates()).isFalse(); // 初始时不渗透
        p1.open(0, 0);
        assertThat(p1.percolates()).isTrue(); // 开放后渗透

        // 测试稍大的网格，但没有开放任何单元格
        N = 3;
        Percolation p2 = new Percolation(N);
        assertThat(p2.percolates()).isFalse();

        // 测试无效的网格大小
        assertThrows(IllegalArgumentException.class, () -> new Percolation(0));
        assertThrows(IllegalArgumentException.class, () -> new Percolation(-5));
    }
}
