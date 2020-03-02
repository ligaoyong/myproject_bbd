package com.leetcode;

import java.util.*;

/**
 * 图
 *
 * @author ligaoyong@gogpay.cn
 * @date 2020/2/3 14:14
 */
public class Map {
    class Node {
        int val;
        List<Node> neighbors;

        public Node() {
            val = 0;
            neighbors = new ArrayList<Node>();
        }

        public Node(int _val) {
            val = _val;
            neighbors = new ArrayList<Node>();
        }

        public Node(int _val, ArrayList<Node> _neighbors) {
            val = _val;
            neighbors = _neighbors;
        }
    }

    /**
     * 133. 克隆图
     *  pass 使用深度便利算法
     * @param node
     * @return
     */
    public Node cloneGraph(Node node) {
        if (node == null) {
            return null;
        }
        if (node.neighbors.size() == 0){
            return new Node(1);
        }
        java.util.Map<Integer, Node> container = new HashMap<>();
        return dfsClone(node, container);
    }

    private Node dfsClone(Node node, java.util.Map<Integer, Node> container) {
        if (node == null) {
            return null;
        }
        if (container.containsKey(node.val)) {
            return container.get(node.val);
        }
        Node newNode = new Node(node.val);
        container.put(newNode.val, newNode);
        for (Node nodeItem : node.neighbors) {
            Node neighbor = dfsClone(nodeItem, container);
            newNode.neighbors.add(neighbor);
        }
        return newNode;
    }
}
