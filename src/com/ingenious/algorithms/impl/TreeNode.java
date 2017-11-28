package com.ingenious.algorithms.impl;
import java.util.List;
import java.util.ArrayList;

public class TreeNode
{
    private int data;
    private List<TreeNode> children = new ArrayList<>();
    private TreeNode parent = null;

    public TreeNode(int data)
    {
        this.data = data;
    }

    public void addChild(TreeNode child)
    {
        child.setParent(this);
        this.children.add(child);
    }

    public void addChild(int data)
    {
        TreeNode newChild = new TreeNode(data);
        newChild.setParent(this);
        children.add(newChild);
    }

    public void addChildren(List<TreeNode> children)
    {
        for(TreeNode t : children)
        {
            t.setParent(this);
        }
        this.children.addAll(children);
    }

    public List<TreeNode> getChildren()
    {
        return children;
    }

    public int getData()
    {
        return data;
    }

    public void setData(int data)
    {
        this.data = data;
    }

    private void setParent(TreeNode parent)
    {
        this.parent = parent;
    }

    public TreeNode getParent()
    {
        return parent;
    }

    public Boolean hasChildren()
    {
        if(this.children.isEmpty())
            return false;
        else
            return true;
    }
}