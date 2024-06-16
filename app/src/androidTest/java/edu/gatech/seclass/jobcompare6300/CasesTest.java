package edu.gatech.seclass.jobcompare6300;

import androidx.test.espresso.Espresso;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;



import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;


@RunWith(AndroidJUnit4.class)
public class CasesTest {

    @Rule
    public ActivityScenarioRule<MainActivity> tActivityRule = new ActivityScenarioRule<>(
        MainActivity.class);
    @Test
    public void TestCase1() {
        onView(withId(R.id.currentJobButton)).perform(click());
        onView(withId(R.id.editJob)).perform(click());
        onView(withId(R.id.inputTitle)).perform(clearText(), replaceText("Software Engineer III"));
        onView(withId(R.id.inputCompany)).perform(clearText(), replaceText("Google"));
        onView(withId(R.id.inputCity)).perform(clearText(), replaceText("Mountain View"));
        onView(withId(R.id.inputState)).perform(clearText(), replaceText("CA"));
        onView(withId(R.id.inputCostOfLiving)).perform(clearText(), replaceText("200"));
        onView(withId(R.id.inputSalary)).perform(clearText(), replaceText("200000"));
        onView(withId(R.id.inputBonus)).perform(clearText(), replaceText("30000"));
        onView(withId(R.id.inputBenefits)).perform(clearText(), replaceText("50"));
        onView(withId(R.id.inputStipend)).perform(clearText(), replaceText("100000"));
        onView(withId(R.id.inputFund)).perform(clearText(), replaceText("10000"));
        onView(withId(R.id.saveJob)).perform(click());
        onView(withId(R.id.currentJobButton)).perform(click());
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.inputTitle)).check(matches(withText("Software Engineer III")));
        onView(withId(R.id.inputCompany)).check(matches(withText("Google")));
        onView(withId(R.id.inputCity)).check(matches(withText("Mountain View")));
        onView(withId(R.id.inputState)).check(matches(withText("CA")));
        onView(withId(R.id.inputCostOfLiving)).check(matches(withText("200")));
        onView(withId(R.id.inputSalary)).check(matches(withText("200000.0")));
        onView(withId(R.id.inputBonus)).check(matches(withText("30000.0")));
        onView(withId(R.id.inputBenefits)).check(matches(withText("50")));
        onView(withId(R.id.inputStipend)).check(matches(withText("100000.0")));
        onView(withId(R.id.inputFund)).check(matches(withText("10000")));
    }

    @Test
    public void TestCase2() {
        onView(withId(R.id.currentJobButton)).perform(click());
        onView(withId(R.id.editJob)).perform(click());
        onView(withId(R.id.inputTitle)).perform(clearText(), replaceText("Data Scientist"));
        onView(withId(R.id.inputCompany)).perform(clearText(), replaceText("META"));
        onView(withId(R.id.inputCity)).perform(clearText(), replaceText("Sunnyvale"));
        onView(withId(R.id.inputState)).perform(clearText(), replaceText("CA"));
        onView(withId(R.id.inputCostOfLiving)).perform(clearText(), replaceText("100"));
        onView(withId(R.id.inputSalary)).perform(clearText(), replaceText("210000"));
        onView(withId(R.id.inputBonus)).perform(clearText(), replaceText("25000"));
        onView(withId(R.id.inputBenefits)).perform(clearText(), replaceText("70"));
        onView(withId(R.id.inputStipend)).perform(clearText(), replaceText("20000"));
        onView(withId(R.id.inputFund)).perform(clearText(), replaceText("10000"));
        onView(withId(R.id.saveJob)).perform(click());
        onView(withId(R.id.currentJobButton)).perform(click());
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.inputTitle)).check(matches(withText("Data Scientist")));
        onView(withId(R.id.inputCompany)).check(matches(withText("META")));
        onView(withId(R.id.inputCity)).check(matches(withText("Sunnyvale")));
        onView(withId(R.id.inputState)).check(matches(withText("CA")));
        onView(withId(R.id.inputCostOfLiving)).check(matches(withText("100")));
        onView(withId(R.id.inputSalary)).check(matches(withText("210000.0")));
        onView(withId(R.id.inputBonus)).check(matches(withText("25000.0")));
        onView(withId(R.id.inputBenefits)).check(matches(withText("70")));
        onView(withId(R.id.inputStipend)).check(matches(withText("20000.0")));
        onView(withId(R.id.inputFund)).check(matches(withText("10000")));
    }

    @Test
    public void TestCase3() {
        onView(withId(R.id.currentJobButton)).perform(click());
        onView(withId(R.id.editJob)).perform(click());
        onView(withId(R.id.deleteJob)).perform(click());
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.currentJobButton)).perform(click());
        onView(withId(R.id.inputTitle)).check(matches(withText("")));
        onView(withId(R.id.inputCompany)).check(matches(withText("")));
        onView(withId(R.id.inputCity)).check(matches(withText("")));
        onView(withId(R.id.inputState)).check(matches(withText("")));
        onView(withId(R.id.inputCostOfLiving)).check(matches(withText("")));
        onView(withId(R.id.inputSalary)).check(matches(withText("")));
        onView(withId(R.id.inputBonus)).check(matches(withText("")));
        onView(withId(R.id.inputBenefits)).check(matches(withText("")));
        onView(withId(R.id.inputStipend)).check(matches(withText("")));
        onView(withId(R.id.inputFund)).check(matches(withText("")));
    }

    @Test
    public void TestCase7() {
        onView(withId(R.id.jobSettingsButton)).perform(click());
        onView(withId(R.id.editSettingsButton)).perform(click());
        onView(withId(R.id.editSalaryWeight)).perform(clearText(), replaceText("2"));
        onView(withId(R.id.editBonusWeight)).perform(clearText(), replaceText("1"));
        onView(withId(R.id.editBenefitsWeight)).perform(clearText(), replaceText("2"));
        onView(withId(R.id.editRelocationWeight)).perform(clearText(), replaceText("1"));
        onView(withId(R.id.editFundWeight)).perform(clearText(), replaceText("2"));
        onView(withId(R.id.saveSettingsButton)).perform(click());
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.jobSettingsButton)).perform(click());
        onView(withId(R.id.editSalaryWeight)).check(matches(withText("2")));
        onView(withId(R.id.editBonusWeight)).check(matches(withText("1")));
        onView(withId(R.id.editBenefitsWeight)).check(matches(withText("2")));
        onView(withId(R.id.editRelocationWeight)).check(matches(withText("1")));
        onView(withId(R.id.editFundWeight)).check(matches(withText("2")));
    }
    public void TestCase8() {
        onView(withId(R.id.jobSettingsButton)).perform(click());
        onView(withId(R.id.editSettingsButton)).perform(click());
        onView(withId(R.id.editSalaryWeight)).perform(clearText(), replaceText("2"));
        onView(withId(R.id.editBonusWeight)).perform(clearText(), replaceText("1"));
        onView(withId(R.id.editBenefitsWeight)).perform(clearText(), replaceText("2"));
        onView(withId(R.id.editRelocationWeight)).perform(clearText(), replaceText("1"));
        onView(withId(R.id.editFundWeight)).perform(clearText(), replaceText("2"));
        onView(withId(R.id.saveSettingsButton)).perform(click());
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.jobSettingsButton)).perform(click());
        onView(withId(R.id.resetSettingsButton)).perform(click());
        onView(withId(R.id.jobSettingsButton)).perform(click());
        onView(withId(R.id.editSalaryWeight)).check(matches(withText("1")));
        onView(withId(R.id.editBonusWeight)).check(matches(withText("1")));
        onView(withId(R.id.editBenefitsWeight)).check(matches(withText("1")));
        onView(withId(R.id.editRelocationWeight)).check(matches(withText("1")));
        onView(withId(R.id.editFundWeight)).check(matches(withText("1")));
    }
    /*@Test
    public void TestCase4() {
        onView(withId(R.id.jobOffersButton)).perform(click());
        onView(withId(R.id.addJobOffer)).perform(click());
        onView(withId(R.id.inputTitle)).perform(clearText(), replaceText("Software Engineer III"));
        onView(withId(R.id.inputCompany)).perform(clearText(), replaceText("Google"));
        onView(withId(R.id.inputCity)).perform(clearText(), replaceText("Mountain View"));
        onView(withId(R.id.inputState)).perform(clearText(), replaceText("CA"));
        onView(withId(R.id.inputCostOfLiving)).perform(clearText(), replaceText("200"));
        onView(withId(R.id.inputSalary)).perform(clearText(), replaceText("200000"));
        onView(withId(R.id.inputBonus)).perform(clearText(), replaceText("30000"));
        onView(withId(R.id.inputBenefits)).perform(clearText(), replaceText("50"));
        onView(withId(R.id.inputStipend)).perform(clearText(), replaceText("100000"));
        onView(withId(R.id.inputFund)).perform(clearText(), replaceText("10000"));
        onView(withId(R.id.saveJob)).perform(click());
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.currentJobButton)).perform(click());

    }*/

}
