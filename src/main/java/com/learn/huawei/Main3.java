package com.learn.huawei;

import java.util.Scanner;

/**
 * Created with IntelliJ IDEA.
 * User: 小王
 * Date: 2021-01-04
 * Time: 22:11
 * Description:
 */
public class Main3 {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int count = 0;
        while (sc.hasNext()) {
            String str = sc.nextLine();
            int max = 0;
            int check = 0;
            Integer temp = null;
            for (int i = 0; i< str.length();i++) {
                int x = str.charAt(i);
                if (x >= 48 && x <= 57) {
                    count++;
                    if (max < count) {
                        max = count;
                    }
                } else if ((x >= 65 && x <= 90) || (x >= 97 && x <=122)) {
                    check++;
                    if (check <= 1) {
                        count++;
                        if (max < count) {
                            max = count;
                        }
                    }
                } else {
                    count = 0;
                }
            }
        }
        System.out.println(count);
    }
}
