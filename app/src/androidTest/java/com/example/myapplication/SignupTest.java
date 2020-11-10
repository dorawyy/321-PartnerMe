package com.example.myapplication;

import androidx.test.espresso.Espresso;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class SignupTest {
    @Rule
    public ActivityTestRule<SignUpActivity> activityRule
            = new ActivityTestRule<>(SignUpActivity.class);

    @Test
    public void signUpTest() {
        String name = "Joshua";
        String language = "English, Korean";
        String className = "CPEN 321";
        String hobby = "Coding, Game, Guitar";
        String toast = "Sign up not complete, please make sure fields are not empty";

        onView(withId(R.id.signupButton))
                .perform(click());
        onView(withText(toast)).inRoot(withDecorView(not(activityRule.getActivity().getWindow().getDecorView())))
                .check(matches(isDisplayed()));

        onView(withId(R.id.signup_nameField))
                .perform(typeText(name), closeSoftKeyboard());
        onView(withId(R.id.signupButton))
                .perform(click());
        onView(withText(toast)).inRoot(withDecorView(not(activityRule.getActivity().getWindow().getDecorView())))
                .check(matches(isDisplayed()));

        onView(withId(R.id.signup_ClassField))
                .perform(typeText(className), closeSoftKeyboard());
        onView(withId(R.id.signupButton))
                .perform(click());
        onView(withText(toast)).inRoot(withDecorView(not(activityRule.getActivity().getWindow().getDecorView())))
                .check(matches(isDisplayed()));

        onView(withId(R.id.signup_Language))
                .perform(typeText(language), closeSoftKeyboard());
        onView(withId(R.id.signupButton))
                .perform(click());
        onView(withText(toast)).inRoot(withDecorView(not(activityRule.getActivity().getWindow().getDecorView())))
                .check(matches(isDisplayed()));

        onView(withId(R.id.signup_Hobbies))
                .perform(typeText(hobby), closeSoftKeyboard());

        onView(withId(R.id.signupButton))
                .perform(click())
                .check(matches(isDisplayed()));

    }
}
