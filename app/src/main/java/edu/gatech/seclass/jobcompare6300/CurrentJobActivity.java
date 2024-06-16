package edu.gatech.seclass.jobcompare6300;


import android.os.Bundle;

import edu.gatech.seclass.jobcompare6300.models.Job;


public class CurrentJobActivity extends JobActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected Job getJobContext() {
        Job job = jobManager.getCurrentJob();
        if (job == null) {
            Job newJob = new Job();
            newJob.setCurrent(true);
            return newJob;
        }
        return job;
    }


    @Override
    protected void update(Job job) {
        if (job.getId() == -1) {
            jobManager.addCurrentJob(job);
        } else {
            jobManager.editCurrentJob(job);
        }
    }

    @Override
    protected void delete() {
        jobManager.deleteCurrentJob();
    }
}
