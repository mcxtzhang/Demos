package anlaiye.com.cn.performancedemo.lint;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import anlaiye.com.cn.performancedemo.R;

public class TestInnerClassActivity extends AppCompatActivity {

    List<TestOuterClassA> list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_inner_class);


        TestOuterClassA testOuterClassA1 = new TestOuterClassA();
        TestOuterClassA testOuterClassA2 = new TestOuterClassA();
        list.add(testOuterClassA1);
        list.add(testOuterClassA2);
    }
}
