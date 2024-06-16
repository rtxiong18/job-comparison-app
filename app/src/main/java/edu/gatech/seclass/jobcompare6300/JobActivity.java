package edu.gatech.seclass.jobcompare6300;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import edu.gatech.seclass.jobcompare6300.api.JobManager;
import edu.gatech.seclass.jobcompare6300.models.Job;
import edu.gatech.seclass.jobcompare6300.models.Location;


public abstract class JobActivity extends BaseActivity {

    JobManager jobManager = JobManager.getInstance(this);

    EditText title;
    EditText company;

    AutoCompleteTextView city;
    AutoCompleteTextView state;
    EditText cost;

    EditText salary;
    EditText bonus;
    EditText benefits;
    EditText relocation;
    EditText funds;

    Button backButton;

    Button saveButton;
    Button editButton;
    Button deleteButton;
    Button cancelButton;
//    Button saveCompareButton;

    boolean loadedJob = false;

    ArrayList<EditText> arrayList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_job);

        // INIT
        title = findViewById(R.id.inputTitle);
        company = findViewById(R.id.inputCompany);

        city = findViewById(R.id.inputCity);
        ArrayAdapter<String> adapterCITY = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.CITY));
        city.setAdapter(adapterCITY);
        city.setThreshold(1);
        state = findViewById(R.id.inputState);
        ArrayAdapter<String> adapterSTATE = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.STATE));
        state.setAdapter(adapterSTATE);
        state.setThreshold(1);
        cost = findViewById(R.id.inputCostOfLiving);

        salary = findViewById(R.id.inputSalary);
        bonus = findViewById(R.id.inputBonus);
        benefits = findViewById(R.id.inputBenefits);
        relocation = findViewById(R.id.inputStipend);
        funds = findViewById(R.id.inputFund);

        backButton = findViewById(R.id.BACK);
        initBackButton(backButton);
        saveButton = findViewById(R.id.saveJob);
        editButton = findViewById(R.id.editJob);
        cancelButton = findViewById(R.id.cancelJob);
        deleteButton = findViewById(R.id.deleteJob);
//        saveCompareButton = findViewById(R.id.saveCompareJob);

        arrayList.add(title);
        arrayList.add(company);

        arrayList.add(city);
        arrayList.add(state);
        arrayList.add(cost);

        arrayList.add(salary);
        arrayList.add(bonus);
        arrayList.add(benefits);
        arrayList.add(relocation);
        arrayList.add(funds);

        saveButton.setBackgroundColor(Color.parseColor("#27ae60"));
        editButton.setBackgroundColor(Color.parseColor("#34495e"));
        cancelButton.setBackgroundColor(Color.parseColor("#0984e3"));

        initOnClick();
        initDefaultValues();
        setViewMode();
    }

    protected void setEditMode() {
        for (EditText editText : arrayList) {
            editText.setBackgroundTintList(ColorStateList.valueOf(Color.BLACK));
            editText.setFocusableInTouchMode(true);
        }
        editButton.setVisibility(View.INVISIBLE);
        saveButton.setVisibility(View.VISIBLE);
        deleteButton.setVisibility(View.VISIBLE);
        cancelButton.setVisibility(View.VISIBLE);

        if (!loadedJob) {
            deleteButton.setVisibility(View.INVISIBLE);
        }
    }
//    protected void setNewOfferMode(){
//        saveCompareButton.setVisibility(View.VISIBLE);
//    }

    protected void setViewMode() {
        for (EditText editText : arrayList) {
            editText.setBackgroundTintList(ColorStateList.valueOf(Color.TRANSPARENT));
            editText.setFocusable(false);
            editText.setError(null);
        }
        editButton.setVisibility(View.VISIBLE);
//        saveCompareButton.setVisibility(View.INVISIBLE);
        saveButton.setVisibility(View.INVISIBLE);
        deleteButton.setVisibility(View.INVISIBLE);
        cancelButton.setVisibility(View.INVISIBLE);
    }

    // Note this must be override
    protected abstract Job getJobContext();

    @SuppressLint("SetTextI18n")
    private void initDefaultValues() {
        Job job = getJobContext();
        if (job != null) {
            try {
                title.setText(job.getTitle());
                company.setText(job.getCompany());

                city.setText(job.getCity());
                state.setText(job.getState());
                cost.setText(Integer.toString(job.getCostOfLiving()));

                salary.setText(Float.toString(job.getYearlySalary()));
                bonus.setText(Float.toString(job.getYearlyBonus()));
                benefits.setText(Integer.toString(job.getRetirementBenefits()));
                relocation.setText(Float.toString(job.getRelocationStipend()));
                funds.setText(Float.toString(job.getTrainingDevelopmentFund()));

                loadedJob = true;
            } catch (Exception e) {
                title.setText("");
                company.setText("");

                city.setText("");
                state.setText("");
                cost.setText("");

                salary.setText("");
                bonus.setText("");
                benefits.setText("");
                relocation.setText("");
                funds.setText("");

                loadedJob = false;
            }
        }
    }

    protected boolean validate() {
        boolean v = validateString(title);
        boolean v1 = validateString(company);
        boolean v2 = validateString(city);
        boolean v3 = validateString(state);

        boolean v4 = validateFloat(cost, 1, 999999999); // https://edstem.org/us/courses/21803/discussion/1611821

        boolean v5 = validateFloat(salary, 1, 999999999);
        boolean v6 = validateFloat(bonus, 0, 999999999);
        boolean v7 = validateInt(benefits, 0, 100);
        boolean v8 = validateFloat(relocation, 1, 999999999);
        boolean v9 = validateFloat(funds, 1, 18000);

        System.out.println("v: " + v);
        System.out.println("v1: " + v1);
        System.out.println("v2: " + v2);
        System.out.println("v3: " + v3);
        System.out.println("v4: " + v4);
        System.out.println("v5: " + v5);
        System.out.println("v6: " + v6);
        System.out.println("v7: " + v7);
        System.out.println("v8: " + v8);
        System.out.println("v9: " + v9);

        return v && v1 && v2 && v3 && v4 && v5 && v6 && v7 && v8 && v9;
    }

    protected void save() {
        Job job = this.getJobContext();
        if (validate()) {
            job.setTitle(convert(title));
            job.setCompany(convert(company));
            job.setLocation(new Location(convert(city), convert(state), convertToInt(cost)));
            job.setYearlySalary(convertToFloat(salary));
            job.setYearlyBonus(convertToFloat(bonus));
            job.setRetirementBenefits(convertToInt(benefits));
            job.setRelocationStipend(convertToFloat(relocation));
            job.setTrainingDevelopmentFund(convertToFloat(funds));
            update(job); //OVERRIDE THIS
            System.out.println("UPDATED");
            setViewMode();
            Toast.makeText(getApplicationContext(), "Saved Successfully", Toast.LENGTH_SHORT).show();
            onBackPressed();
            loadedJob = true;
        } else {
            Toast.makeText(getApplicationContext(), "Unable to save due to errors", Toast.LENGTH_SHORT).show();
        }
    }

