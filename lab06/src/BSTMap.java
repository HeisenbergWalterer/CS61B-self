import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

// 二叉搜索树
public class BSTMap<k extends Comparable<k>, v> implements Map<k, v> {

    private class BSTNode {
        k key;
        v value;
        BSTNode left;
        BSTNode right;

        BSTNode(k k, v v) {
            this.key = k;
            this.value = v;
            this.left = null;
            this.right = null;
        }
        BSTNode(k k, v v, BSTNode l, BSTNode r) {
            this.key = k;
            this.value = v;
            this.left = l;
            this.right = r;
        }
    }
    private BSTNode root;
    private int size;

    BSTMap() {
        root = null;
        size = 0;
    }

    public int cmpareRoots(BSTMap<k, v> other) {
        return this.root.key.compareTo(other.root.key);
    }

    /**
     * 返回此地图中键值映射的数量。
     * 如果是地图包含超过{@code integer.max_value}元素，返回{@code integer.max_value}。
     *
     * @return此地图中的键值映射数量
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * 返回{@code true}如果此映射不包含键值映射。
     *
     * @return {@code true} if this map contains no key-value mappings
     */
    @Override
    public boolean isEmpty() {
        return (size == 0);
    }

    // 以key按序遍历(返回存放value的Node)
    private BSTNode find(BSTNode root, k key) {
        // 错误检查
        isNull(key);

        if(root == null) {
            return null; // 没找到
        }

        if (root.key.compareTo(key) > 0) { // root.key > key
            return find(root.left, key);
        } else if (root.key.compareTo(key) < 0) {
            return find(root.right, key);
        } else { // equal
            return root;
        }
    }

    // 依次完全遍历
    private BSTNode find(BSTNode root, v value) {
        // 错误检查
        isNull(value);

        if(root == null) {
            return null;
        }
        if(root.value.equals(value)) {
            return root;
        }
        BSTNode temp = find(root.left, value);
        if(temp != null) {
            return temp;
        }

        return find(root.right, value);
    }

    // 错误检查
    private void isNull(Object o) {
        if(o == null) {
            throw new NullPointerException();
        }
    }
    private void isClassCastException(Object o, boolean isKey) {
        try{
            if(isKey) {
                k key = (k) o;
            } else {
                v value = (v) o;
            }
        } catch(ClassCastException e) {
            throw new ClassCastException();
        }
    }

    // 插入
    private BSTNode insert(BSTNode root, k key, v value) {
        isNull(key);
        isNull(value);

        if(root == null) {
            return new BSTNode(key, value);
        }
        if(root.key.compareTo(key) > 0) { // root.key > key
            root.left = insert(root.left, key, value);
        } else if(root.key.compareTo(key) < 0) {
            root.right = insert(root.right, key, value);
        } else if(root.key.compareTo(key) == 0) {
            root.value = value; // 已存在则更新value
        }
        return root;
    }

    /**
     * Returns {@code true} if this map contains a mapping for the specified
     * key.  More formally, returns {@code true} if and only if
     * this map contains a mapping for a key {@code k} such that
     * {@code Objects.equals(key, k)}.  (There can be
     * at most one such mapping.)
     *
     * @param key key whose presence in this map is to be tested
     * @return {@code true} if this map contains a mapping for the specified
     * key
     * @throws ClassCastException   if the key is of an inappropriate type for
     *                              this map ({@linkplain Collection##optional-restrictions optional})
     * @throws NullPointerException if the specified key is null and this map
     *                              does not permit null keys ({@linkplain Collection##optional-restrictions optional})
     */
    @Override
    public boolean containsKey(Object key) {
        // 错误情况检查
        isNull(key);
        isClassCastException(key, true);

        k keyK = (k) key;

        return (find(this.root, keyK) != null);
    }

    /**
     * Returns {@code true} if this map maps one or more keys to the
     * specified value.  More formally, returns {@code true} if and only if
     * this map contains at least one mapping to a value {@code v} such that
     * {@code Objects.equals(value, v)}.  This operation
     * will probably require time linear in the map size for most
     * implementations of the {@code Map} interface.
     *
     * @param value value whose presence in this map is to be tested
     * @return {@code true} if this map maps one or more keys to the
     * specified value
     * @throws ClassCastException   if the value is of an inappropriate type for
     *                              this map ({@linkplain Collection##optional-restrictions optional})
     * @throws NullPointerException if the specified value is null and this
     *                              map does not permit null values ({@linkplain Collection##optional-restrictions optional})
     */
    @Override
    public boolean containsValue(Object value) {
        // 错误情况检查
        isNull(value);
        isClassCastException(value, false);

        v valueV = (v) value;

        return (find(this.root, valueV) != null);
    }

