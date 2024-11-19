package com.thomasw.precision

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import java.lang.Exception

class ManageFlashcards : AppCompatActivity() {

    //declare screen variables
    private lateinit var btnRun : Button
    private lateinit var btnEdit : Button
    private lateinit var btnLoad : Button
    private lateinit var btnSave : Button
    private lateinit var btnQuit : Button

    //declare result launcher variable for child activity data return
    private lateinit var resultLauncher: ActivityResultLauncher<Intent>

    //declare default flashcard arrays variables
    private var questions =
        arrayListOf("What is the capital first letter of today's English alphabet?",
            "What is the capital second letter of today's English alphabet?",
            "What is the capital last letter of today's English alphabet?")
    private var answers =
        arrayListOf("A", "B", "Z")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_flashcards)

        //prepare result launcher for child to parent arrays transfer
        resultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()) {dataReturned ->
            if (dataReturned.resultCode == RESULT_OK) {
                questions = dataReturned.data?.getStringArrayListExtra(
                    "questions") as ArrayList<String>
                answers = dataReturned.data?.getStringArrayListExtra(
                    "answers") as ArrayList<String>
            }
        }

        //link button variable to button and set on click listener for each,
        //pass question and answer arrays to child activity RunFlashcards
        btnRun = findViewById(R.id.btn_Run)
        btnRun.setOnClickListener {
            startActivity(Intent(this, RunFlashcards::class.java)
                .putStringArrayListExtra("questions", questions)
                .putStringArrayListExtra("answers", answers))
        }

        //when edit button is pressed, launch editFlashcards activity and pass arrays
        btnEdit = findViewById(R.id.btn_Edit)
        btnEdit.setOnClickListener {

            //launch result launcher, creating EditFlashcards activity and passing arrays
            //when activity ends, the question and answer arrays will be updated as noted
            //in the instantiation above
            resultLauncher.launch(
                Intent(this, EditFlashcards::class.java)
                    .putStringArrayListExtra("questions", questions)
                    .putStringArrayListExtra("answers", answers))
        }

        //load saved flashcard set
        btnLoad = findViewById(R.id.btn_Load)
        btnLoad.setOnClickListener {

            //in case of load error
            try {

                //declare variable for loading combined questions and answers from shared prefs
                val loadArrayList: ArrayList<String> = loadArray()

                if (loadArrayList.size == 0) {
                    Toast.makeText(this, "No Saved Flashcards Loaded", Toast
                        .LENGTH_SHORT).show()
                }
                else {

                    //clear both questions and answers arrays of data
                    questions = arrayListOf()
                    answers = arrayListOf()

                    //loop through the loadArrayList, split each string,
                    //and put questions and answers in the correct array
                    for (i: Int in 0 until loadArrayList.size) {
                        val tempArray = loadArrayList[i].split("|||")
                        questions.add(tempArray[0])
                        answers.add((tempArray[1]))
                    }

                    //show user toast of flashcards loaded
                    Toast.makeText(this, "Flashcards Loaded", Toast.LENGTH_SHORT).show()
                }
            }

            //alert user to error
            catch (e: Exception) {
                Toast.makeText(this, "Exception: No Saved Flashcards Loaded", Toast
                    .LENGTH_SHORT).show()
            }
        }

        //save current flashcard set
        btnSave = findViewById(R.id.btn_Save)
        btnSave.setOnClickListener {

            //declare variable for combined questions and answers array
            val saveArrayList : ArrayList<String> = arrayListOf()

            //loop through all questions and answers, combine them into a single array
            for (i: Int in 0 until questions.size) {
                saveArrayList.add(questions[i] + "|||" + answers[i])
            }

            //save the saveArrayList
            saveArray(saveArrayList)

            //show flashcards saved toast
            Toast.makeText(this, "Flashcards Saved", Toast.LENGTH_SHORT).show()
        }

        btnQuit = findViewById(R.id.btn_Quit2)
        btnQuit.setOnClickListener {
            finish()
        }
    }
    private fun saveArray(arrayList: ArrayList<String>) {
        //turn array into set
        val arraySet = arrayList.toSet()

        //create shared pref instance
        val sharedPreferences = getSharedPreferences("flashcards", Context.MODE_PRIVATE)

        //create editor instance
        val editor = sharedPreferences.edit()

        //load set into editor and write into memory
        editor.putStringSet("flashcards", arraySet)
        editor.apply()
    }

    private fun loadArray(): ArrayList<String> {
        //create shared preference instance
        val sharedPreferences = getSharedPreferences("flashcards", Context.MODE_PRIVATE)

        //load set from shared preferences memory
        val arraySet = sharedPreferences.getStringSet("flashcards", emptySet())

        //create array list from retrieved set
        val loadedArrayList = arrayListOf<String>()
        loadedArrayList.addAll(arraySet!!)

        //return the loaded array list
        return loadedArrayList
    }

}