//    protected void saveCompare() {
//        Job job = this.getJobContext();
//        if (validate()) {
//            job.setTitle(convert(title));
//            job.setCompany(convert(company));
//            job.setLocation(new Location(convert(city), convert(state), convertToInt(cost)));
//            job.setYearlySalary(convertToFloat(salary));
//            job.setYearlyBonus(convertToFloat(bonus));
//            job.setRetirementBenefits(convertToInt(benefits));
//            job.setRelocationStipend(convertToFloat(relocation));
//            job.setTrainingDevelopmentFund(convertToFloat(funds));
//            update(job); //OVERRIDE THIS
//            Job job1 = jobManager.getCurrentJob();
//            if (job1 != null) {
//                Job job2 = jobManager.getLatestJob();
//                Intent i = new Intent(getApplicationContext(), CompareTwoJobsActivity.class);
//                i.putExtra("job1", job1);
//                i.putExtra("job2", job2);
//                startActivity(i);
//            } else {
//                Toast.makeText(getApplicationContext(), "No Current Job Available to Compare With", Toast.LENGTH_SHORT).show();
//            }
//
//            loadedJob = true;
//        } else {
//            Toast.makeText(getApplicationContext(), "Unable to save due to errors", Toast.LENGTH_SHORT).show();
//        }
//    }

    protected abstract void update(Job job);

    protected abstract void delete();

    protected String convert(EditText editText) {
        String s = editText.getText().toString();
        return s;
    }

    protected float convertToFloat(EditText editText) {
        final String s = editText.getText().toString();
        final float f = Float.parseFloat(s);
        return f;
    }

    protected int convertToInt(EditText editText) {
        final String s = editText.getText().toString();
        final int f = Integer.parseInt(s);
        return f;
    }

    private boolean validateString(EditText editText) {
        try {
            final String str = editText.getText().toString();
            if (str.length() > 0) {
                editText.setError(null);
                return true;
            } else {
                editText.setError("Please fill out this field");
                return false;
            }

        } catch (Exception e) {
            // Allow soft warning
            System.err.println("ERROR STRING: " + e.getMessage());
            editText.setError("Please fill out this field");
            return false;
        }
    }

    private boolean validateInt(EditText editText, int min, int max) {
        try {
            final int f = Integer.parseInt((editText.getText().toString()));
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


    private boolean validateFloat(EditText editText, float min, float max) {
        try {
            final float f = Float.parseFloat(editText.getText().toString());
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

    private void initOnClick() {
        title.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (title.hasFocus())
                    validateString(title);
            }
        });

        company.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (company.hasFocus())
                    validateString(company);
            }
        });

        city.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (city.hasFocus())
                    validateString(city);
            }
        });

        state.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (state.hasFocus())
                    validateString(state);
            }
        });

        cost.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (cost.hasFocus())
                    validateFloat(cost, 1, 300);
            }
        });

        salary.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (salary.hasFocus())
                    validateFloat(salary, 1, 999999999);
            }
        });

        bonus.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (bonus.hasFocus())
                    validateFloat(bonus, 1, 999999999);
            }
        });


        benefits.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (benefits.hasFocus())
                    validateInt(benefits, 0, 100);
            }
        });

        relocation.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (relocation.hasFocus())
                    validateFloat(relocation, 1, 999999999);
            }
        });

        funds.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (funds.hasFocus())
                    validateFloat(funds, 1, 18000);
            }
        });

        backButton.setOnClickListener(v -> {
            super.onBackPressed();
        });

        editButton.setOnClickListener(v -> {
            setEditMode();
        });

        saveButton.setOnClickListener(v -> {
            save();
            // super.onBackPressed(); //added to follow the assignment requirement
        });

        cancelButton.setOnClickListener(v -> {
            setViewMode();
            initDefaultValues();
        });

        deleteButton.setOnClickListener(v -> {
            delete();
            super.onBackPressed();
        });

//        saveCompareButton.setOnClickListener(v -> {
//            saveCompare();
//
//        });
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}

