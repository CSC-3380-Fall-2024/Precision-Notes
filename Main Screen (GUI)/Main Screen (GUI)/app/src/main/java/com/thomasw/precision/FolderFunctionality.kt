package com.thomasw.precision

import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.Composable
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.TextField
import androidx.compose.material3.Text
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Folder
import androidx.compose.material3.Icon
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*



class FolderFunctionality {
    @Composable
    fun FolderNameDialog(
        showDialog: Boolean,
        folderNameInput: TextFieldValue,
        onFolderNameChange: (TextFieldValue) -> Unit,
        onFolderCreated: (String) -> Unit
    ) {
        if (showDialog) {
            AlertDialog(
                onDismissRequest = { /* Handle dialog dismissal */ },
                title = { Text("Create New Folder") },
                text = {
                    TextField(
                        value = folderNameInput,
                        onValueChange = onFolderNameChange,
                        label = { Text("Folder Name") }
                    )
                },
                confirmButton = {
                    Button(
                        onClick = {
                            onFolderCreated(folderNameInput.text)
                        }
                    ) {
                        Text("Create")
                    }
                },
                dismissButton = {
                    Button(onClick = { /* Handle cancel */ }) {
                        Text("Cancel")
                    }
                }
            )
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun FolderNameDialogPreview() {
        FolderNameDialog(
            showDialog = true,
            folderNameInput = TextFieldValue("Sample Folder"),
            onFolderNameChange = {},
            onFolderCreated = { folderName -> println("Folder Created: $folderName") }
        )
    }

    @Composable
    fun DisplayFolders(folders: List<Folder>, onFolderClick: (Folder) -> Unit) {
        // Display List of Folders
        Row(modifier = Modifier.padding(64.dp)) {
            folders.forEach { folder ->
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(horizontal = 4.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Use Button instead of Text to make each folder clickable
                    Button(
                        onClick = { onFolderClick(folder) }, // Handle button click
                        modifier = Modifier.padding(vertical = 8.dp) // Add padding for spacing
                    ) {
                        // Icon and Text within the button
                        Icon(
                            imageVector = Icons.Rounded.Folder,
                            contentDescription = "Folder Icon",
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp)) // Space between icon and text
                        Text(folder.name, fontSize = 16.sp)
                    }
                }
            }
        }
    }
}