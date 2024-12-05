// NotebookPreferencesPopup.kt
package com.thomasw.precision.ui

import androidx.compose.foundation.layout.* // For Column, Row, Spacer, etc.
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun NotebookPreferencesPopup(
    showPopup: Boolean,
    onDismiss: () -> Unit,
    onColorChange: (Color) -> Unit,
    onBackgroundTypeChange: (String) -> Unit,
    onImageImport: () -> Unit
) {
    var selectedColor by remember { mutableStateOf(Color.White) }
    var selectedBackground by remember { mutableStateOf("Blank") }

    if (showPopup) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text("Notebook Preferences") },
            text = {
                Column {
                    // Notebook color selection
                    Text("Notebook Color:")
                    Row {
                        Button(onClick = { selectedColor = Color.White; onColorChange(Color.White) }) {
                            Text("White")
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Button(onClick = { selectedColor = Color.Yellow; onColorChange(Color.Yellow) }) {
                            Text("Yellow")
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Button(onClick = { selectedColor = Color.Green; onColorChange(Color.Green) }) {
                            Text("Green")
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Background selection between blank or lined
                    Text("Background:")
                    Row {
                        RadioButton(
                            selected = selectedBackground == "Blank",
                            onClick = { selectedBackground = "Blank"; onBackgroundTypeChange("Blank") }
                        )
                        Text("Blank")
                        Spacer(modifier = Modifier.width(16.dp))
                        RadioButton(
                            selected = selectedBackground == "Lined",
                            onClick = { selectedBackground = "Lined"; onBackgroundTypeChange("Lined") }
                        )
                        Text("Lined")
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Image import functionality
                    Button(onClick = onImageImport) {
                        Text("Import Image")
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
