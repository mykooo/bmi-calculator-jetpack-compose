package org.indbaixcamp.bmia2

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.PathBuilder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

import org.indbaixcamp.bmia2.ui.theme.BmiA2Theme
import org.indbaixcamp.bmia2.ui.theme.IndigoContainer
import org.indbaixcamp.bmia2.ui.theme.IndigoOnContainer
import org.indbaixcamp.bmia2.ui.theme.IndigoPrimary
import org.indbaixcamp.bmia2.ui.theme.Slate200
import org.indbaixcamp.bmia2.ui.theme.Slate600
import org.indbaixcamp.bmia2.ui.theme.Slate900

// --- Custom Outlined Vector Icons ---
private fun createVectorIcon(
    name: String,
    tintColor: Color = IndigoPrimary,
    pathBuilder: PathBuilder.() -> Unit
): ImageVector {
    return ImageVector.Builder(
        name = name,
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).apply {
        path(
            stroke = SolidColor(tintColor),
            strokeLineWidth = 2f,
            strokeLineCap = StrokeCap.Round,
            strokeLineJoin = StrokeJoin.Round,
            pathBuilder = pathBuilder
        )
    }.build()
}

val PersonIcon: ImageVector
    get() = createVectorIcon("PersonIcon") {
        // Head
        moveTo(12f, 11f)
        moveToRelative(-4f, 0f)
        arcTo(4f, 4f, 0f, true, true, 16f, 11f)
        arcTo(4f, 4f, 0f, true, true, 8f, 11f)
        // Body
        moveTo(6f, 21f)
        curveTo(6f, 17f, 8.5f, 14f, 12f, 14f)
        curveTo(15.5f, 14f, 18f, 17f, 18f, 21f)
    }

val WeightIcon: ImageVector
    get() = createVectorIcon("WeightIcon") {
        // Handle
        moveTo(9f, 6f)
        curveTo(9f, 4.5f, 10.3f, 3f, 12f, 3f)
        curveTo(13.7f, 3f, 15f, 4.5f, 15f, 6f)
        // Scale Body
        moveTo(5f, 8f)
        lineTo(19f, 8f)
        lineTo(21f, 20f)
        lineTo(3f, 20f)
        close()
        // Gauge
        moveTo(12f, 11f)
        lineTo(12f, 14f)
    }

val HeightIcon: ImageVector
    get() = createVectorIcon("HeightIcon") {
        // Ruler bar
        moveTo(12f, 3f)
        lineTo(12f, 21f)
        // Top arrow
        moveTo(8f, 6f)
        lineTo(12f, 2f)
        lineTo(16f, 6f)
        // Bottom arrow
        moveTo(8f, 18f)
        lineTo(12f, 22f)
        lineTo(16f, 18f)
    }

val CalculateIcon: ImageVector
    get() = createVectorIcon("CalculateIcon") {
        // Calculator body
        moveTo(5f, 4f)
        lineTo(19f, 4f)
        lineTo(19f, 20f)
        lineTo(5f, 20f)
        close()
        // Display
        moveTo(8f, 8f)
        lineTo(16f, 8f)
        // Buttons grid
        moveTo(8f, 12f)
        lineTo(10f, 12f)
        moveTo(14f, 12f)
        lineTo(16f, 12f)
        moveTo(8f, 16f)
        lineTo(10f, 16f)
        moveTo(14f, 16f)
        lineTo(16f, 16f)
    }

val AddIcon: ImageVector
    get() = createVectorIcon("AddIcon", tintColor = Color.White) {
        moveTo(12f, 5f)
        lineTo(12f, 19f)
        moveTo(5f, 12f)
        lineTo(19f, 12f)
    }

