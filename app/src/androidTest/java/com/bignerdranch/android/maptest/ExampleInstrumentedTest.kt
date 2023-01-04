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

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Rule
import kotlin.concurrent.thread

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

        // klicka på marker
        onView(withText("Sumo Kitchen")).perform(click())
    }

    @Test
    fun testProfileScreenIsDisplayed() {
        onView(withId(R.id.profile)).perform(click())
        Thread.sleep(1000)
        onView(withId(R.id.profile)).check(matches(isDisplayed()))
        Thread.sleep(1000)
        // kollar om GOALS finns med på profil sida etc
        // eller vad man vill testa profil sidan
        onView(withText("GOALS")).check(matches(isDisplayed()))


    }

    @Test
    fun testSetMark() {
        onView(withId(R.id.list)).perform(click())
        Thread.sleep(1000)
        onView(withId(R.id.list)).check(matches(isDisplayed()))
        Thread.sleep(1000)
        onView(withText("Sumo Kitchen")).perform(click())
        Thread.sleep(1000)

        // efter man klickat Restaurant 2 så försöker jag klicka set mark men
        // om jag använder onView(withText("SET MARK")).perform(click()) får
        // jag 4 st set marks istället för 1, behöver på ngt sätt få en som
        // ligger i specifik restaurant

        /*val restaurant2View: ViewInteraction = onView(withText("Restaurant 2"))
        var id = 0
        val viewAction: (View) -> Unit = { view ->
            id = view.id
        }
        //restaurant2View.perform(viewAction)
        onView(isDescendantOfA(withId(id))).perform(click())*/
    }

}