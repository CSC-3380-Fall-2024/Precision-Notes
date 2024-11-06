package com.thomasw.precision

import androidx.compose.runtime.mutableStateListOf

data class Folder(val name: String) // Representing each folder with a name

object FolderManager {
    private val folders = mutableListOf<Folder>()

    fun getFolders(): List<Folder> = folders

    fun createFolder(name: String): Boolean {
        // Check if the folder name is unique
        return if (folders.none { it.name == name }) {
            folders.add(Folder(name))
            true
        } else {
            false // Folder name already exists
        }
    }
}