package com.programmazionemobile.formula1app
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NavigationTest {

    @get:Rule
    var activityRule: ActivityScenarioRule<MainActivity> = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun testNavigationToSomeFragment() {

//        Espresso.onView(withId(R.id.calendarFragment)).perform(click())

      /*  // Naviga al fragment desiderato (supponiamo che sia un fragment con ID R.id.someFragment)
        Espresso.onView(withId(R.id.calendarFragment)).perform(click())
        // Esegui azioni o verifica che il fragment desiderato sia visualizzato correttamente
        Espresso.onView(withId(R.id.fragContainer)).check(matches(isDisplayed()))
        Espresso.onView(withId(R.id.driversStandingsFragment)).perform(click())
        // Esegui azioni o verifica che il fragment desiderato sia visualizzato correttamente
        Espresso.onView(withId(R.id.driversStandingsFragment)).check(matches(isDisplayed()))
        Espresso.onView(withId(R.id.championshipFragment)).perform(click())
        Espresso.onView(withId(R.id.calendarFragment)).perform(click())*/

    }

    // Aggiungi altri test per la navigazione ad altri fragment se necessario
}
