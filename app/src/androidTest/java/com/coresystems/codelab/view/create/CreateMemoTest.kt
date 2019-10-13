package com.coresystems.codelab.view.create

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.coresystems.codelab.R
import com.coresystems.codelab.util.SAVE_CREATED_MEMO_DISCOVERY
import com.coresystems.codelab.util.SharedPreferencesManager
import com.coresystems.codelab.util.TARGET_VIE_NAME
import com.coresystems.codelab.util.checkIfViewWithClassNameDoesNotExist
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CreateMemoTest {

    @Test
    fun normalFlowOnFirstStart() {
        //arrange
        featuresNotDiscovered()
        ActivityScenario.launch(CreateMemo::class.java)

        onView(ViewMatchers.withClassName(CoreMatchers.containsString(TARGET_VIE_NAME))).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(ViewMatchers.withId(R.id.action_save)).perform(ViewActions.click())

        //All features where displayed check the values in share preferences
        MatcherAssert.assertThat(SharedPreferencesManager.instance.getValue(SAVE_CREATED_MEMO_DISCOVERY.toString()), CoreMatchers.`is`(true))

        //Check if new activity opened
        onView(ViewMatchers.withId(R.id.action_save)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }


    @Test
    fun secondRunNoDiscoveryFeatureShown() {
        //arrange
        featureDiscovered()
        ActivityScenario.launch(CreateMemo::class.java)

        //assert
        MatcherAssert.assertThat(true, CoreMatchers.`is`(TARGET_VIE_NAME.checkIfViewWithClassNameDoesNotExist()))
    }


    @Test
    fun clickOutsideOfTheFeatures() {
        //arrange
        featuresNotDiscovered()
        val scenario = ActivityScenario.launch(CreateMemo::class.java)

        //Click outsideOfTheDiscoveryFeature
        onView(ViewMatchers.withId(R.id.action_save)).perform(ViewActions.click())

        MatcherAssert.assertThat(true, CoreMatchers.`is`(TARGET_VIE_NAME.checkIfViewWithClassNameDoesNotExist()))

        //Recreate activity
        scenario.recreate()

        //assert
        MatcherAssert.assertThat(true, CoreMatchers.`is`(TARGET_VIE_NAME.checkIfViewWithClassNameDoesNotExist()))
    }

    private fun featuresNotDiscovered() {
        SharedPreferencesManager.instance.clear()
    }

    private fun featureDiscovered() {
        SharedPreferencesManager.instance.setValue(SAVE_CREATED_MEMO_DISCOVERY.toString(), true)
    }
}