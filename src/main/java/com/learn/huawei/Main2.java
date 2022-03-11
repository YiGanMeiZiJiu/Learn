package com.learn.huawei;


import java.util.Scanner;
import java.util.HashSet;
import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.
 * User: 小王
 * Date: 2021-01-04
 * Time: 20:28
 * Description:
 */
public class Main2 {


    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        // 有n个用户
        int m = sc.nextInt();
        int ans = 0;
        int[] array = new int[m];
        for(int i = 0; i < m; i++){
            array[i] = sc.nextInt();
        }
        int n = sc.nextInt();
        if (m < n*2) {
            System.out.println(-1);
            return;
        }
        quickSort(array, 0, m-1);
        int[] minArray = new int[n];
        for (int i = 0; i < n; i++) {
            if (i == 0) {
                minArray[i] = array[i];
                continue;
            }
            if (minArray[i-1] == array[i]) {
                for (int j = i+1; j < array.length; j++) {
                    if (minArray[i-1] != array[j]) {
                        minArray[i] = array[j];
                    }
                }
            }
        }


        System.out.println(ans);
    }

    public static void quickSort(int[] arr, int low, int high) {
        int i,j,temp,t;
        if (low > high) {
            return;
        }
        i = low;
        j = high;
        temp = arr[low];
        while (i < j) {
            while (temp <= arr[j] && i < j) {
                j--;
            }
            while (temp >= arr[i] && i < j) {
                i++;
            }
            if (i < j) {
                t = arr[j];
                arr[j] = arr[i];
                arr[i] = t;
            }
        }
        arr[low] = arr[i];
        arr[i] = temp;
        quickSort(arr, low, i-1);
        quickSort(arr, i+1, arr.length-1);
    }

}
