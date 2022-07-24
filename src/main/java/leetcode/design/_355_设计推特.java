package leetcode.design;
//设计一个简化版的推特(Twitter)，可以让用户实现发送推文，关注/取消关注其他用户，能够看见关注人（包括自己）的最近 10 条推文。
//
// 实现 Twitter 类：
//
//
// Twitter() 初始化简易版推特对象
// void postTweet(int userId, int tweetId) 根据给定的 tweetId 和 userId 创建一条新推文。每次调用此函
//数都会使用一个不同的 tweetId 。
// List<Integer> getNewsFeed(int userId) 检索当前用户新闻推送中最近 10 条推文的 ID 。新闻推送中的每一项都必须是
//由用户关注的人或者是用户自己发布的推文。推文必须 按照时间顺序由最近到最远排序 。
// void follow(int followerId, int followeeId) ID 为 followerId 的用户开始关注 ID 为
//followeeId 的用户。
// void unfollow(int followerId, int followeeId) ID 为 followerId 的用户不再关注 ID 为
//followeeId 的用户。
//
//
//
//
// 示例：
//
//
//输入
//["Twitter", "postTweet", "getNewsFeed", "follow", "postTweet", "getNewsFeed",
//"unfollow", "getNewsFeed"]
//[[], [1, 5], [1], [1, 2], [2, 6], [1], [1, 2], [1]]
//输出
//[null, null, [5], null, null, [6, 5], null, [5]]
//
//解释
//Twitter twitter = new Twitter();
//twitter.postTweet(1, 5); // 用户 1 发送了一条新推文 (用户 id = 1, 推文 id = 5)
//twitter.getNewsFeed(1);  // 用户 1 的获取推文应当返回一个列表，其中包含一个 id 为 5 的推文
//twitter.follow(1, 2);    // 用户 1 关注了用户 2
//twitter.postTweet(2, 6); // 用户 2 发送了一个新推文 (推文 id = 6)
//twitter.getNewsFeed(1);  // 用户 1 的获取推文应当返回一个列表，其中包含两个推文，id 分别为 -> [6, 5] 。推文
//id 6 应当在推文 id 5 之前，因为它是在 5 之后发送的
//twitter.unfollow(1, 2);  // 用户 1 取消关注了用户 2
//twitter.getNewsFeed(1);  // 用户 1 获取推文应当返回一个列表，其中包含一个 id 为 5 的推文。因为用户 1 已经不再关注用
//户 2
//
//
//
// 提示：
//
//
// 1 <= userId, followerId, followeeId <= 500
// 0 <= tweetId <= 10⁴
// 所有推特的 ID 都互不相同
// postTweet、getNewsFeed、follow 和 unfollow 方法最多调用 3 * 10⁴ 次
//
//
// Related Topics 设计 哈希表 链表 堆（优先队列） 👍 303 👎 0

import java.util.*;

/**
 * @author mayanwei
 * @date 2022-07-24.
 */
public class _355_设计推特{
    static class Twitter {
        /**把合并多个有序链表的算法和面向对象的设计结合起来了，很有意义 */
        private static int timestamp = 0;
        private static class Tweet {
            private int id;
            private int time;
            private Tweet next;
            public Tweet(int id, int time){
                this.id = id;
                this.time = time;
                this.next = null;
            }
        }
        private static class User {
            private int id;
            public Set<Integer> followed;
            // 用户发表的推文链表头节点
            public Tweet head;
            public User(int userId) {
                this.id = userId;
                this.head = null;
                followed = new HashSet<>();
                // 关注一下自己
                follow(id);
            }

            public void follow(int userId) {
                followed.add(userId);
            }
            public void unfollow(int userId) {
                if (userId != id) {
                    followed.remove(userId);
                }
            }
            public void post(int tweetId) {
                Tweet twt = new Tweet(tweetId, timestamp);
                timestamp++;
                // 将新建的推文插入链表头
                // 越靠前的推文 time 值越大
                twt.next = head;
                head = twt;
            }
        }

        public Twitter() {

        }


        private HashMap<Integer, User> userMap = new HashMap<>();

        /**用户 userId 发表了一条 tweetId 动态 */
        public void postTweet(int userId, int tweetId) {
            if (!userMap.containsKey(userId)) {
                userMap.put(userId, new User(userId));
            }
            User u = userMap.get(userId);
            u.post(tweetId);
        }

        /**
         * 1. 返回该 user 关注的人（包括他自己）最近的动态 id， 最多 10条，而且这些动态必须按照从新到旧的时间线顺排列。
         * 2. 如果每个用户各自的推文存储在链表里，每个链表节点存储文章 id 和一个时间戳 time（记录发帖时间以便比较），而且这个链表是按照 time 有序的， 那么如果某个用户关注了 k 个用户，我们就可以用合并 k 个有序链表的肃反啊合并出有序的推文列表，真确地 getNewsFee 了。
         */
        public List<Integer> getNewsFeed(int userId) {
            List<Integer> res = new ArrayList<>();
            if (!userMap.containsKey(userId)){
                return res;
            }
            // 关注列表的用户 Id
            Set<Integer> users = userMap.get(userId).followed;
            // 自动通过 time 属性从大到小排序，容量为 users 的大小
            PriorityQueue<Tweet> pq = new PriorityQueue<>(users.size(), (a, b)->(b.time - a.time));
            // 先将所有链表头节点插入优先级队列
            for (int id : users) {
                Tweet twt = userMap.get(id).head;
                if (twt == null) {
                    continue;
                }
                pq.add(twt);
            }
            while (!pq.isEmpty()) {
                // 最多返回 10 条就够了
                if (res.size() == 10) {
                    break;
                }
                // 弹出 time 值最大的（最近发表的）
                Tweet twt = pq.poll();
                res.add(twt.id);
                // 将下一篇 Tweet 插入进行排序
                if (twt.next != null) {
                    pq.add(twt.next);
                }
            }
            return res;

        }

        /**follower 关注 followee，如果 Id 不存在则新建 */
        public void follow(int followerId, int followeeId) {
            // 若 follower 不存在，则新建
            if(!userMap.containsKey(followerId)){
                User u = new User(followerId);
                userMap.put(followerId, u);
            }
            // 若 followee 不存在，则新建
            if(!userMap.containsKey(followeeId)){
                User u = new User(followeeId);
                userMap.put(followeeId, u);
            }
            userMap.get(followerId).follow(followeeId);
        }

        /** follower 取关 followee，如果id 不存在则什么都不做 */
        public void unfollow(int followerId, int followeeId) {
            if (userMap.containsKey(followerId)) {
                User flwer = userMap.get(followerId);
                flwer.unfollow(followeeId);
            }

        }
    }

/**
 * Your Twitter object will be instantiated and called as such:
 * Twitter obj = new Twitter();
 * obj.postTweet(userId,tweetId);
 * List<Integer> param_2 = obj.getNewsFeed(userId);
 * obj.follow(followerId,followeeId);
 * obj.unfollow(followerId,followeeId);
 */
}
