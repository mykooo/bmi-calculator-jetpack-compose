package org.indbaixcamp.bmia2

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import org.indbaixcamp.bmia2.ui.theme.BmiA2Theme
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BmiA2Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    BMIscreen()
                }
            }
        }
    }
}

@SuppressLint("DefaultLocale")
@Composable
fun BMIscreen(){
    var nom:String by remember { mutableStateOf("") }
    var pes:Int by remember { mutableStateOf(80) }
    var alcada:Int by remember { mutableStateOf(180) }
    var IMC:Double by remember { mutableStateOf(0.0) }

    Column() {
        Text(text = "Calculadora IMC")
        TextField(
            value = nom,
            onValueChange = {nom = it},
            label = { Text(text = "Nom") }
        )

        Row() {
            Text(text = "Pes: $pes KG")

            Button(
                onClick = {pes++}
            ) {
                Text(text = "+")
            }

            Button(
                onClick = {pes--}
            ) {
                Text(text = "-")
            }
        }

        Text(text = "Alçada: $alcada cm")
        Slider(
            value = alcada.toFloat(),
            onValueChange = {alcada = it.toInt()},
            valueRange = 100f..220f
        )

        Button(
            onClick = {
                IMC = pes / ((alcada.toDouble() / 100) * (alcada.toDouble() / 100))
            }
        ) {
            if(IMC == 0.0) {
                Text(text = "Calcular IMC")
            } else {
                Text(text = String.format("%.2f", IMC))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    BmiA2Theme {
        BMIscreen()
    }
}