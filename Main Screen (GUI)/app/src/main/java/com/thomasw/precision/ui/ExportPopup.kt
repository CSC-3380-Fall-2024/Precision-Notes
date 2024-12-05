package com.thomasw.precision.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ExportPopup(
    showPopup: Boolean,
    onDismiss: () -> Unit,
    onExportNotebook: (String) -> Unit
) {
    if (showPopup) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text("Export Notebook") },
            text = {
                Column {
                    Text("Select the notebook to export:")
                    // Replace with a list or dropdown of notebooks
                    Button(onClick = { onExportNotebook("SampleNotebook") }) {
                        Text("Sample Notebook")
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
