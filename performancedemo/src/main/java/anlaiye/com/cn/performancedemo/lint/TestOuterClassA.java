package anlaiye.com.cn.performancedemo.lint;

public class TestOuterClassA {
    private int mOA2;

    private TestInnerClassB mTestInnerClassB = new TestInnerClassB();


    private static class TestInnerClassB {
        private int mIB;
    }
}
