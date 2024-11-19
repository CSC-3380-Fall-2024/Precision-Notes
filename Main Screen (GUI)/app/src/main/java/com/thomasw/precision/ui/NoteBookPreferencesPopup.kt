package com.thomasw.precision.ui

// NotebookPreferencesPopup.kt

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
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
    if (showPopup) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text("Notebook Preferences") },
            text = {
                Column {
                    // Notebook color selection
                    Text("Notebook Color:")
                    Row {
                        Button(onClick = { onColorChange(Color.White) }) { Text("White") }
                        Button(onClick = { onColorChange(Color.Yellow) }) { Text("Yellow") }
                        Button(onClick = { onColorChange(Color.Green) }) { Text("Black") }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Background selection between blank or lined
                    Text("Background:")
                    Row {
                        RadioButton(selected = true, onClick = { onBackgroundTypeChange("Blank") })
                        Text("Blank")
                        Spacer(modifier = Modifier.width(16.dp))
                        RadioButton(selected = false, onClick = { onBackgroundTypeChange("Lined") })
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