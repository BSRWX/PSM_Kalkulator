package com.example.kalkulator

import org.mariuszgromada.math.mxparser.Expression
import org.mariuszgromada.math.mxparser.License
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale
import kotlin.math.abs

class CalculatorEngine {

    companion object {
        init {
            License.iConfirmNonCommercialUse("Kacper Smoleń")
        }
    }

    fun Evaluate(rawInput: String): String
    {
        val trimmed = rawInput.trim();
        if (trimmed.isEmpty()) return ""

        var balance = 0
        for (character in trimmed)
        {
            if (character == '(') balance++
            else if (character == ')') balance--

            if (balance < 0) return ""
        }

        if (balance != 0) return ""

        val lastChar = trimmed.last()
        if (lastChar in listOf('+', '-', 'x', '÷', '*', '/', '^', '.', ',')) {
            return ""
        }

        val mapped = trimmed
            .replace(',', '.')
            .replace('x', '*')
            .replace('÷', '/')
            .replace("³√(", "root(3,")
            .replace("√(", "sqrt(")
            .replace("log(", "log10(")
            .replace("rand", "rUni(0,1)")

        val value = Expression(mapped).calculate()
        if (value.isNaN() || value.isInfinite()) {
            return "ERROR"
        }

        return formatNumber(value)
    }

    private fun formatNumber(value: Double): String {
        val cleanZero = if (abs(value) < 1e-12) 0.0 else value
        val symbols = DecimalFormatSymbols(Locale.US)

        if (cleanZero != 0.0 && (abs(cleanZero) >= 1e12 || abs(cleanZero) < 1e-7)) {
            return DecimalFormat("0.######E0", symbols).format(cleanZero)
        }
        if (cleanZero % 1.0 == 0.0) {
            return cleanZero.toLong().toString()
        }
        return DecimalFormat("0.##########", symbols).format(cleanZero)
    }

}