val RemoveIcon: ImageVector
    get() = createVectorIcon("RemoveIcon", tintColor = IndigoPrimary) {
        moveTo(5f, 12f)
        lineTo(19f, 12f)
    }

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BmiA2Theme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    containerColor = MaterialTheme.colorScheme.background
                ) { innerPadding ->
                    BMIscreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@SuppressLint("DefaultLocale")
@Composable
fun BMIscreen(modifier: Modifier = Modifier) {
    var nom: String by remember { mutableStateOf("") }
    var pes: Int by remember { mutableStateOf(80) }
    var alcada: Int by remember { mutableStateOf(180) }
    var IMC: Double by remember { mutableStateOf(0.0) }

    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(scrollState)
            .padding(horizontal = 20.dp, vertical = 24.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        // --- Header Section ---
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = "Calculadora IMC",
                style = MaterialTheme.typography.headlineMedium,
                color = Slate900,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = if (nom.isNotBlank()) "Hola, $nom!" else "Calcula el teu índex de massa corporal",
                style = MaterialTheme.typography.bodyMedium,
                color = Slate600
            )
        }

        // --- Card 1: User Profile / Name ---
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(IndigoContainer),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = PersonIcon,
                            contentDescription = "Nom",
                            tint = IndigoPrimary,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    Text(
                        text = "Informació personal",
                        style = MaterialTheme.typography.titleMedium,
                        color = Slate900,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                OutlinedTextField(
                    value = nom,
                    onValueChange = { nom = it },
                    label = { Text(text = "Nom") },
                    placeholder = { Text(text = "Introdueix el teu nom") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = IndigoPrimary,
                        unfocusedBorderColor = Slate200,
                        focusedLabelColor = IndigoPrimary,
                        unfocusedLabelColor = Slate600
                    )
                )
            }
        }

        // --- Card 2: Weight Control ---
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .background(IndigoContainer),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = WeightIcon,
                                contentDescription = "Pes",
                                tint = IndigoPrimary,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        Text(
                            text = "Pes: $pes KG",
                            style = MaterialTheme.typography.titleMedium,
                            color = Slate900,
                            fontWeight = FontWeight.SemiBold
                        )
                    }

                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = IndigoContainer
                    ) {
                        Text(
                            text = "$pes kg",
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                            style = MaterialTheme.typography.labelLarge,
                            color = IndigoOnContainer,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                HorizontalDivider(color = Slate200, thickness = 1.dp)

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Decrement Button pes--
                    OutlinedButton(
                        onClick = { pes-- },
                        modifier = Modifier
                            .weight(1f)
                            .height(52.dp),
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = IndigoPrimary
                        )
                    ) {
                        Icon(
                            imageVector = RemoveIcon,
                            contentDescription = "Restar pes",
                            modifier = Modifier.size(20.dp),
                            tint = IndigoPrimary
                        )
                    }

                    // Increment Button pes++
                    Button(
                        onClick = { pes++ },
                        modifier = Modifier
                            .weight(1f)
                            .height(52.dp),
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = IndigoPrimary,
                            contentColor = Color.White
                        )
                    ) {
                        Icon(
                            imageVector = AddIcon,
                            contentDescription = "Afegir pes",
                            modifier = Modifier.size(20.dp),
                            tint = Color.White
                        )
                    }
                }
            }
        }

        // --- Card 3: Height Control ---
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .background(IndigoContainer),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = HeightIcon,
                                contentDescription = "Alçada",
                                tint = IndigoPrimary,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        Text(
                            text = "Alçada: $alcada cm",
                            style = MaterialTheme.typography.titleMedium,
                            color = Slate900,
                            fontWeight = FontWeight.SemiBold
                        )
                    }

                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = IndigoContainer
                    ) {
                        Text(
                            text = "$alcada cm",
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                            style = MaterialTheme.typography.labelLarge,
                            color = IndigoOnContainer,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                HorizontalDivider(color = Slate200, thickness = 1.dp)

                Slider(
                    value = alcada.toFloat(),
                    onValueChange = { alcada = it.toInt() },
                    valueRange = 100f..220f,
                    colors = SliderDefaults.colors(
                        thumbColor = IndigoPrimary,
                        activeTrackColor = IndigoPrimary,
                        inactiveTrackColor = IndigoContainer
                    )
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "100 cm", style = MaterialTheme.typography.bodyMedium, color = Slate600)
                    Text(text = "220 cm", style = MaterialTheme.typography.bodyMedium, color = Slate600)
                }
            }
        }

        // --- Card 4: Action & Result Card ---
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    onClick = {
                        IMC = pes / ((alcada.toDouble() / 100) * (alcada.toDouble() / 100))
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = IndigoPrimary,
                        contentColor = Color.White
                    ),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 2.dp)
                ) {
                    Icon(
                        imageVector = CalculateIcon,
                        contentDescription = "Calcular",
                        modifier = Modifier.size(22.dp),
                        tint = Color.White
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    if (IMC == 0.0) {
                        Text(
                            text = "Calcular IMC",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                    } else {
                        Text(
                            text = String.format("%.2f", IMC),
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                if (IMC > 0.0) {
                    HorizontalDivider(color = Slate200, thickness = 1.dp)

                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "El teu Índex de Massa Corporal",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Slate600
                        )

                        Text(
                            text = String.format("%.2f", IMC),
                            style = MaterialTheme.typography.displayMedium,
                            color = IndigoPrimary,
                            fontWeight = FontWeight.Bold
                        )

                        val (categoria, colorCategoria) = when {
                            IMC < 18.5 -> "Sota pes" to Color(0xFF0EA5E9)
                            IMC in 18.5..24.9 -> "Pes normal" to Color(0xFF10B981)
                            IMC in 25.0..29.9 -> "Sobrepès" to Color(0xFFF59E0B)
                            else -> "Obesitat" to Color(0xFFEF4444)
                        }

                        Surface(
                            shape = RoundedCornerShape(20.dp),
                            color = colorCategoria.copy(alpha = 0.12f)
                        ) {
                            Text(
                                text = categoria,
                                modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp),
                                style = MaterialTheme.typography.labelLarge,
                                color = colorCategoria,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
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
