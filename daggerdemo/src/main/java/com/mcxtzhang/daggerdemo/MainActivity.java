package com.mcxtzhang.daggerdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    TextView tvCoffee;
    @Inject
    CoffeeMachine coffeeMachine;

    private SimpleComponent simpleComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        simpleComponent = DaggerSimpleComponent.builder().simpleMakerModule(new SimpleMakerModule()).build();
        simpleComponent.inject(this);


        initView();
    }

    private void initView() {
        tvCoffee = (TextView) findViewById(R.id.tv);
        tvCoffee.setOnClickListener(this);
    }

    private String makeCoffee() {
        return coffeeMachine.makeCoffee();
    }

    @Override
    public void onClick(View v) {
        tvCoffee.setText(makeCoffee());
    }
}
