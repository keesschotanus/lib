/*
 * Copyright (C) 2003 Kees Schotanus
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

package com.schotanus.util;


import java.io.Serial;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;


/**
 * A thread-safe LRU (Least Recently Used) cache.<br>
 * An LRU cache discards its least recently used objects, effectively keeping the MRU (Most Recently Used) objects in cache.<br>
 * This cache locates objects with the speed of a Map while maintaining an ordering from MRU to LRU.
 * The speed of maintaining the ordering is always the same, no matter what cache size you use.<br>
 * Note: Access to the LRU cache is synchronized, but I did not bother to sync keeping track of statistics, for performance
 * reasons and because you normally want to display this information after you are done using this LRU cache.
 * @see LinkedHashMap
 * @param <K> The Key of the objects to cache.
 * @param <V> The Value of the objects to cache.
 */
public class LruCache<K, V> {

    /**
     * The map that stores all the cached objects.
     */
    private final Map<K, V> cache;

    /**
     * The maximum size of the cache.
     */
    private final int maximumSize;

    /**
     * Number of times an object is requested from the cache.
     */
    private long cacheRequests;

    /**
     * Number of times the requested object existed in the cache.
     */
    private long cacheHits;

    /**
     * Constructs a cache of the supplied maximum size.
     * @param maximumSize The maximum size of the cache.
     * @throws IllegalArgumentException When the supplied maximum size is less than 2.
     */
    public LruCache(final int maximumSize) {
        if (maximumSize < 2) {
            throw  new IllegalArgumentException("Cache size must be larger than one!");
        }

        this.maximumSize = maximumSize;
        final float loadFactor = 0.01F;
        this.cache = Collections.synchronizedMap(new LinkedHashMap<>(maximumSize, loadFactor, true) {
            /**
             * Universal version identifier for this serializable class.
             */
            @Serial
            private static final long serialVersionUID = -5356952054519896491L;

            /**
             * Removes the eldest entry when the maximum size of this cache is exceeded.
             * @param eldestEntry The eldest entry in this cache.
             * @return True When the size of this cache exceeds the maximum size.
             */
            @Override
            protected boolean removeEldestEntry(final Map.Entry<K, V> eldestEntry) {
                return size() > maximumSize;
            }
        });
    }

    /**
     * Adds the supplied key/value pair to the cache.
     * @param key The key used to store the supplied value.
     * @param value The value to cache.
     * @throws NullPointerException When either the supplied key or the supplied value is null.
     */
    public void add(final K key, final V value) {
        this.cache.put(Objects.requireNonNull(key), Objects.requireNonNull(value));
    }

    /**
     * Determines whether the cache contains a value corresponding to the supplied key.<br>
     * This method is non-intrusive (does not change the cache in any way).
     * @param key Key corresponding to the value to check for existence.
     * @return boolean True when the cache contains a value corresponding to the supplied key or null when this is not the case.
     */
    public boolean containsKey(final K key) {
        return this.cache.containsKey(key);
    }

    /**
     * Gets the value corresponding to the supplied key from the cache.
     * @param key Key corresponding to the value to get from the cache.
     * @return Value corresponding to the supplied key, or null when the key does not exist.
     */
    public V get(final K key) {
        ++this.cacheRequests;
        final V value = this.cache.get(key);
        if (value != null) {
            ++this.cacheHits;
        }

        return value;
    }

    /**
     * Clears the cache.<br>
     * This includes the statistical data.
     */
    public void clear() {
        this.cache.clear();

        this.cacheRequests = 0;
        this.cacheHits = 0;
    }

    /**
     * Creates a String representation of this cache, consisting of all the comma separated keys, within brackets.
     * For example: "[1,2,3]".<br>
     * The order is from LRU to MRU.
     * @return A String representation of this cache.
     */
    @Override
    public String toString() {
        synchronized (cache) {
            return this.cache.keySet().stream().map(Object::toString).collect(Collectors.joining(",", "[", "]"));
        }
    }

    /**
     * Gets the cache/hit ratio.
     * @return The cache/hit ratio.
     */
    public double getCacheHitRatio() {
        return this.cacheRequests == 0 ? 0 : 100.0D * this.cacheHits / this.cacheRequests;
    }

    /**
     * Creates a String representation of the statistics of this cache.
     * @return A String representation of the statistics of this cache.
     */
    public String statisticsToString() {
        final StringBuilder result = new StringBuilder();

        result.append("Cache:maximumSize=").append(this.maximumSize);
        result.append(":currentSize=").append(this.cache.size());

        result.append(":hit ratio=");
        if (this.cacheRequests == 0) {
            result.append("n/a");
        } else {
            result.append((int)(100.0D * this.cacheHits / this.cacheRequests));
            result.append("%");
        }
        return result.toString();
    }

}
