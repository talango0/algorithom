package leetcode.程序员面试金典;
//每年，政府都会公布一万个最常见的婴儿名字和它们出现的频率，也就是同名婴儿的数量。有些名字有多种拼法，例如，John 和 Jon 本质上是相同的名字，
// 但被当成了两个名字公布出来。给定两个列表，一个是名字及对应的频率，另一个是本质相同的名字对。设计一个算法打印出每个真实名字的实际频率。
// 注意，如果 John 和 Jon 是相同的，并且 Jon 和 Johnny 相同，则 John 与 Johnny 也相同，即它们有传递和对称性。
//
//在结果列表中，选择 字典序最小 的名字作为真实名字。
//
//示例：
//
//输入：names = ["John(15)","Jon(12)","Chris(13)","Kris(4)","Christopher(19)"],
//  synonyms = ["(Jon,John)","(John,Johnny)","(Chris,Kris)","(Chris,Christopher)"]
//输出：["John(27)","Chris(36)"]
//
//
//提示：
//
//names.length <= 100000
//
//来源：力扣（LeetCode）
//链接：https://leetcode.cn/problems/baby-names-lcci
//著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。

import org.junit.jupiter.api.Test;

import java.util.*;

/**
 * @author mayanwei
 * @date 2023-06-22.
 */
public class _17_07_婴儿名字{
    /**
     * <pre>
     * 思路：将有关系的名字放到一个集合中。每个名字都映射到该集合，而集合中的所有项目都映射到相同的集合实例上。
     * 如果需要合并两个集合，那么将一个集合复制到另一个集合中，并更新散列表使其指向新的集合。
     *
     * ┌───────────────────────────────────────────┐
     * │  Read (Jonathan, John)                    │
     * │     CREATE Set1 = Jonathan, John          │
     * │     L1.ADD Jonathan -> Set1               │
     * │     L1.ADD John -> Set1                   │
     * │                                           │
     * │  Read (Jon, Johnny)                       │
     * │     CREATE Set2 = Jon, Johnny             │
     * │     L1.ADD Jon -> Set2                    │
     * │     L1.ADD Johnny -> Set2                 │
     * │                                           │
     * │  Read (Johnny, John)                      │
     * │     COPY Set2 into Set2.                  │
     * │        Set1 = Jonathan, John, Jon, Johnny │
     * │     L1.UPDATE Jon -> Set1                 │
     * │     L1.UPDATE Johnny -> Set1              │
     * └───────────────────────────────────────────┘
     * 时间复杂度：
     * 对于这个算法，最􏰘的情况是所有的名字都相同，我们必须不断地合并所有集合。同样，
     * 对于最􏰘的情况，应尽量以最糟糕的方式进行合并，即重复合并成对的集合。每次合并都需要
     * 将一个集合中的元素复制到现有集合中，并更新这些项指向的对象。当集合变大时，该操作会
     * 越来越慢。
     * 如果你注意一下归并排序的并行过程(你必须将单元素数组合并为 2 个元素的数组，然后 将 2 个元素的数组合并为 4 个元素的数组，
     * 直到最后有一个完整的数组)，可能会发现其时间复 杂度是 O(N log N)，的确如此。
     *
     * 假设我们有名字(a, b, c, d, ..., z)。在最􏰘情况下，首先将相同的项目合并，即(a, b), (c, d), (e, f), ..., (y, z)。
     * 然后将它们合并成(a, b, c, d), (e, f, g, h), ..., (w, x, y, z)。􏰙􏰚合并，直到只有下一个类为止。
     * 在合并集合的过程中，每一步“扫描”操作，一半的项目被移动到一个新的集合中。因此 每一步“扫描”操作花费的时间为 O(N)(需要合并的集合会越来越少，
     * 但每一个集合大小都会 变大)。
     * 我们需要完成多少次“􏰜描”操作?在每一次􏰜描中，我们获得集合的数量是之前的一半。 因此，需要完成 O(log N)次“􏰜描”。
     * 由于需要完成 O(log N)次􏰜描，每次􏰜描操作需要 O(N)的工作量，因此总运行时间是 O(N log N)。
     * </pre>
     */
    class Solution{
        public String[] trulyMostPopular(String[] names, String[] synonyms) {
            HashMap<String, Integer> namesMap = transferToMap(names);
            String[][] synonyms2 = transferTo2Arr(synonyms);
            HashMap<String, Integer> res = stringIntegerHashMap(namesMap, synonyms2);
            return transferTo(res);
        }

