package com.example;

import java.util.Comparator;
import java.util.TreeSet;

/**
 * 介绍：
 * 作者：zhangxutong
 * 邮箱：mcxtzhang@163.com
 * 主页：http://blog.csdn.net/zxt0601
 * 时间： 2016/11/29.
 */

public class TestTreeSet {

    public static void main(String[] args) {
        TreeSet<TestBean> mTreeSet = new TreeSet<>(new Comparator<TestBean>() {
            @Override
            public int compare(TestBean testBean, TestBean t1) {
                return testBean.getIndex() - t1.getIndex();
            }
        });
        mTreeSet.add(new TestBean(5, "第五个"));
        mTreeSet.add(new TestBean(2, "第2个"));
        mTreeSet.add(new TestBean(4, "第4个"));
        mTreeSet.add(new TestBean(65, "第65个"));
        mTreeSet.add(new TestBean(1, "第1个"));
        mTreeSet.add(new TestBean(75, "第75个"));
        mTreeSet.add(new TestBean(25, "第25个"));
        mTreeSet.add(new TestBean(35, "第35个"));


        for (TestBean testBean : mTreeSet) {
            System.out.println(testBean);
        }

    }

    public static class TestBean {
        private int index;
        private String name;

        @Override
        public String toString() {
            return "TestBean{" +
                    "index=" + index +
                    ", name='" + name + '\'' +
                    '}';
        }

        public TestBean(int index, String name) {
            this.index = index;
            this.name = name;
        }

        public int getIndex() {
            return index;
        }

        public TestBean setIndex(int index) {
            this.index = index;
            return this;
        }

        public String getName() {
            return name;
        }

        public TestBean setName(String name) {
            this.name = name;
            return this;
        }
    }
}
