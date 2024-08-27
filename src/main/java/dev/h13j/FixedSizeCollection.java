package dev.h13j;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

public class FixedSizeCollection<E> implements Collection<E> {

    private final E[] elements;
    private final boolean[] dummies;
    private int size;

    @SuppressWarnings("unchecked")
    public FixedSizeCollection(int size) {
        elements = (E[]) new Object[size];
        dummies = new boolean[size];
        Arrays.fill(dummies, true);
        this.size = 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean contains(Object o) {
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        return null;
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return null;
    }

    @Override
    public boolean add(E e) {
        return false;
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public void clear() {

    }
}
