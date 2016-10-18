package mcxtzhang.retrofitdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.List;

import mcxtzhang.retrofitdemo.model.GithubRepoBean;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "zxt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

/*        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(*//*"http://www.kuaidi100.com/"*//*"https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        GitHubService service = retrofit.create(GitHubService.class);
        Call<ResponseBody> repos = service.listRepos("mcxtzhang");
        repos.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d(TAG, "onResponse() called with: call.request().url().toString(); = [" + call.request().url().toString());
                Log.e("APP",response.body().source().toString());
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
            }
        });*/

/*
GitHubService service = retrofit.create(GitHubService.class);
Call<PostQueryInfo> yuantong = service.search("yuantong", "500379523313");
        yuantong.enqueue(new Callback<PostQueryInfo>() {
            @Override
            public void onResponse(Call<PostQueryInfo> call, Response<PostQueryInfo> response) {
                Log.e("APP",response.body().getNu());
            }

            @Override
            public void onFailure(Call<PostQueryInfo> call, Throwable t) {

            }
        });*/


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .build();
        GitHubService service = retrofit.create(GitHubService.class);

        Call<List<GithubRepoBean>> repos = service.listRepos2("mcxtzhang");

        repos.enqueue(new Callback<List<GithubRepoBean>>() {
            @Override
            public void onResponse(Call<List<GithubRepoBean>> call, Response<List<GithubRepoBean>> response) {
                List<GithubRepoBean> body = response.body();
                for (GithubRepoBean githubRepoBean : body) {
                    Log.d(TAG, "onResponse() called with: githubRepoBean = [" + githubRepoBean);
                }
            }

            @Override
            public void onFailure(Call<List<GithubRepoBean>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
