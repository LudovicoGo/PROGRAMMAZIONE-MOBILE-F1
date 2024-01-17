package com.programmazionemobile.formula1app
import com.programmazionemobile.formula1app.data.qualifyingData.Constructor
import com.programmazionemobile.formula1app.data.qualifyingData.Driver
import com.programmazionemobile.formula1app.data.qualifyingData.QualifyingResult
import org.junit.After
import org.junit.Test
import org.junit.Assert.assertEquals
import org.junit.Before
class QualifyingResultTest {

    private lateinit var qualifyingResult: QualifyingResult

    @Before
    fun setup() {
        val constructor = Constructor(
            constructorId = "red_bull",
            name = "Red Bull",
            nationality = "Austrian",
            url = "http://en.wikipedia.org/wiki/Red_Bull_Racing"
        )

        val driver = Driver(
            code = "VER",
            dateOfBirth = "1997-09-30",
            driverId = "max_verstappen",
            familyName = "Verstappen",
            givenName = "Max",
            nationality = "Dutch",
            permanentNumber = "33",
            url = "http://en.wikipedia.org/wiki/Max_Verstappen"
        )

        qualifyingResult = QualifyingResult(
            constructor = constructor,
            driver = driver,
            number = "1",
            position = "1",
            q1 = "1:29.428",
            q2 = "1:27.702",
            q3 = "1:26.720"
        )
    }

   @Test
    fun createQualifyingResult() {
        assertEquals("red_bull", qualifyingResult.constructor.constructorId)
        assertEquals("Red Bull", qualifyingResult.constructor.name)
        assertEquals("Austrian", qualifyingResult.constructor.nationality)
        assertEquals("http://en.wikipedia.org/wiki/Red_Bull_Racing",
            qualifyingResult.constructor.url)

        assertEquals("VER", qualifyingResult.driver.code)
        assertEquals("1997-09-30", qualifyingResult.driver.dateOfBirth)
        assertEquals("max_verstappen", qualifyingResult.driver.driverId)
        assertEquals("Verstappen", qualifyingResult.driver.familyName)
        assertEquals("Max", qualifyingResult.driver.givenName)
        assertEquals("Dutch", qualifyingResult.driver.nationality)
        assertEquals("33", qualifyingResult.driver.permanentNumber)
        assertEquals("http://en.wikipedia.org/wiki/Max_Verstappen",
            qualifyingResult.driver.url)

        assertEquals("1", qualifyingResult.number)
        assertEquals("1", qualifyingResult.position)
        assertEquals("1:29.428", qualifyingResult.q1)
        assertEquals("1:27.702", qualifyingResult.q2)
        assertEquals("1:26.720", qualifyingResult.q3)
    }
}

