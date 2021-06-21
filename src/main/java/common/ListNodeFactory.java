package common;

/**
 * @author mayanwei
 * @date 2021-06-17.
 */
public class ListNodeFactory{
    private static ListNodeVisitor listNodeVisitor = new ListNodeVisitor();

    private ListNodeFactory(){

    }

    public static ListNodeBuilder getListNodeBuilder(){
        return ListNodeBuilder.builder();
    }

    public static ListNodeVisitor getListNodeVisitor(){
        return listNodeVisitor;
    }
}
