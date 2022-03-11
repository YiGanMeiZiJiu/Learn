package com.learn.huawei;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 小王
 * Date: 2021-01-14
 * Time: 19:38
 * Description:
 */
class Solution {

    public static void main(String[] args) {
        ListNode l1 = new ListNode(1);
        l1.next = new ListNode(2);
        l1.next.next = new ListNode(4);
        ListNode l2 = new ListNode(1);
        l2.next = new ListNode(3);
        l2.next.next = new ListNode(4);

        ListNode merge = mergeTwoLists(l1, l2);
        while (merge != null) {
            System.out.println(merge.val);
            merge = merge.next;
        }
    }

    public static ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        if (l1 == null) {
            return l2;
        } else if (l2 == null) {
            return l1;
        }
        ListNode first = new ListNode(0);
        ListNode res = new ListNode(0);
        first = res;

        while (l1 != null && l2 != null) {
            if (l1.val > l2.val) {
                first.next = l2;
                l2 = l2.next;
            } else {
                first.next = l1;
                l1 = l1.next;
            }
            first = first.next;
        }
        if (l1 == null) {
            first.next = l2;
        } else {
            first.next = l1;
        }
        return res.next;
    }

    public static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }

}
