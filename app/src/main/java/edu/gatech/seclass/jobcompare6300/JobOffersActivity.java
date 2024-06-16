package edu.gatech.seclass.jobcompare6300;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;


import org.w3c.dom.Text;

import java.util.ArrayList;

import edu.gatech.seclass.jobcompare6300.api.JobManager;
import edu.gatech.seclass.jobcompare6300.models.Job;


public class JobOffersActivity extends BaseActivity {

    JobManager jobManager = JobManager.getInstance(this);
    Button backButton;

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        refresh();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        refresh();
    }

    public TableRow initTableRow() {
        TableRow tableRow = new TableRow(this);
        TableRow.LayoutParams rowParams = new TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT, 100
        );
        tableRow.setLayoutParams(rowParams);
        tableRow.setGravity(Gravity.CENTER);
        return tableRow;
    }

    @SuppressLint("SetTextI18n")
    private void refresh() {
        setContentView(R.layout.activity_job_offers);

        backButton = findViewById(R.id.BACK3);
        initBackButton(backButton);

        // Job Offer Add
        final Button addJobOffer = findViewById(R.id.addJobOffer);
        addJobOffer.setOnClickListener(v -> {
            Intent i = new Intent(getApplicationContext(), AddJobOfferActivity.class);
            startActivity(i);
        });

        // Job Offers View and Edit
        final LinearLayout linearLayout = findViewById(R.id.jobOffersLayout);
        final ArrayList<Job> jobs = jobManager.getJobOffers();
        final TextView tap = findViewById(R.id.tapTextView);
        if (jobs.size() > 0) {
            tap.setVisibility(View.VISIBLE);
        } else {
            tap.setVisibility(View.INVISIBLE);
        }

        for (final Job job : jobs) {
            final TableRow tableRow = new TableRow(this);
            final TableRow.LayoutParams rowParams = new TableRow.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT, 700
            );
            tableRow.setLayoutParams(rowParams);
            tableRow.setGravity(Gravity.CENTER);

            final Button button = new Button(this);
            final String text = job.toJobOfferTableString();
            button.setText(text);
            button.setHeight(700);
            button.setLayoutParams(rowParams);

            button.setOnClickListener(v -> {
                Intent i = new Intent(getApplicationContext(), EditJobOfferActivity.class);
                i.putExtra("job", job);
                startActivity(i);
            });
            tableRow.addView(button);
            linearLayout.addView(tableRow);
        }
    }

}