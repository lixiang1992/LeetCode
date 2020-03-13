package design.medium;

import java.util.Iterator;

/**
 * 284.顶端迭代器
 */
public class PeekingIterator implements Iterator<Integer> {

    private Integer cache;
    private Iterator<Integer> iterator;
    public PeekingIterator(Iterator<Integer> iterator) {
        this.iterator = iterator;
    }

    // Returns the next element in the iteration without advancing the iterator.
    public Integer peek() {
        if (cache == null && iterator.hasNext()){
            cache = iterator.next();
        }
        return cache;
    }

    @Override
    public boolean hasNext() {
        return cache != null || iterator.hasNext();
    }

    @Override
    public Integer next() {
        if (cache == null){
            return iterator.next();
        }else {
            int temp = cache;
            cache = null;
            return temp;
        }
    }

}
