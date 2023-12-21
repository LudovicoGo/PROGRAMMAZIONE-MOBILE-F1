package com.programmazionemobile.formula1app

import com.programmazionemobile.formula1app.data.calendarData.Circuit
import com.programmazionemobile.formula1app.data.calendarData.FirstPractice
import com.programmazionemobile.formula1app.data.calendarData.Location
import com.programmazionemobile.formula1app.data.calendarData.Qualifying
import com.programmazionemobile.formula1app.data.calendarData.Race
import com.programmazionemobile.formula1app.data.calendarData.SecondPractice
import com.programmazionemobile.formula1app.data.calendarData.ThirdPractice
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4


@RunWith(JUnit4::class)
class RaceTest {

    lateinit var calendarioGare: ArrayList<Race>

    @Before
    fun beforeTest(){
        calendarioGare = ArrayList<Race>()
    }


    @Test
    fun addRaceCalendar(){

        val location = Location( lat = "26.0325",
            long = "50.5106",
            locality = "Sakhir", country = "Bahrain")

        val circuito = Circuit( circuitId = "bahrain",
            url = "http://en.wikipedia.org/wiki/Bahrain_International_Circuit",
            circuitName = "Bahrain International Circuit", location = location)

        val firstPractice = FirstPractice(date = "2023-03-03", time = "11:30:00Z")
        val secondPractice = SecondPractice(date = "2023-03-03", time = "15:00:00Z")
        val thirdPractice = ThirdPractice(date = "2023-03-04", time = "11:30:00Z")

        val qualifying = Qualifying(date = "2023-03-04", time = "15:00:00Z")

        val nuovaGara = Race(season = "2023", round = "1", url = "https://en.wikipedia.org/wiki/2023_Bahrain_Grand_Prix",
            raceName = "Bahrain Grand Prix", circuit = circuito, date = "2023-03-05", time = "15:00:00Z", firstPractice = firstPractice,
            secondPractice = secondPractice , thirdPractice = thirdPractice, qualifying = qualifying, sprint = null)

        calendarioGare.add(nuovaGara)


        // Prendo il primo(ed unico) elemento dalla lista e vedo se l'oggetto è stato correttamente creato,
        // verificando il valore dei singoli attributi
        assertEquals(calendarioGare.first().season , "2023")
        assertEquals(calendarioGare.first().round , "1")
        assertEquals(calendarioGare.first().url , "https://en.wikipedia.org/wiki/2023_Bahrain_Grand_Prix")
        assertEquals(calendarioGare.first().raceName , "Bahrain Grand Prix")
        assertEquals(calendarioGare.first().circuit , circuito)
        assertEquals(calendarioGare.first().date , "2023-03-05")
        assertEquals(calendarioGare.first().time , "15:00:00Z")
        assertEquals(calendarioGare.first().firstPractice ,firstPractice)
        assertEquals(calendarioGare.first().secondPractice , secondPractice)
        assertEquals(calendarioGare.first().thirdPractice , thirdPractice)
        assertEquals(calendarioGare.first().qualifying , qualifying)
        assertEquals(calendarioGare.first().sprint , null)
    }

}