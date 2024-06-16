package edu.gatech.seclass.jobcompare6300;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class BaseActivity extends Activity {

    final static String NAME = "BaseSettings";
    final static String displayBackButton = "displayBackButton";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void toggleBackButtonDisplay() {
        boolean current = !this.showBackButton();
        SharedPreferences.Editor editor = getSharedPreferences(BaseActivity.NAME, MODE_PRIVATE).edit();
        editor.putBoolean(BaseActivity.displayBackButton, current);
        editor.apply();
    }

    public boolean showBackButton() {
        SharedPreferences prefs = getSharedPreferences(DeveloperToolsActivity.NAME, MODE_PRIVATE);
        boolean displayBackButton = prefs.getBoolean(BaseActivity.displayBackButton, true);
        return displayBackButton;
    }

    public void initBackButton(Button backButton) {
        if (this.showBackButton()) {
            backButton.setVisibility(View.VISIBLE);
        } else {
            backButton.setVisibility(View.INVISIBLE);
        }
        backButton.setOnClickListener(v2 -> {
            super.onBackPressed();
        });
    }
}