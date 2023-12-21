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

    @Before
    fun beforeTest(){
        classificaPiloti = ArrayList<DriverStanding>()
    }


    @Test
    fun addDriver(){

        val driver = Driver(
            driverId = "max_verstappen",
            permanentNumber = "33",
            code = "VER",
            url = "http://en.wikipedia.org/wiki/Max_Verstappen",
            givenName = "Max",
            familyName = "Verstappen",
            dateOfBirth = "1997-09-30",
            nationality = "Dutch")

        val constructor = Constructor(constructorId = "red_bull",
            url = "http://en.wikipedia.org/wiki/Red_Bull_Racing",
            name = "Red Bull",
            nationality = "Austrian")

        val pilota = DriverStanding(
            position = "1",
            positionText = "1", points = "575",
            wins = "19", driver = driver,
            constructors = listOf(constructor)
        )

        classificaPiloti.add(pilota)


        // Prendo il primo(ed unico) elemento dalla lista e vedo se l'oggetto è stato correttamente creato,
        // verificando il valore dei singoli attributi
        assertEquals(classificaPiloti.first().position, "1")
        assertEquals(classificaPiloti.first().positionText, "1")
        assertEquals(classificaPiloti.first().points, "575")
        assertEquals(classificaPiloti.first().wins, "19")
        assertEquals(classificaPiloti.first().driver, driver)
        assertEquals(classificaPiloti.first().constructors.first(), constructor)

    }

}