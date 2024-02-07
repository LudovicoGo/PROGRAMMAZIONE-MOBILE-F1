package com.programmazionemobile.formula1app

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignInStatusCodes
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Tasks
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class InstrumentedTest {

    @get:Rule
    var activityRule: ActivityScenarioRule<MainActivity> = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setUp() {
    }

    @Test
    fun  testSendMessage() {

        Espresso.onView(withId(R.id.raceInfoBackArrow)).perform(click())

        Espresso.onView(withId(R.id.calendarFragment)).perform(click())

        Espresso.onView(withId(R.id.prossimoEvento)).perform(click())

        Espresso.onView(withId(R.id.liveChatCard)).perform(click())

        Espresso.onView(withId(R.id.editText)).perform(typeText("Prova prova"))
        Espresso.closeSoftKeyboard()

        Espresso.onView(withId(R.id.send)).perform(click())

    }
}
