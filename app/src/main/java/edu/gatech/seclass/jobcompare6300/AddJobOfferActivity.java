package edu.gatech.seclass.jobcompare6300;

import android.os.Bundle;
import android.widget.TextView;

import edu.gatech.seclass.jobcompare6300.models.Job;


public class AddJobOfferActivity extends JobActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView tv = findViewById(R.id.CURRENT_JOB);
        String s = "ADD NEW JOB OFFER!";
        tv.setText(s);

        // Override
        setEditMode();
//        setNewOfferMode();
        cancelButton.setOnClickListener(v -> {
            setViewMode();
            super.onBackPressed();
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public Job getJobContext() {
        Job newJob = new Job();
        newJob.setCurrent(false);
        return newJob;
    }

    @Override
    protected void update(Job job) {
        if (job.getId() == -1) {
            jobManager.addJobOffer(job);
        } else {
            jobManager.editJobOffer(job);
        }
    }


//    protected void saveCompare(Job job) {
//        if (job.getId() == -1) {
//            jobManager.addJobOffer(job);
//        } else {
//            jobManager.editJobOffer(job);
//        }
//    }

    @Override
    protected void delete() {
        System.out.println("Override to not do anything");
    }
}

