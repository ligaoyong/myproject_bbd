package com.leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * 前缀树：实现ok leetcode通过
 *
 * @author ligaoyong@gogpay.cn
 * @date 2020/1/10 14:48
 */
public class Trie {

    private Node root;

    class Node{
        char val;
        boolean end = false;
        List<Node> child = new ArrayList<>();
    }

    /** Initialize your data structure here. */
    public Trie() {
        root = new Node();
    }

    /** Inserts a word into the trie. */
    public void insert(String word) {
        Node current = root;
        char[] chars = word.toCharArray();
        for (int n=0;n<chars.length;n++) {
            boolean exits = false;
            //在当前节点的子节点中查找是否有该值
            for (int i=0;i<current.child.size();i++){
                //在当前节点的子节点中找到值
                Node child = current.child.get(i);
                if (child.val == chars[n]){
                    current = child;
                    exits = true;
                    if (n == chars.length-1){
                        child.end = true;
                    }
                    break;
                }
            }
            //树中没有值 则加入到当前节点的子节点中
            if (!exits){
                Node node = new Node();
                node.val = chars[n];
                if (n == chars.length-1){
                    node.end = true;
                }
                current.child.add(node);
                current = node;
            }
        }
    }

    /** Returns if the word is in the trie. */
    public boolean search(String word) {
        Node current = root;
        char[] chars = word.toCharArray();
        for (int n=0;n<chars.length;n++) {
            boolean exits = false;
            //在当前节点的子节点中查找是否有该值
            for (int i=0;i<current.child.size();i++){
                //在当前节点的子节点中找到值
                Node child = current.child.get(i);
                if (child.val == chars[n]){
                    if (n == chars.length-1){
                        if (!child.end){
                            return false;
                        }
                    }
                    current = child;
                    exits = true;
                    break;
                }
            }
            if (!exits){
                return false;
            }
        }
        return true;
    }

    /** Returns if there is any word in the trie that starts with the given prefix. */
    public boolean startsWith(String prefix) {
        Node current = root;
        char[] chars = prefix.toCharArray();
        for (int n=0;n<chars.length;n++) {
            boolean exits = false;
            //在当前节点的子节点中查找是否有该值
            for (int i=0;i<current.child.size();i++){
                //在当前节点的子节点中找到值
                Node child = current.child.get(i);
                if (child.val == chars[n]){
                    current = child;
                    exits = true;
                    break;
                }
            }
            if (!exits){
                return false;
            }
        }
        return true;
    }
}
