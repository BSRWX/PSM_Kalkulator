package com.example.kalkulator

import android.os.Bundle
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import com.example.kalkulator.ui.theme.rajdhaniMediumFont
import java.util.EnumSet.range

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KalkulatorTheme {
                var formula by remember { mutableStateOf("") }

                Scaffold(Modifier.fillMaxSize(), containerColor = Color(0xFF161616))
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
                                    formula += key
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}

val keyboardLayout = listOf(
    listOf("C", "7", "4", "1", "0"),
    listOf("(", "8", "5", "2", "."),
    listOf(")", "9", "6", "3", "<<"),
    listOf("÷", "x", "-", "+", "=")
)

@Composable
fun Screen(modifier : Modifier = Modifier, formula : String)
{

    Column(modifier)
    {
        Box(Modifier.fillMaxSize().weight(3f).background(Color(0xFF1A1A1A)).padding(16.dp), contentAlignment = Alignment.CenterEnd)
        {
            Text(text=formula, color=Color(0xFFA4A4A4), fontSize = 28.sp, textAlign = TextAlign.End)
        }
        HorizontalDivider(Modifier.fillMaxWidth(), thickness = 1.dp, color = Color(0xFF5B5B5B))
        Box(Modifier.fillMaxSize().weight(1f).background(Color(0xFF1A1A1A)).padding(16.dp), contentAlignment = Alignment.CenterEnd)
        {
            Text(text="5.5", color=Color(0xFFA4A4A4), fontSize = 28.sp, textAlign = TextAlign.End)
        }
    }
}

@Composable
fun Keyboard(modifier : Modifier = Modifier,
             onKeyClick : (String) -> Unit) {
    Row(modifier.fillMaxSize().padding(16.dp), Arrangement.spacedBy(8.dp))
    {
        for (col in keyboardLayout) {
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
                            "C", "<<" -> Color(0xFF651717)
                            "(", ")", "+", "-", "x", "÷" -> Color(0xFF5B5B5B)
                            "=" -> Color(0xFF244511)
                            else -> Color(0xFF5B5B5B)
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
            Text(
                text = text,
                color = Color.White,
                fontFamily = rajdhaniMediumFont,
                fontSize = dynamicFontSize
            )
        }
    }

}