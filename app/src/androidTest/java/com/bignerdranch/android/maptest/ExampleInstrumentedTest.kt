package com.bignerdranch.android.maptest

import android.view.View
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onData
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule

import kotlinx.coroutines.MainScope


import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Rule


/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MapsActivity::class.java)

    @Test
    fun testRestaurantClick() {
        // onView(withId(R.id)).perform(click())
        // onView(withId(R.layout.fragment_map)).perform(click())
        // onView(withText("Restaurant2")).perform(click())
        //onView(withId(R.id.map)).check(matches(isDisplayed()))

        onView(withText("Sumo Kitchen")).perform(click())

    }

    @Test
    fun testProfileScreenIsDisplayed() {
        Thread.sleep(500)
        onView(withId(R.id.profile)).perform(click())
        Thread.sleep(500)
        onView(withId(R.id.profile)).check(matches(isDisplayed()))
        Thread.sleep(500)

        // gör vad man vill med profile, just nu har jag en tom fragment_profile i denna branch
        onView(withText("Fragment_profile")).check(matches(isDisplayed()))
    }

    @Test
    fun testSetMark() {
        onView(withId(R.id.list)).perform(click())
        Thread.sleep(500)
        onView(withId(R.id.list)).check(matches(isDisplayed()))
        Thread.sleep(500)
        onView(withText("Restaurant 2")).perform(click())
        Thread.sleep(500)


        // materialbutton ID = 2131231112, problemet här är alla 4 set marker har likadana ID
        // vid klick
        onView(withId(2131231112)).perform(click())

    }

    @Test
    fun testVisitedButton() {
        onView(withId(R.id.list)).perform(click())
        onView(withId(R.id.list)).check(matches(isDisplayed()))
    }
}