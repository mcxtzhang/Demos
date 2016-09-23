package com.mcxtzhang.learnannotationdemo.expand;

import com.mcxtzhang.learnannotationdemo.expand.zhujie.TreeNodeId;
import com.mcxtzhang.learnannotationdemo.expand.zhujie.TreeNodeLabel;
import com.mcxtzhang.learnannotationdemo.expand.zhujie.TreeNodePid;

public class TestBean
{  
    @TreeNodeId
    private int _id;  
    @TreeNodePid
    private int parentId;  
    @TreeNodeLabel
    private String name;  
    private long length;  
    private String desc;  
  
    public TestBean(int _id, int parentId, String name)
    {  
        super();  
        this._id = _id;  
        this.parentId = parentId;  
        this.name = name;  
    }  
  
}  