package com.mcxtzhang.animdemo;

import android.os.Bundle;
import android.support.animation.SpringAnimation;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.widget.SeekBar;

public class NewAnim20170316Activity extends AppCompatActivity {
    private float downX, downY;
    private SeekBar damping, stiffness;
    private VelocityTracker velocityTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_anim20170316);
        findViewById(android.R.id.content).setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        stiffness = (SeekBar) findViewById(R.id.stiffness);
        damping = (SeekBar) findViewById(R.id.damping);
        velocityTracker = VelocityTracker.obtain();
        final View box = findViewById(R.id.box);
        findViewById(R.id.root).setOnTouchListener(new View.OnTouchListener() {
            @Override public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        downX = event.getX();
                        downY = event.getY();
                        velocityTracker.addMovement(event);
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        box.setTranslationX(event.getX() - downX);
                        box.setTranslationY(event.getY() - downY);
                        velocityTracker.addMovement(event);
                        return true;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        velocityTracker.computeCurrentVelocity(1000);
                        if (box.getTranslationX() != 0) {
                            SpringAnimation animX = new SpringAnimation(box, SpringAnimation.TRANSLATION_X, 0);
                            animX.getSpring().setStiffness(getStiffness());
                            animX.getSpring().setDampingRatio(getDamping());
                            animX.setStartVelocity(velocityTracker.getXVelocity());
                            animX.start();
                        }
                        if (box.getTranslationY() != 0) {
                            SpringAnimation animY = new SpringAnimation(box, SpringAnimation.TRANSLATION_Y, 0);
                            animY.getSpring().setStiffness(getStiffness());
                            animY.getSpring().setDampingRatio(getDamping());
                            animY.setStartVelocity(velocityTracker.getYVelocity());
                            animY.start();
                        }
                        velocityTracker.clear();
                        return true;
                }
                return false;
            }
        });
    }

    private float getStiffness() {
        return Math.max(stiffness.getProgress(), 1f);
    }

    private float getDamping() {
        return damping.getProgress() / 100f;
    }
}
