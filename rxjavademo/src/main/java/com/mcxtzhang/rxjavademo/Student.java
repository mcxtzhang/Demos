package com.mcxtzhang.rxjavademo;

import java.util.ArrayList;
import java.util.List;

/**
 * 介绍：
 * 作者：zhangxutong
 * 邮箱：zhangxutong@imcoming.com
 * 时间： 2016/9/28.
 */

public class Student {
    String name;
    List<Course> mCourse;

    public Student() {
        mCourse = new ArrayList<>();
        mCourse.add(new Course());
        mCourse.add(new Course());
        mCourse.add(new Course());

    }

    public Student(String name, List<Course> course) {
        this.name = name;
        mCourse = course;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Course> getCourse() {
        return mCourse;
    }

    public void setCourse(List<Course> course) {
        mCourse = course;
    }
}
