package edu.gatech.seclass.jobcompare6300.models;


import android.content.ContentValues;
import android.provider.BaseColumns;
import android.widget.TableRow;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import edu.gatech.seclass.jobcompare6300.api.JobManager;


public class Job implements Serializable {

    // =============================== Identifiers ===============================
    int id = -1;
    String title;
    String company;
    boolean isCurrent = false;

    // =============================== Location ===============================
    Location location;

    // =============================== Money ===============================
    float yearlySalary;
    float yearlyBonus;
    Integer retirementBenefits;
    float relocationStipend;
    float trainingDevelopmentFund;

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    // UI
    boolean active = false;

    // Columns
    public static class Col implements BaseColumns {
        public static final String TABLE_NAME = "jobs";

        public static final String TITLE = "title";
        public static final String COMPANY = "company";

        public static final String CITY = "city";
        public static final String STATE = "state";
        public static final String COST = "cost"; // cost of living

        public static final String SALARY = "salary"; // yearly salary
        public static final String BONUS = "bonus"; // yearly bonus
        public static final String BENEFITS = "benefits";
        public static final String STIPEND = "stipend";
        public static final String FUND = "fund";

        public static final String CURRENT = "current";
    }

    public static class TableCol {
        public static final String TITLE = "Title";
        public static final String COMPANY = "Company";

        public static final String CITY = "City";
        public static final String STATE = "State";
        public static final String COST = "Cost\nOf\nLiving"; // cost of living

        public static final String SALARY = "Yearly\nSalary"; // yearly salary
        public static final String BONUS = "Yearly\nBonus"; // yearly bonus
        public static final String BENEFITS = "Retirement\nBenefits";
        public static final String STIPEND = "Relocation\nStipend";
        public static final String FUND = "Training\nDevelopment\nFund";

        public static final String CURRENT = "Is\nCurrent\nJob?";
    }

    // =============================== Constructors ===============================
    public Job() {
    }

    public Job(String title, String company, Location location,
               float yearlySalary, float yearlyBonus, Integer retirementBenefits,
               float retirementStipend, float trainingDevelopmentFund
    ) {
        this.title = title;
        this.company = company;
        this.location = location;

        this.yearlySalary = yearlySalary;
        this.yearlyBonus = yearlyBonus;
        this.retirementBenefits = retirementBenefits;
        this.relocationStipend = retirementStipend;
        this.trainingDevelopmentFund = trainingDevelopmentFund;
    }

    public Job(String title, String company, Location location,
               float yearlySalary, float yearlyBonus, Integer retirementBenefits,
               float retirementStipend, float trainingDevelopmentFund, boolean isCurrent
    ) {
        this(title, company, location,
                yearlySalary, yearlyBonus, retirementBenefits,
                retirementStipend, trainingDevelopmentFund);
        this.isCurrent = isCurrent;
    }

    public ContentValues getContent() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(Job.Col.TITLE, getTitle());
        contentValues.put(Job.Col.COMPANY, getCompany());

        contentValues.put(Job.Col.CITY, getCity());
        contentValues.put(Job.Col.STATE, getState());
        contentValues.put(Job.Col.COST, getCostOfLiving());

        contentValues.put(Job.Col.SALARY, getYearlySalary());
        contentValues.put(Job.Col.BONUS, getYearlyBonus());
        contentValues.put(Job.Col.BENEFITS, getRetirementBenefits());
        contentValues.put(Job.Col.STIPEND, getRelocationStipend());
        contentValues.put(Job.Col.FUND, getTrainingDevelopmentFund());

