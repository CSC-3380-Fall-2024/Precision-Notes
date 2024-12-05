package com.thomasw.precision

import android.os.Bundle
import android.util.Log
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
import com.thomasw.precision.ui.PensPopup
import com.thomasw.precision.ui.NotebookPreferencesPopup
import com.thomasw.precision.ui.ExportPopup

// Folder imports
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.flowlayout.FlowRow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PrecisionTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    FolderFunctionality().FolderAppNavigation()
                }
            }
        }
    }
}

@Composable
fun TitleScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    parentFolder: Folder? // Accept parent folder to display its subfolders
) {
    var expandedCreate by remember { mutableStateOf(false) }
    var expandedSettings by remember { mutableStateOf(false) }

    // State for popups
    var showDialog by remember { mutableStateOf(false) }
    var folderNameInput by remember { mutableStateOf(TextFieldValue()) }
    var currentParentFolder by remember { mutableStateOf<Folder?>(parentFolder) }

    var showPensPopup by remember { mutableStateOf(false) }
    var showNotebookPreferencesPopup by remember { mutableStateOf(false) }
    var showExportPopup by remember { mutableStateOf(false) }

    // Variables for notebook settings
    var penSize by remember { mutableStateOf(5f) }
    var penColor by remember { mutableStateOf(Color.Black) }
    var notebookColor by remember { mutableStateOf(Color.White) }
    var backgroundType by remember { mutableStateOf("Blank") }

    // Determine the folders to show
    val folders = remember(currentParentFolder) {
        mutableStateListOf<Folder>().apply {
            if (currentParentFolder == null) {
                addAll(FolderManager.getRootFolders())
            } else {
                addAll(currentParentFolder?.let { FolderManager.getSubfolders(it) } ?: emptyList())
            }
        }
    }

    // Folder creation dialog
    if (showDialog) {
        FolderFunctionality().FolderNameDialog(
            showDialog = showDialog,
            folderNameInput = folderNameInput,
            onFolderNameChange = { newText -> folderNameInput = newText },
            onFolderCreated = { folderName ->
                FolderManager.createFolder(currentParentFolder, folderName)
                folders.clear()
                if (currentParentFolder == null) {
                    folders.addAll(FolderManager.getRootFolders())
                } else {
                    folders.addAll(currentParentFolder?.let { FolderManager.getSubfolders(it) } ?: emptyList())
                }
                showDialog = false
            }
        )
    }

    Box(modifier = modifier.fillMaxSize()) {
        // Top Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Plus Button
            IconButton(onClick = { expandedCreate = true }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Plus Button"
                )
            }

            DropdownMenu(
                expanded = expandedCreate,
                onDismissRequest = { expandedCreate = false }
            ) {
                Text(
                    text = "Create New:",
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
                DropdownMenuItem(
                    text = { Text("Notebook") },
                    onClick = {
                        expandedCreate = false
                        Log.d("TitleScreen", "Navigating to NotesPage")
                        navController.navigate("NotesPage")
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Rounded.Book,
                            contentDescription = "Notebook Icon"
                        )
                    }
                )
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
                DropdownMenuItem(
                    text = { Text("Folder") },
                    onClick = {
                        expandedCreate = false
                        showDialog = true
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Rounded.Folder,
                            contentDescription = "Folder Icon"
                        )
                    }
                )
            }

            // Export Button (Left of Settings)
            IconButton(onClick = { showExportPopup = true }) {
                Icon(
                    imageVector = Icons.Default.Share,
                    contentDescription = "Export Button"
                )
            }

            // Settings Button
            IconButton(onClick = { expandedSettings = true }) {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = "Settings Button"
                )
            }

            DropdownMenu(
                expanded = expandedSettings,
                onDismissRequest = { expandedSettings = false }
            ) {
                DropdownMenuItem(
                    text = { Text("Pens") },
                    onClick = {
                        expandedSettings = false
                        showPensPopup = true
                    }
                )
                DropdownMenuItem(
                    text = { Text("Notebook Preferences") },
                    onClick = {
                        expandedSettings = false
                        showNotebookPreferencesPopup = true
                    }
                )
            }
        }

        // Pens Popup
        PensPopup(
            showPopup = showPensPopup,
            onDismiss = { showPensPopup = false },
            onSizeChange = { newSize -> penSize = newSize },
            onColorChange = { newColor -> penColor = newColor }
        )

        // Notebook Preferences Popup
        NotebookPreferencesPopup(
            showPopup = showNotebookPreferencesPopup,
            onDismiss = { showNotebookPreferencesPopup = false },
            onColorChange = { newColor -> notebookColor = newColor },
            onBackgroundTypeChange = { newType -> backgroundType = newType },
            onImageImport = { /* Handle image import */ }
        )

        // Export Popup
        ExportPopup(
            showPopup = showExportPopup,
            onDismiss = { showExportPopup = false },
            onExportNotebook = { notebookName ->
                // Implement export logic here
                Log.d("ExportPopup", "Exporting notebook: $notebookName")
            }
        )

        // Display folders
        FlowRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, top = 96.dp),
            mainAxisSpacing = 16.dp,
            crossAxisSpacing = 16.dp
        ) {
            folders.forEach { folder ->
                Button(
                    onClick = {
                        navController.navigate("titleScreen/${folder.name}")
                    },
                    modifier = Modifier.padding(4.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Folder,
                            contentDescription = "Folder Icon",
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = folder.name)
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TitleScreenPreview() {
    PrecisionTheme {
        val mockParentFolder: Folder? = null
        TitleScreen(
            parentFolder = mockParentFolder,
            modifier = Modifier.fillMaxSize(),
            navController = rememberNavController()
        )
    }
}
