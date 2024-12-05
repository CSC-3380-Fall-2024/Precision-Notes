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

import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Settings
import androidx.navigation.compose.rememberNavController
import com.thomasw.precision.ui.PensPopup
import com.thomasw.precision.ui.NotebookPreferencesPopup

//folder imports -- Konor
import androidx.navigation.NavController
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.ui.text.input.TextFieldValue
import com.google.accompanist.flowlayout.FlowRow
import ru.noties.jlatexmath.JLatexMathView


// Define the interface outside of the onCreate method
//interface OnContentViewChangeListener {
//    fun onChangeContentView()
//}

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

// Composable to represent the layout for the DrawingCanvasView
//@Composable
//fun DrawingCanvasViewLayout(listener: OnContentViewChangeListener?) {
//    // Your layout for DrawingCanvasView
//
//    // Trigger the listener when some action happens
//    listener?.onChangeContentView() // Example trigger for layout change
//}


@Composable
fun TitleScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    parentFolder: Folder? // Accept parent folder to display its subfolders
) {
    var expandedCreate by remember { mutableStateOf(false) }
    var expandedSettings by remember { mutableStateOf(false) }

    // folders state
    var showDialog by remember { mutableStateOf(false) }
    var folderNameInput by remember { mutableStateOf(TextFieldValue()) }
    var currentParentFolder by remember { mutableStateOf<Folder?>(parentFolder) }  // Use parentFolder

    //pens popup
    var showPensPopup by remember { mutableStateOf(false) }

    //notebook preferences popup
    var showNotebookPreferencesPopup by remember { mutableStateOf(false) }

    // Determine the folders to show based on the parentFolder
    val folders = remember(currentParentFolder) {
        mutableStateListOf<Folder>().apply {
            // If parentFolder is null, show root folders; otherwise, show subfolders of the current parent
            if (currentParentFolder == null) {
                // If no parent folder, show root folders
                addAll(FolderManager.getRootFolders())
            } else {
                // Show subfolders of the current parent folder
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
                // Update the folder list after creation
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
            // Plus Button (Top Left) with Create Dropdown
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
                // "Create New:" Header
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
//                        MainActivity().onNotebookSelected()
                        navController.navigate("NotesPage") // Navigate to the NotesPage route},
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
                    onClick = { /* Handle Create Folder */
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

            // Back Button (Top Left)
            IconButton(onClick = {
                // Simply pop the current screen from the navigation stack
                navController.navigateUp()
            }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back Button"
                )
            }

            // App Name in the Center (keeps it centered)
            Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                Text(
                    text = "Precision",
                    fontSize = 20.sp,
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            //Right-side buttons
                    Row {
                        IconButton(onClick = { /* Add Share functionality */ }) {
                            Icon(
                                imageVector = Icons.Default.Share,
                                contentDescription = "Share Button"
                            )
                        }

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
        }

        PensPopup(
            showPopup = showPensPopup,
            onDismiss = { showPensPopup = false },
            onSizeChange = { /* Handle size change */ },
            onColorChange = { /* Handle color change */ }
        )

        NotebookPreferencesPopup(
            showPopup = showNotebookPreferencesPopup,
            onDismiss = { showNotebookPreferencesPopup = false },
            onColorChange = { /* Handle color change */ },
            onBackgroundTypeChange = { /* Handle background type change */ },
            onImageImport = { /* Handle image import */ }
        )


        // Inside the TitleScreen function, replace the Row for displaying folders:
        FlowRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp,top = 96.dp),
            mainAxisSpacing = 16.dp, // Spacing between items horizontally
            crossAxisSpacing = 16.dp // Spacing between items vertically
        ) {
            folders.forEach { folder ->
                Button(
                    onClick = {
                        // When a folder is clicked, navigate to a new TitleScreen with the clicked folder as the parent
                        navController.navigate("titleScreen/${folder.name}")
                    },
                    modifier = Modifier.padding(4.dp) // Adjust padding for individual buttons
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Folder, // Use a folder icon
                            contentDescription = "Folder Icon",
                            modifier = Modifier.size(20.dp) // Adjust size as needed
                        )
                        Spacer(modifier = Modifier.width(8.dp)) // Add space between icon and text
                        Text(text = folder.name)
                    }
                }
            }
        }
    }
}

@Composable
fun NotesPage() {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("This is the Notes Page!")
        // Add more content/layout for the Notes page here
    }
}



@Preview(showBackground = true)
@Composable
fun TitleScreenPreview() {
    PrecisionTheme {
        // Mock data for preview
        val mockParentFolder: Folder? = null // or provide a mock Folder instance

        // Preview of TitleScreen with mock data
        TitleScreen(
            parentFolder = mockParentFolder,
            modifier = Modifier.fillMaxSize(),
            navController = rememberNavController()
        )
    }
}
