package com.thomasw.precision

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.rounded.Book
import androidx.compose.material.icons.rounded.Style
import androidx.compose.material.icons.rounded.Folder
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.thomasw.precision.ui.theme.PrecisionTheme
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.platform.LocalContext

//folder class file -- Konor
import com.thomasw.precision.FolderFunctionality.*

import android.widget.Toast


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PrecisionTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    TitleScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun TitleScreen(modifier: Modifier = Modifier) {
    var expanded by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }  // State for dialog visibility
    var folderNameInput by remember { mutableStateOf(TextFieldValue()) } // State for folder name input
    val folders = FolderManager.getFolders() // Observe folders list

    Box(modifier = modifier.fillMaxSize()) {
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
                onDismissRequest = { expanded = false }
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
                    onClick = { /* Handle Create Folder */
                        expanded = false;
                        showDialog = true
                        //FolderManager.createFolder(folderNameInput);
                        //folderCounter.value++;
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Rounded.Folder,
                            contentDescription = "Folder Icon"
                        )
                    }
                )
                //endregion
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

        // In your TitleScreen composable function
        if (showDialog) {
            FolderFunctionality().FolderNameDialog(
                showDialog = showDialog,
                folderNameInput = folderNameInput,
                onFolderNameChange = { newText ->
                    folderNameInput = newText // Update folder name input state
                },
                onFolderCreated = { folderName ->
                    // Create folder and close the dialog
                    FolderManager.createFolder(folderName)
                    showDialog = false
                }
            )
        }

        val context = LocalContext.current
        // Display the list of folders
        FolderFunctionality().DisplayFolders(folders.toList()) { folder ->
            // Handle folder click

            Toast.makeText(context, "Clicked on folder: ${folder.name}", Toast.LENGTH_SHORT).show()
        }

    }
}


@Preview(showBackground = true)
@Composable
fun TitleScreenPreview() {
    PrecisionTheme {
        TitleScreen()
    }
}