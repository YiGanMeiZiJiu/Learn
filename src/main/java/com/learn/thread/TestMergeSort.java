package com.learn.thread;

import com.sun.scenario.effect.Merge;
//import org.junit.Test;

import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class TestMergeSort {

    //@Test
    public void mergeSort() {
        long[] arrs = new long[1000000];
        for (int i = 0; i < 1000000; i++) {
            arrs[i] = (long)(Math.random() * 1000000);
        }
        long startTime = System.currentTimeMillis();
        ForkJoinPool forkJoinPool = new ForkJoinPool((Runtime.getRuntime().availableProcessors()));
        MergeSort mergeSort = new MergeSort(arrs);
        arrs = forkJoinPool.invoke(mergeSort);
        long endTime = System.currentTimeMillis();
        System.out.println("耗时："+(endTime - startTime));
    }

    class MergeSort extends RecursiveTask<long[]> {

        long[] arrs;

        public MergeSort(long[] arrs) {
            this.arrs = arrs;
        }

        @Override
        protected long[] compute() {
            if (arrs.length < 2)
                return arrs;
            int mid = arrs.length / 2;
            MergeSort left = new MergeSort(Arrays.copyOfRange(arrs, 0, mid));
            left.fork();
            MergeSort right = new MergeSort(Arrays.copyOfRange(arrs, mid, arrs.length));
            return merge(right.compute(), left.join());
        }
    }

    public static long[] merge(long[] left, long[] right) {
        long[] result = new long[left.length + right.length];
        for (int i = 0, m = 0, j = 0; m < result.length; m++) {
            if (i >= left.length) {
                result[m] = right[j++];
            } else if (j >= right.length) {
                result[m] = left[j++];
            } else if (left[i] > right[j]) {
                result[m] = right[j++];
            } else
                result[m] = left[i++];
        }
        return result;
    }

}