    /**
     * Returns the value to which the specified key is mapped,
     * or {@code null} if this map contains no mapping for the key.
     *
     * <p>More formally, if this map contains a mapping from a key
     * {@code k} to a value {@code v} such that
     * {@code Objects.equals(key, k)},
     * then this method returns {@code v}; otherwise
     * it returns {@code null}.  (There can be at most one such mapping.)
     *
     * <p>If this map permits null values, then a return value of
     * {@code null} does not <i>necessarily</i> indicate that the map
     * contains no mapping for the key; it's also possible that the map
     * explicitly maps the key to {@code null}.  The {@link #containsKey
     * containsKey} operation may be used to distinguish these two cases.
     *
     * @param key the key whose associated value is to be returned
     * @return the value to which the specified key is mapped, or
     * {@code null} if this map contains no mapping for the key
     * @throws ClassCastException   if the key is of an inappropriate type for
     *                              this map ({@linkplain Collection##optional-restrictions optional})
     * @throws NullPointerException if the specified key is null and this map
     *                              does not permit null keys ({@linkplain Collection##optional-restrictions optional})
     */
    @Override
    public v get(Object key) {
        // 错误情况检查
        isNull(key);
        isClassCastException(key, true);

        k keyK = (k) key;

        if(find(this.root, keyK) != null) {
            return find(this.root, keyK).value;
        }
        return null;
    }

    /**
     * Associates the specified value with the specified key in this map
     * (optional operation).  If the map previously contained a mapping for
     * the key, the old value is replaced by the specified value.  (A map
     * {@code m} is said to contain a mapping for a key {@code k} if and only
     * if {@link #containsKey(Object) m.containsKey(k)} would return
     * {@code true}.)
     *
     * @param key   key with which the specified value is to be associated
     * @param value value to be associated with the specified key
     * @return the previous value associated with {@code key}, or
     * {@code null} if there was no mapping for {@code key}.
     * (A {@code null} return can also indicate that the map
     * previously associated {@code null} with {@code key},
     * if the implementation supports {@code null} values.)
     * @throws UnsupportedOperationException if the {@code put} operation
     *                                       is not supported by this map
     * @throws ClassCastException            if the class of the specified key or value
     *                                       prevents it from being stored in this map
     * @throws NullPointerException          if the specified key or value is null
     *                                       and this map does not permit null keys or values
     * @throws IllegalArgumentException      if some property of the specified key
     *                                       or value prevents it from being stored in this map
     */
    @Override
    public v put(k key, v value) {
        // 错误检查
        isNull(key);
        isNull(value);

        v valuePre = find(this.root, key).value;
        this.root = insert(this.root, key, value);

        return valuePre;
    }

    public boolean isLeafNode(BSTNode node) {
        return (node.left == null && node.right == null);
    }

    /**
     * Removes the mapping for a key from this map if it is present
     * (optional operation).   More formally, if this map contains a mapping
     * from key {@code k} to value {@code v} such that
     * {@code Objects.equals(key, k)}, that mapping
     * is removed.  (The map can contain at most one such mapping.)
     *
     * <p>Returns the value to which this map previously associated the key,
     * or {@code null} if the map contained no mapping for the key.
     *
     * <p>If this map permits null values, then a return value of
     * {@code null} does not <i>necessarily</i> indicate that the map
     * contained no mapping for the key; it's also possible that the map
     * explicitly mapped the key to {@code null}.
     *
     * <p>The map will not contain a mapping for the specified key once the
     * call returns.
     *
     * @param key key whose mapping is to be removed from the map
     * @return the previous value associated with {@code key}, or
     * {@code null} if there was no mapping for {@code key}.
     * @throws UnsupportedOperationException if the {@code remove} operation
     *                                       is not supported by this map
     * @throws ClassCastException            if the key is of an inappropriate type for
     *                                       this map ({@linkplain Collection##optional-restrictions optional})
     * @throws NullPointerException          if the specified key is null and this
     *                                       map does not permit null keys ({@linkplain Collection##optional-restrictions optional})
     */
    @Override
    public v remove(Object key) {
        // 错误处理
        isNull(key);
        isClassCastException(key, true);

        k keyK = (k) key;

        // 无key || 树为空
        if(!containsKey(keyK) || root == null) {
            return null;
        }

        v valReturn = null;
        BSTNode delNode = root;;
        BSTNode delNodeParent = null;
        boolean isLeft = false;

        // 找到要删除的节点
        while(delNode != null && !delNode.key.equals(keyK)) {
            delNodeParent = delNode;
            if(delNode.key.compareTo(keyK) > 0) {
                delNode = delNode.left;
                isLeft = true;
            } else {
                delNode = delNode.right;
                isLeft = false;
            }
        }

        valReturn = delNode.value;

        // 为叶节点
        if(isLeafNode(delNode)) {
            if (delNode == root) {
                root = null;
            } else if (isLeft) {
                delNodeParent.left = null;
            } else {
                delNodeParent.right = null;
            }
        }

        // 只有右子节点
        else if(delNode.left == null) {
            if(delNode == root) {
                root = delNode.right;
            } else if(isLeft) {
                delNodeParent.left = delNode.right;
            } else {
                delNodeParent.right = delNode.right;
            }
        }
        // 只有左子节点
        else if(delNode.right == null) {
            if(delNode == root) {
                root = delNode.left;
            } else if(isLeft) {
                delNodeParent.left = delNode.left;
            } else {
                delNodeParent.right = delNode.left;
            }
        }

        // 有两个子节点
        else {
            // 先寻找后继节点（把右子树的最小项）
            BSTNode successor = delNode.right;
            BSTNode sucParent = delNode;

            while(successor.left != null) {
                sucParent = successor;
                successor = successor.left;
            }

            // 如果后继节点不是直接的右子节点
            if(!successor.equals(delNode.right)) {
                sucParent.left = successor.right;
                successor.right = delNode.right;
            }

            successor.left = delNode.left;

            if(delNode == root) {
                root = successor;
            } else if(isLeft) {
                delNodeParent.left = successor;
            } else {
                delNodeParent.right = successor;
            }
        }

        size--;
        return valReturn;
    }

