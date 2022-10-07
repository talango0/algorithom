package leetcode.多线程;

import java.util.concurrent.Semaphore;

/**
 * @author mayanwei
 * @date 2022-10-07.
 */
public class _1279_红绿灯路口{
    /**
     * Semaphore
     */
    static class Solution1{
        static class TrafficLight{
            private boolean isAGreen;
            private final Semaphore semaphore;

            public TrafficLight() {
                isAGreen = true;
                semaphore = new Semaphore(1);
            }


            /**
             * @param cardId    ID of the car
             * @param roadId    ID of the road travels on. Can be 1 (road A) or 2(road B)
             * @param direction Direction of the car
             * @param turnGreen Use turnGree.run() to turn light to green on current road.
             * @param crossCar  User crossCar.run() to make car cross the intersection
             */
            public void canArrived(int cardId, int roadId, int direction, Runnable turnGreen, Runnable crossCar) throws InterruptedException {
                semaphore.acquire();
                if ((roadId == 2 && isAGreen) || (roadId == 1 && !isAGreen)) {
                    turnGreen.run();
                    isAGreen = !isAGreen;
                }
                crossCar.run();
                semaphore.release();
            }
        }
    }

    /**
     * volatile 变量
     */
    static class Solution2{
        class TrafficLight{
            volatile boolean isAGreen;

            public TrafficLight() {
                isAGreen = true;
            }

            /**
             * @param cardId    ID of the car
             * @param roadId    ID of the road travels on. Can be 1 (road A) or 2(road B)
             * @param direction Direction of the car
             * @param turnGreen Use turnGree.run() to turn light to green on current road.
             * @param crossCar  User crossCar.run() to make car cross the intersection
             */
            public void canArrived(int cardId, int roadId, int direction, Runnable turnGreen, Runnable crossCar) throws InterruptedException {
                if (roadId == 2 && isAGreen == true || roadId == 1 && isAGreen == false) {
                    turnGreen.run();
                    isAGreen = !isAGreen;
                }
                crossCar.run();
            }
        }

    }

    /**
     * synchronized
     */
    static class Solution4{
        class TrafficLight{
            boolean isAGreen;

            public TrafficLight() {
                isAGreen = true;
            }

            /**
             * @param cardId    ID of the car
             * @param roadId    ID of the road travels on. Can be 1 (road A) or 2(road B)
             * @param direction Direction of the car
             * @param turnGreen Use turnGree.run() to turn light to green on current road.
             * @param crossCar  User crossCar.run() to make car cross the intersection
             */
            public synchronized void canArrived(int cardId, int roadId, int direction, Runnable turnGreen, Runnable crossCar) throws InterruptedException {
                if (roadId == 2 && isAGreen == true || roadId == 1 && isAGreen == false) {
                    turnGreen.run();
                    isAGreen = !isAGreen;
                }
                crossCar.run();
            }
        }

    }

}
