public class UnionFind {
    // 并查集
    // TODO: Instance variables
    int[] parent;

    /* Creates a UnionFind data structure holding N items. Initially, all
       items are in disjoint sets. */
    public UnionFind(int N) {
        // TODO: YOUR CODE HERE
        parent = new int [N];
        for(int i = 0; i < parent.length; i++) {
            parent[i] = -1;
        }
    }

    /* Returns the size of the set V belongs to. */
    public int sizeOf(int v) {
        // TODO: YOUR CODE HERE
        if(v < 0 || v >= parent.length) {
            throw new IllegalArgumentException("Some comment to describe the reason for throwing.");
        }

        int root = v;
        while(parent[root] >= 0) {
            root = parent[root];
        }
        return Math.abs(parent[root]);
    }

    /* Returns the parent of V. If V is the root of a tree, returns the
       negative size of the tree for which V is the root. */
    public int parent(int v) {
        // TODO: YOUR CODE HERE
        if(v < 0 || v >= parent.length) {
            throw new IllegalArgumentException("Some comment to describe the reason for throwing.");
        }

        return parent[v];
    }

    /* Returns true if nodes/vertices V1 and V2 are connected. */
    public boolean connected(int v1, int v2) {
        // TODO: YOUR CODE HERE
        if(v1 < 0 || v2 < 0 || v1 >= parent.length || v2 >= parent.length) {
            throw new IllegalArgumentException("Some comment to describe the reason for throwing.");
        }

        if(find(v1) == find(v2)) {
            return true;
        }
        return false;
    }

    /* Returns the root of the set V belongs to. Path-compression is employed
       allowing for fast search-time. If invalid items are passed into this
       function, throw an IllegalArgumentException. */
    public int find(int v) {
        // TODO: YOUR CODE HERE
        if(v < 0 || v >= parent.length) {
            throw new IllegalArgumentException("Some comment to describe the reason for throwing.");
        }

        int root = v;
        while(parent[root] >= 0) {
            root = parent[root];
        }

        // 路径压缩
        while(parent[v] >= 0) {
            int temp = parent[v];
            parent[v] = root;
            v = temp;
        }
        return root;
    }

    /* Connects two items V1 and V2 together by connecting their respective
       sets. V1 and V2 can be any element, and a union-by-size heuristic is
       used. If the sizes of the sets are equal, tie break by connecting V1's
       root to V2's root. Union-ing an item with itself or items that are
       already connected should not change the structure. */
    public void union(int v1, int v2) {
        // TODO: YOUR CODE HERE
        if(v1 < 0 || v2 < 0 || v1 >= parent.length || v2 >= parent.length) {
            throw new IllegalArgumentException("Some comment to describe the reason for throwing.");
        }

        if(connected(v1, v2)) {
            return;
        }

        if(sizeOf(v1) < sizeOf(v2)) {
            parent[find(v2)] = (sizeOf(v1) + sizeOf(v2)) * (-1);
            parent[find(v1)] = find(v2);
        } else if(sizeOf(v1) > sizeOf(v2)) {
            parent[find(v1)] = (sizeOf(v1) + sizeOf(v2)) * (-1);
            parent[find(v2)] = find(v1);
        } else{ // 2个集合大小相同时，将v1连到v2
            parent[find(v2)] = (sizeOf(v1) + sizeOf(v2)) * (-1);
            parent[find(v1)] = find(v2);
        }
    }
}
