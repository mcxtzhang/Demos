package com.mcxtzhang.unittestdemo;

import org.junit.Assert;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MockTest {

    public static void main(String[] args) {
        IMathUtils mathUtils = mock(IMathUtils.class); // 生成mock对象

        when(mathUtils.abs(-1)).thenReturn(1); // 当调用abs(-1)时，返回1

        int abs = mathUtils.abs(-1); // 输出结果 1

        Assert.assertEquals(abs, 1);// 测试通过
    }
}