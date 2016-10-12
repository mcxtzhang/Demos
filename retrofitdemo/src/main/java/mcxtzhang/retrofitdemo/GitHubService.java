package mcxtzhang.retrofitdemo;

import java.util.List;

import mcxtzhang.retrofitdemo.model.GithubRepoBean;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GitHubService {
    @GET("users/{zhanweifu}/repos")
    Call<ResponseBody> listRepos(@Path("zhanweifu") String user);

    @POST("query")
    Call<PostQueryInfo> search(@Query("type") String type, @Query("postid") String postid);


    @GET("users/{luanqibazaodezhanweifu}/repos")
    Call<List<GithubRepoBean>> listRepos2(@Path("luanqibazaodezhanweifu") String user);

}