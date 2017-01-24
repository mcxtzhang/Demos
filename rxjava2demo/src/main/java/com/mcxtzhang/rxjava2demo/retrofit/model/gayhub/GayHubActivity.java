package com.mcxtzhang.rxjava2demo.retrofit.model.gayhub;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.mcxtzhang.rxjava2demo.R;

import java.util.List;

import butterknife.Bind;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class GayHubActivity extends AppCompatActivity {
    @Bind(R.id.tv)
    TextView mTv;

    @Bind(R.id.btnStart)
    Button mBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gay_hub);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com")
                .build();

        GayHubService gayHubService = retrofit.create(GayHubService.class);

        Call<List<FullRepoBean>> mcxtzhang = gayHubService.getRepos1("mcxtzhang");
        mcxtzhang.enqueue(new Callback<List<FullRepoBean>>() {
            @Override
            public void onResponse(Call<List<FullRepoBean>> call, Response<List<FullRepoBean>> response) {
                List<FullRepoBean> body = response.body();
                Toast.makeText(GayHubActivity.this, "body.size():" + body.size(), Toast.LENGTH_SHORT).show();
                mTv.setText(body.get(0).getName());
            }

            @Override
            public void onFailure(Call<List<FullRepoBean>> call, Throwable t) {

            }
        });
    }
}
