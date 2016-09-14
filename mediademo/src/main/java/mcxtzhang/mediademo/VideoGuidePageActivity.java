package mcxtzhang.mediademo;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.VideoView;

public class VideoGuidePageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_guide_page);


        VideoView mVideoView = (VideoView) findViewById(R.id.vv_preview);
        mVideoView.setVideoURI(Uri.parse(getVideoPath()));

        mVideoView.start();
    }

    /**
     * 获取video文件的路径
     *
     * @return 路径
     */
    private String getVideoPath() {
        return "android.resource://" + this.getPackageName() + "/" + R.raw.intro_video;
    }
}
