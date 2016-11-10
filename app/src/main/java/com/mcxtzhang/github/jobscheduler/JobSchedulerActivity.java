package com.mcxtzhang.github.jobscheduler;

import android.graphics.Path;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.mcxtzhang.github.R;
import com.mcxtzhang.pathanimlib.StoreHouseAnimView;
import com.mcxtzhang.pathanimlib.utils.SvgPathParser;

import java.text.ParseException;

public class JobSchedulerActivity extends AppCompatActivity {

    private StoreHouseAnimView pathAnimView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_scheduler);

/*        JobScheduler jobScheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);

        JobInfo jobInfo = new JobInfo.Builder(1, new ComponentName(this, JobSchedulerService.class))
                .setPeriodic(3000)
                .build();

        jobScheduler.schedule(jobInfo);*/


        pathAnimView1 = (StoreHouseAnimView) findViewById(R.id.pathAnimView1);
        Path sPath = new Path();
        sPath.moveTo(0, 0);
        sPath.addCircle(40, 40, 30, Path.Direction.CW);
        //pathAnimView1.setSourcePath(PathParserUtils.getPathFromArrayFloatList(StoreHousePath.getPath("McXtZhang")));
        try {
            pathAnimView1.setSourcePath(new SvgPathParser().parsePath("M 530.00,34.00  \n" +
                    "           C 530.00,34.00 526.08,59.00 526.08,59.00  \n" +
                    "             526.08,59.00 518.00,105.00 518.00,105.00  \n" +
                    "             518.00,105.00 515.42,119.00 515.42,119.00  \n" +
                    "             515.42,119.00 513.26,125.01 513.26,125.01  \n" +
                    "             513.26,125.01 506.00,126.00 506.00,126.00  \n" +
                    "             506.00,126.00 496.00,126.00 496.00,126.00  \n" +
                    "             496.00,126.00 496.00,120.00 496.00,120.00  \n" +
                    "             490.87,124.16 486.71,126.42 480.00,126.91  \n" +
                    "             475.71,127.22 471.06,126.94 467.00,125.44  \n" +
                    "             454.13,120.68 451.86,110.19 452.00,98.00  \n" +
                    "             452.22,79.34 465.14,64.55 484.00,63.18  \n" +
                    "             492.14,62.59 498.96,65.71 504.00,72.00  \n" +
                    "             504.00,72.00 510.00,34.00 510.00,34.00  \n" +
                    "             510.00,34.00 530.00,34.00 530.00,34.00 Z  \n" +
                    "           M 551.00,56.89  \n" +
                    "           C 539.01,55.86 537.45,39.82 551.00,35.55  \n" +
                    "             568.60,33.45 567.67,58.33 551.00,56.89 Z "));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        pathAnimView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pathAnimView1.startAnim();
            }
        });
    }
}
