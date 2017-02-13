package com.example.interview;

/**
 * Intro:给定一个字符串，求第一个不重复的字符    abbcad -> c
 * Author: zhangxutong
 * E-mail: mcxtzhang@163.com
 * Home Page: http://blog.csdn.net/zxt0601
 * Created:   2017/2/13.
 * History:
 */

public class Ali_2_CustomLinkedHashMapTest {

    public static class Node {
        Node next;
        char value;

        public Node(char value) {
            this.value = value;
        }
    }


    public static void main(String[] args) {

        String src = "abbcad";

        Node[] sortCharArray = new Node[src.length()];


        char[] chars = src.toCharArray();
        for (char aChar : chars) {
            //put
            for (int i = 0; i < sortCharArray.length; i++) {
                Node node = sortCharArray[i];
                if (node == null) {
                    sortCharArray[i] = new Node(aChar);
                    break;
                } else if (node.value == aChar) {
                    Node newNode = new Node(aChar);
                    newNode.next = node;
                    sortCharArray[i] = newNode;
                    break;
                }
            }
        }

        //get
        for (Node node : sortCharArray) {
            if (node!=null && node.next==null){
                System.out.println("不重复的是:"+node.value);
                break;
            }else if (node!=null){
                System.out.println("重复的是:"+node.value);

            }else {
                System.out.println("空的");
            }
        }


    }
}
