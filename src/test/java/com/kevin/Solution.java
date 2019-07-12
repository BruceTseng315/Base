package com.kevin;

import org.apache.poi.ss.usermodel.charts.ScatterChartSerie;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Solution {
    public List<String> letterCombinations(String digits) {

        List<String> res = new ArrayList<>();
        if(digits == null || digits.length() < 1) return res;

        Queue<String> queue = new LinkedList<>();
        char start = get(digits.charAt(0));
        queue.add(String.valueOf(start));
        queue.add(String.valueOf((char)(start+1)));
        queue.add(String.valueOf((char)(start+2)));
        if(digits.charAt(0) == '7' || digits.charAt(0) == '9'){
            queue.add(String.valueOf((char)(start+3)));
        }
        for(int i=1;i<digits.length();i++){
            char cur = get(digits.charAt(i));
            int curLen = queue.size();
            for(int j=0;j<curLen;j++){
                String out = queue.poll();
                queue.add(out+cur);
                queue.add(out+(char)(cur+1));
                queue.add(out+(char)(cur+2));
                if(digits.charAt(i) == '7' || digits.charAt(i) == '9'){
                    queue.add(out + (char)(cur+3));
                }
            }
        }

        while(!queue.isEmpty()){
            res.add(queue.poll());
        }
        return res;


    }
    public char get(char d){
        switch (d){
            case '2':
                return 'a';
            case '3':
                return 'd';
            case '4':
                return 'g';
            case '5':
                return 'j';
            case '6':
                return 'm';
            case '7':
                return 'p';
            case '8':
                return 't';
            case '9':
                return 'w';
            default:
                return 'a';
        }
    }

    public ListNode removeNthFromEnd(ListNode head, int n) {
        if(head==null) return null;
        if(n==0) return head;
        if(head.next == null) return null;
        ListNode slow = head;
        ListNode fast = head;
        for(int i=0;i<n;i++){
            if(fast.next !=null){
                fast = fast.next;
            }else {
                fast = head;
            }
        }
        while (fast.next != null){
            fast = fast.next;
            if(slow.next == null){
                slow = head;
            }else {
                slow = slow.next;
            }
        }
        ListNode pre = slow;
        ListNode delet = slow.next==null?head:slow.next;
        if(delet.next == null){
            pre.next = null;
            return head;
        }else if(delet == head){
            return head.next;
        }else {
            pre.next = delet.next;
            return head;
        }

    }

}
