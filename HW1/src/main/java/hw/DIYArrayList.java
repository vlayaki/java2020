package hw;

import java.util.*;
import java.util.function.Predicate;

public class DIYArrayList<T> implements List<T> {

    private Object[] elements;
    private int lastElement;

    public DIYArrayList(T[] elements) {
        this.elements = elements;
        lastElement = elements.length;
    }

    public DIYArrayList() {
        elements = new Object[10];
        lastElement = -1;
    }

    @Override
    public int size() {
        return lastElement + 1;
    }

    @Override
    public boolean isEmpty() {
        return lastElement < 0;
    }

    @Override
    public boolean contains(Object o) {
        Predicate<T> predicate = o == null
                ? Objects::isNull
                : e -> o.equals(e);
        return contains(predicate);
    }

    private boolean contains(Predicate<T> predicate) {
        for (int i = 0; i < elements.length; i++) {
            if (predicate.test((T) elements[i])) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private int i = 0;

            @Override
            public boolean hasNext() {
                return i < lastElement;
            }

            @Override
            public T next() {
                return (T) elements[i++];
            }
        };
    }

    @Override
    public Object[] toArray() {
        return Arrays.copyOf(elements, elements.length);
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        a = increaseArraySizeIfNeeded(a);
        for (int i = 0; i < elements.length; i++) {
            a[i] = (T1) elements[i];
        }
        return a;
    }

    private <T1> T1[] increaseArraySizeIfNeeded(T1[] a) {

        int requiredLength = lastElement + 1;
        if (requiredLength < a.length) {
            return a;
        }
        return Arrays.copyOf(a, requiredLength);
    }

    @Override
    public boolean add(T t) {
        increaseSizeIfNeeded(0);
        elements[++lastElement] = t;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        int index = indexOf(o);
        if (index == -1) {
            return false;
        }
        int nOfElementsToMoveLeft = elements.length - 1 - index;
        System.arraycopy(elements, index + 1, elements, index, nOfElementsToMoveLeft);
        elements[lastElement--] = null;
        return true;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (int i = 0; i < c.size(); i++) {
            if (!contains(c))
                return false;
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        if (isEmpty(c)) return false;
        Object[] arr = c.toArray();
        increaseSizeIfNeeded(arr.length);
        System.arraycopy(arr, 0, elements, lastElement + 1, arr.length);
        lastElement += arr.length;
        return true;
    }

    private boolean isEmpty(Collection<?> c) {
        if (c == null || c.size() == 0)
            return true;
        return false;
    }

    private void increaseSizeIfNeeded(int nOfNewelements) {
        if (elements.length - lastElement - 1 > nOfNewelements)
            return;
        int newSize = Math.max(lastElement + nOfNewelements + 1, elements.length * 2);
        elements = Arrays.copyOf(elements, newSize);
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        checkIndex(index);
        if (isEmpty(c))
            return false;
        increaseSizeIfNeeded(c.size());
        System.arraycopy(elements, index, elements, index + c.size(), c.size());
        Object[] array = c.toArray();
        System.arraycopy(array, 0, this.elements, index, array.length);
        lastElement += array.length;
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return batchRemove(c, false);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return batchRemove(c, true);
    }

    private boolean batchRemove(Collection<?> c, boolean retain) {
        if (isEmpty(c))
            return false;
        int w = 0;
        for (int i = 0; i < elements.length; i++) {
            if (c.contains(elements[i]) == retain) {
                elements[w++] = elements[i];
            }
        }
        for (int i = w; i < elements.length; i++) {
            elements[w] = null;
        }
        lastElement = w;
        return w > 0;
    }

    @Override
    public void clear() {
        for (int i = 0; i < elements.length; i++) {
            elements[i] = null;
        }
        lastElement = -1;
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return (T) elements[index];
    }

    @Override
    public T set(int index, T element) {
        checkIndex(index);
        T oldValue = (T) elements[index];
        elements[index] = element;
        return oldValue;
    }

    private void checkIndex(int index) {
        if (index < 0 || index > lastElement) {
            throw new IndexOutOfBoundsException();
        }
    }

    @Override
    public void add(int index, T element) {
        increaseSizeIfNeeded(0);
        System.arraycopy(element, index, element, index + 1, 1);
        elements[index] = element;
        lastElement++;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        T oldValue = (T) elements[index];
        System.arraycopy(elements, index + 1, elements, index, lastElement - index);
        elements[lastElement--] = null;
        return oldValue;
    }

    @Override
    public int indexOf(Object o) {
        Predicate predicate = o == null
                ? Objects::isNull
                : e -> o.equals(e);
        for (int i = 0; i < elements.length; i++) {
            if (predicate.test(elements[i])) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        Predicate predicate = o == null
                ? Objects::isNull
                : e -> o.equals(e);
        for (int i = elements.length; i >= 0; i--) {
            if (predicate.test(elements[i])) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public ListIterator<T> listIterator() {
        throw new UnsupportedOperationException();
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException();
    }
}