        private String[] transferTo(HashMap<String, Integer> data) {
            ArrayList<String> arr = new ArrayList<>(data.size());
            data.forEach((k, v) -> {
                arr.add(String.format("(%s,%s)", k, v));
            });
            return arr.toArray(new String[arr.size()]);
        }

        private HashMap<String, Integer> stringIntegerHashMap(HashMap<String, Integer> namesMap, String[][] synonyms) {
            HashMap<String, NameSet> groups = constractGroups(namesMap);
            mergeClasses(groups, synonyms);
            return convertToMap(groups);
        }

        /**
         * 合并相同类型，检查每组值，合并相同的类别并将第二个类别映射到第一个集合上。
         *
         * @param groups
         * @param synonyms
         */
        private void mergeClasses(HashMap<String, NameSet> groups, String[][] synonyms) {
            for (String[] entry : synonyms) {
                String name1 = entry[0];
                String name2 = entry[1];
                NameSet set1 = groups.getOrDefault(name1, new NameSet(name1, 0));
                NameSet set2 = groups.getOrDefault(name2, new NameSet(name2, 0));

                if (set1 != set2) {
                    // 将较小的集合合并值较大的集合
                    NameSet smaller = set2.size() < set1.size() ? set2 :set1;
                    NameSet bigger = set2.size() < set1.size() ? set1 :set2;
                    // 合并链表
                    Set<String> otherNames = smaller.getNames();
                    int frequency = smaller.getFrequency();
                    bigger.copyNameWithFrequency(otherNames, frequency);
                    // 更新映射
                    for (String name : otherNames) {
                        groups.put(name, bigger);
                    }
                }
            }
        }

        /**
         * 转换为散列表
         *
         * @param groups
         * @return
         */
        private HashMap<String, Integer> convertToMap(HashMap<String, NameSet> groups) {
            HashMap<String, Integer> list = new HashMap<>();
            for (NameSet group : groups.values()) {
                list.put(group.getRootName(), group.getFrequency());
            }
            return list;
        }

        /**
         * 遍历（姓名，频率）组合，并初始化一个从姓名到 NameSets 的映射
         *
         * @param namesMap
         * @return
         */
        private HashMap<String, NameSet> constractGroups(HashMap<String, Integer> namesMap) {
            HashMap<String, NameSet> groups = new HashMap<>();
            for (Map.Entry<String, Integer> entry : namesMap.entrySet()) {
                String name = entry.getKey();
                Integer frequency = entry.getValue();
                NameSet group = new NameSet(name, frequency);
                groups.put(name, group);
            }
            return groups;
        }

        private String[][] transferTo2Arr(String[] synonyms) {
            int length = synonyms.length;
            String[][] res = new String[length][2];
            for (int i = 0; i < synonyms.length; i++) {
                String synonym = synonyms[i];
                res[i][0] = synonym.substring(1, synonym.indexOf(','));
                res[i][1] = synonym.substring(synonym.indexOf(',') + 1, synonym.length() - 1);
            }
            return res;
        }

        private HashMap<String, Integer> transferToMap(String[] names) {
            HashMap<String, Integer> res = new HashMap<>();
            for (String name : names) {
                res.put(name.substring(0, name.lastIndexOf('(')),
                        Integer.valueOf(name.substring(name.lastIndexOf('(') + 1, name.lastIndexOf(')'))));
            }
            return res;
        }

        class NameSet{
            private Set<String> names = new HashSet<>();
            private int frequency = 0;
            private String rootName;

            public NameSet(String name, int frequency) {
                this.names.add(name);
                this.frequency = frequency;
                rootName = name;
            }

            public void copyNameWithFrequency(Set<String> more, int freq) {
                if (more == null) {
                    return;
                }
                names.addAll(more);
                this.frequency += freq;
            }

            public Set<String> getNames() {
                return names;
            }

            public int getFrequency() {
                return frequency;
            }

            public String getRootName() {
                return rootName;
            }

            public int size() {
                return names.size();
            }
        }
    }


    /**
     * 算法运行的总时间是O(B + P)。我们至少要在B + P的数据中读取。
     * B 是婴儿名字的数量，P 是同义名字的对数。
     */
    class Solution2{
        public String[] trulyMostPopular(String[] names, String[] synonyms) {
            HashMap<String, Integer> namesMap = transferToMap(names);
            String[][] synonyms2 = transferTo2Arr(synonyms);

            // 创建数据
            Graph graph = contstructGraph(namesMap);
            connectEdges(graph, synonyms2);

            // 寻找连通部分
            HashMap<String, Integer> res = getTrueFrequencies(graph);
            return transferTo(res);
        }

