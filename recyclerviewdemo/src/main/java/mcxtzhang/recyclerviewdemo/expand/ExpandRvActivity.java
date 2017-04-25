package mcxtzhang.recyclerviewdemo.expand;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.mcxtzhang.commonadapter.rv.CommonAdapter;
import com.mcxtzhang.commonadapter.rv.ViewHolder;

import mcxtzhang.recyclerviewdemo.R;

public class ExpandRvActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expand_rv);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new CommonAdapter<MoonBuildingInfo>(this, null, R.layout.activity_expand_rv_2) {
            @Override
            public void convert(ViewHolder viewHolder, MoonBuildingInfo bean1) {

            }
        });
    }
}
