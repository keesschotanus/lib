/*
 * Copyright (C) 1999 Kees Schotanus
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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


/**
 * Unit tests for class {@link LruCache}.<br>
 */
class LruCacheTest {

    /**
     * The cache object to test.
     */
    private LruCache<String, String> cache;

    /**
     * Initializes the cache.
     */
    @BeforeEach
    public void initCache() {
        final int cacheSize = 3;
        cache = new LruCache<>(cacheSize);
        cache.add("1", "one");
        cache.add("2", "two");
        cache.add("3", "three");
    }

    /**
     * Tests {@link LruCache(int)} constructor.
     */
    @Test
    void testConstructor() {
        assertThrows(IllegalArgumentException.class, () -> new LruCache<>(1));
    }

    /**
     * Tests the add and get methods of class {@link LruCache}.
     */
    @Test
    void cache() {
        // Order should now be 1,2,3
        assertEquals("[1,2,3]", cache.toString(), "Unexpected order of objects in the cache");

        // Adding a fourth object while there is only room for three
        cache.add("4", "four");

        // Order should now be 2,3,4
        assertEquals("[2,3,4]", cache.toString(), "Unexpected order of objects in the cache");

        // Get 4, 3 and 2 from the cache to reverse ordering in list
        assertEquals("four", cache.get("4"), "Object four from cache");
        assertEquals("three", cache.get("3"), "Object three from cache");
        assertEquals("two", cache.get("2"), "Object two from cache");

        // Order should now be 4, 3, 2
        assertEquals("[4,3,2]", cache.toString(), "Unexpected order of objects in the cache");

        // Object with key "1" should not exist in the cache.
        assertNull(cache.get("1"));
    }

    /**
     * Tests {@link LruCache#containsKey}.
     */
    @Test
    void testContainsKey() {
        // using containsKey(...) should not change the order of the cache!
        assertTrue(cache.containsKey("1"));
        assertFalse(cache.containsKey("unknown key"));

        assertEquals("[1,2,3]", cache.toString(), "Cache should remain unchanged: 1, 2, 3");
    }

    /**
     * Tests {@link LruCache#toString()}.
     */
    @Test
    void testToString() {
        assertEquals("[1,2,3]", cache.toString(), "In cache: 1,2,3");
    }

    /**
     * Tests {@link LruCache#getCacheHitRatio()}.
     */
    @Test
    void testGetCacheHitRatio() {
        final double hundredPercent = 100.0D;
        final double fiftyPercent = 50.0D;

        assertEquals(0.0D, cache.getCacheHitRatio(), 0.0D, "No cache hits yet, so 0% hit");
        cache.get("2");
        assertEquals(hundredPercent, cache.getCacheHitRatio(), 0.0D, "All fetches are from cache so 100% hit");
        cache.get("Cache miss");
        assertEquals(fiftyPercent, cache.getCacheHitRatio(), 0.0D, "Fetch, not from cache, 50% hit");
    }

    /**
     * Tests {@link LruCache#clear()}.
     */
    @Test
    void testClear() {
        // Get existing object from cache, making hit ration 100%
        cache.get("2");
        assertEquals(100.0D, (cache.getCacheHitRatio()));

        // Clear the cache so hit ration will be 0%
        cache.clear();
        assertEquals(0.0D, cache.getCacheHitRatio());
    }

    /**
     * Tests {@link LruCache#statisticsToString()}.
     */
    @Test
    void testStatisticsToString() {
        // No requests so no hit ratio
        assertTrue(cache.statisticsToString().endsWith("n/a"));
        cache.get("2");
        assertTrue(cache.statisticsToString().endsWith("%"));
    }

    /**
     * Tests thread-safety of {@link LruCache#}.<br>
     * Using a cache size of 5, and a thread pool size of 10, 1000 tasks are executed.
     * Each task gets a random key (0 - 9) and stored the same key wit a random value (0 - 99).
     * Since half the values fit the cache a cache hit ration of around 50% is expected.
     * The end result should be that no exception is thrown.
     */
    @Test
    void testMultiThreaded() {
        final LruCache<Integer, Integer> integerCache = new LruCache<>(5);
        final ExecutorService service = Executors.newFixedThreadPool(10);

        for (int i = 0; i < 1000; i++) {
            service.submit(() -> {
                for (int j = 0; j < 10; ++j) {
                    final int randomKey = (int)Math.floor(Math.random() * 10);
                    final int randomValue = (int)Math.floor(Math.random() * 100);
                    integerCache.get(randomKey);
                    integerCache.add(randomKey, randomValue);
                }
            });
        }
        service.shutdown();
        assertTrue(integerCache.getCacheHitRatio() > 45.0);
    }
}
