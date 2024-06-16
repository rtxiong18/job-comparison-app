package edu.gatech.seclass.jobcompare6300;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;


import edu.gatech.seclass.jobcompare6300.api.JobManager;

public class DeveloperToolsActivity extends BaseActivity {

    final JobManager jobManager = JobManager.getInstance(this);


    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, MainActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_developer);

        Button preloadJobs = findViewById(R.id.preloadData);
        preloadJobs.setOnClickListener(v -> {
            jobManager.test(this);
            onBackPressed();
        });

        Button reset = findViewById(R.id.resetApp);
        reset.setOnClickListener(v -> {
            jobManager.resetAll();
            onBackPressed();
        });
        Button backButton = findViewById(R.id.backDevSettings);
        initBackButton(backButton);
        @SuppressLint("UseSwitchCompatOrMaterialCode") Switch displayBackButtonSwitch = findViewById(R.id.displayBackButton);
        displayBackButtonSwitch.setChecked(this.showBackButton());
        displayBackButtonSwitch.setOnClickListener(v -> {
            Toast.makeText(this, "Toggle display back button", Toast.LENGTH_SHORT).show();
            toggleBackButtonDisplay();
            initBackButton(backButton);
        });
    }
}
