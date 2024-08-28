package dev.h13j;

import java.util.*;

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
        return size == 0;
    }

    public boolean isFull() {
        return size == dummies.length;
    }

    @Override
    public boolean contains(Object o) {
        if (size == 0)
            return false;
        if (o == null) {
            return hasNull();
        }
        for (E e : this) {
            if (e != null && e.equals(o)) {
                return true;
            }
        }
        return false;
    }

    public boolean hasNull() {
        for (E e : this) {
            if (e == null) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private int i = 0;

            @Override
            public boolean hasNext() {
                if (i == dummies.length) return false;
                while (i < dummies.length && dummies[i])
                    i++;
                return i < dummies.length;
            }

            @Override
            public E next() {
                if (!hasNext())
                    throw new NoSuchElementException();
                if (!dummies[i])
                    return elements[i++];
                else
                    i++;
                return next();
            }

            @Override
            public void remove() {
                elements[i] = null;
                dummies[i] = true;
                size--;
            }
        };
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
        for (int i = 0; i < dummies.length; i++) {
            if (dummies[i]) {
                dummies[i] = false;
                elements[i] = e;
                size++;
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object o : c) {
            if (!contains(o))
                return false;
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        boolean isAnyAdded = false;
        for (E e : c) {
            if (isFull()) break;
            isAnyAdded = add(e) || isAnyAdded;
        }
        return isAnyAdded;
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
