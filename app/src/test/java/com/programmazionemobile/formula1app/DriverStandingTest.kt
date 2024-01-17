package com.programmazionemobile.formula1app

import com.programmazionemobile.formula1app.data.driverStandingsData.Constructor
import com.programmazionemobile.formula1app.data.driverStandingsData.Driver
import com.programmazionemobile.formula1app.data.driverStandingsData.DriverStanding
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4


@RunWith(JUnit4::class)
class DriverStandingTest {

    lateinit var classificaPiloti: ArrayList<DriverStanding>
    lateinit var driver: Driver
    lateinit var constructor: Constructor
    lateinit var pilota: DriverStanding

    @Before
    fun beforeTest(){
        classificaPiloti = ArrayList<DriverStanding>()

        driver = Driver(
            driverId = "max_verstappen",
            permanentNumber = "33",
            code = "VER",
            url = "http://en.wikipedia.org/wiki/Max_Verstappen",
            givenName = "Max",
            familyName = "Verstappen",
            dateOfBirth = "1997-09-30",
            nationality = "Dutch")

        constructor = Constructor(constructorId = "red_bull",
            url = "http://en.wikipedia.org/wiki/Red_Bull_Racing",
            name = "Red Bull",
            nationality = "Austrian")

        pilota = DriverStanding(
            position = "1",
            positionText = "1", points = "575",
            wins = "19", driver = driver,
            constructors = listOf(constructor)
        )
    }


    @Test
    fun addDriver(){
        classificaPiloti.add(pilota)

        assertEquals(classificaPiloti.first().position, "1")
        assertEquals(classificaPiloti.first().positionText, "1")
        assertEquals(classificaPiloti.first().points, "575")
        assertEquals(classificaPiloti.first().wins, "19")
        assertEquals(classificaPiloti.first().driver, driver)
        assertEquals(classificaPiloti.first().constructors.first(), constructor)

    }

}