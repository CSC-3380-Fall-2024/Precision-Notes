package com.thomasw.precision

import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class RunFlashcards : AppCompatActivity() {

    //set variables for screen ops
    private lateinit var textQuestion : TextView
    private lateinit var textAnswer : TextView
    private lateinit var btnCheck : Button
    private lateinit var btnQuit : Button

    //set defaults for flashcard ops
    private var index = 0
    private var size = 0
    private var flashcardQuestions : MutableList<String>? = null
    private var flashcardAnswers : MutableList<String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_run_flashcards)

        //link textView variables to textView and textMultilineView boxes
        textQuestion = findViewById(R.id.tvQuestion)
        textAnswer = findViewById(R.id.tml_Answer)

        //get flashcard arrays for questions and answers
        flashcardQuestions = intent.getStringArrayListExtra("questions")
        flashcardAnswers = intent.getStringArrayListExtra("answers")

        //get size of arrays
        if (flashcardQuestions != null) {
            size = flashcardAnswers!!.size -1
        }

        //display current question
        textQuestion.text = flashcardQuestions?.get(index)

        //link button variables to buttons and set on click listener for each
        btnCheck = findViewById(R.id.btn_Check)
        btnCheck.setOnClickListener {

            //temporarily hide soft keyboard
            //set focus variable
            val focus = this.currentFocus

            //if focus is valid, get rid of soft keyboard
            if (focus != null) {

                //get instance of soft keyboard
                val keyboard = this.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager

                //temporarily hide keyboard
                keyboard.hideSoftInputFromWindow(
                    focus.windowToken,
                    InputMethodManager.HIDE_NOT_ALWAYS
                )
            }

            //if entered answer is correct, advance to next else signal false
            if (flashcardAnswers?.get(index) == textAnswer.text.toString()) {

                //so long as the index is less than the array size, increment index
                if (index < size) {
                    index += 1

                    //post next flashcard question and erase answer text
                    textQuestion.text = flashcardQuestions?.get(index)
                    textAnswer.text = ""

                    //show toast with correct message
                    Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show()
                }

                //inform user all the flashcards have been answered
                else {

                    //clear question and answer text boxes

                    //inform user all flashcards have been answered
                    Toast.makeText(this, "You answered all the Flashcards correctly!",
                        Toast.LENGTH_SHORT).show()
                }
            }

            //clear answer text box and show toast with incorrect message
            else {
                textAnswer.text = ""
                Toast.makeText(this, "Incorrect", Toast.LENGTH_SHORT).show()
            }
        }

        //when quit button is pressed, end activity
        btnQuit = findViewById(R.id.btn_Quit)
        btnQuit.setOnClickListener {

            //if quit is pressed, return to parent activity
            finish()
        }
    }
}
