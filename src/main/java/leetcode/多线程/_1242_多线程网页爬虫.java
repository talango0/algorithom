package leetcode.多线程;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author mayanwei
 * @date 2022-10-07.
 */
public class _1242_多线程网页爬虫{
    interface HtmlParser{
        // Return a list of all urls from a webpage of given url.
        // This is a blocking call, that means it will do HTTP request and return when this request is finished.
        List<String> getUrls(String url);
    }

    class Solution{
        private String host;
        private  HtmlParser htmlParser;
        private AtomicInteger cnt = new AtomicInteger();
        // 1.因为部分测试数据会构造有环图，导致爬虫死循环，所以必须采用哈希集合去重
        private Set<String> set = new HashSet<>();
        ExecutorService es = Executors.newFixedThreadPool(30);


        public List<String> crawl(String startUrl, HtmlParser htmlParser) {
            host = getHost(startUrl);
            this.htmlParser = htmlParser;
            set.add(startUrl);
            es.execute(()->helper(startUrl));
            while (cnt.get() > 0) {
                // 2. 如果调用 yield 方法是这放弃使用cpu，测试结果会在 800 ms以上，所以此处该用 sleep
                // Thread.yield()
                try{
                    Thread.sleep(1);
                }catch (InterruptedException e) {

                }
            }
            // 3. 必须主动关闭线程池, 否则超时
            es.shutdown();
            return new ArrayList<>(set);
        }

        private void helper(String startUrl) {
            for (String url : htmlParser.getUrls(startUrl)) {
                if (!host.equals(getHost(url))) {
                    continue;
                }
                synchronized (set) {
                    if (!set.contains(getHost(url)))  {
                        continue;
                    }
                }
                cnt.incrementAndGet();
                es.execute(()->helper(url));
            }
            cnt.decrementAndGet();
        }

        public String getHost(String url) {
            return url.split("/",4)[2];
        }

    }

    class Solution2 {

        ConcurrentHashMap<String, Integer> set = new ConcurrentHashMap<>();

        public List<String> crawl(String startUrl, HtmlParser htmlParser) {
            set.put(startUrl, 0);
            String prefix = "http://" + startUrl.split("http://")[1].split("/")[0];
            threadCrawl(startUrl, prefix, htmlParser);

            return new ArrayList<>(set.keySet());
        }

        private void threadCrawl(String startUrl, String prefix, HtmlParser htmlParser) {
            List<Thread> list = new ArrayList<Thread>();
            for (String s : htmlParser.getUrls(startUrl)) {
                if (s.startsWith(prefix) && !set.containsKey(s)) {
                    set.put(s, 0);
                    Thread thread = new Thread(() -> threadCrawl(s, prefix, htmlParser));
                    thread.start();
                    list.add(thread);
                }
            }
            try {
                for (Thread thread : list) {
                    thread.join();
                }
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }

}
