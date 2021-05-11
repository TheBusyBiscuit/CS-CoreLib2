package io.github.thebusybiscuit.cscorelib2.collections;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Collection;
import java.util.Map;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Supplier;

/**
 * Utilities for deep-copying collections, maps and arrays
 */
public final class CopyUtils {
    
    private CopyUtils() {}

    /**
     * Perform a deep copy of all the elements from a given {@link Collection} to another.
     * <p>
     * If the source collection contains <code>null</code> elements, the cloning function should be
     * handle null input. Additionally, the sink should also accept any values
     * contained by the source collection, null or not.
     *
     * @param source          The source of the elements
     * @param cloningFunction The function which clones the elements
     * @param sink            The collection in which to copy to the cloned elements to
     * @param <T>             The type of elements in the collections
     * @param <C>             The type of the sink
     *
     */
    public static <T, C extends Collection<T>> void deepCopy(@NonNull Collection<T> source, @NonNull Function<T, T> cloningFunction, @NonNull C sink) {
        for (T original : source) {
            T cloned = cloningFunction.apply(original);
            sink.add(cloned);
        }
    }

    /**
     * Perform a deep copy of all the elements from a given {@link Collection} to another.
     * <p>
     * If the source collection contains <code>null</code> elements, the cloning function should be
     * handle null input. Additionally, the sink supplied by the sink supplier
     * should also accept any values contained by the source collection, null or not.
     *
     * @param source          The source of the elements
     * @param cloningFunction The function which clones the elements
     * @param sinkSupplier    The supplier which consumes the size of the source collection and 
     *                        supplies the collection to copy cloned elements to
     *                        
     * @param <T>             The type of elements in the collections
     * @param <C>             The type of the returned collection
     *
     */
    public static <T, C extends Collection<T>> C deepCopy(@NonNull Collection<T> source, @NonNull Function<T, T> cloningFunction, @NonNull IntFunction<C> sinkSupplier) {
        final C sink = sinkSupplier.apply(source.size());
        deepCopy(source, cloningFunction, sink);
        return sink;
    }

    /**
     * Perform a deep copy of all the elements from a given {@link Map} to another.
     * <p>
     * If the source map contains <code>null</code> values, the cloning function should be
     * handle null input. Additionally, the sink should also accept any keys and values
     * contained by the source Map, null or not.
     *
     * @param source          The source of the elements
     * @param cloningFunction The function which clones the elements
     * @param sink            The map in which to copy to the cloned elements to
     * @param <K>             The type of keys in the maps
     * @param <V>             The type of values in the maps
     * @param <M>             The type of the sink
     *
     */
    public static <K, V, M extends Map<K, V>> void deepCopy(@NonNull Map<K, V> source, @NonNull Function<V, V> cloningFunction, @NonNull M sink) {
        for (Map.Entry<K, V> entry : source.entrySet()) {
            final V original = entry.getValue();
            final V cloned = cloningFunction.apply(original);
            sink.put(entry.getKey(), cloned);
        }
    }

    /**
     * Perform a deep copy of all the elements from a given {@link Map} to another.
     * <p>
     * If the source map contains <code>null</code> values, the cloning function should be
     * handle null input. Additionally, the sink should also accept any keys and values
     * contained by the source Map, null or not.
     *
     * @param source          The source of the elements
     * @param cloningFunction The function which clones the elements
     * @param sinkSupplier    The supplier which consumes the size of the source map and 
     *                        supplies the map to copy cloned elements to
     * @param <K>             The type of keys in the maps
     * @param <V>             The type of values in the maps
     * @param <M>             The type of the returned map
     *
     */
    public static <K, V, M extends Map<K, V>> M deepCopy(@NonNull Map<K, V> source, @NonNull Function<V, V> cloningFunction, @NonNull Supplier<M> sinkSupplier) {
        final M sink = sinkSupplier.get();
        deepCopy(source, cloningFunction, sink);
        return sink;
    }

    /**
     * Perform a deep-clone transformation on all values in a given {@link Map}
     * <p>
     * If the source map contains <code>null</code> values, the cloning function should be
     * handle null input
     *
     * @param source          The source of the elements
     * @param cloningFunction The function which clones the elements
     * @param <K>             The type of keys in the map
     * @param <V>             The type of values in the map
     *
     */
    public static <K, V> void deepCopy(@NonNull Map<K, V> source, @NonNull Function<V, V> cloningFunction) {
        for (Map.Entry<K, V> entry : source.entrySet()) {
            final V original = entry.getValue();
            final V cloned = cloningFunction.apply(original);
            entry.setValue(cloned);
        }
    }

    /**
     * Perform a deep copy of all the elements from a given array to another.
     * <p>
     * If the source array contains <code>null</code> elements, the cloning function should be
     * handle null input.
     *
     * @param source          The source of the elements
     * @param cloningFunction The function which clones the elements
     * @param sink            The array in which to copy to the cloned elements to
     * @param <T>             The type of elements in the arrays
     *
     */
    public static <T> void deepCopy(@NonNull T[] source, @NonNull Function<T, T> cloningFunction, @NonNull T[] sink) {
        if (source.length > sink.length) {
            throw new IllegalArgumentException("Length of sink must be greater than or equal to that of the source!");
        }
        for (int i = 0; i < source.length; i++) {
            sink[i] = cloningFunction.apply(source[i]);
        }
    }

    /**
     * Perform a deep copy of all the elements from a given array to another.
     * <p>
     * If the source array contains <code>null</code> elements, the cloning function should be
     * handle null input.
     *
     * @param source          The source of the elements
     * @param cloningFunction The function which clones the elements
     * @param sinkSupplier    The supplier which consumes the length of the source array and supplies 
     *                        the array to copy the cloned elements to
     * @param <T>             The type of elements in the arrays
     *
     */
    public static <T> T[] deepCopy(@NonNull T[] source, @NonNull Function<T, T> cloningFunction, @NonNull IntFunction<T[]> sinkSupplier) {
        final T[] sink = sinkSupplier.apply(source.length);
        deepCopy(source, cloningFunction, sink);
        return sink;
    }

}
