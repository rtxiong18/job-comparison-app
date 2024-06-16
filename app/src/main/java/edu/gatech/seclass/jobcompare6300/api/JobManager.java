package edu.gatech.seclass.jobcompare6300.api;

import android.content.Context;

import java.util.ArrayList;

import edu.gatech.seclass.jobcompare6300.models.Job;
import edu.gatech.seclass.jobcompare6300.models.JobSettings;
import edu.gatech.seclass.jobcompare6300.models.Location;

public class JobManager {

    // ======================================= INIT ==========================================
    private static JobManager singleton = null;
    private static JobManagerSQL jobManagerSQL;

    // Singleton Constructor
    public static JobManager getInstance(Context context) {
        if (singleton == null)
            singleton = new JobManager(context);
        return singleton;
    }

    private JobManager(Context context) {
        // Singleton instance
        jobManagerSQL = JobManagerSQL.getInstance(context);
    }

    public void test(Context context) {
        jobManagerSQL = JobManagerSQL.getInstance(context);

        jobManagerSQL.deleteJobTable(null);
        jobManagerSQL.deleteJobSettingsTable(null);
        jobManagerSQL.createJobTable(null);
        jobManagerSQL.createJobSettingsTable(null);

        Job job = new Job("Software Engineer 1",
                "Amazon",
                new Location("New York City", "NY", 267),
                100000,
                20000,
                50,
                1000,
                500,
                true
        );

        Job job2 = new Job("Software Engineer 2",
                "Amazon",
                new Location("San Francisco", "CA", 254),
                90000,
                10000,
                30,
                1000,
                500
        );

        Job job3 = new Job("Software Engineer 3",
                "Amazon",
                new Location("Austin", "TX", 175),
                800000,
                200,
                40,
                2000,
                1000
        );

        Job job4 = new Job("Software Engineer 4",
                "Tesla",
                new Location("Chicago", "IL", 194),
                120000,
                50000,
                70,
                1000,
                500
        );

        jobManagerSQL.insertJob(job);
        jobManagerSQL.insertJob(job2);
        jobManagerSQL.insertJob(job3);
        jobManagerSQL.insertJob(job4);

        ArrayList<Job> jobs = getJobOffers();
        jobs.forEach(System.out::println);

        System.out.println();
        jobs = getSortedJobsByRank();

        jobs.forEach(System.out::println);

        Job jobX = getCurrentJob();
        System.out.println(jobX);
    }

    // ======================================= Client APIs =========================================
    public Job getCurrentJob() {
        return jobManagerSQL.getCurrentJob();
    }
//    public Job getLatestJob() {
//        return jobManagerSQL.getLatestJob();
//    }

    public void addCurrentJob(Job job) {
        if (job.isCurrent())
            jobManagerSQL.insertJob(job);
    }

    public void editCurrentJob(Job job) {
        jobManagerSQL.editCurrentJob(job);
    }

    public void editJobOffer(Job job) {
        jobManagerSQL.editJobOffer(job);
    }

    public void deleteCurrentJob() {
        jobManagerSQL.deleteCurrentJob();
    }

    public void deleteJobOffer(Job job) {
        jobManagerSQL.deleteJobOffer(job);
    }

    public void addJobOffer(Job job) {
        jobManagerSQL.insertJob(job);
    }

    public ArrayList<Job> getJobOffers() {
        return jobManagerSQL.getAllJobsOffers();
    }

    public ArrayList<Job> getSortedJobsByRank() {
        ArrayList<Job> jobs = jobManagerSQL.getAllJobsOffers();
        if (jobManagerSQL.getCurrentJob() != null) {
            jobs.add(jobManagerSQL.getCurrentJob());
        }
        jobs.sort(getJobSettings());
        return jobs;
    }

    public void adjustJobSettings(JobSettings jobSettings) {
        jobManagerSQL.adjustJobSettings(jobSettings);
    }

    public JobSettings getJobSettings() {
        return jobManagerSQL.getJobSettings();
    }

    public float computeJobScore(Job job) {
        JobSettings jobSettings = getJobSettings();
        float round = (float) Math.round(jobSettings.computeJobScore(job) * 100) / 100;
        return round;
    }

    // WARNING: only for developer testing
    public void resetAll() {
        jobManagerSQL.deleteJobTable(null);
        jobManagerSQL.deleteJobSettingsTable(null);
        jobManagerSQL.createJobTable(null);
        jobManagerSQL.createJobSettingsTable(null);
    }
}
