package com.mcxtzhang.github;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.View;

import com.example.ZRouter;

@ZRouter(path = SparseArrayOrderTestActivity.TAG)
public class SparseArrayOrderTestActivity extends AppCompatActivity {

    public static final String TAG = "zxt/SparseArray";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sparce_array_order_test);


    }

    public void run(View view) {
        SparseArray<Integer> sparseArray = new SparseArray<>();
        sparseArray.put(1, 1);
        sparseArray.put(2, 2);
        sparseArray.put(4, 4);
        sparseArray.put(6, 6);
        sparseArray.put(3, 3);


        int size = sparseArray.size();
        for (int i = 0; i < size; i++) {
            int key = sparseArray.keyAt(i);
            Log.d(TAG, "key = [" + key + "]" + ", value:" + sparseArray.get(key));
        }
    }

    public void runInt(View view) {
        SparseIntArray sparseArray = new SparseIntArray();
        sparseArray.put(1, 1);
        sparseArray.put(2, 2);
        sparseArray.put(4, 4);
        sparseArray.put(6, 6);
        sparseArray.put(3, 3);


        int size = sparseArray.size();
        for (int i = 0; i < size; i++) {
            int key = sparseArray.keyAt(i);
            Log.d(TAG, "key = [" + key + "]" + ", value:" + sparseArray.get(key));
        }
    }
}
