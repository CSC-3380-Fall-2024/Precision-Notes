package com.thomasw.precision

import android.util.Log
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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


class FolderFunctionality {
    @Composable
    fun FolderAppNavigation() {
        val navController = rememberNavController()

        NavHost(
            navController = navController,
            startDestination = "titleScreen"
        ) {
            composable("NotesPage") {
                NotesPageWithDrawing(navController = navController)
                navController.addOnDestinationChangedListener { _, destination, _ ->
                    Log.d("NavigationDebug", "Navigated to: ${destination.label}")
                }
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
            composable("titleScreen/{folderID}") { backStackEntry ->
                val folderName = backStackEntry.arguments?.getString("folderID")?.toIntOrNull()

                // Ensure folderName is not null before calling getFolderByName
                val parentFolder = folderName?.let { FolderManager.getFolderByID(it) }

                if (parentFolder != null) {
                    TitleScreen(
                        parentFolder = parentFolder,
                        modifier = Modifier.fillMaxSize(),
                        navController = navController
                    )
                }
            }
        }
    }


    @Composable
    fun FolderNameDialog(
        showDialog: Boolean,
        folderNameInput: TextFieldValue,
        onFolderNameChange: (TextFieldValue) -> Unit,
        onFolderCreated: (String) -> Unit,
        onDismissRequest: () -> Unit
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
                    Button(onClick = { /* Handle cancel */
                        onDismissRequest()
                    }) {
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
            onFolderCreated = { folderName -> println("Folder Created: $folderName") },
            onDismissRequest = {}
        )
    }

}