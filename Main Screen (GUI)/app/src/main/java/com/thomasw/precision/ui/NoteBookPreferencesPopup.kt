package com.thomasw.precision.ui.theme

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.thomasw.precision.DrawingCanvasView

@Composable
fun NotebookPreferencesPopup(
    showPopup: Boolean,
    onDismiss: () -> Unit,
    onBackgroundColorChange: (Boolean) -> Unit, // True for black, false for white
    onLinedChange: (Boolean) -> Unit // True for lined, false for unlined
) {
    if (showPopup) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text(text = "Notebook Preferences") },
            text = {
                Column {
                    // Background color toggle
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
                    ) {
                        Text(text = "Background Color:")
                        Spacer(modifier = Modifier.weight(1f))
                        var isBlack by remember { mutableStateOf(false) }
                        Switch(
                            checked = isBlack,
                            onCheckedChange = {
                                isBlack = it
                                onBackgroundColorChange(it)
                            }
                        )
                        Text(text = if (isBlack) "Black" else "White")
                    }

                    // Lined or unlined toggle
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
                    ) {
                        Text(text = "Lined Background:")
                        Spacer(modifier = Modifier.weight(1f))
                        var isLined by remember { mutableStateOf(false) }
                        Switch(
                            checked = isLined,
                            onCheckedChange = {
                                isLined = it
                                onLinedChange(it)
                            }
                        )
                        Text(text = if (isLined) "Lined" else "Unlined")
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = onDismiss) {
                    Text(text = "OK")
                }
            },
            dismissButton = {
                TextButton(onClick = onDismiss) {
                    Text(text = "Cancel")
                }
            }
        )
    }
}