        contentValues.put(Job.Col.CURRENT, isCurrent());
        return contentValues;
    }

    // =============================== Identifiers: Getters & Setters ===============================
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isCurrent() {
        return isCurrent;
    }

    public void setCurrent(boolean current) {
        isCurrent = current;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    // =============================== Location: Getters & Setters ===============================
    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getCity() {
        if (location == null) return null;
        return location.getCity();
    }

    public String getState() {
        if (location == null) return null;
        return location.getState();
    }

    public int getCostOfLiving() {
        if (location == null) return 0;
        return location.getCostOfLiving();
    }

    // =============================== Money: Getters & Setters ===============================
    public float getYearlySalary() {
        return yearlySalary;
    }

    public void setYearlySalary(float yearlySalary) {
        this.yearlySalary = yearlySalary;
    }

    public float getYearlyBonus() {
        return yearlyBonus;
    }

    public void setYearlyBonus(float yearlyBonus) {
        this.yearlyBonus = yearlyBonus;
    }

    public int getRetirementBenefits() {
        return retirementBenefits;
    }

    public void setRetirementBenefits(Integer retirementBenefits) {
        this.retirementBenefits = retirementBenefits;
    }

    public float getRelocationStipend() {
        return relocationStipend;
    }

    public void setRelocationStipend(float relocationStipend) {
        this.relocationStipend = relocationStipend;
    }

    public float getTrainingDevelopmentFund() {
        return trainingDevelopmentFund;
    }

    public void setTrainingDevelopmentFund(float trainingDevelopmentFund) {
        this.trainingDevelopmentFund = trainingDevelopmentFund;
    }

    @NonNull
    public String toLongString() {
        return "Job{" +
                "title='" + title + '\n' +
                ", company='" + company + '\n' +
                ", location='" + location + '\n' +
                ", yearlySalary=" + yearlySalary + '\n' +
                ", yearlyBonus=" + yearlyBonus + '\n' +
                ", retirementBenefits=" + retirementBenefits + '\n' +
                ", retirementStipend=" + relocationStipend + '\n' +
                ", trainingDevelopmentFund=" + trainingDevelopmentFund + '\n' +
                ", isCurrent=" + isCurrent + '\n' +
                '}';
    }

    public String getCategory() {
        if (isCurrent) return "Current Job";
        return "Job Offer";
    }


    public HashMap<String, String> getTableMap() {
        final HashMap<String, String> hashMap = new HashMap<String, String>() {{
            put(Job.Col.TITLE, title);
            put(Job.Col.COMPANY, company);
            put(Job.Col.STATE, location.getState());
            put(Job.Col.CITY, location.getCity());
            put(Job.Col.COST, Float.toString(location.getCostOfLiving()));
            put(Job.Col.SALARY, Float.toString(yearlySalary));
            put(Job.Col.BONUS, Float.toString(yearlyBonus));
            put(Job.Col.BENEFITS, Float.toString(retirementBenefits));
            put(Job.Col.STIPEND, Float.toString(relocationStipend));
            put(Job.Col.FUND, Float.toString(trainingDevelopmentFund));
            put(Job.Col.CURRENT, Boolean.toString(isCurrent));
        }};
        return hashMap;
    }

    public static ArrayList<String> getMetadata() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add(Col.TITLE);
        arrayList.add(Col.COMPANY);
        arrayList.add(Col.STATE);
        arrayList.add(Col.CITY);
        arrayList.add(Col.COST);
        arrayList.add(Col.SALARY);
        arrayList.add(Col.BONUS);
        arrayList.add(Col.BENEFITS);
        arrayList.add(Col.STIPEND);
        arrayList.add(Col.FUND);
        arrayList.add(Col.CURRENT);
        return arrayList;
    }

    public static ArrayList<String> getTableMetadata() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add(TableCol.TITLE);
        arrayList.add(TableCol.COMPANY);
        arrayList.add(TableCol.STATE);
        arrayList.add(TableCol.CITY);
        arrayList.add(TableCol.COST);
        arrayList.add(TableCol.SALARY);
        arrayList.add(TableCol.BONUS);
        arrayList.add(TableCol.BENEFITS);
        arrayList.add(TableCol.STIPEND);
        arrayList.add(TableCol.FUND);
        arrayList.add(TableCol.CURRENT);
        return arrayList;
    }

    public String toJobOfferTableString() {
        return
                "Title:" + title + '\n' +
                        "Company: " + company + '\n' + '\n' +
                        "City: " + location.getCity() + '\n' +
                        "State: " + location.getState() + '\n' +
                        "Cost Of Living: " + location.getCostOfLiving() + '\n' + '\n' +
                        "Yearly Salary: " + yearlySalary + '\n' +
                        "Yearly Bonus: " + yearlyBonus + '\n' +
                        "Retirement Benefits: " + retirementBenefits + '\n' +
                        "Relocation Stipend: " + relocationStipend + '\n' +
                        "Training Development Funds: " + trainingDevelopmentFund + '\n';
    }

    public String toTableString() {
        String category = "Category:";
        if (isCurrent) category += "Current Job";
        else category += "Job Offer";
        return toJobOfferTableString() + category;
    }


    @NonNull
    public String toString() {
        JobSettings js = JobManager.getInstance(null).getJobSettings();
        float score = Math.round(js.computeJobScore(this));
        return score + ":" + title + ", " + company + ", " + location.getCity();
    }
}