        // 连接相似拼写法
        private void connectEdges(Graph graph, String[][] synonyms) {
            for (String[] entry : synonyms) {
                String name1 = entry[0];
                String name2 = entry[1];
                graph.addEdge(name1, name2);
            }
        }

        // 对每一个连通部分进行深度优先搜索如果一个节点被访问过，则其已经呗计算过
        private HashMap<String, Integer> getTrueFrequencies(Graph graph) {
            HashMap<String, Integer> rootNames = new HashMap<>();
            for (GraphNode node : graph.getNodes()) {
                if (!node.isVisited()) {// 已经访问过这个连通部分
                    Integer frequency = getComponentFrequency(node);
                    String name = node.getName();
                    rootNames.put(name, frequency);
                }
            }
            return rootNames;
        }

        private Integer getComponentFrequency(GraphNode node) {
            if (node.isVisited()) {
                return 0;
            }
            node.setIsVisited(true);
            int sum = node.getFrequency();
            for (GraphNode child : node.getNeighbors()) {
                sum += getComponentFrequency(child);
            }
            return sum;
        }

        private Graph contstructGraph(HashMap<String, Integer> namesMap) {
            Graph graph = new Graph();
            for (Map.Entry<String, Integer> entry : namesMap.entrySet()) {
                String name = entry.getKey();
                Integer frequency = entry.getValue();
                graph.createNode(name, frequency);
            }
            return graph;
        }


        private HashMap<String, Integer> transferToMap(String[] names) {
            HashMap<String, Integer> res = new HashMap<>();
            for (String name : names) {
                res.put(name.substring(0, name.lastIndexOf('(')),
                        Integer.valueOf(name.substring(name.lastIndexOf('(') + 1, name.lastIndexOf(')'))));
            }
            return res;
        }

        private String[][] transferTo2Arr(String[] synonyms) {
            int length = synonyms.length;
            String[][] res = new String[length][2];
            for (int i = 0; i < synonyms.length; i++) {
                String synonym = synonyms[i];
                res[i][0] = synonym.substring(1, synonym.indexOf(','));
                res[i][1] = synonym.substring(synonym.indexOf(',') + 1, synonym.length() - 1);
            }
            return res;
        }

        private String[] transferTo(HashMap<String, Integer> data) {
            ArrayList<String> arr = new ArrayList<>(data.size());
            data.forEach((k, v) -> {
                arr.add(String.format("(%s,%s)", k, v));
            });
            return arr.toArray(new String[arr.size()]);
        }

        public class Graph{
            private ArrayList<GraphNode> nodes;
            private HashMap<String, GraphNode> map;

            public Graph() {
                map = new HashMap<String, GraphNode>();
                nodes = new ArrayList<GraphNode>();
            }

            public boolean hasNode(String name) {
                return map.containsKey(name);
            }

            public GraphNode createNode(String name, int freq) {
                if (map.containsKey(name)) {
                    return getNode(name);
                }

                GraphNode node = new GraphNode(name, freq);
                nodes.add(node);
                map.put(name, node);
                return node;
            }

            private GraphNode getNode(String name) {
                return map.get(name);
            }

            public ArrayList<GraphNode> getNodes() {
                return nodes;
            }

            public void addEdge(String startName, String endName) {
                GraphNode start = getNode(startName);
                GraphNode end = getNode(endName);
                if (start != null && end != null) {
                    start.addNeighbor(end);
                    end.addNeighbor(start);
                }
            }
        }

        public class GraphNode{
            private ArrayList<GraphNode> neighbors;
            private HashMap<String, GraphNode> map;
            private String name;
            private int frequency;
            private boolean visited = false;

            public GraphNode(String nm, int freq) {
                name = nm;
                frequency = freq;
                neighbors = new ArrayList<GraphNode>();
                map = new HashMap<String, GraphNode>();
            }

            public String getName() {
                return name;
            }

            public int getFrequency() {
                return frequency;
            }

            public boolean addNeighbor(GraphNode node) {
                if (map.containsKey(node.getName())) {
                    return false;
                }
                neighbors.add(node);
                map.put(node.getName(), node);
                return true;
            }

            public ArrayList<GraphNode> getNeighbors() {
                return neighbors;
            }

            public boolean isVisited() {
                return visited;
            }

            public void setIsVisited(boolean v) {
                visited = v;
            }
        }

    }

    // 并查集
    class Solution3{
        Map<String, String> map;

