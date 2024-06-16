package edu.gatech.seclass.jobcompare6300;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import java.util.ArrayList;

import edu.gatech.seclass.jobcompare6300.api.JobManager;
import edu.gatech.seclass.jobcompare6300.models.Job;


public class MainActivity extends AppCompatActivity {

    JobManager jobManager = JobManager.getInstance(this);
    private Button currentJobButton;
    private Button jobOffersButton;
    private Button compareJobsButton;
    private Button jobSettingsButton;
    private Button developerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        currentJobButton = findViewById(R.id.currentJobButton);
        jobOffersButton = findViewById(R.id.jobOffersButton);
        compareJobsButton = findViewById(R.id.compareJobButton);
        jobSettingsButton = findViewById(R.id.jobSettingsButton);
        developerButton = findViewById(R.id.developerMode);

        initOnClick();
        refresh();
    }

    @Override
    public void onResume() {
        super.onResume();
        refresh();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        refresh();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        refresh();
    }

    @Override
    protected void onStart() {
        super.onStart();
        refresh();
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        refresh();
    }

    private void refresh() {
        ArrayList<Job> jobs = jobManager.getSortedJobsByRank();
        if (jobs.size() < 2) {
            compareJobsButton.setError("ERROR");
            compareJobsButton.setEnabled(false);
        }
    }

    private void initOnClick() {
        currentJobButton.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), CurrentJobActivity.class));
        });

        jobOffersButton.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), JobOffersActivity.class));
        });

        compareJobsButton.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), CompareJobsActivity.class));
        });

        jobSettingsButton.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), JobSettingsActivity.class));
        });

        developerButton.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), DeveloperToolsActivity.class));
        });
    }
}