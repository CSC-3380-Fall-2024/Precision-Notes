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
    fun AppNavigation() {
        val navController = rememberNavController()

        NavHost(navController = navController, startDestination = "title") {
            composable("title") {
                TitleScreen(
                    modifier = Modifier.fillMaxSize(),
                    navController = navController
                )
            }
            composable("folderDetail/{folderName}") { backStackEntry ->
                val folderName = backStackEntry.arguments?.getString("folderName") ?: "Unknown Folder"
                val folder = findFolderByName(folderName) // Find the folder by name from FolderManager
                if (folder != null) {
                    FolderDetailScreen(
                        folder = folder,
                        navController = navController
                    )
                }
            }
        }
    }

    fun findFolderByName(folderName: String): Folder? {
        // Search for the folder in the root folders and their subfolders
        return FolderManager.getRootFolders().firstOrNull { it.name == folderName }
    }

    @Composable
    fun FolderDetailScreen(folder: Folder, navController: NavController) {
        var expanded by remember { mutableStateOf(false) }
        var showDialog by remember { mutableStateOf(false) }  // State for dialog visibility
        var folderNameInput by remember { mutableStateOf(TextFieldValue()) } // State for folder name input
        val subfolders = FolderManager.getSubfolders(folder) // Get the subfolders for this folder

        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = folder.name,
                fontSize = 40.sp,
                modifier = Modifier
                    .align(Alignment.Center) // Center the Text inside the Box
                    .padding(16.dp)
            )

            // Top Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {


                // Plus Button (Top Left) with Dropdown
                IconButton(onClick = { expanded = true }) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Plus Button"
                    )
                }

                //region DropdownMenu
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier.align(Alignment.CenterVertically) // Aligns dropdown below the "+" button
                ) {
                    //region NoteBook
                    DropdownMenuItem(
                        text = { Text("Notebook") },
                        onClick = { /* Handle Create Notebook */ },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Rounded.Book,
                                contentDescription = "Notebook Icon"
                            )
                        }
                    )
                    //endregion
                    //region Flashcard
                    DropdownMenuItem(
                        text = { Text("Flashcard") },
                        onClick = { /* Handle Create Flashcard */ },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Rounded.Style,
                                contentDescription = "Flashcard Icon"
                            )
                        }
                    )
                    //endregion
                    //region Folder
                    DropdownMenuItem(
                        text = { Text("Folder") },
                        onClick = {
                            expanded = false
                            showDialog = true
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Rounded.Folder,
                                contentDescription = "Folder Icon"
                            )
                        }
                    )
                    //endregion
                    DropdownMenuItem(
                        text = { Text("Return") },
                        onClick = {
                            navController.popBackStack()
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Back Button"
                            )
                        }
                    )
                }
                //endregion

                // App Name in the Center
                Text(
                    text = "Precision",
                    fontSize = 20.sp,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )

                // Row for the right-side buttons
                Row {
                    // Export/Share Button (Left of Settings)
                    IconButton(onClick = { /* Add Share Button functionality */ }) {
                        Icon(
                            imageVector = Icons.Default.Share,
                            contentDescription = "Share Button"
                        )
                    }

                    // Settings Button (Top Right)
                    IconButton(onClick = { /* Add Settings functionality */ }) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = "Settings Button"
                        )
                    }
                }
            }

            // Folder creation dialog
            if (showDialog) {
                FolderNameDialog(
                    showDialog = showDialog,
                    folderNameInput = folderNameInput,
                    onFolderNameChange = { newText -> folderNameInput = newText },
                    onFolderCreated = { newFolderName ->
                        FolderManager.createFolder(folder, newFolderName)
                        showDialog = false
                    }
                )
            }



            // Display subfolders of this folder under the "+" button
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 100.dp) // Adjust padding to place it below the top row
            ) {

            }
            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 100.dp) // Adjust padding to place it below the top row
            ) {
                // Loop through the subfolders and create buttons for each one
                subfolders.forEach { subfolder ->
                    Button(
                        onClick = { navController.navigate("folderDetail/${subfolder.name}") },
                        modifier = Modifier
                            .padding(16.dp) // Adjust padding as needed
                    ) {
                        Text(text = subfolder.name)
                    }
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