        public String[] trulyMostPopular(String[] names, String[] synonyms) {
            Map<String, Integer> cnt = new HashMap<>();
            map = new HashMap<>();
            // 初始化并查集元素
            for (String name : names) {
                int i = 0;
                while (name.charAt(i) != '(') i++;
                map.put(name.substring(0, i), name.substring(0, i));
            }
            // 1.联通的并查集合并操作
            for (String name : synonyms) {
                String[] temp = name.split(",");
                String x = temp[0].substring(1, temp[0].length());
                String y = temp[1].substring(0, temp[1].length() - 1);

                if (!map.containsKey(x)) map.put(x, x);
                if (!map.containsKey(y)) map.put(y, y);

                // 获得两个集合的根
                String fx = find(x);
                String fy = find(y);

                // 合并两个并查集，将字典序小的根作为新的根
                if (!fx.equals(fy)) {
                    if (fx.compareTo(fy) > 0) map.put(fx, fy);
                    else map.put(fy, fx);
                }
            }

            // 2.计数
            for (String name : names) {
                int i = 0;
                while (name.charAt(i) != '(') i++;

                // 将数值都累加到根的位置
                String root = find(name.substring(0, i));
                cnt.put(root, cnt.getOrDefault(root, 0) + Integer.parseInt(name.substring(i + 1, name.length() - 1)));
            }

            List<String> res = new ArrayList<>();

            // 3.输出答案
            for (String name : names) {
                int i = 0;
                while (name.charAt(i) != '(') i++;

                String root = find(name.substring(0, i));

                // 只输出根
                if (!root.equals(name.substring(0, i))) continue;

                res.add(root + "(" + String.valueOf(cnt.get(root)) + ")");
            }

            return res.toArray(new String[res.size()]);
        }

        // 查根
        public String find(String x) {
            if (!map.get(x).equals(x)) {
                map.put(x, find(map.get(x)));
            }

            return map.get(x);
        }
    }

    class Solution4{
        private final class IntegerHolder{
            private int num = 0;

            public int getNum() {
                return num;
            }

            public void add(Integer c) {
                this.num += c;
            }
        }

        public String[] trulyMostPopular(String[] names, String[] synonyms) {
            Map<String, IntegerHolder> map = new HashMap<>();
            Map<IntegerHolder, ArrayList<String>> resMap = new HashMap<>();
            for (String s : synonyms) {
                int splitI = s.indexOf(",");
                String s1 = s.substring(1, splitI);
                String s2 = s.substring(splitI + 1, s.length() - 1);
                IntegerHolder i1 = map.get(s1);
                IntegerHolder i2 = map.get(s2);
                if (i1 != null && i2 != null) {
                    if (i1 != i2) {
                        ArrayList<String> a1 = resMap.get(i1);
                        ArrayList<String> a2 = resMap.get(i2);
                        for (String a2s : a2) {
                            map.put(a2s, i1);
                            a1.add(a2s);
                        }
                        resMap.remove(i2);
                    }
                }
                else if (i1 != null) {
                    map.put(s2, i1);
                    resMap.get(i1).add(s2);
                }
                else if (i2 != null) {
                    map.put(s1, i2);
                    resMap.get(i2).add(s1);
                }
                else {
                    IntegerHolder i3 = new IntegerHolder();
                    ArrayList<String> list = new ArrayList<String>();
                    list.add(s1);
                    list.add(s2);
                    map.put(s1, i3);
                    map.put(s2, i3);
                    resMap.put(i3, list);
                }
            }
            ArrayList<String> result = new ArrayList<>();
            for (String name : names) {
                int numI = name.indexOf("(");
                String realName = name.substring(0, numI);
                int c = Integer.parseInt(name.substring(numI + 1, name.indexOf(")")));
                if (map.containsKey(realName)) {
                    map.get(realName).add(c);
                }
                else {
                    result.add(name);
                }
            }
            for (Map.Entry<IntegerHolder, ArrayList<String>> entry : resMap.entrySet()) {
                ArrayList<String> list = entry.getValue();
                String name = list.get(0);
                for (String lName : list) {
                    if (lName.compareTo(name) < 0) {
                        name = lName;
                    }
                }
                result.add(name + "(" + entry.getKey().getNum() + ")");
            }
            String[] r = new String[result.size()];
            return result.toArray(r);
        }
    }

    @Test
    public void test() {
        String[] names = {"John(15)", "Jon(12)", "Chris(13)", "Kris(4)", "Christopher(19)"};
        String[] synonyms = {"(Jon,John)", "(John,Johnny)", "(Chris,Kris)", "(Chris,Christopher)"};
        Solution2 solution = new Solution2();
        String[] res = solution.trulyMostPopular(names, synonyms);
        System.out.println(Arrays.toString(res));
    }
}
