package com.example.bean;

import java.util.List;

/**
 * 介绍：
 * 作者：zhangxutong
 * 邮箱：zhangxutong@imcoming.com
 * 时间： 2016/9/14.
 */

public class JobInfoBean {
    String job_id = "2"; //职位id
    Integer type = 1; //类型
    String company_id = "2"; //公司id
    String name = "张旭童测试公司"; //名称
    String department = "张旭童测试部门"; //部门
    Integer low_pay = 100; //最低工资
    Integer high_pay = 200; //最高工资
    Integer experience = 3; //经验
    Integer education = 3; //教育
    String desc = "张旭童测试描述"; //描述
    Integer payway = 3; //支付方式
    String certificate = "张旭童测试证书"; //证书
    String address="张旭童测试地址"; //地址
    String skill="张旭童测试技能"; //技能
    Integer status=3; //状态
    List<String> advantages; //职业优势
    Integer interview_address=1; //面试地点
    Integer interview_province=1; //面试省份
    Integer interview_city=1; //面试城市
    Long start_time=1L; //开始时间
    Long end_time=1L; //结束时间
    Integer recipients=1; //服务对象
    String province="张旭童测试省份"; //省份
    String city="张旭童测试城市"; //城市
    String district="张旭童测试区域"; //区域
    String salary="12345"; //薪资
    Integer long_term=1; //是否长期，1是0不是
    String start_in_day="张旭童测试上班"; //上班时间
    String end_in_day="张旭童测试下班"; //下班时间
    Integer days_per_week=7; //每周天数
    Long update_time=1L; //更新时间
    Integer member_ct=1; //报名人数
    Integer cv_ct=1; //收到简历数
    Integer view_ct=1; //浏览次数
    //List<UserBean> users; //报名人列表
}
