package inductiontoalgorithm.btree;

import java.util.Iterator;

/**
 * @author mayanwei
 * @date 2021-05-17.
 */
public interface Page<Key>{

    /**
     * create and open a page
     *
     * @param bottom
     * @return
     */
    Page page(boolean bottom);

    /**
     * close a page
     */
    void close();

    /**
     * put key into the (external) page
     */
    void add(Key key);

    /**
     * open a page and put a entry into this (internal) page
     * that associates this smallest key p with p
     *
     * @param p
     */
    void add(Page p);

    /**
     * is this page external?
     */
    boolean isExternal();

    /**
     * is key in the page?
     *
     * @param key
     * @return
     */
    boolean contains(Key key);

    /**
     * the subtree that contain the key
     *
     * @param key
     * @return
     */
    Page next(Key key);

    /**
     * has the page overflowed?
     *
     * @return
     */
    boolean isFull();

    /**
     * move the highest-ranking half of the keys in the page
     * into a new page
     *
     * @return
     */
    Page split();

    Iterator<Key> keys();

}
