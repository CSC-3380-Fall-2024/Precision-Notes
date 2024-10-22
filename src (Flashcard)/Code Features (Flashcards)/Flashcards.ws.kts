data class Flashcard(val question: String, val answer: String)

class FlashcardRepository {
    private val flashcards = mutableListOf<Flashcard>()

    fun addFlashcard(flashcard: Flashcard) {
        flashcards.add(flashcard)
    }

    fun getFlashcards(): List<Flashcard> {
        return flashcards
    }
}

fun main() {
    val repository = FlashcardRepository()

    while (true) {
        println("1. Add Flashcard")
        println("2. View Flashcards")
        println("3. Exit")

        print("Choose an option: ")
        val option = readLine()!!.toInt()

        when (option) {
            1 -> {
                print("Enter question: ")
                val question = readLine()!!
                print("Enter answer: ")
                val answer = readLine()!!
                repository.addFlashcard(Flashcard(question, answer))
                println("Flashcard added!")
            }
            2 -> {
                val flashcards = repository.getFlashcards()
                if (flashcards.isEmpty()) {
                    println("No flashcards yet!")
                } else {
                    for ((index, flashcard) in flashcards.withIndex()) {
                        println("${index + 1}. ${flashcard.question} - ${flashcard.answer}")
                    }
                }
            }
            3 -> {
                println("Goodbye!")
                return
            }
            else -> {
                println("Invalid option. Please try again.")
            }
        }
    }
}