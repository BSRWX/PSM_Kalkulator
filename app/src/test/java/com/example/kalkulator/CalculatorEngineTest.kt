package com.example.kalkulator

import org.junit.Assert.assertEquals
import org.junit.Test

class CalculatorEngineTest {

    private val engine = CalculatorEngine()

    @Test
    fun `1 kolejnosc dzialan`() {
        assertEquals("6", engine.Evaluate("2+2*2"))
    }

    @Test
    fun `2 nawiasy`() {
        assertEquals("8", engine.Evaluate("(2+2)*2"))
    }

    @Test
    fun `3 dzielenie przez zero`() {
        assertEquals("ERROR", engine.Evaluate("1/0"))
    }

    @Test
    fun `4 wyrazenie niedokonczone`() {
        assertEquals("", engine.Evaluate("2+"))
    }

    @Test
    fun `5 nawias niezamkniety`() {
        assertEquals("", engine.Evaluate("((2+2)"))
    }

    @Test
    fun `6 puste pole`() {
        assertEquals("", engine.Evaluate(""))
    }

    @Test
    fun `7 dziedzina funkcji`() {
        assertEquals("ERROR", engine.Evaluate("sqrt(-1)"))
        assertEquals("ERROR", engine.Evaluate("√(-1)"))
    }

    @Test
    fun `8 duze liczby`() {
        assertEquals("1.524158E18", engine.Evaluate("1234567890*1234567890"))
    }


    @Test
    fun `separator dziesietny kropka i przecinek`() {
        assertEquals("3.5", engine.Evaluate("2.5+1"))
        assertEquals("3.5", engine.Evaluate("2,5+1"))
    }

    @Test
    fun `funkcje z klawiatury naukowej`() {
        assertEquals("2", engine.Evaluate("√(4)"))
        assertEquals("0", engine.Evaluate("sin(0)"))
        assertEquals("2", engine.Evaluate("log(100)"))
        assertEquals("9", engine.Evaluate("3^2"))
        assertEquals("0.5", engine.Evaluate("2^(-1)"))
    }
}