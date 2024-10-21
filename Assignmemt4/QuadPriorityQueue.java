package edu.neu.coe.info6205.pq;

import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Consumer;

public class QuadPriorityQueue<K> implements Iterable<K> {

    public QuadPriorityQueue(boolean max, Object[] quadHeap, int first, int last, Comparator<K> comparator, boolean floyd) {
        this.max = max;
        this.first = first;
        this.comparator = comparator;
        this.last = last;
        this.quadHeap = (K[]) quadHeap;
        this.floyd = floyd;
    }

    public QuadPriorityQueue(int n, int first, boolean max, Comparator<K> comparator, boolean floyd) {
        this(max, new Object[n + first], first, 0, comparator, floyd);
    }

    public QuadPriorityQueue(int n, boolean max, Comparator<K> comparator, boolean floyd) {
        this(n, 1, max, comparator, floyd);
    }

    public QuadPriorityQueue(int n, boolean max, Comparator<K> comparator) {
        this(n, 1, max, comparator, false);
    }

    public QuadPriorityQueue(int n, Comparator<K> comparator) {
        this(n, 1, true, comparator, true);
    }

    public boolean isEmpty() {
        return last == 0;
    }

    public int size() {
        return last;
    }

    public void give(K key) {
        if (last == quadHeap.length - first)
            last--;
        quadHeap[++last + first - 1] = key;
        swimUp(last + first - 1);
    }

    public K take() throws PQException {
        if (isEmpty()) throw new PQException("4-ary Heap is empty");
        if (floyd) return doTake(this::snake);
        else return doTake(this::sink);
    }

    K doTake(Consumer<Integer> f) {
        K result = quadHeap[first];
        swap(first, last-- + first - 1);
        f.accept(first);
        quadHeap[last + first] = null;
        return result;
    }

    void sink(int k) {
        doHeapify(k, (a, b) ->!unordered(a, b));
    }

    private int doHeapify(int k, BiPredicate<Integer, Integer> p) {
        int i = k;
        while (firstChild(i) <= last + first - 1) {
            int j = firstChild(i);
            for (int c = 1; c < 4; c++) {
                if (j + c < last + first - 1 && unordered(j, j + c)) j++;
            }
            if (!unordered(k, j)) break;
            swap(i, j);
            i = j;
        }
        return i;
    }

    void snake(int k) {
        swimUp(doHeapify(k, (a, b) ->!unordered(a, b)));
    }

    void swimUp(int k) {
        int i = k;
        while (i > first && unordered(parent(i), i)) {
            swap(i, parent(i));
            i = parent(i);
        }
    }

    private void swap(int i, int j) {
        K tmp = quadHeap[i];
        quadHeap[i] = quadHeap[j];
        quadHeap[j] = tmp;
    }

    boolean unordered(int i, int j) {
        if (quadHeap[i] == null || quadHeap[j] == null) {
            return false; 
        }
        return (comparator.compare(quadHeap[i], quadHeap[j]) > 0) ^ max;
    }

    private int parent(int k) {
        return (k + 1 - first) / 4 + first - 1;
    }

    private int firstChild(int k) {
        return (k + 1 - first) * 4 + first - 1;
    }

    private final boolean max;
    private final int first;
    private final Comparator<K> comparator;
    private final K[] quadHeap;
    private int last;
    private final boolean floyd;

    public Iterator<K> iterator() {
        Collection<K> copy = new ArrayList<>(Arrays.asList(Arrays.copyOf(quadHeap, last + first)));
        Iterator<K> result = copy.iterator();
        if (first > 0) result.next();
        return result;
    }
}
