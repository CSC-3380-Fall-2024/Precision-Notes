package com.thomasw.precision

import android.os.Bundle
import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.thomasw.precision.ui.ExportPopup
import com.thomasw.precision.ui.theme.PrecisionTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PrecisionTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    FolderFunctionality().AppNavigation()
                }
            }
        }
    }
}

@Composable
fun TitleScreen(modifier: Modifier = Modifier, navController: NavController) {
    var expanded by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) } // State for folder creation dialog visibility
    var showExportPopup by remember { mutableStateOf(false) } // State for export popup visibility
    var folderNameInput by remember { mutableStateOf(TextFieldValue()) } // State for folder name input
    var currentParentFolder by remember { mutableStateOf<Folder?>(null) } // Track current parent folder for subfolders
    val folders = remember { mutableStateListOf(*FolderManager.getRootFolders().toTypedArray()) } // Observe root folders
    val context = LocalContext.current // Context for handling toast messages

    // Track folder history for back navigation
    var folderHistory by remember { mutableStateOf(listOf<Folder>()) }

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

            // DropdownMenu
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                // Notebook Option
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

                // Flashcard Option
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

                // Folder Option
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

                // Export as PDF Option
                DropdownMenuItem(
                    text = { Text("Export as PDF") },
                    onClick = {
                        expanded = false
                        showExportPopup = true
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Share,
                            contentDescription = "Export PDF Icon"
                        )
                    }
                )
            }

            // App Name in the Center
            Text(
                text = "Precision",
                fontSize = 20.sp,
                modifier = Modifier.align(Alignment.CenterVertically)
            )

            // Row for the right-side buttons
            Row {
                // Export/Share Button (Left of Settings)
                IconButton(onClick = { showExportPopup = true }) {
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

        // Folder Creation Dialog
        if (showDialog) {
            FolderFunctionality().FolderNameDialog(
                showDialog = showDialog,
                folderNameInput = folderNameInput,
                onFolderNameChange = { newText ->
                    folderNameInput = newText
                },
                onFolderCreated = { folderName ->
                    FolderManager.createFolder(currentParentFolder, folderName)
                    folders.clear() // Refresh the list after folder creation
                    folders.addAll(FolderManager.getRootFolders()) // Reload folders
                    showDialog = false
                }
            )
        }

        // Export Popup
        ExportPopup(
            showPopup = showExportPopup,
            context = context,
            onDismiss = { showExportPopup = false },
            onExport = { fileName ->
                val success = saveNotebookAsPDF(context, fileName)
                if (success) {
                    Toast.makeText(context, "Exported to Documents/$fileName.pdf", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(context, "Export failed!", Toast.LENGTH_LONG).show()
                }
            }
        )

        // Display subfolders of this folder
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 100.dp) // Adjust padding to place it below the top row
        ) {
            folders.forEach { folder ->
                Button(
                    onClick = {
                        if (folder.subfolders.isNotEmpty()) {
                            // Navigate to folder's detail view
                            folderHistory = folderHistory + folder
                            navController.navigate("folderDetail/${folder.name}")
                        } else {
                            // Open empty folder detail view
                            navController.navigate("folderDetail/${folder.name}")
                        }
                    },
                    modifier = Modifier
                        .padding(16.dp) // Adjust padding as needed
                ) {
                    Text(text = folder.name)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TitleScreenPreview() {
    PrecisionTheme {
        TitleScreen(
            modifier = Modifier.fillMaxSize(),
            navController = rememberNavController()
        )
    }
}
