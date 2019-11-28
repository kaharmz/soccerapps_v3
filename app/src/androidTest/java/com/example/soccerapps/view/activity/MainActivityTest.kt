package com.example.soccerapps.view.activity

import android.widget.EditText
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import com.example.soccerapps.R
import com.example.soccerapps.view.util.EspressoIdlingResource
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class MainActivityTest {

    @Rule
    @JvmField
    var activityRule = ActivityTestRule(MainActivity::class.java)

    @Before
    fun setUp() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.idlingResource)
    }

    @Test
    fun testAppBehaviour() {

        onView(withId(R.id.main_container)).check(matches(isDisplayed()))

        onView(withId(R.id.homes)).check(matches(isDisplayed()))

        onView(withId(R.id.favorite)).check(matches(isDisplayed()))

        onView(withId(R.id.homes)).perform(click())

        onView(withId(R.id.league_recycler)).check(matches(isDisplayed()))

        onView(withText("English Premier League")).perform(click())

        onView(withId(R.id.detail_list)).check(matches(isDisplayed()))

        onView(withId(R.id.detail_list)).perform(click())

        onView(withId(R.id.search)).check(matches(isDisplayed()))

        onView(withId(R.id.search)).perform(click())

        onView(isAssignableFrom(EditText::class.java)).perform(
            typeText("Everton"),
            pressImeActionButton()
        )

        onView(withId(R.id.recycler_search)).check(matches(isDisplayed()))

        onView(withId(R.id.search_list)).check(matches(isDisplayed()))

        onView(withId(R.id.search_list)).perform(click())

        onView(isAssignableFrom(EditText::class.java)).perform(
            typeText("No Club"),
            pressImeActionButton()
        )

        onView(withId(R.id.error_message)).check(matches(isDisplayed()))

        pressBack()
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.idlingResource)
    }
}
