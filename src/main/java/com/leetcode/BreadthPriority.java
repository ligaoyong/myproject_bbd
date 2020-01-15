package com.leetcode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * 广度优选搜索算法
 *
 * @author ligaoyong@gogpay.cn
 * @date 2020/1/13 11:26
 */
public class BreadthPriority {
    class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    /**
     * 101. 对称二叉树
     *
     * @param root
     * @return
     */
    public boolean isSymmetric(TreeNode root) {
        if (root == null) {
            return true;
        }
        LinkedList<TreeNode> layer = new LinkedList<>();
        layer.add(root.left);
        layer.add(root.right);
        while (!layer.isEmpty()) {
            for (int i = 0, j = layer.size() - 1; i < j; i++, j--) {
                if (layer.get(i) == null || layer.get(j) == null) {
                    if (layer.get(i) != layer.get(j)) {
                        return false;
                    }
                } else if (layer.get(i).val != layer.get(j).val) {
                    return false;
                }
            }
            LinkedList<TreeNode> nextLayer = new LinkedList<>();
            for (TreeNode treeNode : layer) {
                if (treeNode != null) {
                    nextLayer.add(treeNode.left);
                    nextLayer.add(treeNode.right);
                }
            }
            layer = nextLayer;
        }
        return true;
    }

    /**
     * 102. 二叉树的层次遍历
     *
     * @param root
     * @return
     */
    public List<List<Integer>> levelOrder(TreeNode root) {
        if (root == null) {
            return new ArrayList<>();
        }
        List<List<Integer>> res = new ArrayList<>();
        Queue<TreeNode> layer = new LinkedList<>();
        layer.offer(root);
        while (!layer.isEmpty()) {
            ArrayList<Integer> lay = new ArrayList<>();
            int length = layer.size();
            for (int i = 0; i < length; i++) {
                TreeNode node = layer.remove();
                lay.add(node.val);
                if (node.left != null) {
                    layer.offer(node.left);
                }
                if (node.right != null) {
                    layer.offer(node.right);
                }
            }
            if (lay.size() > 0) {
                res.add(lay);
            }
        }
        return res;
    }
}
