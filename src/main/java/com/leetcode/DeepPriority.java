package com.leetcode;

import com.google.common.collect.Lists;
import sun.reflect.generics.tree.Tree;

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
     * 105. 从前序与中序遍历序列构造二叉树 pass
     * @param preorder
     * @param inorder
     * @return
     */
    public TreeNode buildTree1(int[] preorder, int[] inorder) {
        TreeNode root = null;
        return recurBuild1(root, preorder, 0, preorder.length - 1, inorder, 0, inorder.length - 1);
    }
    private TreeNode recurBuild1(TreeNode root, int[] preorder,int preStart,int preEnd, int[] inorder,int inoStart,int inoEnd){
        if (preStart > preEnd){
            return null;
        }
        //构建根节点
        root = new TreeNode(preorder[preStart]);
        int mid = 0;
        for (int i = 0;i<inorder.length;i++){
            if (inorder[i] == preorder[preStart]){
                mid = i;
                break;
            }
        }
        //左子树的长度
        int leftLength = mid - inoStart;
        //右子树的长度
        int rightLength = preEnd - mid;
        //左子树下标
        int leftPreStart = preStart + 1;
        int leftPreend = leftPreStart - 1 + leftLength;
        int leftInoStart = inoStart;
        int leftInoEnd = mid - 1;
        //右子树下标
        int rightPreStart = leftPreend + 1;
        int rightPreend = preEnd;
        int rightInoStart = mid+1;
        int rightInoEnd = inoEnd;

        //构建左子树
        if (leftLength > 0){
            root.left = recurBuild1(root.left,preorder,leftPreStart,leftPreend,inorder,leftInoStart,leftInoEnd);
        }
        //构建右子树
        if (rightLength > 0){
            root.right = recurBuild1(root.right,preorder,rightPreStart,rightPreend,inorder,rightInoStart,rightInoEnd);
        }
        return root;
    }


    /**
     * 106. 从中序与后序遍历序列构造二叉树 pass
     * @param inorder
     * @param postorder
     * @return
     */
    public TreeNode buildTree(int[] inorder, int[] postorder) {
        TreeNode root = null;
        return recurBuild(root, postorder, postorder.length - 1,0, inorder, 0, inorder.length - 1);
    }

    private TreeNode recurBuild(TreeNode root, int[] postorder,int postStart,int postEnd, int[] inorder,int inoStart,int inoEnd){
        if (postStart < postEnd){
            return null;
        }
        //构建根节点
        root = new TreeNode(postorder[postStart]);
        int mid = 0;
        for (int i = 0;i<inorder.length;i++){
            if (inorder[i] == postorder[postStart]){
                mid = i;
                break;
            }
        }
        //左子树的长度
        int leftLength = mid - inoStart;
        //右子树的长度
        int rightLength = inoEnd - mid;
        //右子树下标
        int rightPreStart = postStart - 1;
        int rightPreend = rightPreStart + 1 - rightLength;
        int rightInoStart = mid+1;
        int rightInoEnd = inoEnd;
        //左子树下标
        int leftPreStart = rightPreend - 1;
        int leftPreend = postEnd;
        int leftInoStart = inoStart;
        int leftInoEnd = mid-1;

        //构建左子树
        if (leftLength > 0){
            root.left = recurBuild(root.left,postorder,leftPreStart,leftPreend,inorder,leftInoStart,leftInoEnd);
        }
        //构建右子树
        if (rightLength > 0){
            root.right = recurBuild(root.right,postorder,rightPreStart,rightPreend,inorder,rightInoStart,rightInoEnd);
        }
        return root;
    }

    public static void main(String[] args) {
        DeepPriority deepPriority = new DeepPriority();
        int[] inorder = { 3,2,1};
        int[] postorder = {3,2,1};
        TreeNode treeNode = deepPriority.buildTree(inorder, postorder);
        System.out.println(treeNode);
    }
}
