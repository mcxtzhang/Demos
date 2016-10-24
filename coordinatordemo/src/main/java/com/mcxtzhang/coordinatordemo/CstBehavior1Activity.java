package com.mcxtzhang.coordinatordemo;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;

public class CstBehavior1Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cst_behavior1);
/*

        RecyclerView rv = (RecyclerView) findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter();*/

        //底部bottom栏
        LinearLayout bottomLayout = (LinearLayout) findViewById(R.id.bottomLayout);
        CoordinatorLayout.LayoutParams bottomLp = (CoordinatorLayout.LayoutParams) bottomLayout.getLayoutParams();
        bottomLp.setBehavior(new BottomHideBehavior());

        //fab
        FloatingActionButton  fab = (FloatingActionButton) findViewById(R.id.fab);
        CoordinatorLayout.LayoutParams fabLp = (CoordinatorLayout.LayoutParams) fab.getLayoutParams();
        fabLp.setBehavior(new CstFABBehavior());


    }
}
