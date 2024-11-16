package com.thomasw.precision

import androidx.compose.runtime.mutableStateListOf

data class Folder(
    val name: String,
    val subfolders: MutableList<Folder> = mutableListOf()
)

object FolderManager {
    private val rootFolders = mutableListOf<Folder>()

    // Create a new folder
    // Modify the FolderManager's createFolder function
    fun createFolder(parentFolder: Folder?, folderName: String) {
        val newFolder = Folder(folderName)
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

    fun getFolderByName(name: String): Folder? {
        return findFolderRecursively(rootFolders, name)
    }

    private fun findFolderRecursively(folders: List<Folder>, name: String): Folder? {
        for (folder in folders) {
            if (folder.name == name) return folder
            val result = findFolderRecursively(folder.subfolders, name)
            if (result != null) return result
        }
        return null
    }
}