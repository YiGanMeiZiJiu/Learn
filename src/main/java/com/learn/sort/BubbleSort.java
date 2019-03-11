package com.learn.sort;

public class BubbleSort {

    public static void bubbleSort(int[] sorted) {
        int temp;
        for (int i = 0; i < sorted.length-1; i++) {
            for (int j = 0; j < sorted.length-i-1; j++) {
                if (sorted[j+1] < sorted[j]) {
                    temp = sorted[j+1];
                    sorted[j+1] = sorted[j];
                    sorted[j] = temp;
                }
            }
        }
    }

    public static void main(String[] args) {
        int sorted[] = new int[]{1,6,2,2,5};
        bubbleSort(sorted);
        for (int a : sorted) {
            System.out.println(a);
        }
    }
}
