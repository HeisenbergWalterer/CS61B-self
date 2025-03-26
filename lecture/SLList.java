public class SLList {
    // 节点
    public class IntNode{
        public int item;
        public IntNode next;
        // 构造
        public IntNode(int f, IntNode r){
            item = f;
            next = r;
        }
    }

    private IntNode sentinel; // 将头节点改为私有，外部变量无法调用（即first只能通过SLList的内部函数进行调用）
    private int size;

    // 构造
    public SLList(int x){
        sentinel = new IntNode(0, null);
        sentinel.next = new IntNode(x, null);
        size = 1;
    }
    public SLList(){
        sentinel = new IntNode(0, null);
        size = 0;
    }

    // 头插
    public void addFirst(int x){
        size += 1;
        sentinel.next = new IntNode(x, sentinel.next);

    }

    public void addLast(int x){
        size += 1;
        IntNode p = sentinel;

        while(p.next != null){
            p = p.next;
        }

        p.next = new IntNode(x, null);
    }

    public int getFirst(){
        return sentinel.next.item;
    }

    public static void main(String[] args){
        SLList L = new SLList(5);
        L.addFirst(10);
        System.out.println(L.getFirst());

    }

}
