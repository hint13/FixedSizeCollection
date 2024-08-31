package dev.h13j;

import org.jetbrains.annotations.NotNull;

import java.util.*;

public class FixedSizeCollection<E> implements Collection<E> {

    private final E[] elements;
    private final boolean[] fakeMarkers;
    private int size;

    @SuppressWarnings("unchecked")
    public FixedSizeCollection(int size) {
        elements = (E[]) new Object[size];
        fakeMarkers = new boolean[size];
        Arrays.fill(fakeMarkers, true);
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
        return size == fakeMarkers.length;
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
    public @NotNull Iterator<E> iterator() {
        return new FixedSizeCollectionIterator<>();
    }

    @Override
    public Object @NotNull [] toArray() {
        Object[] result = new Object[size];
        int i = 0;
        for (E e : this) {
            result[i] = e;
            i++;
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T @NotNull [] toArray(T @NotNull [] a) {
        T[] result = (T[]) java.lang.reflect.Array.newInstance(a.getClass().componentType(), size);
        int i = 0;
        for (E e : this) {
            result[i] = (T) e;
            i++;
        }
        return result;
    }

    @Override
    public boolean add(E e) {
        for (int i = 0; i < fakeMarkers.length; i++) {
            if (fakeMarkers[i]) {
                fakeMarkers[i] = false;
                elements[i] = e;
                size++;
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean remove(Object o) {
        FixedSizeCollectionIterator<E> iterator = new FixedSizeCollectionIterator<>();
        boolean isAnyRemoved = false;
        while (iterator.hasNext()) {
            iterator.next();
            int i = iterator.index();
            if (o == null) {
                if (elements[i] == null) {
                    iterator.remove();
                    isAnyRemoved = true;
                }
            } else {
                if (o.equals(elements[i])) {
                    iterator.remove();
                    isAnyRemoved = true;
                }
            }
        }
        return isAnyRemoved;
    }

    @Override
    public boolean containsAll(@NotNull Collection<?> c) {
        for (Object o : c) {
            if (!contains(o))
                return false;
        }
        return !c.isEmpty();
    }

    @Override
    public boolean addAll(@NotNull Collection<? extends E> c) {
        boolean isAnyAdded = false;
        for (E e : c) {
            isAnyAdded = add(e) || isAnyAdded;
            if (isFull()) break;
        }
        return isAnyAdded;
    }

    @Override
    public boolean removeAll(@NotNull Collection<?> c) {
        if (c.isEmpty())
            return false;
        boolean isAnyRemoved = false;
        FixedSizeCollectionIterator<E> iterator = new FixedSizeCollectionIterator<>();
        while (iterator.hasNext()) {
            if (c.contains(iterator.next())) {
                iterator.remove();
                isAnyRemoved = true;
            }
        }
        return isAnyRemoved;
    }

    @Override
    public boolean retainAll(@NotNull Collection<?> c) {
        if (c.isEmpty())
            return false;
        boolean isAnyRemoved = false;
        FixedSizeCollectionIterator<E> iterator = new FixedSizeCollectionIterator<>();
        while (iterator.hasNext()) {
            if (!c.contains(iterator.next())) {
                iterator.remove();
                isAnyRemoved = true;
            }
        }
        return isAnyRemoved;
    }

    @Override
    public void clear() {
        Arrays.fill(fakeMarkers, true);
        size = 0;
    }

    @Override
    public String toString() {
        StringJoiner result = new StringJoiner(" ");
        for (E e : this) {
            result.add(String.valueOf(e));
        }
        return result.toString();
    }

    class FixedSizeCollectionIterator<T> implements Iterator<T> {
        private int i = -1;

        @Override
        public boolean hasNext() {
            if (i == fakeMarkers.length) return false;
            return nextNotFakeIndex() < fakeMarkers.length;
        }

        @SuppressWarnings("unchecked")
        @Override
        public T next() {
            if (!hasNext())
                throw new NoSuchElementException();
            i = nextNotFakeIndex();
            return (T) elements[i];
        }

        @Override
        public void remove() {
            if ((i < 0 || i >= fakeMarkers.length) || fakeMarkers[i])
                throw new NoSuchElementException();
            elements[i] = null;
            fakeMarkers[i] = true;
            size--;
        }

        private int nextNotFakeIndex() {
            int res = i + 1;
            while (res < fakeMarkers.length && fakeMarkers[res])
                res++;
            return res;
        }

        int index() {
            return i;
        }
    }

}
