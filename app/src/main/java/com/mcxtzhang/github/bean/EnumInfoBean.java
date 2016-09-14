package com.mcxtzhang.github.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * 介绍：所有枚举类型的Bean
 * 作者：zhangxutong
 * 邮箱：zhangxutong@imcoming.com
 * 时间： 2016/9/14.
 */

public class EnumInfoBean extends BaseSelectBean implements Cloneable{
    private String name; //名称
    private int type; //类型

    public EnumInfoBean(String name, int type) {
        this.name = name;
        this.type = type;
    }

    public static List<EnumInfoBean> fakerDatas(){
        List<EnumInfoBean> enumInfoBeanList = new ArrayList<EnumInfoBean>();
        enumInfoBeanList.add(new EnumInfoBean("傻逼",1));
        enumInfoBeanList.add(new EnumInfoBean("智障",2));
        enumInfoBeanList.add(new EnumInfoBean("大傻逼",3));
        enumInfoBeanList.add(new EnumInfoBean("妈的智障",4));
        enumInfoBeanList.add(new EnumInfoBean("草他大爷",5));
        return enumInfoBeanList;
    }

    public Object clone() {
        EnumInfoBean o = null;
        try {
            o = (EnumInfoBean) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return o;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
