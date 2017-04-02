package com.lab.gruszczynski.laboratory;

import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.action.ViewActions.*;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by maciej on 02.04.17.
 */

@RunWith(AndroidJUnit4.class)
public class CountBMITestUI {


    private String massToBeTyped;
    private String heightToBeTyped;
    private String expectedResult;

    @Rule
    public ActivityTestRule<BMIActivity> mActivityRule = new ActivityTestRule<>(
            BMIActivity.class);

    @Before
    public void initValidStrings() {
        massToBeTyped = "63.0";
        heightToBeTyped = "1.74";
        expectedResult = "20.808561";
    }

    @Test
    public void enterDataAndCountBMI() {

        Espresso.onView(withId(R.id.editText_mass)).perform(clearText())
                .perform(typeText(massToBeTyped), closeSoftKeyboard());
        Espresso.onView(withId(R.id.editText_height)).perform(clearText())
                .perform(typeText(heightToBeTyped), closeSoftKeyboard());
        Espresso.onView(withId(R.id.button_countBMI)).perform(click());


        Espresso.onView(withId(R.id.textView_result))
                .check(matches(withText(expectedResult)));
    }

}

