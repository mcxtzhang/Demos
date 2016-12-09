package com.mcxtzhang.github.jobscheduler;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.mcxtzhang.github.R;
import com.mcxtzhang.github.service.TestThreadAndProcessService;
import com.mcxtzhang.pathanimlib.StoreHouseAnimView;

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

        //测试服务
        findViewById(R.id.btnStart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startService(new Intent(JobSchedulerActivity.this, TestThreadAndProcessService.class));
            }
        });

        findViewById(R.id.btnStop).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopService(new Intent(JobSchedulerActivity.this, TestThreadAndProcessService.class));
            }
        });
    }
}
