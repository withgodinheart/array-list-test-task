package core.basesyntax;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import javax.annotation.Nonnull;

/**
 * Custom implementation of ArrayList
 *
 * @param <T> type of stored elements
 * @author Oleksandr Vashchenko
 * @see java.util.ArrayList
 */
public class ArrayList<T> implements List<T> {

    /**
     * Initial capacity by default
     */
    private static final int DEFAULT_CAPACITY = 10;

    /**
     * Resize coefficient
     */
    private static final float RESIZE_COEFFICIENT = 1.5f;

    /**
     * Array where elements are stored
     */
    private Object[] data;

    /**
     * Number of stored elements in this list
     */
    private int size;

    /**
     * Constructor with capacity argument
     *
     * @param capacity initial capacity of this list
     */
    public ArrayList(int capacity) {
        data = new Object[capacity];
    }

    /**
     * Default constructor with default capacity = {@value #DEFAULT_CAPACITY}
     */
    public ArrayList() {
        this(DEFAULT_CAPACITY);
    }

    /**
     * Checks if this list is full and calls {@link #resize()}
     */
    private void checkCapacityAndResize() {
        if (size == data.length) {
            resize();
        }
    }

    /**
     * Enlarges capacity of this list
     *
     * @see #RESIZE_COEFFICIENT
     */
    private void resize() {
        int capacity = (int) (size * RESIZE_COEFFICIENT);
        Object[] newData = new Object[capacity];
        System.arraycopy(data, 0, newData, 0, size);
        data = newData;
    }

    /**
     * Checks whether index is valid
     *
     * @param index index of element
     * @throws ArrayListIndexOutOfBoundsException if index is invalid
     */
    private void checkIndex(int index) {
        if (index > size - 1 || index < 0) {
            throw new ArrayListIndexOutOfBoundsException(
                    String.format("Index %s is out of bounds for size %s", index, size)
            );
        }
    }

    /**
     * Checks whether index is valid to add
     *
     * @param index index of element
     * @throws ArrayListIndexOutOfBoundsException if index is invalid
     */
    private void checkIndexForAdd(int index) {
        if (index > size || index < 0) {
            throw new ArrayListIndexOutOfBoundsException(
                    String.format("Index %s is invalid to add for size %s", index, size)
            );
        }
    }

    /**
     * Adds new element to this list
     *
     * @param value element to add
     */
    @Override
    public void add(T value) {
        checkCapacityAndResize();
        data[size++] = value;
    }

    /**
     * Adds new element by s specific index to this list
     *
     * @param value element to add
     * @param index index of element
     */
    @Override
    public void add(T value, int index) {
        checkIndexForAdd(index);
        checkCapacityAndResize();
        System.arraycopy(data, index, data, index + 1, size - index);
        data[index] = value;
        size++;
    }

    /**
     * Adds all elements from parameter's list to this list
     *
     * @param list list with elements to add
     */
    @Override
    public void addAll(@Nonnull List<T> list) {
        Objects.requireNonNull(list);
        for (int i = 0; i < list.size(); i++) {
            add(list.get(i));
        }
    }

    /**
     * Gets element by index
     *
     * @param index index of element
     * @return element at specified position in this list
     */
    @Override
    @SuppressWarnings("unchecked")
    public T get(int index) {
        checkIndex(index);
        return (T) data[index];
    }

    /**
     * Replaces element by index
     *
     * @param value element to replace
     * @param index index of element
     */
    @Override
    public void set(T value, int index) {
        checkIndex(index);
        data[index] = value;
    }

    /**
     * Removes element by index
     *
     * @param index index of element
     * @return removed element
     */
    @Override
    public T remove(int index) {
        checkIndex(index);
        T oldValue = get(index);
        System.arraycopy(data, index + 1, data, index, size - index - 1);
        data[--size] = null;
        return oldValue;
    }

    /**
     * Removes element
     *
     * @param element element to remove
     * @return removed element
     * @throws NoSuchElementException if no such element found
     */
    @Override
    public T remove(T element) {
        Optional<?> nullableElement = Optional.ofNullable(element);
        Optional<?> nullableDataElement;
        for (int i = 0; i < size; i++) {
            nullableDataElement = Optional.ofNullable(data[i]);
            if (nullableElement.equals(nullableDataElement)) {
                return remove(i);
            }
        }
        throw new NoSuchElementException("No such element found");
    }

    /**
     * @return size of this list
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Checks whether this list is empty
     *
     * @return true if this list is empty
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }
}
