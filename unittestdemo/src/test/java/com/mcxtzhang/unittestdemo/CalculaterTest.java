package com.mcxtzhang.unittestdemo;

import junit.framework.Assert;

import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class CalculaterTest {

    Calculater calculater = new Calculater();

    @org.junit.Test
    public void testAdd() {
        int a = 1;
        int b = 2;

        int result = calculater.add(2, 2);

        Assert.assertEquals(result, 3); // 验证result==3，如果不正确，测试不通过
    }

    @Test
    public void testAdd2() {

        Assert.assertEquals(calculater.add(3, 4), 7);

    }

    @Test
    public void testAdd3(){
        calculater = mock(Calculater.class);
        calculater.add(3,4);
        verify(calculater).add(5,6);

    }
}

