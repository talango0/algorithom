package common;

/**
 * @author mayanwei
 */
public class TreeNode{
    public Integer key;
    public TreeNode right;
    public TreeNode left;
    public TreeNode p;
    public TreeNode(Integer key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return "TreeNode{" +
                "key=" + key +
                '}';
    }
}
