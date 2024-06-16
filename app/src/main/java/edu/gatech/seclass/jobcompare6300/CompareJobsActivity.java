package edu.gatech.seclass.jobcompare6300;


import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import edu.gatech.seclass.jobcompare6300.api.JobManager;
import edu.gatech.seclass.jobcompare6300.models.Job;

public class CompareJobsActivity extends AppCompatActivity {

    JobManager jobManager = JobManager.getInstance(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        ListView listView = findViewById(R.id.listView);
        Button button = findViewById(R.id.button);

        ArrayList<Job> jobs = jobManager.getSortedJobsByRank();
        JobArrayAdapter arrayAdapter = new JobArrayAdapter(this, jobs);
        listView.setAdapter(arrayAdapter);

        button.setOnClickListener(v -> {
            System.out.println();
            int nChecked = arrayAdapter.getNumberOfCheckboxesChecked();
            if (nChecked == 2) {
                ArrayList<Job> selectedJobs = arrayAdapter.getSelectedJobs();
                Job job1 = selectedJobs.get(0);
                Job job2 = selectedJobs.get(1);

                Intent i = new Intent(getApplicationContext(), CompareTwoJobsActivity.class);
                i.putExtra("job1", job1);
                i.putExtra("job2", job2);
                startActivity(i);
            } else {
                Toast.makeText(
                        getApplicationContext(),
                        "Please select two jobs to compare.",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}