package com.thomasw.precision.ui

import android.content.Context
import android.os.Environment
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import java.io.File
import java.io.FileOutputStream

@Composable
fun ExportPopup(
    showPopup: Boolean,
    context: Context,
    onDismiss: () -> Unit,
    onExport: (String) -> Unit
) {
    var fileName by remember { mutableStateOf(TextFieldValue("Notebook")) }

    if (showPopup) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text("Export as PDF") },
            text = {
                Column {
                    Text("Enter File Name:")
                    TextField(
                        value = fileName,
                        onValueChange = { fileName = it },
                        label = { Text("File Name") }
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        onExport(fileName.text)
                        onDismiss()
                    }
                ) {
                    Text("Export")
                }
            },
            dismissButton = {
                Button(onClick = onDismiss) {
                    Text("Cancel")
                }
            }
        )
    }
}

// Function to create a simple PDF (mock data for now)
fun saveNotebookAsPDF(context: Context, fileName: String): Boolean {
    return try {
        val pdfDir = File(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), "PrecisionNotes")
        if (!pdfDir.exists()) {
            pdfDir.mkdirs()
        }
        val pdfFile = File(pdfDir, "$fileName.pdf")
        FileOutputStream(pdfFile).use { outputStream ->
            outputStream.write("Notebook content goes here.".toByteArray())
        }
        true
    } catch (e: Exception) {
        e.printStackTrace()
        false
    }
}
