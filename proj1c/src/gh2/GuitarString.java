package gh2;
import deque.Deque61B;
import deque.ArrayDeque61B; // 导入一个Deque61B的实现类

//Note: 在完成Deque61B实现之前，此文件将无法编译
public class GuitarString {
    /** Constants. Do not change. In case you're curious, the keyword final
     * means the values cannot be changed at runtime. We'll discuss this and
     * other topics in lecture on Friday. */
    private static final int SR = 44100;      // 采样率
    private static final double DECAY = .996; // 能量衰减因子

    /* 用于存储声音数据的缓冲区。 */
     private Deque61B<Double> buffer;

    /* 创建给定频率的吉他弦。  */
    public GuitarString(double frequency) {
        buffer = new ArrayDeque61B<>();

        // 处理极端情况，防止frequency太小导致溢出
        if (frequency <= 0) {
            throw new IllegalArgumentException("频率必须为正数");
        }

        // 使用Math.min确保capacity不会太大
        double exactCapacity = SR / frequency;
        if (exactCapacity > Integer.MAX_VALUE) {
            throw new IllegalArgumentException("频率太小，导致缓冲区容量过大");
        }

        int capacity = (int) Math.round(exactCapacity);
        // 确保capacity至少为2（Karplus-Strong算法需要至少2个元素）
        capacity = Math.max(capacity, 2);

        for(int i = 0; i < capacity; i++) { // 初始化
            buffer.addLast(0.0);
        }
    }


    /* 用白噪音代替缓冲器来拨动吉他弦。 */
    public void pluck() {
        //       Make sure that your random numbers are different from each
        //       other. This does not mean that you need to check that the numbers
        //       are different from each other. It means you should repeatedly call
        //       Math.random() - 0.5 to generate new random numbers for each array index.

        // 保存当前缓冲区大小
        int size = buffer.size();

        // 清空当前缓冲区
        for (int i = 0; i < size; i++) {
            buffer.removeFirst();
        }

        // 用随机值重新填充缓冲区
        for (int i = 0; i < size; i++) {
            // 生成-0.5到0.5之间的随机数
            double randomValue = Math.random() - 0.5;
            buffer.addLast(randomValue);
        }
    }

    /* Advance the simulation one time step by performing one iteration of
     * the Karplus-Strong algorithm.
     */
    public void tic() {
        double first = buffer.removeFirst();
        double newSample = DECAY * ((first + buffer.get(0)) / 2);
        buffer.addLast(newSample);
    }

    /* Return the double at the front of the buffer. */
    public double sample() {
        return buffer.get(0);
    }
}

