package com.yurwar.service;

import com.yurwar.utils.ParallelArrays;

import java.util.Map;
import java.util.Random;
import java.util.stream.IntStream;

public class DefaultArrayService implements ArrayService {
    private final Random random = new Random();

    @Override
    public void processArray(int size) {
        int[] array = initializeRandomArray(size);

        int lessThanZeroElementsCount = ParallelArrays
                .countElementsByCondition(array, el -> el < 0);

        System.out.println("Count of elements by condition: " + lessThanZeroElementsCount);

        Map.Entry<Integer, Integer> maxEntry = ParallelArrays.findMaxEntry(array);

        System.out.println("Max element: " + maxEntry.getValue() + " with index: " + maxEntry.getKey());

        Map.Entry<Integer, Integer> minEntry = ParallelArrays.findMinEntry(array);

        System.out.println("Min element: " + minEntry.getValue() + " with index: " + minEntry.getKey());

        int checkSum = ParallelArrays.calculateCheckSum(array);

        System.out.println("Check sum for array: " + checkSum);
    }

    private int[] initializeRandomArray(int size) {
        return IntStream.range(0, size)
                .map(index -> random.nextInt())
                .toArray();
    }
}
