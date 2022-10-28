package leetcode.jzhoffer;

import leetcode.slidewindow._346_滑动窗口平均值;

/**
 * @author mayanwei
 * @date 2022-10-28.
 * @see _346_滑动窗口平均值
 */
public class 剑指_Offer_II_041_滑动窗口的平均值{
    class MovingAverage{
        private int[] array;
        private int size;
        private double sum = 0;
        private int count = 0;

        /**
         * Initialize your data structure here.
         */
        public MovingAverage(int size) {
            this.array = new int[size];
            this.size = size;
        }

        public double next(int val) {
            this.sum += val;
            if (this.count >= this.size) {
                int toOverrideIndex = (this.count - this.size) % this.size;
                this.sum -= array[toOverrideIndex];
            }
            this.array[this.count % this.size] = val;
            this.count++;
            return this.sum / (this.count >= this.size ? this.size :this.count);
        }
    }

}
