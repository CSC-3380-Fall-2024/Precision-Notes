// PensPopup.kt
package com.thomasw.precision.ui

import androidx.compose.foundation.layout.* // For Column, Row, Spacer, etc.
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier // Import Modifier here
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
                    Slider(
                        value = 5f,  // Default size
                        onValueChange = onSizeChange,
                        valueRange = 1f..10f
                    )

                    // Color Selection Buttons
                    Text("Color:")
                    Row {
                        Button(onClick = { onColorChange(Color.Black) }) { Text("Black") }
                        Spacer(modifier = Modifier.width(8.dp))
                        Button(onClick = { onColorChange(Color.Red) }) { Text("Red") }
                        Spacer(modifier = Modifier.width(8.dp))
                        Button(onClick = { onColorChange(Color.Blue) }) { Text("Blue") }
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

