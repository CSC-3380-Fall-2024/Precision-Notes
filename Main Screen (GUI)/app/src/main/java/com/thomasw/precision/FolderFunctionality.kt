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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Folder
import androidx.compose.material3.Icon
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.rounded.Book
import androidx.compose.material.icons.rounded.Style
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.IconButton
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController


class FolderFunctionality {
    @Composable
    fun FolderAppNavigation() {
        val navController = rememberNavController()

        NavHost(
            navController = navController,
            startDestination = "titleScreen"
        ) {
            composable("NotesPage") {
                NotesPageWithDrawing()
            }
            composable("titleScreen") {
                // Initial screen showing root folders
                TitleScreen(
                    parentFolder = null,  // Show root folders
                    modifier = Modifier.fillMaxSize(),
                    navController = navController
                )

            }

            // This route handles opening TitleScreen for a specific folder's subfolders
            composable("titleScreen/{folderName}") { backStackEntry ->
                val folderName = backStackEntry.arguments?.getString("folderName")

                // Ensure folderName is not null before calling getFolderByName
                val parentFolder = folderName?.let { FolderManager.getFolderByName(it) }

                if (parentFolder != null) {
                    TitleScreen(
                        parentFolder = parentFolder,
                        modifier = Modifier.fillMaxSize(),
                        navController = navController
                    )
                } else {
                    // Handle the case where folderName is null or invalid
                    // You can navigate back or show an error screen
                }
            }
        }
    }


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
        Row(modifier = Modifier.padding(16.dp)) {
            folders.forEach { folder ->
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(horizontal = 8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Button(
                        onClick = { onFolderClick(folder) },
                        modifier = Modifier.padding(vertical = 8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Folder,
                            contentDescription = "Folder Icon",
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(folder.name, fontSize = 16.sp)
                    }
                }
            }
        }
    }
}