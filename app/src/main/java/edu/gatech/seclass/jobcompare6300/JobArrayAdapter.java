package edu.gatech.seclass.jobcompare6300;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import edu.gatech.seclass.jobcompare6300.api.JobManager;
import edu.gatech.seclass.jobcompare6300.models.Job;
import edu.gatech.seclass.jobcompare6300.models.JobSettings;

public class JobArrayAdapter extends ArrayAdapter<Job> {
    private final Context context;
    private final ArrayList<Job> lists;
    private static final JobSettings jobSettings = JobManager.getInstance(null).getJobSettings();
    private final boolean[] selectedItems;

    public JobArrayAdapter(Context context, ArrayList<Job> objects) {
        super(context, 0, objects);
        this.context = context;
        this.lists = objects;

        selectedItems = new boolean[this.lists.size()];
        for (int i = 0; i < lists.size(); i++) {
            selectedItems[i] = false;
        }
    }

    public int getNumberOfCheckboxesChecked() {
        int counter = 0;
        for (int i = 0; i < lists.size(); i++) {
            if (selectedItems[i]) {
                counter += 1;
            }
        }
        return counter;
    }

    public ArrayList<Job> getSelectedJobs() {
        ArrayList<Job> jobs = new ArrayList<>();
        for (int i = 0; i < lists.size(); i++) {
            if (selectedItems[i]) {
                jobs.add(lists.get(i));
            }
        }
        return jobs;
    }

    @SuppressLint("SetTextI18n")
    public View getView(int position, final View convertView, ViewGroup parent) {

        final LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("ViewHolder") final View rowView = inflater.inflate(R.layout.activity_list_view_item, parent, false);

        final TextView textView = rowView.findViewById(R.id.listTextView);
        final Job job = lists.get(position);
        final String displayString = job.toTableString();

        textView.setText(displayString);
        textView.setMinHeight(800);
        textView.setHeight(800);

        final CheckBox checkBox = rowView.findViewById(R.id.checkBox);

        checkBox.setText("Score: " + jobSettings.computeJobScore(job));
        checkBox.setTextSize(20);
        checkBox.setChecked(selectedItems[position]);

        checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {

            if (isChecked && getNumberOfCheckboxesChecked() >= 2) {
                checkBox.setChecked(false);
                selectedItems[position] = false;
                Toast.makeText(
                        getContext(),
                        "Please select maximum two jobs only to compare",
                        Toast.LENGTH_SHORT).show();
            }
            selectedItems[position] = checkBox.isChecked();

            System.out.println();
            System.out.println("Checked count: " + getNumberOfCheckboxesChecked());
            System.out.println("is Checked?: " + isChecked);
            System.out.println();
        });
        return rowView;
    }
}

