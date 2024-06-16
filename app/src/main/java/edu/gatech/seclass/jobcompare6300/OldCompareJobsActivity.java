package edu.gatech.seclass.jobcompare6300;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.*;
import android.widget.*;

import java.util.ArrayList;

import edu.gatech.seclass.jobcompare6300.api.JobManager;
import edu.gatech.seclass.jobcompare6300.models.Job;
import edu.gatech.seclass.jobcompare6300.models.JobSettings;


public class OldCompareJobsActivity extends Activity {
    JobManager jobManager = JobManager.getInstance(this);

    Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compare_jobs);

        backButton = findViewById(R.id.BACK4);
        backButton.setOnClickListener(v -> {
            super.onBackPressed();
        });

        TextView textView = findViewById(R.id.textViewJobOffers);
        String s = "Compare Job Offers by Rank";
        textView.setText(s);
        LinearLayout linearLayout = findViewById(R.id.compareJobOffersLayout);

        ArrayList<Job> jobs = jobManager.getSortedJobsByRank();

        if (jobs.size() < 2) {
            super.onBackPressed();
        }

        JobSettings jobSettings = jobManager.getJobSettings();

        for (Job job : jobs) {
            TableRow tableRow = new TableRow(this);

            TableRow.LayoutParams rowParams = new TableRow.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT, 1000
            );
            tableRow.setLayoutParams(rowParams);
            tableRow.setGravity(Gravity.CENTER);

            Button button = new Button(this);

            float score = jobSettings.computeJobScore(job);
            String scoreString = "Score:" + score + "\n\n" + job.toLongString();

            button.setText(scoreString);
            button.setHeight(1000);

            button.setLayoutParams(rowParams);
            tableRow.addView(button);

            linearLayout.addView(tableRow);
        }
    }


    //    @Override
    protected void onCreateOld(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compare_jobs);

        ArrayList<Job> jobs = jobManager.getSortedJobsByRank();
        JobSettings jobSettings = jobManager.getJobSettings();
//        TableLayout tableLayout = findViewById(R.id.tableLayout);

        for (int i = 0; i < jobs.size(); i++) {
            Job job = jobs.get(i);

            TableLayout.LayoutParams tableParams = new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT);
            TableRow.LayoutParams rowParams = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);

            TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(tableParams);// TableLayout is the parent view

            TextView textViewScore = new TextView(this);
            textViewScore.setTypeface(null, Typeface.BOLD);

            float score = jobSettings.computeJobScore(job);
            String scoreString = "Score:" + Float.toString(score);
            textViewScore.setText(scoreString);
            textViewScore.setLayoutParams(rowParams);// TableRow is the parent view


            TextView textView = new TextView(this);
            String s = job.toLongString() + "\n";
            textView.setText(s);
            textView.setLayoutParams(rowParams);// TableRow is the parent view

//            TextView textViewB = new TextView(this);
//            String sB= " ???";
//            textViewB.setText(sB);
//            textViewB.setLayoutParams(rowParams);// TableRow is the parent view


            textViewScore.setMovementMethod(new ScrollingMovementMethod());
            textView.setMovementMethod(new ScrollingMovementMethod());
//            textView.setMovementMethod(new ScrollingMovementMethod());


            tableRow.addView(textViewScore);
            tableRow.addView(textView);
//            tableRow.addView(textViewB);

//            tableLayout.addView(tableRow);
        }
    }
}