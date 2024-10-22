import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.flashcardapplication.R


data class Flashcard(val question: String, val answer: String)

    class FlashcardRepository {
        private val flashcards = mutableListOf<Flashcard>()

        fun addFlashcard(flashcard: Flashcard) {
            flashcards.add(flashcard)
        }

        fun getFlashcards(): List<Flashcard> {
            return flashcards
        }

        fun removeFlashcard(flashcard: Flashcard) {
            flashcards.remove(flashcard)
        }
    }

class FlashcardActivity : AppCompatActivity() {

    private lateinit var flashcardRepository: FlashcardRepository
    private lateinit var questionTextView: TextView
    private lateinit var answerTextView: TextView
    private lateinit var nextButton: Button
    private lateinit var previousButton: Button
    private lateinit var saveButton: Button
    private lateinit var newButton: Button
    private var currentFlashcardIndex = 0

        @SuppressLint("MissingInflatedId")

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_main)

            flashcardRepository = FlashcardRepository()
            questionTextView = findViewById(R.id.Question_text)
            answerTextView = findViewById(R.id.Answer_text)
            nextButton = findViewById(R.id.next_button)
            previousButton = findViewById(R.id.prev_button)
            saveButton = findViewById(R.id.save_button)
            newButton= findViewById(R.id.new_button)

            nextButton.setOnClickListener {
                navigateToNextFlashcard()
        }
            previousButton.setOnClickListener {
                navigateToPreviousFlashcard()
        }

            saveButton.setOnClickListener {
                saveFlashcard()
            }
            newButton.setOnClickListener {
                newFlashcard()
            }
    }

   private fun navigateToNextFlashcard() {

        val flashcards = flashcardRepository.getFlashcards()
        if (currentFlashcardIndex < flashcards.size - 1) {
               currentFlashcardIndex++
            displayFlashcard(flashcards[currentFlashcardIndex])
        }
    }

    private fun navigateToPreviousFlashcard() {
        val flashcards = flashcardRepository.getFlashcards()
        if (currentFlashcardIndex > 0) {
            currentFlashcardIndex--
            displayFlashcard(flashcards[currentFlashcardIndex])
        }
    }

    private fun saveFlashcard() {
        val title = questionTextView.text.toString()
        val body = answerTextView.text.toString()
        val flashcard = Flashcard(title, body)
        flashcardRepository.addFlashcard(flashcard)
        clearEditTexts()
    }

   private fun newFlashcard() {
        clearEditTexts()
    }

    private fun displayFlashcard(flashcard: Flashcard) {
        questionTextView.setText(flashcard.question)
        answerTextView.setText(flashcard.answer)
    }

    private fun clearEditTexts() {
        questionTextView.setText("")
        answerTextView.setText("")
    }
}