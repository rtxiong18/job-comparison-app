package edu.gatech.seclass.jobcompare6300;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import edu.gatech.seclass.jobcompare6300.api.JobManager;
import edu.gatech.seclass.jobcompare6300.models.Job;


public class CompareTwoJobsActivity extends AppCompatActivity {
    JobManager jobManager = JobManager.getInstance(this);

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setContentView(R.layout.table);
        initTable();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.table);
        initTable();
    }

    private void hideTopBar() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }


    public TableRow getTableRowTitle() {
        TableRow tableRow = new TableRow(this);
        ArrayList<String> metadata = new ArrayList<>();
        metadata.add("Attributes\n\tID ");

        Job job1 = (Job) getIntent().getSerializableExtra("job1");
        Job job2 = (Job) getIntent().getSerializableExtra("job2");

        metadata.add("Job 1\nID:" + job1.getId());
        metadata.add("Job 2\nID:" + job2.getId());
        for (String meta : metadata) {
            TextView tv = getTextView(meta);
            tableRow.addView(tv);
        }
        return tableRow;
    }

    @SuppressLint("SetTextI18n")
    public TextView getTextView(String s) {
        TextView tv = new TextView(this);
        tv.setText("" + s);

        tv.setTextColor(Color.WHITE);
        tv.setGravity(Gravity.CENTER);
        tv.setWidth(400);
        tv.setHeight(500);
        tv.setTextSize(15);
        tv.setTypeface(null, Typeface.BOLD);
        tv.setSingleLine(false);

        return tv;
    }

    public String toLines(String s) {
        String[] splited = s.split(" ");
        return String.join("\n", splited);
    }

    public void addTableRow(TableLayout tableLayout, TextView tv, TextView tv1, TextView tv2) {
        TableRow tableRow = new TableRow(this);
        tableRow.addView(tv);
        tableRow.addView(tv1);
        tableRow.addView(tv2);

        tableLayout.addView(tableRow);
    }

    @SuppressLint("SetTextI18n")
    public void initTable() {
        TableLayout tableLayout = findViewById(R.id.tableLayoutCompareJobs);
        tableLayout.addView(getTableRowTitle());

        Job job1 = (Job) getIntent().getSerializableExtra("job1");
        Job job2 = (Job) getIntent().getSerializableExtra("job2");

        HashMap<String, String> hashMap = job1.getTableMap();
        HashMap<String, String> hashMap2 = job2.getTableMap();

        addTableRow(
                tableLayout,
                getTextView("Score"),
                getTextView(Float.toString(jobManager.computeJobScore(job1))),
                getTextView(Float.toString(jobManager.computeJobScore(job2)))
        );

        ArrayList<String> metadata = Job.getMetadata();
        ArrayList<String> tableMetadata = Job.getTableMetadata();
        for (int i = 0; i < metadata.size(); i++) {
            String s = metadata.get(i);

            TextView tv1;
            TextView tv2;
            if (Objects.equals(s, Job.Col.CURRENT)) {
                tv1 = getTextView(toLines(job1.getCategory()));
                tv2 = getTextView(toLines(job2.getCategory()));
            } else {
                tv1 = getTextView(toLines(hashMap.get(s)));
                tv2 = getTextView(toLines(hashMap2.get(s)));
            }

            addTableRow(tableLayout, getTextView(tableMetadata.get(i)), tv1, tv2);
        }

        // Add empty space for better user scrolling experience
        addTableRow(
                tableLayout,
                getTextView(""),
                getTextView(""),
                getTextView("")
        );
    }

}