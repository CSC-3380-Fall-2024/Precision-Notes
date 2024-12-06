// PensPopup.kt
package com.thomasw.precision.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun PensPopup(
    showPopup: Boolean,
    onDismiss: () -> Unit,
    onSizeChange: (Float) -> Unit,
    onColorChange: (Color) -> Unit
) {
    if (showPopup) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text("Adjust Pen Settings") },
            text = {
                Column {
                    // Size Slider
                    Text("Size:")
                    val sizeRange = remember { 1f..20f } // Pen size range
                    Slider(
                        value = 5f, // Default size
                        onValueChange = onSizeChange,
                        valueRange = sizeRange
                    )

                    // Color Selection Buttons
                    Text("Color:")
                    Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()) {
                        listOf(
                            Color.Red, Color(0xFFFFA500), Color.Yellow, Color.Green,
                            Color.Blue, Color(0xFF4B0082), Color(0xFF8A2BE2), Color.Black, Color.White
                        ).forEach { color ->
                            Button(
                                onClick = { onColorChange(color) },
                                colors = ButtonDefaults.buttonColors(containerColor = color),
                                modifier = Modifier.size(40.dp)
                            ) { }
                        }
                    }
                }
            },
            confirmButton = {
                Button(onClick = onDismiss) {
                    Text("Done")
                }
            }
        )
    }
}
