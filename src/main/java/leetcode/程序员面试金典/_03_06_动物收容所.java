package leetcode.程序员面试金典;
//动物收容所。有家动物收容所只收容狗与猫，且严格遵守“先进先出”的原则。在收养该收容所的动物时，
// 收养人只能收养所有动物中“最老”（由其进入收容所的时间长短而定）的动物，或者可以挑选猫或狗（同时必须收养此类动物中“最老”的）。
// 换言之，收养人不能自由挑选想收养的对象。请创建适用于这个系统的数据结构，实现各种操作方法，
// 比如enqueue、dequeueAny、dequeueDog和dequeueCat。允许使用Java内置的LinkedList数据结构。
//
//enqueue方法有一个animal参数，animal[0]代表动物编号，animal[1]代表动物种类，其中 0 代表猫，1 代表狗。
//
//dequeue*方法返回一个列表[动物编号, 动物种类]，若没有可以收养的动物，则返回[-1,-1]。
//
//示例1:
//
// 输入：
//["AnimalShelf", "enqueue", "enqueue", "dequeueCat", "dequeueDog", "dequeueAny"]
//[[], [[0, 0]], [[1, 0]], [], [], []]
// 输出：
//[null,null,null,[0,0],[-1,-1],[1,0]]
//示例2:
//
// 输入：
//["AnimalShelf", "enqueue", "enqueue", "enqueue", "dequeueDog", "dequeueCat", "dequeueAny"]
//[[], [[0, 0]], [[1, 0]], [[2, 1]], [], [], []]
// 输出：
//[null,null,null,null,[2,1],[0,0],[1,0]]
//
//
//来源：力扣（LeetCode）
//链接：https://leetcode.cn/problems/animal-shelter-lcci
//著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。

import java.util.LinkedList;

/**
 * @author mayanwei
 * @date 2023-06-26.
 */
public class _03_06_动物收容所{
    class AnimalShelf{

        LinkedList<int[]> queueCat;
        LinkedList<int[]> queueDog;

        public AnimalShelf() {
            queueCat = new LinkedList<>();
            queueDog = new LinkedList<>();
        }

        public void enqueue(int[] animal) {
            // 判断种类后入队
            if (animal[1] == 0) {
                queueCat.addLast(animal);
            }
            else if (animal[1] == 1) {
                queueDog.addLast(animal);
            }
        }

        // 挑选所有动物中最老的
        public int[] dequeueAny() {
            // 取出cat的队首，判空则直接返回
            int[] headCat;
            if (!queueCat.isEmpty()) {
                headCat = queueCat.getFirst();
            }
            else if (!queueDog.isEmpty()) {
                // 当猫队列无猫时，直接将狗队列的第一个出队
                return queueDog.removeFirst();
            }
            else {
                // 代表猫狗队列中无任何猫狗
                return new int[]{-1, -1};
            }
            // 取出dog的队首，判空则直接返回
            int[] headDog;
            if (!queueDog.isEmpty()) {
                headDog = queueDog.getFirst();
            }
            else {
                // 当狗队列无狗时，直接将猫队列的第一个出队
                return queueCat.removeFirst();
            }
            // 当同时都有猫狗时 比较后返回 判断猫狗中谁比较老
            if (headCat[0] <= headDog[0]) {
                return queueCat.removeFirst();
            }
            else {
                return queueDog.removeFirst();
            }
        }

        // 挑选狗
        public int[] dequeueDog() {
            if (!queueDog.isEmpty()) {
                return queueDog.removeFirst();
            }
            else {
                return new int[]{-1, -1};
            }
        }

        // 挑选猫
        public int[] dequeueCat() {
            if (!queueCat.isEmpty()) {
                return queueCat.removeFirst();
            }
            else {
                return new int[]{-1, -1};
            }
        }
    }
/**
 * Your AnimalShelf object will be instantiated and called as such:
 * AnimalShelf obj = new AnimalShelf();
 * obj.enqueue(animal);
 * int[] param_2 = obj.dequeueAny();
 * int[] param_3 = obj.dequeueDog();
 * int[] param_4 = obj.dequeueCat();
 */
}
