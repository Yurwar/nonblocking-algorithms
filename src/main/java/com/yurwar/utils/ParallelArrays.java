package com.yurwar.utils;

import java.util.AbstractMap;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.stream.IntStream;

public class ParallelArrays {
    public static int countElementsByCondition(int[] array, Predicate<Integer> condition) {
        AtomicInteger res = new AtomicInteger(0);

        IntStream.of(array)
                .parallel()
                .filter(condition::test)
                .forEach(element -> {
                    int oldCounter;
                    int newCounter;

                    do {
                        oldCounter = res.get();
                        newCounter = oldCounter + 1;
                    } while (!res.compareAndSet(oldCounter, newCounter));
                });

        return res.get();
    }

    public static Entry<Integer, Integer> findMaxEntry(int[] array) {
        AtomicInteger maxIndex = new AtomicInteger(0);

        IntStream.range(0, array.length)
                .parallel()
                .forEach(index ->
                        updateIndex(array, maxIndex, index, (curr, old) -> curr > old));

        return new AbstractMap.SimpleEntry<>(maxIndex.get(), array[maxIndex.get()]);
    }

    public static Entry<Integer, Integer> findMinEntry(int[] array) {
        AtomicInteger minIndex = new AtomicInteger(0);

        IntStream.range(0, array.length)
                .parallel()
                .forEach(index ->
                        updateIndex(array, minIndex, index, (curr, old) -> curr < old));

        return new AbstractMap.SimpleEntry<>(minIndex.get(), array[minIndex.get()]);
    }

    private static void updateIndex(int[] array, AtomicInteger minIndex, int currIndex,
                                    BiPredicate<Integer, Integer> updatePredicate) {
        int oldIndex;
        int currentValue = array[currIndex];

        do {
            oldIndex = minIndex.get();
        } while (updatePredicate.test(currentValue, array[oldIndex]) && !minIndex.compareAndSet(oldIndex, currIndex));
    }

    public static int calculateCheckSum(int[] array) {
        AtomicInteger checkSum = new AtomicInteger(0);
        IntStream.of(array)
                .parallel()
                .forEach(el -> {
                    int oldSum;
                    int newSum;

                    do {
                        oldSum = checkSum.get();
                        newSum = oldSum ^ el;
                    } while (!checkSum.compareAndSet(oldSum, newSum));
                });
        return checkSum.get();
    }
}
