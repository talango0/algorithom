package inductiontoalgorithm.btree;

import java.util.Iterator;

/**
 * @author mayanwei
 * @date 2021-06-25.
 */
public class PageImpl<Key> implements Page<Key> {
    @Override
    public Page page(boolean bottom){
        return null;
    }

    @Override
    public void close(){

    }

    @Override
    public void add(Key key){

    }

    @Override
    public void add(Page p){

    }

    @Override
    public boolean isExternal(){
        return false;
    }

    @Override
    public boolean contains(Key key){
        return false;
    }

    @Override
    public Page next(Key key){
        return null;
    }

    @Override
    public boolean isFull(){
        return false;
    }

    @Override
    public Page split(){
        return null;
    }

    @Override
    public Iterator<Key> keys(){
        return null;
    }
}
