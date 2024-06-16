package edu.gatech.seclass.jobcompare6300;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.*;


import java.util.ArrayList;

import edu.gatech.seclass.jobcompare6300.api.JobManager;
import edu.gatech.seclass.jobcompare6300.models.JobSettings;


public class JobSettingsActivity extends BaseActivity {
    JobManager jobManager = JobManager.getInstance(this);

    EditText salaryWeight;
    EditText bonusWeight;
    EditText benefitsWeight;
    EditText relocationWeight;
    EditText fundsWeight;

    Button backButton;
    Button resetSettingsButton;
    Button editSettingsButton;
    Button saveSettingsButton;
    Button cancelSettingsButton;

    ArrayList<EditText> arrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.requestWindowFeature(Window.FEATURE_ACTION_BAR);
        setContentView(R.layout.activity_settings);

        salaryWeight = findViewById(R.id.editSalaryWeight);
        bonusWeight = findViewById(R.id.editBonusWeight);
        benefitsWeight = findViewById(R.id.editBenefitsWeight);
        relocationWeight = findViewById(R.id.editRelocationWeight);
        fundsWeight = findViewById(R.id.editFundWeight);

        backButton = findViewById(R.id.BACK2);
        initBackButton(backButton);

        resetSettingsButton = findViewById(R.id.resetSettingsButton);
        saveSettingsButton = findViewById(R.id.saveSettingsButton);
        editSettingsButton = findViewById(R.id.editSettingsButton);
        cancelSettingsButton = findViewById(R.id.cancelSettingsButton);

        arrayList.add(salaryWeight);
        arrayList.add(bonusWeight);
        arrayList.add(benefitsWeight);
        arrayList.add(relocationWeight);
        arrayList.add(fundsWeight);

        saveSettingsButton.setBackgroundColor(Color.parseColor("#27ae60"));
        editSettingsButton.setBackgroundColor(Color.parseColor("#34495e"));
        cancelSettingsButton.setBackgroundColor(Color.parseColor("#0984e3"));

        initOnClick();
        initDefaultValues();
        setViewMode();

    }

    @SuppressLint("SetTextI18n")
    private void initDefaultValues() {
        JobSettings jobSettings = jobManager.getJobSettings();
        salaryWeight.setText(Integer.toString((int) jobSettings.getYearlySalaryWeight()));
        bonusWeight.setText(Integer.toString((int) jobSettings.getYearlyBonusWeight()));
        benefitsWeight.setText(Integer.toString((int) jobSettings.getRetirementBenefitsWeight()));
        relocationWeight.setText(Integer.toString((int) jobSettings.getRelocationStipendWeight()));
        fundsWeight.setText(Integer.toString((int) jobSettings.getTrainingDevelopmentFundWeight()));
    }

    protected boolean validate() {
        boolean v5 = validateInt(salaryWeight, 1, 7);
        boolean v6 = validateInt(bonusWeight, 1, 7);
        boolean v7 = validateInt(benefitsWeight, 1, 7);
        boolean v8 = validateInt(relocationWeight, 1, 7);
        boolean v9 = validateInt(fundsWeight, 1, 7);

        System.out.println("v5: " + v5);
        System.out.println("v6: " + v6);
        System.out.println("v7: " + v7);
        System.out.println("v8: " + v8);
        System.out.println("v9: " + v9);

        return v5 && v6 && v7 && v8 && v9;
    }

    protected void reset() {
        setViewMode();
        JobSettings jobSettings = jobManager.getJobSettings();
        jobSettings.reset();
        jobManager.adjustJobSettings(jobSettings);
        initDefaultValues();
    }

    protected void save() {
        JobSettings jobSettings = jobManager.getJobSettings();
        if (validate()) {
            jobSettings.setYearlySalaryWeight(convert(salaryWeight));
            jobSettings.setYearlyBonusWeight(convert(bonusWeight));
            jobSettings.setRetirementBenefitsWeight(convert(benefitsWeight));
            jobSettings.setRelocationStipendWeight(convert(relocationWeight));
            jobSettings.setTrainingDevelopmentFundWeight(convert(fundsWeight));
            jobManager.adjustJobSettings(jobSettings);
            System.out.println("UPDATED");
            setViewMode();
            Toast.makeText(getApplicationContext(), "Saved Successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "Unable to save due to errors", Toast.LENGTH_SHORT).show();
        }
    }

    private float convert(EditText editText) {
        String s = editText.getText().toString();
        float f = Float.parseFloat(s);
        return f;
    }


    protected void setEditMode() {
        for (EditText editText : arrayList) {
            editText.setBackgroundTintList(ColorStateList.valueOf(Color.BLACK));
            editText.setFocusableInTouchMode(true);
        }
        editSettingsButton.setVisibility(View.INVISIBLE);
        saveSettingsButton.setVisibility(View.VISIBLE);
        resetSettingsButton.setVisibility(View.VISIBLE);
        cancelSettingsButton.setVisibility(View.VISIBLE);
    }

    protected void setViewMode() {
        for (EditText editText : arrayList) {
            editText.setBackgroundTintList(ColorStateList.valueOf(Color.TRANSPARENT));
            editText.setFocusable(false);
        }
        editSettingsButton.setVisibility(View.VISIBLE);
        saveSettingsButton.setVisibility(View.INVISIBLE);
        resetSettingsButton.setVisibility(View.INVISIBLE);
        cancelSettingsButton.setVisibility(View.INVISIBLE);
    }

    private boolean validateInt(EditText editText, int min, int max) {
        try {
            final int f = Integer.parseInt(editText.getText().toString());
            if (f >= min && f <= max) {
                editText.setError(null);
                return true;
            } else {
                editText.setError("Must be in range " + min + " and " + max + " inclusive ");
                return false;
            }
        } catch (Exception e) {
            // Allow soft warning
            System.err.println("ERROR STRING: " + e.getMessage());
            editText.setError("Must be in range " + min + " and " + max + " inclusive ");
            return false;
        }
    }

    private void initOnClickHelper(EditText editText) {
        editText.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (editText.hasFocus())
                    validateInt(editText, 1, 7);
            }
        });
    }

    private void initOnClick() {
        for (EditText editText : arrayList) {
            initOnClickHelper(editText);
        }

        backButton.setOnClickListener(v -> {
            super.onBackPressed();
        });

        editSettingsButton.setOnClickListener(v -> {
            setEditMode();
        });

        saveSettingsButton.setOnClickListener(v -> {
            save();
            super.onBackPressed();  //added to follow the assignment requirement
        });

        cancelSettingsButton.setOnClickListener(v -> {
            setViewMode();
            initDefaultValues();
        });

        resetSettingsButton.setOnClickListener(v -> {
            reset();
        });
    }
}



