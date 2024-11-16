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

//folder imports -- Konor
import com.thomasw.precision.FolderFunctionality.*
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import android.widget.Toast
import androidx.compose.ui.text.input.TextFieldValue


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
                    onClick = { /* Handle Create Notebook */ },
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
                    imageVector = Icons.Default.ArrowBack,
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

            // Row for the right-side buttons
            Row {
                // Export/Share Button (Left of Settings)
                IconButton(onClick = { /* Add Share Button functionality */ }) {
                    Icon(
                        imageVector = Icons.Default.Share,
                        contentDescription = "Share Button"
                    )
                }

                // Settings Button with Dropdown (Top Right)
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
                    // "Settings:" Header
                    Text(
                        text = "Settings:",
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    DropdownMenuItem(
                        text = { Text("Pens") },
                        onClick = { /* Handle Pens Settings */ }
                    )
                    DropdownMenuItem(
                        text = { Text("Notebook Preferences") },
                        onClick = { /* Handle Notebook Preferences */ }
                    )
                    DropdownMenuItem(
                        text = { Text("Defaults") },
                        onClick = { /* Handle Defaults */ }
                    )
                }
            }
        }

        // Display subfolders or root folders
        Row(modifier = Modifier.padding(top = 100.dp)) {
            folders.forEach { folder ->
                Button(
                    onClick = {
                        // When a folder is clicked, navigate to a new TitleScreen with the clicked folder as the parent
                        navController.navigate("titleScreen/${folder.name}")
                    },
                    modifier = Modifier.padding(16.dp)
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
