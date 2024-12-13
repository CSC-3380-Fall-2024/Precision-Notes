package com.thomasw.precision

import android.util.Log
import androidx.compose.runtime.mutableStateListOf

data class Folder(
    val name: String,
    val folderID: Int,
    val subfolders: MutableList<Folder> = mutableListOf()
)

object FolderManager {
    private val rootFolders = mutableListOf<Folder>()
    private var folderNum = 0
    // Create a new folder
    // Modify the FolderManager's createFolder function
    fun createFolder(parentFolder: Folder?, folderName: String) {
        folderNum += 1;
        Log.d("TitleScreen", "Folder Created: $folderName, FolderNum: $folderNum")
        val newFolder = Folder(folderName, folderNum)
        if (parentFolder == null) {
            // If no parent folder, it's a top-level folder
            rootFolders.add(newFolder)
        } else {
            // Add new folder as a subfolder to the parent folder
            parentFolder.subfolders.add(newFolder)
        }
    }

    // Get the top-level folders
    fun getRootFolders(): List<Folder> = rootFolders

    // Get subfolders of a given folder
    fun getSubfolders(folder: Folder): List<Folder> = folder.subfolders

    fun getFolderByID(folderID: Int): Folder? {
        return findFolderRecursively(rootFolders, folderID)
    }

    private fun findFolderRecursively(folders: List<Folder>, folderID: Int): Folder? {
        for (folder in folders) {
            if (folder.folderID == folderID) return folder
            val result = findFolderRecursively(folder.subfolders, folderID)
            if (result != null) return result
        }
        return null
    }
}