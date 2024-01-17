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
    lateinit var location: Location
    lateinit var circuito: Circuit

    lateinit var firstPractice: FirstPractice
    lateinit var secondPractice: SecondPractice
    lateinit var thirdPractice: ThirdPractice
    lateinit var qualifying: Qualifying
    lateinit var nuovaGara: Race



    @Before
    fun beforeTest(){
        calendarioGare = ArrayList<Race>()

        location = Location( lat = "26.0325",
            long = "50.5106",
            locality = "Sakhir", country = "Bahrain")

        circuito = Circuit( circuitId = "bahrain",
            url = "http://en.wikipedia.org/wiki/Bahrain_International_Circuit",
            circuitName = "Bahrain International Circuit", location = location)

        firstPractice = FirstPractice(date = "2023-03-03", time = "11:30:00Z")
        secondPractice = SecondPractice(date = "2023-03-03", time = "15:00:00Z")
        thirdPractice = ThirdPractice(date = "2023-03-04", time = "11:30:00Z")

        qualifying = Qualifying(date = "2023-03-04", time = "15:00:00Z")

        nuovaGara = Race(season = "2023", round = "1",
            url = "https://en.wikipedia.org/wiki/2023_Bahrain_Grand_Prix",
            raceName = "Bahrain Grand Prix", circuit = circuito,
            date = "2023-03-05", time = "15:00:00Z", firstPractice = firstPractice,
            secondPractice = secondPractice , thirdPractice = thirdPractice,
            qualifying = qualifying, sprint = null)

    }


    @Test
    fun addRaceCalendar(){

        calendarioGare.add(nuovaGara)

        assertEquals(calendarioGare.first().season , "2023")
        assertEquals(calendarioGare.first().round , "1")
        assertEquals(calendarioGare.first().url ,
            "https://en.wikipedia.org/wiki/2023_Bahrain_Grand_Prix")
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