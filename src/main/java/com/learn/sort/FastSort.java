package com.learn.sort;

public class FastSort {

    public static void fastSort(int[] sorted, int start, int end) {
        int low = start;
        int high = end;
        // 选取目标值为数组首位
        int key = sorted[low];

        while(high > low) {
            /*
            当高位的值大于首位的目标值的话，高位下移继续比较
             */
            while(high > low && sorted[high] >= key)
                high--;
            if (sorted[high] <= key) {
                int temp = sorted[high];
                sorted[high] = key;
                sorted[low] = temp;
            }

            while(high > low && sorted[low] <= key)
                low++;
            if (sorted[low] >= key) {
                int temp = sorted[low];
                sorted[low] = sorted[high];
                sorted[high] = temp;
            }
        }

        if (low > start)
            fastSort(sorted, start, low-1);
        if (high < end)
            fastSort(sorted, high+1, end);
    }

}
