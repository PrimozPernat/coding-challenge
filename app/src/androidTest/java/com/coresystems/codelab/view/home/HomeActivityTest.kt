package com.coresystems.codelab.view.home

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.coresystems.codelab.R
import com.coresystems.codelab.util.ACTION_SHOW_ALL_DISCOVERY
import com.coresystems.codelab.util.FAB_CREATE_MEMOS_DISCOVERY
import com.coresystems.codelab.util.SharedPreferencesManager
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.containsString
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HomeActivityTest {

    val TARGET_VIE_NAME = "TapTarget"

    @Test
    fun normalFlowOnFirstStart() {
        //arrange
        featuresNotDiscovered()
        ActivityScenario.launch(Home::class.java)

        onView(withClassName(containsString(TARGET_VIE_NAME))).check(matches(isDisplayed()))
        onView(withId(R.id.action_show_all)).perform(click())

        //Select the FAB button
        onView(withClassName(containsString(TARGET_VIE_NAME))).check(matches(isDisplayed()))
        onView(withId(R.id.fab)).perform(click())

        //All features where displayed check the values in share preferences
        assertThat(SharedPreferencesManager.instance.getValue(ACTION_SHOW_ALL_DISCOVERY.toString()), `is`(true))
        assertThat(SharedPreferencesManager.instance.getValue(FAB_CREATE_MEMOS_DISCOVERY.toString()), `is`(true))

        //Check if new activity opened
        onView(withId(R.id.action_save)).check(matches(isDisplayed()))
    }


    @Test
    fun recreateActivityMidwayDiscoveryFlow() {
        //arrange
        featuresNotDiscovered()
        val scenario = ActivityScenario.launch(Home::class.java)

        onView(withClassName(containsString(TARGET_VIE_NAME))).check(matches(isDisplayed()))
        onView(withId(R.id.action_show_all)).perform(click())

        //Recreate activity an check if only one is displayed
        scenario.recreate()
        assertThat(SharedPreferencesManager.instance.getValue(ACTION_SHOW_ALL_DISCOVERY.toString()), `is`(true))
        assertThat(SharedPreferencesManager.instance.getValue(FAB_CREATE_MEMOS_DISCOVERY.toString()), `is`(false))


        //Select the FAB button
        onView(withClassName(containsString(TARGET_VIE_NAME))).check(matches(isDisplayed()))
        onView(withId(R.id.fab)).perform(click())

        //All features where displayed check the values in share preferences
        assertThat(SharedPreferencesManager.instance.getValue(ACTION_SHOW_ALL_DISCOVERY.toString()), `is`(true))
        assertThat(SharedPreferencesManager.instance.getValue(FAB_CREATE_MEMOS_DISCOVERY.toString()), `is`(true))

        //Check if new activity opened
        onView(withId(R.id.action_save)).check(matches(isDisplayed()))
    }

    @Test
    fun secondRunNoDiscoveryFeaturesShown() {
        //arrange
        allFeaturesDiscovered()
        ActivityScenario.launch(Home::class.java)


        //assert
        assertThat(true, `is`(checkIfViewWithClassNameDoesNotExist(TARGET_VIE_NAME)))
    }

    private fun featuresNotDiscovered() {
        SharedPreferencesManager.instance.clear()
    }

    private fun allFeaturesDiscovered() {
        SharedPreferencesManager.instance.setValue(ACTION_SHOW_ALL_DISCOVERY.toString(), true)
        SharedPreferencesManager.instance.setValue(FAB_CREATE_MEMOS_DISCOVERY.toString(), true)
    }

    private fun checkIfViewWithClassNameDoesNotExist(className: String): Boolean {
        return try {
            //Check if a view with the TapTarget view is displayed
            onView(withClassName(containsString(className))).check(matches(isDisplayed()))
            false
        } catch (e: NoMatchingViewException) {
            true
        }
    }
}