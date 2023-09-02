package leetcode.jzhoffer;

import leetcode.arrays.random.searchremove._380_常数时间插入删除和获取随机元素;
import leetcode.arrays.random.searchremove._710_黑名单中的随机数;

import java.util.*;

/**
 * @author mayanwei
 * @date 2022-10-30.
 * @see _380_常数时间插入删除和获取随机元素
 * @see _710_黑名单中的随机数
 */
public class 剑指_Offer_II_030_插入_删除和随机访问都是_O_1_的容器{
    class RandomizedSet {
        /**
         链表，按下标 i * random 随机获取元素
         Hash表，按 O(1) 的时间复杂度获取和删除元素 */

        List<Integer> nums;
        Map<Integer, Integer> indices;
        Random random;
        /** Initialize your data structure here. */
        public RandomizedSet() {
            nums = new ArrayList<>();
            indices = new HashMap<>();
            random = new Random();
        }

        /** Inserts a value to the set. Returns true if the set did not already contain the specified element. */
        public boolean insert(int val) {
            if (indices.containsKey(val)) {
                return false;
            }
            int index = nums.size();
            nums.add(val);
            indices.put(val, index);
            return true;
        }

        /** Removes a value from the set. Returns true if the set contained the specified element. */
        public boolean remove(int val) {
            if(!indices.containsKey(val)){
                return false;
            }
            int index = indices.get(val);
            int last = nums.get(nums.size() - 1);
            nums.set(index, last);
            indices.put(last, index);
            nums.remove(nums.size() -  1);
            indices.remove(val);
            return true;
        }

        /** Get a random element from the set. */
        public int getRandom() {
            return nums.get(random.nextInt(nums.size()));
        }
    }

}
