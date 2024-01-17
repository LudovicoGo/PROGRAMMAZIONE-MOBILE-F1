package com.programmazionemobile.formula1app.data.driverStandingsDataimport

import com.programmazionemobile.formula1app.data.raceResultsData.Driver
import org.junit.Before
import org.junit.Test
import org.junit.Assert.assertEquals

class DriverTest {

    private lateinit var driver: Driver

    @Before
    fun setup() {
        driver = Driver(
            code = "VER",
            dateOfBirth = "1997-09-30",
            driverId = "max_verstappen",
            familyName = "Verstappen",
            givenName = "Max",
            nationality = "Dutch",
            permanentNumber = "33",
            url = "http://en.wikipedia.org/wiki/Max_Verstappen"
        )
    }

    @Test
    fun createDriver() {
        assertEquals("VER", driver.code)
        assertEquals("1997-09-30", driver.dateOfBirth)
        assertEquals("max_verstappen", driver.driverId)
        assertEquals("Verstappen", driver.familyName)
        assertEquals("Max", driver.givenName)
        assertEquals("Dutch", driver.nationality)
        assertEquals("33", driver.permanentNumber)
        assertEquals("http://en.wikipedia.org/wiki/Max_Verstappen", driver.url)
    }
}
