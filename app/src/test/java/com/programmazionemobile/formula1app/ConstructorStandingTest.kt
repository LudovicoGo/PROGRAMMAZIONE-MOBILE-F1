package com.programmazionemobile.formula1app

import com.programmazionemobile.formula1app.data.constructorStandingsData.ConstructorStanding
import com.programmazionemobile.formula1app.data.constructorStandingsData.Constructor
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4


@RunWith(JUnit4::class)
class ConstructorStandingTest {

    lateinit var classificaCostruttori: ArrayList<ConstructorStanding>

    @Before
    fun beforeTest(){
        classificaCostruttori = ArrayList<ConstructorStanding>()
    }


    @Test
    fun addConstructor(){

        val datiCostruttore = Constructor(
            constructorId = "red_bull",
            url = "http://en.wikipedia.org/wiki/Red_Bull_Racing",
            name = "Red Bull",
            nationality = "Austrian")

        val costruttore = ConstructorStanding(
            position = "1",
            positionText = "1", points = "860",
            wins = "21",
            constructor = datiCostruttore)

        classificaCostruttori.add(costruttore)


        // Prendo il primo(ed unico) elemento dalla lista e vedo se l'oggetto è stato correttamente creato,
        // verificando il valore dei singoli attributi
        assertEquals(classificaCostruttori.first().position, "1")
        assertEquals(classificaCostruttori.first().positionText, "1")
        assertEquals(classificaCostruttori.first().points, "860")
        assertEquals(classificaCostruttori.first().wins, "21")
        assertEquals(classificaCostruttori.first().constructor, datiCostruttore)

    }

}