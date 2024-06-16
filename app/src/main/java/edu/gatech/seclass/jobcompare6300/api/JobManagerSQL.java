package edu.gatech.seclass.jobcompare6300.api;

import edu.gatech.seclass.jobcompare6300.models.*;

import android.annotation.*;
import android.content.*;
import android.database.*;
import android.database.sqlite.*;

import java.util.*;


public class JobManagerSQL extends SQLiteOpenHelper {
    public static final String DB_NAME = "JobCompare.db";
    public static final int DB_VERSION = 1;
    private static final String DB_TABLE = "CREATE TABLE ";
    private static final String DB_PRIMARY_KEY = "ID INTEGER PRIMARY KEY";
    private static final String DB_COMMA_SEPARATOR = ", ";
    private static final String DB_TEXT = "TEXT";
    private static final String DB_INT = "INTEGER";
    private static final String DB_REAL = "REAL";
    private static final JobManagerSQL singleton = null;

    // ======================================= INIT ==========================================
    // Singleton Constructor
    public static JobManagerSQL getInstance(Context context) {
        return singleton == null ? new JobManagerSQL(context) : singleton;
    }

    // Singleton Constructor
    private JobManagerSQL(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    // WINDOWS SPECIFIC OVERRIDES
    private SQLiteDatabase initializingDB = null;

    @Override
    public SQLiteDatabase getWritableDatabase() {
        if (initializingDB != null) {
            return initializingDB;
        }
        return super.getWritableDatabase();
    }

    // WINDOWS SPECIFIC OVERRIDES
    @Override
    public SQLiteDatabase getReadableDatabase() {
        if (initializingDB != null) {
            return initializingDB;
        }
        return super.getReadableDatabase();
    }

    // =================================== Overrides ===================================
    @Override
    public void onCreate(SQLiteDatabase db) {
        initializingDB = db;  // WINDOWS SPECIFIC OVERRIDES
        createJobTable(db);
        createJobSettingsTable(db);
        initializingDB = null;  // WINDOWS SPECIFIC OVERRIDES
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    private SQLiteDatabase DB_READ() {
        return getReadableDatabase();
    }

    private SQLiteDatabase DB_WRITE() {
        return getWritableDatabase();
    }

    // =================================== Tables Creation & Deletion ==============================
    private void createTableHelper(SQLiteDatabase db, String tableName, Map<String, String> hashMap) {
        StringBuilder stringBuilder = new StringBuilder(DB_TABLE + tableName + " (" + DB_PRIMARY_KEY);
        for (Map.Entry<String, String> entry : hashMap.entrySet())
            stringBuilder.append(DB_COMMA_SEPARATOR).append(entry.getKey()).append(" ").append(entry.getValue());
        stringBuilder.append(")");
        if (db == null) db = DB_WRITE();
        db.execSQL(stringBuilder.toString());
    }

    public void createJobTable(SQLiteDatabase db) {
        final Map<String, String> hashMap = new HashMap<String, String>() {{
            put(Job.Col.TITLE, DB_TEXT);
            put(Job.Col.COMPANY, DB_TEXT);
            put(Job.Col.STATE, DB_TEXT);
            put(Job.Col.CITY, DB_TEXT);
            put(Job.Col.COST, DB_INT);
            put(Job.Col.SALARY, DB_REAL);
            put(Job.Col.BONUS, DB_REAL);
            put(Job.Col.BENEFITS, DB_INT);
            put(Job.Col.STIPEND, DB_REAL);
            put(Job.Col.FUND, DB_REAL);
            put(Job.Col.CURRENT, DB_INT);
        }};
        createTableHelper(db, Job.Col.TABLE_NAME, hashMap);
    }

    public void createJobSettingsTable(SQLiteDatabase db) {
        final Map<String, String> hashMap = new HashMap<String, String>() {{
            put(JobSettings.Col.SALARY, DB_INT);
            put(JobSettings.Col.BONUS, DB_INT);
            put(JobSettings.Col.BENEFITS, DB_INT);
            put(JobSettings.Col.RELOCATION, DB_INT);
            put(JobSettings.Col.FUND, DB_INT);
        }};
        createTableHelper(db, JobSettings.Col.TABLE_NAME, hashMap);
        insertJobSettings(new JobSettings());
    }

    public void deleteJobTable(SQLiteDatabase db) {
        if (db == null) db = DB_WRITE();
        db.execSQL("DROP TABLE IF EXISTS " + Job.Col.TABLE_NAME);
    }

    public void deleteJobSettingsTable(SQLiteDatabase db) {
        if (db == null) db = DB_WRITE();
        db.execSQL("DROP TABLE IF EXISTS " + JobSettings.Col.TABLE_NAME);
    }

    // =================================== Job CRUD ===================================

    // ======================================= Create ==========================================
    public void insertJob(Job job) {
        // If intended to insert a new current job, check existing first in the database
        if (job.isCurrent()) {
            final Job currentJob = getCurrentJob();
            if (currentJob == null) {
                insertJobHelper(job);
            } else {
                System.err.println("ERROR INSERTING MORE THAN ONE CURRENT JOB");
            }
        } else {
            insertJobHelper(job);
        }
    }

    private void insertJobHelper(Job job) {
        DB_WRITE().insert(Job.Col.TABLE_NAME, null, job.getContent());
    }

    // ======================================= Update ==========================================
    private boolean validateCurrentJobWithDB(Job job) {
        // If intended to update a job offer to make sure its a job offer
        final Job dbCurrentJob = getCurrentJob();
        return dbCurrentJob != null && dbCurrentJob.isCurrent() && job.isCurrent() && dbCurrentJob.getId() == job.getId();
    }

//    private boolean validateJobOfferWithDB(Job job) {
//        // If intended to update a job offer to make sure its a job offer
//        final Job dbCurrentJob = getCurrentJob();
//        if (dbCurrentJob != null) {
//            return dbCurrentJob.isCurrent() && !job.isCurrent() && dbCurrentJob.getId() != job.getId();
//        }
//        return job.isCurrent() && dbCurrentJob.getId() != job.getId();
//    }


    public void editCurrentJob(Job job) {
        // If intended to update a job offer to make sure its a job offer
        if (validateCurrentJobWithDB(job)) {
            editJobOfferHelper(job);
        } else {
            System.err.println("CANNOT UPDATE A NON CURRENT JOB");
        }
    }

    public void editJobOffer(Job job) {
        if (!job.isCurrent()) {
            editJobOfferHelper(job);
        } else {
            System.err.println("ERROR UPDATING MORE THAN ONE CURRENT JOB");
        }
    }

    public void editJobOfferHelper(Job job) {
        DB_WRITE().update(Job.Col.TABLE_NAME, job.getContent(), "ID=?", new String[]{Integer.toString(job.getId())});
    }

    // ======================================= Read ==========================================
    public Job getCurrentJob() {
        final Cursor cursor = DB_READ().rawQuery("SELECT * FROM " + Job.Col.TABLE_NAME + " WHERE current=" + 1 + "", null);
        cursor.moveToFirst(); // next()
        Job job = null;
        if (!cursor.isAfterLast()) {
            job = setJobFromCursorHelper(cursor);
        }
        return job;
    }

    public ArrayList<Job> getAllJobsOffers() {
        final ArrayList<Job> array = new ArrayList<>();
        Cursor cursor = DB_READ().rawQuery("SELECT * FROM " + Job.Col.TABLE_NAME + " WHERE current=" + 0 + "", null);
        cursor.moveToFirst(); // next()
        while (!cursor.isAfterLast()) {
            final Job job = setJobFromCursorHelper(cursor);
            array.add(job);
            cursor.moveToNext(); // next()
        }
        return array;
    }

    // ======================================= Delete ==========================================
    public void deleteCurrentJob() {
        Job job = getCurrentJob();
        if (job != null && job.isCurrent()) {
            DB_WRITE().delete(Job.Col.TABLE_NAME, "ID=? ", new String[]{Integer.toString(job.getId())});
        } else {
            System.err.println("No current job to delete");
        }
    }

    public void deleteJobOffer(Job job) {
        if (!job.isCurrent()) {
            DB_WRITE().delete(Job.Col.TABLE_NAME, "ID=? ", new String[]{Integer.toString(job.getId())});
        } else {
            System.err.println("Cannot delete job offer ");
        }
    }

    // ===================================== Set Job Attributes Helper =======================================
    @SuppressLint("Range")
    private Job setJobFromCursorHelper(Cursor cursor) {
        final boolean isCurrent = cursor.getInt(cursor.getColumnIndex(Job.Col.CURRENT)) >= 1;
        final Job job = new Job(
                cursor.getString(cursor.getColumnIndex(Job.Col.TITLE)),
                cursor.getString(cursor.getColumnIndex(Job.Col.COMPANY)),

                new Location(
                        cursor.getString(cursor.getColumnIndex(Job.Col.CITY)),
                        cursor.getString(cursor.getColumnIndex(Job.Col.STATE)),
                        cursor.getInt(cursor.getColumnIndex(Job.Col.COST))
                ),

                cursor.getFloat(cursor.getColumnIndex(Job.Col.SALARY)),
                cursor.getFloat(cursor.getColumnIndex(Job.Col.BONUS)),
                cursor.getInt(cursor.getColumnIndex(Job.Col.BENEFITS)),
                cursor.getFloat(cursor.getColumnIndex(Job.Col.STIPEND)),
                cursor.getFloat(cursor.getColumnIndex(Job.Col.FUND)),
                isCurrent
        );
        try {
            job.setId(cursor.getInt(cursor.getColumnIndex("ID")));
        } catch (Exception e) {
            String message = e.getMessage();
            System.out.println(message);
        }
        return job;
    }

    // =================================== Settings CRUD ===================================
    public void insertJobSettings(JobSettings jobSettings) {
        // If intended to insert a new current job, check existing first in the database
        final JobSettings currentJobSettings = getJobSettings();
        if (currentJobSettings == null) insertJobSettingsHelper(jobSettings);
    }

    private void insertJobSettingsHelper(JobSettings jobSettings) {
        long result = DB_WRITE().insert(JobSettings.Col.TABLE_NAME, null, jobSettings.getContent());
        System.out.println("INSERT RESULT??? " + result);
    }

    public void adjustJobSettings(JobSettings jobSettings) {
        DB_WRITE().update(JobSettings.Col.TABLE_NAME, jobSettings.getContent(), "ID = ?", new String[]{Integer.toString(1)});
    }

    @SuppressLint("Range")
    public JobSettings getJobSettings() {
        final SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + JobSettings.Col.TABLE_NAME + " where ID=" + 1 + "", null);
        cursor.moveToFirst();
        JobSettings jobSettings = null;
        if (!cursor.isAfterLast()) {
            System.out.println("DATABASE JOB SETTINGS QUERY SUCCESS");
            jobSettings = new JobSettings(
                    cursor.getInt(cursor.getColumnIndex(JobSettings.Col.SALARY)),
                    cursor.getInt(cursor.getColumnIndex(JobSettings.Col.BONUS)),
                    cursor.getInt(cursor.getColumnIndex(JobSettings.Col.BENEFITS)),
                    cursor.getInt(cursor.getColumnIndex(JobSettings.Col.RELOCATION)),
                    cursor.getInt(cursor.getColumnIndex(JobSettings.Col.FUND))
            );
            jobSettings.id = cursor.getInt(cursor.getColumnIndex("ID"));
        } else {
            System.out.println("NOTHING IN JOB SETTINGS!!");
        }
        return jobSettings;
    }

//    public Job getLatestJob() {
//        final Cursor cursor = DB_READ().rawQuery("SELECT * FROM " + Job.Col.TABLE_NAME + " WHERE current=" + 0 + " ORDER BY Id DESC LIMIT 1", null);
//        cursor.moveToFirst(); // next()
//        Job job = null;
//        if (!cursor.isAfterLast()) {
//            job = setJobFromCursorHelper(cursor);
//        }
//        return job;
//    }
}