# Precision Notes

Hi and Welcome to our Project "Precision-Notes"!
Created by: Amy Granados, Cole Heausler, Konnor, Kendrick and Thomas Woodcum


Goal: 
The goal of this application is to create a interactive applicative with note-taking properties. The application consists of a general homepage, with several subfunctions (Calculator, Flashcards, etc.)
We hope and wish that this application will be used by students to improve their academic standings and change study habits.


Designs and Documentation:
Documentation can be found within the "Design and Documentation" directory. This directory contains a UI blueprint and a design report amoong other things.


Flashcards Functionality:
  Android Studio Files (Ease of Access):

  Precision-Notes\src (Flashcard)\Code Features (Flashcards)\app\src\main\java\MainActivity.kt" (This is main_activity for the Flashcard functionality. This contains the coded functions that allow for the flashcard mechanisms.)
  Precision-Notes\src (Flashcard)\Code Features (Flashcards)\app\src\main\res\layout"  (This contains the layout/GUI for the flashcard functionality.)


FormulaLib:
  Contains a list of calulcator functions and calculations to allow for faster computations for users. (This will work in conjuction with the calculation application.)

Important Notes:

For accessing the repo, follow these steps:

1) Download gitbash (it's free)
2) Create an empty folder and access it with either the gitbash terminal or right-clicking the folder and opening gitbash here. 
3) Git clone (repo link), into new folder with the use of gitbash.
4) You can move whatever folders/files into here (please make it organized.)
5) Then you can use android studio to create new branches, all of these functions should be under the git tab.
   

Use guide for our Application:

  
  HUB:
    The "HUB" is the default space for users when opening the application. They are greeted with a near blank page with a header and several drop down menus. These menus contain connecting functionality to other sections of our application (read more below.)

    
  FLASHCARDS:
    The "FLASHCARDS" are supposed to a helpful tool for users to use in order to learn material and practice memorization. It is designed for students in mind but, can also be used by other demographics.
    The application works as follows:

    1)  TITLE SCREEN 
        It initially opens up to a title screen, upon clicking the button it directs the users to the management section.


    2)   MANAGEMENT LIST
        The management list is a series of buttons of which each focusing on a different purpose.

          --"EDIT"
            Edit is self-explanitory, it opens up a display UI allowing users to edit the type of question presented on the flashcard and the answer. Users can also create several other flashcards here with the "NEXT" which opens up a new card while also allowing them to move between cards with the "PREVIOUS" button. The "DELETE" button deletes the current flashcard out of the current list.

          --"LOAD"
            The "LOAD" button allows users to load flashcards without making edits, this is to allow users to practice while still being presented with the answer that they provided inside of the "EDIT" section. This is different compared to the "RUN" function. (See Below)

          --"SAVE"
            Saves the current flashcard list present in the "EDIT" button. Users must hit SAVE before entering to practice otherwise, the flashcards will be displayed with the default inofrmation. 

          --"RUN"
            This runs the currently saved flashcards, putting them into a type-in-answer style practice session. The information present in the flashcard here is stored away and not present to the user. WHen inputting an answer to check if the response is correct, a Android Toast will appear at the bottom of the page. This Toast will confirm or deny whether the user inputted the answer saved to the flashcard correctly.
            Users may also hit the "NEXT" and "PREVIOUS" buttons to cycle between saved flashcards in order to maximize effective practice.

          --"QUIT"
            This quits the application bringing the user back to the "MAIN" of the application to allow them to access other functions of our application.


    
    
    
