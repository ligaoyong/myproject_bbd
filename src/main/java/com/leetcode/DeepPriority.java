package com.leetcode;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

    /**
     * 104. 二叉树的最大深度
     * @param root
     * @return
     */
    public int maxDepth(TreeNode root) {
        if (root == null){
            return 0;
        }
        return Math.max(maxDepth(root.left), maxDepth(root.right)) +1;
    }

    /**
     * 105. 从前序与中序遍历序列构造二叉树 xxxxxx
     * @param preorder
     * @param inorder
     * @return
     */
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        if (preorder.length == 0){
            return null;
        }
        TreeNode root = null;
        ArrayList<Integer> preorder1= new ArrayList<>();
        for (int value:preorder){
            preorder1.add(value);
        }
        ArrayList<Integer> inorder1= new ArrayList<>();
        for (int value:inorder){
            inorder1.add(value);
        }
        return recurBuild(root, preorder1, inorder1);
    }
    private TreeNode recurBuild(TreeNode root, List<Integer> preorder, List<Integer> inorder){
        if (preorder.size() == 0){
            return null;
        }
        //构建根节点
        root = new TreeNode(preorder.get(0));
        //构建左子树
        root.left = recurBuild(root.left,preorder.subList(1,preorder.size()),inorder.subList(0,inorder.indexOf(preorder.get(0))));
        root.right = recurBuild(root.right,preorder.subList(2,preorder.size()),inorder.subList(inorder.indexOf(preorder.get(0))+1,inorder.size()));
        return root;
    }
}
