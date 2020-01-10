package com.leetcode;

/**
 * 深度优先算法
 *
 * @author ligaoyong@gogpay.cn
 * @date 2020/1/10 15:41
 */
public class DeepPriority {

    class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    /**
     * 100 相同的树
     *
     * @param p
     * @param q
     * @return
     */
    public boolean isSameTree(TreeNode p, TreeNode q) {
        if (p == null || q == null) {
            return p == q;
        }
        if (p.left == null && p.right == null && q.left == null && q.right == null) {
            if (p.val != q.val) {
                return false;
            } else {
                return true;
            }
        }
        return p.val == q.val && isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
    }

    /**
     * 98
     *
     * @param root
     * @return
     */
    public boolean isValidBST(TreeNode root) {
        if (root == null) {
            return true;
        }
        if (root.left != null) {
            if (root.left.val >= root.val) {
                return false;
            }
        }
        if (root.right != null) {
            if (root.right.val <= root.val) {
                return false;
            }
        }
        if (root.left != null && leftTreeMax(root.left, Integer.MIN_VALUE) >= root.val) {
            return false;
        }
        if (root.right!= null && rightTreeMin(root.right, Integer.MAX_VALUE) <= root.val) {
            return false;
        }
        return isValidBST(root.left) && isValidBST(root.right);
    }

    private int leftTreeMax(TreeNode root, int max) {
        if (root == null) {
            return max;
        }
        if (root.val >= max) {
            max = root.val;
        }
        return Math.max(leftTreeMax(root.left, max), leftTreeMax(root.right, max));
    }

    private int rightTreeMin(TreeNode root, int min) {
        if (root == null) {
            return min;
        }
        if (root.val <= min) {
            min = root.val;
        }
        return Math.min(rightTreeMin(root.left, min), rightTreeMin(root.right, min));
    }
}
