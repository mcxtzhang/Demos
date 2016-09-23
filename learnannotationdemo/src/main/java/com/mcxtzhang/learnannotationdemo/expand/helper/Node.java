package com.mcxtzhang.learnannotationdemo.expand.helper;

import java.util.ArrayList;
import java.util.List;

public class Node
{  
  
    private int id;  
    /** 
     * 根节点pId为0 
     */  
    private int pId = 0;  
  
    private String name;  
  
    /** 
     * 当前的级别 
     */  
    private int level;  
  
    /** 
     * 是否展开 
     */  
    private boolean isExpand = false;  
  
    private int icon;  
  
    /** 
     * 下一级的子Node 
     */  
    private List<Node> children = new ArrayList<Node>();
  
    /** 
     * 父Node 
     */  
    private Node parent;  
  
    public Node()  
    {  
    }  
  
    public Node(int id, int pId, String name)  
    {  
        super();  
        this.id = id;  
        this.pId = pId;  
        this.name = name;  
    }  
  
    public int getIcon()  
    {  
        return icon;  
    }  
  
    public void setIcon(int icon)  
    {  
        this.icon = icon;  
    }  
  
    public int getId()  
    {  
        return id;  
    }  
  
    public void setId(int id)  
    {  
        this.id = id;  
    }  
  
    public int getpId()  
    {  
        return pId;  
    }  
  
    public void setpId(int pId)  
    {  
        this.pId = pId;  
    }  
  
    public String getName()  
    {  
        return name;  
    }  
  
    public void setName(String name)  
    {  
        this.name = name;  
    }  
  
    public void setLevel(int level)  
    {  
        this.level = level;  
    }  
  
    public boolean isExpand()  
    {  
        return isExpand;  
    }  
  
    public List<Node> getChildren()  
    {  
        return children;  
    }  
  
    public void setChildren(List<Node> children)  
    {  
        this.children = children;  
    }  
  
    public Node getParent()  
    {  
        return parent;  
    }  
  
    public void setParent(Node parent)  
    {  
        this.parent = parent;  
    }  
  
    /** 
     * 是否为跟节点 
     *  
     * @return 
     */  
    public boolean isRoot()  
    {  
        return parent == null;  
    }  
  
    /** 
     * 判断父节点是否展开 
     *  
     * @return 
     */  
    public boolean isParentExpand()  
    {  
        if (parent == null)  
            return false;  
        return parent.isExpand();  
    }  
  
    /** 
     * 是否是叶子界点 
     *  
     * @return 
     */  
    public boolean isLeaf()  
    {  
        return children.size() == 0;  
    }  
  
    /** 
     * 获取level 
     */  
    public int getLevel()  
    {  
        return parent == null ? 0 : parent.getLevel() + 1;  
    }  
  
    /** 
     * 设置展开 
     *  
     * @param isExpand 
     */  
    public void setExpand(boolean isExpand)  
    {  
        this.isExpand = isExpand;  
        if (!isExpand)  
        {  
  
            for (Node node : children)  
            {  
                node.setExpand(isExpand);  
            }  
        }  
    }  
  
}  