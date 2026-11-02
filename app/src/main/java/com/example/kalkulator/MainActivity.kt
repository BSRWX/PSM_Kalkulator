package com.example.kalkulator

import android.content.res.Configuration
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kalkulator.ui.theme.KalkulatorTheme
import com.example.kalkulator.ui.theme.rajdhaniMediumFont

class MainActivity : ComponentActivity() {

    val keyboardLayoutPortrait = listOf(
        listOf("C", "7", "4", "1", "0"),
        listOf("(", "8", "5", "2", "."),
        listOf(")", "9", "6", "3", "<<"),
        listOf("÷", "x", "-", "+", "=")
    )

    val keyboardLayoutLandscape = listOf(
        listOf("√", "sin", "asin", "ln", "|x|"),
        listOf("x²", "cos", "acos", "log", "³√"),
        listOf("xʸ", "tan", "atan", "1/x", "rand"),
        listOf("%", "x", "e", "x!", "EXP")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KalkulatorTheme {
                var formula by rememberSaveable { mutableStateOf("") }
                val configuration = LocalConfiguration.current
                val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

                val handleKeyClick: (String) -> Unit = { key ->
                    val functionsWithParens = listOf("sin", "cos", "tan", "asin", "acos", "atan", "ln", "log", "√", "³√", "abs")
                    val tokensToDrop = listOf("asin(", "acos(", "atan(", "sin(", "cos(", "tan(", "log(", "EXP(", "ln(", "³√(", "√(", "abs(", "^2", "^-1")

                    when (key) {
                        "C" -> formula = ""

                        "<<" -> {
                            if (formula.isNotEmpty()) {
                                val token = tokensToDrop.find { formula.endsWith(it) }
                                if (token != null) formula = formula.dropLast(token.length)
                                else formula = formula.dropLast(1)
                            }
                        }

                        "=" -> { TODO() }

                        else -> {
                            val parsedKey = when (key) {
                                "x²" -> "^2"
                                "xʸ" -> "^"
                                "1/x" -> "^-1"
                                "|x|" -> "abs"
                                "EXP" -> "E"
                                "x!" -> "!"
                                else -> key
                            }

                            val textToAdd = if (parsedKey in functionsWithParens) "$parsedKey(" else parsedKey

                            if (formula.length + textToAdd.length <= 110) {
                                formula += textToAdd
                            } else {
                                Toast.makeText(
                                    applicationContext,
                                    "Osiągnięto maksymalną długość równania",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                }

                Scaffold(Modifier.fillMaxSize(), containerColor = MaterialTheme.colorScheme.background)
                { innerPadding ->
                    if (isLandscape)
                    {
                        Column(Modifier.fillMaxSize().padding(innerPadding))
                        {
                            Screen(modifier = Modifier.fillMaxWidth().weight(1f), formula = formula)
                            Row (modifier = Modifier.fillMaxWidth().weight(2.5f))
                            {
                                Keyboard(
                                    layout = keyboardLayoutLandscape,
                                    modifier = Modifier.fillMaxWidth().weight(1f),
                                    onKeyClick = handleKeyClick // Zawsze wywołuje wspólną logikę
                                )
                                Keyboard(
                                    layout = keyboardLayoutPortrait,
                                    modifier = Modifier.fillMaxWidth().weight(1f),
                                    onKeyClick = handleKeyClick
                                )
                            }
                        }
                    }
                    else
                    {
                        Column(Modifier.fillMaxSize().padding(innerPadding))
                        {
                            Screen(modifier = Modifier.fillMaxWidth().weight(2f), formula = formula)
                            Keyboard(
                                layout = keyboardLayoutPortrait,
                                modifier = Modifier.fillMaxWidth().weight(3f),
                                onKeyClick = handleKeyClick
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Screen(modifier : Modifier = Modifier, formula : String)
{
    Column(modifier)
    {
        Box(Modifier.fillMaxSize().weight(3f).background(MaterialTheme.colorScheme.background).padding(16.dp), contentAlignment = Alignment.CenterEnd)
        {
            Text(
                text=formula,
                color=contentColorFor(MaterialTheme.colorScheme.onBackground),
                fontSize = 28.sp,
                textAlign = TextAlign.End,
                maxLines = 5
            )
        }
        HorizontalDivider(
            Modifier.fillMaxWidth(0.98f)
                .align(Alignment.CenterHorizontally),
            thickness = 1.dp,
            color = Color(0xFF5B5B5B)
        )
        Box(Modifier.fillMaxSize().weight(1f).background(MaterialTheme.colorScheme.background).padding(16.dp), contentAlignment = Alignment.CenterEnd)
        {
            Text(
                text="0",
                color=contentColorFor(MaterialTheme.colorScheme.onBackground),
                fontSize = 28.sp,
                textAlign = TextAlign.End
            )
        }
    }
}

@Composable
fun Keyboard(modifier : Modifier = Modifier,
             onKeyClick : (String) -> Unit,
            layout : List<List<String>>)
{
    Row(modifier.fillMaxSize().padding(16.dp), Arrangement.spacedBy(8.dp))
    {
        for (col in layout) {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                for (key in col) {
                    Key(
                        text = key,
                        onClick = { onKeyClick(key) },
                        modifier = Modifier.weight(1f),
                        color = when (key)
                        {
                            "C", "<<" -> MaterialTheme.colorScheme.secondaryContainer
                            "(", ")", "+", "-", "x", "÷" -> MaterialTheme.colorScheme.surfaceVariant
                            "=" -> MaterialTheme.colorScheme.primary
                            else -> MaterialTheme.colorScheme.surfaceVariant
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun Key(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    color: Color
) {
    BoxWithConstraints(modifier = modifier)
    {
        val dynamicFontSize = (minOf(maxWidth.value, maxHeight.value) * 0.4f).sp

        Button(
            onClick = onClick,
            modifier = Modifier.fillMaxSize(),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = color
            ),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp),
            contentPadding = PaddingValues(0.dp)
        )
        {
            if (text == "<<") {
                Text(
                    text = "⌫",
                    color = MaterialTheme.colorScheme.onSurface,
                    fontFamily = rajdhaniMediumFont,
                    fontSize = dynamicFontSize
                )
            } else {
                Text(
                    text = text,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontFamily = rajdhaniMediumFont,
                    fontSize = dynamicFontSize
                )
            }
        }
    }

}