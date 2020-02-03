package com.leetcode;

import java.util.ArrayList;
import java.util.List;

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
     * @param node
     * @return
     */
    public Node cloneGraph(Node node) {

        return null;
    }
}
