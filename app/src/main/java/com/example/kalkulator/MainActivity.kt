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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kalkulator.ui.theme.KalkulatorTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalConfiguration
import com.example.kalkulator.ui.theme.rajdhaniMediumFont
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Backspace
import androidx.compose.material.icons.automirrored.outlined.Backspace
import androidx.compose.material3.Icon

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KalkulatorTheme {
                var formula by remember { mutableStateOf("") }
                val configuration = LocalConfiguration.current
                val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

                Scaffold(Modifier.fillMaxSize(), containerColor = MaterialTheme.colorScheme.background)
                { innerPadding ->
                    Column(Modifier.fillMaxSize().padding(innerPadding))
                    {
                        Screen(modifier = Modifier.fillMaxWidth().weight(2f), formula = formula)
                        Keyboard(modifier = Modifier.fillMaxWidth().weight(3f),
                            onKeyClick = {
                                key -> if (key == "C") {
                                    formula = ""
                                }
                                else if (key == "<<")
                                {
                                    if (formula.length > 0)
                                    {
                                        formula = formula.dropLast(1)
                                    }
                                }
                                else if (key == "=")
                                {

                                }
                                else
                                {
                                    if (formula.length < 110)
                                    {
                                        formula += key
                                    }
                                    else
                                    {
                                        Toast.makeText(
                                            applicationContext,
                                            "Osiągnięto maksymalną długość równania",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}

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
             onKeyClick : (String) -> Unit)
{
    Row(modifier.fillMaxSize().padding(16.dp), Arrangement.spacedBy(8.dp))
    {
        for (col in keyboardLayoutPortrait) {
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