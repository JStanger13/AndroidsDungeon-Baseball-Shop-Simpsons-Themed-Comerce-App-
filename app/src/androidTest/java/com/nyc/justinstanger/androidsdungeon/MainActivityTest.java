/*
package com.nyc.justinstanger.androidsdungeon;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasErrorText;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * James Davis (Impactable)
 * Created on 3/28/16.
 */
/*
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {
    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule = new ActivityTestRule<MainActivity>(MainActivity.class);

    @Test
    public void testIfGoToCartWorks(){
        onView(withId(R.id.go_to_checkout)).perform(click());
        onView(withId(R.id.checkout_recyclerview)).check(matches(isDisplayed()));
    }

    @Test
    public void testIfGoToDetailWorks() throws Exception {
        onView(withId(R.id.recyclerview)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.detail_item_image)).check(matches(isDisplayed()));
    }

    @Test
    public void testIfAddToCartWorks(){
        onView(withId(R.id.recyclerview)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.detail_add_item)).perform(click());
        onView(withId(R.id.detail_go_to_cart)).perform(click());
        onView(withId(R.id.checkout_recyclerview)).check(matches(isDisplayed()));
    }

    @Test
    public void testIfDeleteFromCartWorks(){
        onView(withId(R.id.recyclerview)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.detail_add_item)).perform(click());
        onView(withId(R.id.detail_go_to_cart)).perform(click());
        onView(withId(R.id.delete_item)).perform(click());
        onView(withId(R.id.checkout_recyclerview)).check(matches(isDisplayed()));
    }

}
*/