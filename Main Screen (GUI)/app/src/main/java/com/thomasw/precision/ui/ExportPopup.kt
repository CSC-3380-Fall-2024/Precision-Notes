package com.thomasw.precision.ui

import android.content.Context
import android.graphics.pdf.PdfDocument
import android.os.Environment
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.io.File
import java.io.FileOutputStream

@Composable
fun ExportPopup(
    showPopup: Boolean,
    onDismiss: () -> Unit,
    onExport: () -> Unit,
    onCopyLink: (String) -> Unit
) {
    if (showPopup) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text(text = "Export Notebook") },
            text = {
                Column {
                    Text("Export the notebook as a PDF.")
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("You can also copy a link to the generated PDF.")
                }
            },
            confirmButton = {
                TextButton(onClick = {
                    onExport()
                    onDismiss()
                }) {
                    Text(text = "Export")
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