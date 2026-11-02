package com.example.kalkulator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
                        Screen(modifier = Modifier.fillMaxWidth().weight(2f))
                        Keyboard(modifier = Modifier.fillMaxWidth().weight(3f))
                    }
                }
            }
        }
    }
}

val keyboardLayout = arrayOf("1", "2")

@Composable
fun Screen(modifier : Modifier = Modifier)
{

    Column(modifier)
    {
        Box(Modifier.fillMaxSize().weight(3f).background(Color(0xFF1A1A1A)).padding(16.dp), contentAlignment = Alignment.CenterEnd)
        {
            Text(text="5.5", color=Color(0xFFA4A4A4), fontSize = 28.sp, textAlign = TextAlign.Center)
        }
        HorizontalDivider(Modifier.fillMaxWidth(), thickness = 1.dp, color = Color(0xFF5B5B5B))
        Box(Modifier.fillMaxSize().weight(1f).background(Color(0xFF1A1A1A)).padding(16.dp), contentAlignment = Alignment.CenterEnd)
        {
            Text(text="5.5", color=Color(0xFFA4A4A4), fontSize = 28.sp, textAlign = TextAlign.Center)
        }
    }
}

@Composable
fun Keyboard(modifier : Modifier = Modifier) {
    Row(modifier.fillMaxSize().padding(16.dp), Arrangement.spacedBy(8.dp))
    {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Key(text = "C", onClick = { }, modifier = Modifier.weight(1f), Color(0xFF651717))
            Key(text = "7", onClick = { }, modifier = Modifier.weight(1f), Color(0xFF5B5B5B))
            Key(text = "4", onClick = { }, modifier = Modifier.weight(1f), Color(0xFF5B5B5B))
            Key(text = "1", onClick = { }, modifier = Modifier.weight(1f), Color(0xFF5B5B5B))
            Key(text = "0", onClick = { }, modifier = Modifier.weight(1f), Color(0xFF5B5B5B))
        }

        Column(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Key(text = "(", onClick = { }, modifier = Modifier.weight(1f), Color(0xFF5B5B5B))
            Key(text = "8", onClick = { }, modifier = Modifier.weight(1f), Color(0xFF5B5B5B))
            Key(text = "5", onClick = { }, modifier = Modifier.weight(1f), Color(0xFF5B5B5B))
            Key(text = "2", onClick = { }, modifier = Modifier.weight(1f), Color(0xFF5B5B5B))
            Key(text = ".", onClick = { }, modifier = Modifier.weight(1f), Color(0xFF5B5B5B))

        }

        Column(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Key(text = ")", onClick = { }, modifier = Modifier.weight(1f), Color(0xFF5B5B5B))
            Key(text = "9", onClick = { }, modifier = Modifier.weight(1f), Color(0xFF5B5B5B))
            Key(text = "6", onClick = { }, modifier = Modifier.weight(1f), Color(0xFF5B5B5B))
            Key(text = "3", onClick = { }, modifier = Modifier.weight(1f), Color(0xFF5B5B5B))
            Key(text = "<<", onClick = { }, modifier = Modifier.weight(1f), Color(0xFF651717))

        }

        Column(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Key(text = "÷", onClick = { }, modifier = Modifier.weight(1f), Color(0xFF5B5B5B))
            Key(text = "x", onClick = { }, modifier = Modifier.weight(1f), Color(0xFF5B5B5B))
            Key(text = "-", onClick = { }, modifier = Modifier.weight(1f), Color(0xFF5B5B5B))
            Key(text = "+", onClick = { }, modifier = Modifier.weight(1f), Color(0xFF5B5B5B))
            Key(text = "=", onClick = { }, modifier = Modifier.weight(1f), Color(0xFF244511))
        }
    }
}

fun Test()
{

}

@Composable
fun Key(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    color: Color
) {
    Button(
        onClick = onClick,
        modifier = modifier.fillMaxSize(),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = color
        ),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp)
    ) {
        Text(text = text, color = Color.White)
    }
}