    /**
     * Copies all of the mappings from the specified map to this map
     * (optional operation).  The effect of this call is equivalent to that
     * of calling  on this map once
     * for each mapping from key {@code k} to value {@code v} in the
     * specified map.  The behavior of this operation is undefined if the specified map
     * is modified while the operation is in progress. If the specified map has a defined
     * <a href="SequencedCollection.html#encounter">encounter order</a>,
     * processing of its mappings generally occurs in that order.
     *
     * @param m mappings to be stored in this map
     * @throws UnsupportedOperationException if the {@code putAll} operation
     *                                       is not supported by this map
     * @throws ClassCastException            if the class of a key or value in the
     *                                       specified map prevents it from being stored in this map
     * @throws NullPointerException          if the specified map is null, or if
     *                                       this map does not permit null keys or values, and the
     *                                       specified map contains null keys or values
     * @throws IllegalArgumentException      if some property of a key or value in
     *                                       the specified map prevents it from being stored in this map
     */
    @Override
    public void putAll(Map<? extends k, ? extends v> m) {

    }

    /**
     * Removes all of the mappings from this map (optional operation).
     * The map will be empty after this call returns.
     *
     * @throws UnsupportedOperationException if the {@code clear} operation
     *                                       is not supported by this map
     */
    @Override
    public void clear() {

    }

    /**
     * Returns a {@link Set} view of the keys contained in this map.
     * The set is backed by the map, so changes to the map are
     * reflected in the set, and vice-versa.  If the map is modified
     * while an iteration over the set is in progress (except through
     * the iterator's own {@code remove} operation), the results of
     * the iteration are undefined.  The set supports element removal,
     * which removes the corresponding mapping from the map, via the
     * {@code Iterator.remove}, {@code Set.remove},
     * {@code removeAll}, {@code retainAll}, and {@code clear}
     * operations.  It does not support the {@code add} or {@code addAll}
     * operations.
     *
     * @return a set view of the keys contained in this map
     */
    @Override
    public Set<k> keySet() {
        return Set.of();
    }

    /**
     * Returns a {@link Collection} view of the values contained in this map.
     * The collection is backed by the map, so changes to the map are
     * reflected in the collection, and vice-versa.  If the map is
     * modified while an iteration over the collection is in progress
     * (except through the iterator's own {@code remove} operation),
     * the results of the iteration are undefined.  The collection
     * supports element removal, which removes the corresponding
     * mapping from the map, via the {@code Iterator.remove},
     * {@code Collection.remove}, {@code removeAll},
     * {@code retainAll} and {@code clear} operations.  It does not
     * support the {@code add} or {@code addAll} operations.
     *
     * @return a collection view of the values contained in this map
     */
    @Override
    public Collection<v> values() {
        return List.of();
    }

    /**
     * Returns a {@link Set} view of the mappings contained in this map.
     * The set is backed by the map, so changes to the map are
     * reflected in the set, and vice-versa.  If the map is modified
     * while an iteration over the set is in progress (except through
     * the iterator's own {@code remove} operation, or through the
     * {@code setValue} operation on a map entry returned by the
     * iterator) the results of the iteration are undefined.  The set
     * supports element removal, which removes the corresponding
     * mapping from the map, via the {@code Iterator.remove},
     * {@code Set.remove}, {@code removeAll}, {@code retainAll} and
     * {@code clear} operations.  It does not support the
     * {@code add} or {@code addAll} operations.
     *
     * @return a set view of the mappings contained in this map
     */
    @Override
    public Set<Entry<k, v>> entrySet() {
        return Set.of();
    }
}

