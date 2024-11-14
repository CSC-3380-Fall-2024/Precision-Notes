package com.thomasw.precision

import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

    class EditFlashcards : AppCompatActivity() {

        //set variables for screen ops
        private lateinit var textQuestion : TextView
        private lateinit var textAnswer : TextView
        private lateinit var btnAccept : Button
        private lateinit var btnDelete : Button
        private lateinit var btnDone : Button
        private lateinit var btnNext : Button
        private lateinit var btnPrevious : Button

        //set defaults for flashcard ops
        private var index = 0
        private var size = 0
        private var flashcardQuestions : MutableList<String>? = null
        private var flashcardAnswers : MutableList<String>? = null

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_edit_flashcards)

            //link editTextView variables to editTextMultilineView boxes
            textQuestion = findViewById(R.id.etml_EnterQuestion)
            textAnswer = findViewById(R.id.etml_EnterAnswer)

            //get flashcard arrays for questions and answers
            flashcardQuestions = intent.getStringArrayListExtra("questions")
            flashcardAnswers = intent.getStringArrayListExtra("answers")

            //get size of arrays
            if (flashcardQuestions != null) {
                size = flashcardAnswers!!.size -1
            }

            //display current question and answer in editTextView boxes
            textQuestion.text = flashcardQuestions?.get(index)
            textAnswer.text = flashcardAnswers?.get(index)

            //when Accept is pressed, save flashcard data in arrays
            btnAccept = findViewById(R.id.btn_Accept)
            btnAccept.setOnClickListener {

                //if either the question or answer edit text box is empty, show error toast
                if (textQuestion.text.toString() == "" || textAnswer.text.toString() == "") {
                    Toast.makeText(
                        this, "Both question and answer must exist!", Toast
                            .LENGTH_SHORT
                    ).show()
                } else {

                    //if index > size, add flashcard to arrays, erase text in question and
                    //answer text boxes, increment index
                    if (index > size) {

                        //add question and answer to arrays
                        flashcardQuestions?.add(textQuestion.text.toString())
                        flashcardAnswers?.add(textAnswer.text.toString())

                        //increment index and size
                        index += 1
                        size += 1

                        //clear question and answer edit text boxes
                        textQuestion.text = ""
                        textAnswer.text = ""
                    }

                    //else delete current question and answer from arrays, add new question and
                    //answer to arrays in current place, advance index, check against size, and
                    //either show the next question and answer or clear text edit boxes
                    else {

                        //remove old question and answer
                        flashcardQuestions!!.removeAt(index)
                        flashcardAnswers!!.removeAt(index)

                        //add new question and answer
                        flashcardQuestions?.add(index, textQuestion.text.toString())
                        flashcardAnswers?.add(index, textAnswer.text.toString())

                        //increment index
                        index += 1

                        //if index is within the array bounds, populate text edit boxes with
                        //current question and answer
                        if (index <= size) {

                            //populate text edit boxes with current question and answer
                            textQuestion.text = flashcardQuestions!![index]
                            textAnswer.text = flashcardAnswers!![index]
                        }

                        //else clear text edit boxes in preparation for new flashcard
                        else {

                            //clear question and answer edit text boxes
                            textQuestion.text = ""
                            textAnswer.text = ""
                        }
                    }

                    //show toast with flashcard added message
                    clearSoftKeyboard()
                    Toast.makeText(this, "Flashcard Added", Toast.LENGTH_SHORT).show()
                }
            }

            //when next button is pressed, advance index and show data if available, if not
            //show toast of no more flashcards
            btnNext = findViewById(R.id.btn_Next)
            btnNext.setOnClickListener {

                //if index < size, increment index and show array data in the text boxes
                if (index < size) {
                    index += 1

                    //populate text edit boxes with current question and answer
                    textQuestion.text = flashcardQuestions!![index]
                    textAnswer.text = flashcardAnswers!![index]
                }

                //else clear text boxes, increment index, and wait for user input, show toast
                //with no more flashcard message
                else {
                    if (index == size)
                        index += 1

                    //clear question and answer edit text boxes
                    textQuestion.text = ""
                    textAnswer.text = ""

                    Toast.makeText(this, "There are no more flashcards!",
                        Toast.LENGTH_SHORT).show()
                }
                //clear soft keyboard
                clearSoftKeyboard()
            }

            //when previous button is pressed decrement index and show array data in text boxes if
            //available - if not, show toast of no more flashcards
            btnPrevious = findViewById(R.id.btn_Previous)
            btnPrevious.setOnClickListener {

                //if index < size, decrement index and show array data in the text boxes
                if (index > 0) {
                    index -= 1

                    //populate text edit boxes with current question and answer
                    textQuestion.text = flashcardQuestions!![index]
                    textAnswer.text = flashcardAnswers!![index]
                }

                //else show toast with no more flashcard message
                else {
                    Toast.makeText(this, "There are no more flashcards!",
                        Toast.LENGTH_SHORT).show()
                }

                //clear soft keyboard
                clearSoftKeyboard()
            }

            //when delete button is pressed, remove flashcard data from arrays
            btnDelete = findViewById(R.id.btn_Delete)
            btnDelete.setOnClickListener {


                if (size != -1 && index <= size) {

                    //remove flashcard data from arrays
                    flashcardQuestions!!.removeAt(index)
                    flashcardAnswers!!.removeAt(index)

                    //decrement size and index as appropriate
                    size -= 1
                    if (index > 0)
                        index -= 1

                    //if there are more flashcards in the array, show it and show toast of flashcard
                    //deleted
                    if (size > -1) {

                        //populate text edit boxes with current question and answer
                        textQuestion.text = flashcardQuestions!![index]
                        textAnswer.text = flashcardAnswers!![index]

                        //show toast of flashcard deleted
                        Toast.makeText(this, "Flashcard Deleted", Toast.LENGTH_SHORT).show()
                    }

                    //last flashcard was deleted, so clear the text boxes and set index to zero
                    else {
                        //clear question and answer edit text boxes
                        textQuestion.text = ""
                        textAnswer.text = ""

                        //set index to zero
                        index = 0

                        //show toast of no more flashcards
                        Toast.makeText(this, "No More Flashcards", Toast
                            .LENGTH_SHORT).show()
                    }
                }

                //no flashcard to delete so clear text boxes and show toast
                else {

                    //clear question and answer edit text boxes
                    textQuestion.text = ""
                    textAnswer.text = ""

                    //show no flashcard to delete toast
                    Toast.makeText(
                        this, "No Flashcard to Delete.", Toast.LENGTH_SHORT).show()
                }

                //clear soft keyboard
                clearSoftKeyboard()
            }

            //when quit button is pressed, end activity
            btnDone = findViewById(R.id.btn_Done)
            btnDone.setOnClickListener {

                //cast mutable lists to array lists for data return
                val questions = flashcardQuestions as ArrayList<String>
                val answers = flashcardAnswers as ArrayList<String>

                //if arrays are not null and same size return to previous activity with new arrays
                if (questions.size > 0 && answers.size > 0 && questions.size == answers.size) {
                    setResult(
                        RESULT_OK, Intent()
                            .putStringArrayListExtra("questions", questions)
                            .putStringArrayListExtra("answers", answers))
                    finish()
                }

                //else clear soft keyboard, show toast with no flashcards message
                else {
                    clearSoftKeyboard()
                    Toast.makeText(
                        this, "You have no flashcards to pass back!",
                        Toast.LENGTH_SHORT).show()
                }
            }
        }

        //temporarily hide soft keyboard
        private fun clearSoftKeyboard() {

            //set focus variable
            val focus = this.currentFocus

            //if focus not null, get rid of soft keyboard
            if (focus != null) {

                //get current instance of soft keyboard
                val keyboard = this.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager

                //temporarily hide keyboard
                keyboard.hideSoftInputFromWindow(
                    focus.windowToken,
                    InputMethodManager.HIDE_NOT_ALWAYS
                )
            }
        }
    }
