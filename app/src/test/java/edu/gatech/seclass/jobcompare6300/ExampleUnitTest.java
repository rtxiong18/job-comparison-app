package edu.gatech.seclass.jobcompare6300;

import org.junit.Test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import edu.gatech.seclass.jobcompare6300.models.Job;
import edu.gatech.seclass.jobcompare6300.models.JobSettings;
import edu.gatech.seclass.jobcompare6300.models.Location;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void testJobs() {
        ArrayList<Job> jobs = getJobs();
        assertEquals(jobs.size(), 3);
    }

    public static ArrayList<Job> getJobs() {
        ArrayList<Job> jobs = new ArrayList<>();
        jobs.add(new Job("Software Engineer 1",
                "Amazon",
                new Location("Seattle", "WA", 2),
                1,
                2,
                1,
                1,
                1
        ));
        jobs.add(new Job("Software Engineer 2",
                "Netflix",
                new Location("Austin", "TX", 1),
                1,
                4,
                1,
                1,
                1
        ));

        jobs.add(new Job("Software Engineer 2",
                "Facebook",
                new Location("Los Angeles", "CA", 3),
                1,
                3,
                1,
                1,
                1
        ));
        return jobs;
    }


    public static void testSort() {
        JobSettings jobSettings = new JobSettings(
                2,
                1,
                1,
                2,
                1
        );
        ArrayList<Job> jobs = getJobs();
        System.out.println("BEFORE SORTING");
        for (Job job : jobs) {
            System.out.println(" SCORE:" + jobSettings.computeJobScore(job) + " " + job);
        }
        System.out.println("AFTER SORTING");
        jobs.sort(jobSettings);
        for (Job job : jobs) {
            System.out.println(" SCORE:" + jobSettings.computeJobScore(job) + " " + job);
        }
    }
}