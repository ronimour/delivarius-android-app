package com.delivarius.android.app.service;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.util.Log;


public class AtualizaStatusPedidosJobService extends JobService {

    private static final String TAG = "AtualizaStatusPedidosJobService";

    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        Intent service = new Intent(getApplicationContext(), AtualizaStatusPedidosService.class);
        getApplicationContext().startService(service);
        ScheduleJobUtil.scheduleJob(getApplicationContext()); // reschedule the job

        Log.i(TAG,"Job Service executado");
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return true;
    }
}
