package edu.gatech.seclass.jobcompare6300.models;

import android.content.ContentValues;
import android.provider.BaseColumns;

import java.io.Serializable;
import java.util.Comparator;


public class JobSettings implements Comparator<Job>, Serializable {
    public int id;
    float yearlySalaryWeight = 1;
    float yearlyBonusWeight = 1;
    float retirementBenefitsWeight = 1;
    float relocationStipendWeight = 1;
    float trainingDevelopmentFundWeight = 1;

    public static class Col implements BaseColumns {
        public static final String TABLE_NAME = "jobSettings";

        public static final String SALARY = "salary";
        public static final String BONUS = "bonus";
        public static final String BENEFITS = "benefits";
        public static final String RELOCATION = "relocation";
        public static final String FUND = "fund";
    }

    public JobSettings() {
    }

    public JobSettings(
            float yearlySalaryWeight,
            float yearlyBonusWeight,
            float retirementBenefitsWeight,
            float retirementStipendWeight,
            float trainingDevelopmentFundWeight) {
        this.yearlySalaryWeight = yearlySalaryWeight;
        this.yearlyBonusWeight = yearlyBonusWeight;
        this.retirementBenefitsWeight = retirementBenefitsWeight;
        this.relocationStipendWeight = retirementStipendWeight;
        this.trainingDevelopmentFundWeight = trainingDevelopmentFundWeight;
    }

    public void reset() {
        yearlySalaryWeight = 1;
        yearlyBonusWeight = 1;
        retirementBenefitsWeight = 1;
        relocationStipendWeight = 1;
        trainingDevelopmentFundWeight = 1;
    }

    public ContentValues getContent() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(JobSettings.Col.SALARY, getYearlySalaryWeight());
        contentValues.put(JobSettings.Col.BONUS, getYearlyBonusWeight());
        contentValues.put(JobSettings.Col.BENEFITS, getRetirementBenefitsWeight());
        contentValues.put(JobSettings.Col.RELOCATION, getRelocationStipendWeight());
        contentValues.put(JobSettings.Col.FUND, getTrainingDevelopmentFundWeight());
        return contentValues;
    }

    public float computeJobScore(Job job) {
        final float cost = job.getCostOfLiving();
        final float AYS = job.getYearlySalary() * (100 / cost);
        final float AYB = job.getYearlyBonus() * (100 / cost);
        final float RBP = job.getRetirementBenefits();
        final float RS = job.getRelocationStipend();
        final float TDF = job.getTrainingDevelopmentFund();

        final float score = (
                (yearlySalaryWeight / 7) * AYS +
                        (yearlyBonusWeight / 7) * AYB +
                        (retirementBenefitsWeight / 7) * (RBP * AYS / 100) +
                        (relocationStipendWeight / 7) * RS +
                        (trainingDevelopmentFundWeight / 7) * TDF
        );
        return score;
    }

    @Override
    public int compare(Job o1, Job o2) {
        if (computeJobScore(o1) < computeJobScore(o2)) {
            return 1;
        } else if (computeJobScore(o1) > computeJobScore(o2)) {
            return -1;
        }
        return 0;
    }

    public float getYearlySalaryWeight() {
        return yearlySalaryWeight;
    }

    public void setYearlySalaryWeight(float yearlySalaryWeight) {
        this.yearlySalaryWeight = yearlySalaryWeight;
    }

    public float getYearlyBonusWeight() {
        return yearlyBonusWeight;
    }

    public void setYearlyBonusWeight(float yearlyBonusWeight) {
        this.yearlyBonusWeight = yearlyBonusWeight;
    }

    public float getRetirementBenefitsWeight() {
        return retirementBenefitsWeight;
    }

    public void setRetirementBenefitsWeight(float retirementBenefitsWeight) {
        this.retirementBenefitsWeight = retirementBenefitsWeight;
    }

    public float getRelocationStipendWeight() {
        return relocationStipendWeight;
    }

    public void setRelocationStipendWeight(float retirementStipendWeight) {
        this.relocationStipendWeight = retirementStipendWeight;
    }

    public float getTrainingDevelopmentFundWeight() {
        return trainingDevelopmentFundWeight;
    }

    public void setTrainingDevelopmentFundWeight(float trainingDevelopmentFundWeight) {
        this.trainingDevelopmentFundWeight = trainingDevelopmentFundWeight;
    }
}
