package com.coresystems.codelab.util

import androidx.test.espresso.Espresso
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import org.hamcrest.CoreMatchers

const val TARGET_VIE_NAME = "TapTarget"

fun String.checkIfViewWithClassNameDoesNotExist(): Boolean {
    return try {
        //Check if a view with the TapTarget view is displayed
        Espresso.onView(ViewMatchers.withClassName(CoreMatchers.containsString(this))).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        false
    } catch (e: NoMatchingViewException) {
        true
    }
}