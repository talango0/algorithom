package leetcode.程序员面试金典;
//两个(具有不同单词的)文档的交集(intersection)中元素的个数除以并集(union)中元素的个数，就是这两个文档的相似度。
// 例如，{1, 5, 3} 和 {1, 7, 2, 3} 的相似度是 0.4，其中，交集的元素有 2 个，并集的元素有 5 个。
// 给定一系列的长篇文档，每个文档元素各不相同，并与一个 ID 相关联。它们的相似度非常“稀疏”，也就是说任选 2 个文档，相似度都很接近 0。
// 请设计一个算法返回每对文档的 ID 及其相似度。只需输出相似度大于 0 的组合。请忽略空文档。
// 为简单起见，可以假定每个文档由一个含有不同整数的数组表示。
//
//输入为一个二维数组 docs，docs[i]表示id 为 i 的文档。返回一个数组，其中每个元素是一个字符串，代表每对相似度大于 0 的文档，
// 其格式为 {id1},{id2}: {similarity}，其中 id1 为两个文档中较小的 id，similarity 为相似度，精确到小数点后 4 位
// 。以任意顺序返回数组均可。
//
//示例:
//
//输入: 
//[
// [14, 15, 100, 9, 3],
// [32, 1, 9, 3, 5],
// [15, 29, 2, 6, 8, 7],
// [7, 10]
//]
//输出:
//[
// "0,1: 0.2500",
// "0,2: 0.1000",
// "2,3: 0.1429"
//]
//提示：
//
//docs.length <= 500
//docs[i].length <= 500
//
//
//来源：力扣（LeetCode）
//链接：https://leetcode.cn/problems/sparse-similarity-lcci
//著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。

import org.junit.Test;

import java.util.*;

/**
 * @author mayanwei
 * @date 2023-06-25.
 */
public class _17_26_稀疏相似度{
    class Solution{
        class DocPair{
            public int doc1, doc2;

            public DocPair(int doc1, int doc2) {
                this.doc1 = doc1;
                this.doc2 = doc2;
            }

            @Override
            public boolean equals(Object o) {
                if (this == o) return true;
                if (!(o instanceof DocPair)) return false;
                DocPair docPair = (DocPair) o;
                return doc1 == docPair.doc1 && doc2 == docPair.doc2;
            }

            @Override
            public int hashCode() {
                return Objects.hash(doc1, doc2);
            }
        }

        class Document{
            private List<Integer> words;
            private int docId;

            public Document(List<Integer> words, int docId) {
                this.words = words;
                this.docId = docId;
            }

            public List<Integer> getWords() {
                return words;
            }

            public void setWords(List<Integer> words) {
                this.words = words;
            }

            public int getDocId() {
                return docId;
            }

            public void setDocId(int docId) {
                this.docId = docId;
            }

            public int size() {
                return words == null ? 0 :words.size();
            }
        }

        public List<String> computeSimilarities(int[][] docs) {
            HashMap<DocPair, Double> similarities = new HashMap<>();
            for (int i = 0; i < docs.length; i++) {
                for (int j = i + 1; j < docs.length; j++) {
                    int[] doc1 = docs[i];
                    int[] doc2 = docs[j];
                    ArrayList<Integer> l1 = new ArrayList<>();
                    ArrayList<Integer> l2 = new ArrayList<>();
                    for (int doc : doc1) {
                        l1.add(doc);
                    }
                    for (int doc : doc2) {
                        l2.add(doc);
                    }
                    Document doci = new Document(l1, i);
                    Document docj = new Document(l2, j);
                    double sim = computeSimilarity(doci, docj);
                    if (sim > 0) {
                        DocPair pair = new DocPair(doci.getDocId(), docj.getDocId());
                        similarities.put(pair, sim);
                    }
                }
            }
            return toList(similarities);
        }

        private List<String> toList(HashMap<DocPair, Double> similarities) {
            ArrayList<String> res = new ArrayList<>();
            for (Map.Entry<DocPair, Double> entry : similarities.entrySet()) {
                res.add(String.format("%s,%s: %.4f", entry.getKey().doc1, entry.getKey().doc2, entry.getValue()));
            }
            return res;
        }

        private double computeSimilarity(Document doc1, Document doc2) {
            int intersection = 0;
            HashSet<Integer> set1 = new HashSet<>();
            set1.addAll(doc1.getWords());
            for (Integer word : doc2.getWords()) {
                if (set1.contains(word)) {
                    intersection++;
                }
            }
            double union = doc1.size() + doc2.size() - intersection;
            return intersection / union;
        }
    }


    /**
     * 倒排索引
     */
    class Solution1{
        public List<String> computeSimilarities(int[][] docs) {
            List<String> ans = new ArrayList<>();
            Map<Integer, List<Integer>> map = new HashMap<>();
            // help[i][j]表示 doci 和 docj 共有多少个相同的单词
            int[][] help = new int[docs.length][docs.length];
            for (int i = 0; i < docs.length; i++) {
                for (int j = 0; j < docs[i].length; j++) {
                    List<Integer> list = map.get(docs[i][j]);
                    if (list == null) {
                        list = new ArrayList<>();
                        map.put(docs[i][j], list);
                    }
                    else {
                        for (Integer k : list) {
                            help[i][k]++;
                        }
                    }
                    list.add(i);
                }

                for (int k = 0; k < docs.length; k++) {
                    if (help[i][k] > 0) {
                        ans.add(k + "," + i + ": " + String.format("%.4f", (double) help[i][k] / (docs[i].length + docs[k].length - help[i][k])));
                    }
                }
            }
            return ans;
        }
    }

    @Test
    public void test() {

        int[][] input = {{14, 15, 100, 9, 3}, {32, 1, 9, 3, 5}, {15, 29, 2, 6, 8, 7}, {7, 10}};
        Solution1 solution1 = new Solution1();
        List<String> strings = solution1.computeSimilarities(input);
        System.out.println(Arrays.toString(strings.toArray()));
    }

}
