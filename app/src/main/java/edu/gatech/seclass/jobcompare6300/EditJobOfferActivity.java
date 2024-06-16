package edu.gatech.seclass.jobcompare6300;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import edu.gatech.seclass.jobcompare6300.models.Job;

public class EditJobOfferActivity extends JobActivity {

    TextView tv;
    Button compareJobOfferWithCurrentButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tv = findViewById(R.id.CURRENT_JOB);
        final String s = "JOB OFFER";
        tv.setText(s);

        this.showCompareJobOfferWithCurrentButton();
    }

    @Override
    public Job getJobContext() {
        return (Job) getIntent().getSerializableExtra("job");
    }

    @Override
    protected void update(Job job) {
        jobManager.editCurrentJob(job);
    }

    @Override
    protected void setViewMode() {
        super.setViewMode();
        this.showCompareJobOfferWithCurrentButton();
    }

    @Override
    protected void setEditMode() {
        super.setEditMode();
        this.hideCompareJobOfferWithCurrentButton();
    }

    protected void showCompareJobOfferWithCurrentButton() {
        compareJobOfferWithCurrentButton = findViewById(R.id.compareWithCurrentJob);
        compareJobOfferWithCurrentButton.setVisibility(View.VISIBLE);
        compareJobOfferWithCurrentButton.setOnClickListener(v -> {
            final Job job1 = getJobContext();
            final Job job2 = jobManager.getCurrentJob();

            Intent i = new Intent(getApplicationContext(), CompareTwoJobsActivity.class);
            i.putExtra("job1", job1);
            i.putExtra("job2", job2);
            startActivity(i);
        });
    }

    protected void hideCompareJobOfferWithCurrentButton() {
        compareJobOfferWithCurrentButton = findViewById(R.id.compareWithCurrentJob);
        compareJobOfferWithCurrentButton.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void delete() {
        Job job = this.getJobContext();
        jobManager.deleteJobOffer(job);
    }
}
