import edu.princeton.cs.algs4.WeightedQuickUnionUF;

// 渗透模型
public class Percolation {
    // TODO: Add any necessary instance variables.
    int size;
    WeightedQuickUnionUF uf;    // 包含顶部、底部虚拟节点
    WeightedQuickUnionUF ufAdd; // 只包含顶部虚拟节点
    boolean[][] opened;
    int numOpened;

    public Percolation(int N) {
        // TODO: Fill in this constructor.
        if(N <= 0) {
            throw new IllegalArgumentException("N应为正整数");
        }

        size = N;
        uf = new WeightedQuickUnionUF(N * N + 2);    // 加入两个虚拟节点（顶部 size * size、底部 size * size + 1）
        ufAdd = new WeightedQuickUnionUF(N * N + 1); // 一个虚拟节点
        opened = new boolean[N][N]; // 默认值false
        numOpened = 0;
    }

    public void open(int row, int col) {
        // TODO: Fill in this method.
        if(!isOpen(row, col)) {
            opened[row][col] = true;
            numOpened++;

            // 连接虚拟节点
            if (row == 0) {
                uf.union(xyTo1(row, col), size * size); // 连接顶部
                ufAdd.union(xyTo1(row, col), size * size); // 连接顶部
            }
            if (row == size - 1) {
                uf.union(xyTo1(row, col), size * size + 1); // 连接底部虚拟节点
            }

            // 连接相邻格子
            if (row - 1 >= 0 && opened[row - 1][col]) { // 上
                uf.union(xyTo1(row, col), xyTo1(row - 1, col));
                ufAdd.union(xyTo1(row, col), xyTo1(row - 1, col));
            }
            if (row + 1 < size && opened[row + 1][col]) { // 下
                uf.union(xyTo1(row, col), xyTo1(row + 1, col));
                ufAdd.union(xyTo1(row, col), xyTo1(row + 1, col));
            }
            if (col - 1 >= 0 && opened[row][col - 1]) { // 左
                uf.union(xyTo1(row, col), xyTo1(row, col - 1));
                ufAdd.union(xyTo1(row, col), xyTo1(row, col - 1));
            }
            if (col + 1 < size && opened[row][col + 1]) { // 右
                uf.union(xyTo1(row, col), xyTo1(row, col + 1));
                ufAdd.union(xyTo1(row, col), xyTo1(row, col + 1));
            }
        }
    }

    public boolean isOpen(int row, int col) {
        // TODO: Fill in this method.
        if(row < 0 || row >= size || col < 0 || col >= size) {
            throw new IndexOutOfBoundsException("row,col不合法");
        }

        if(opened[row][col]) {
            return true;
        }
        return false;
    }

    public boolean isFull(int row, int col) {
        // TODO: Fill in this method.
        if(row < 0 || row >= size || col < 0 || col >= size) {
            throw new IndexOutOfBoundsException("row,col不合法");
        }

        if(isOpen(row, col) && ufAdd.connected(xyTo1(row, col), size * size)) {
            return true;
        }
        return false;
    }

    public int numberOfOpenSites() {
        // TODO: Fill in this method.
        return numOpened;
    }

    public boolean percolates() {
        // TODO: Fill in this method.
        if(uf.connected(size * size, size * size + 1)) {
            return true;
        }
        return false;
    }

    // TODO: Add any useful helper methods (we highly recommend this!).
    // TODO: Remove all TODO comments before submitting.

    int xyTo1(int row, int col) {
        // row,col从0开始计数
        return row * size + col;
    }
}
