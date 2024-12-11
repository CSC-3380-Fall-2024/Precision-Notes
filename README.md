# Precision Notes

Hi and Welcome to our Project "Precision-Notes"!

Created by: Amy Granados, Cole Heausler, Konnor Trosclair, Kendrick Manchester and Thomas Woodcum

We are/were currently students at LSU (Louisiana State Univeristy), taking a project class: 3380 Object-Orientated Design.


Goal: 
The goal of this project is to create an interactive application with note-taking properties. The application consists of a general homepage with several subfunctions (Calculator, Flashcards, etc.)
We hope and wish that this application will be used by students to improve their academic standings and change study habits.


Designs and Documentation:
Documentation can be found within the "Design and Documentation" directory. This directory contains a UI blueprint and a design report amoong other things.
  

Important Notes:

For accessing the repo, follow these steps:

1) Download gitbash (it's free)
2) Create an empty folder and access it with either the gitbash terminal or right-clicking the folder and opening gitbash here. 
3) Git clone (repo link), into new folder with the use of gitbash.
4) You can move whatever folders/files into here (please make it organized.)
5) Then you can use android studio to create new branches, all of these functions should be under the git tab.
   
Use guide for our Application:

  STEP 1: Ensure succesful download of AndroidStudio most recent vers. https://developer.android.com/studio (Link is provided for easy download.)
  STEP 2: Ensure that it is successfully downloaded and run the IDE Application.
  STEP 3: When you open Andorid Studios, press the hamburger symbol at the top left then press file and select open and navigate to Precision-Notes.
  STEP 4: Once you are in the Precision-Notes folder click on the hamburger symbol at the top left then click "File" and then "Open". Inside of the Precison-Notes folder navigate to the folder called "Main Screen (GUI)" and open it. 
  STEP 5: Once you are in the Main Screen (GUI) folder, sync the gradle by clicking the elephant icon in the top right corner to make sure all of the dependencies are wokring.
  STEP 6: To change the device you are using from a phone to a tablet you can press the phone symbol on the far right of the screen it should pop up with device manager when hovering your mouse over it.
  STEP 7: To create a new device you can click on the plus button after pressing device manager. Then you press create virtual device (you can run whatever tablet you prefer but we recomend you use Pixel Tablet API 35 as this was the one we used when designing the project) the tablet can be accessed by pressing tablet then navigate to pixel tablet and click on that.
  STEP 8: to finally run the project you can press the green play button at the top of the screen then you can press the plus button to change the device as needed.
  
  HUB:
    The "HUB" is the default space for users when opening the application. They are greeted with a near blank page with a header and several drop down menus. These menus contain connecting functionality to other sections of our application (read more below.)

![Screenshot 2024-11-25 170125](https://github.com/user-attachments/assets/cb2c1992-8a26-47cd-9d69-a39770ac7d15)
![Screenshot 2024-11-25 170022](https://github.com/user-attachments/assets/eb2e8114-a99d-4cde-9099-81795217dd15)
![Screenshot 2024-11-25 170048](https://github.com/user-attachments/assets/4a082cf7-8426-4af3-af4b-d5d23eaa2b2a)


a) NOTEBOOK
  - Upon clicking the NOTEBOOK tab a blank page will appear. On this page user's are able to draw whatever they wish upon clicking the screen. Currently, there is no eraser so users have to leave the screen to restart drawing.
  - The notebook aslo has a formula button that can be accessed by pressing the gear icon, then pressing formulas which it will display a list of formula categories that can be pressed with each category containing multiple formulas within that field. Once a formula is clicked on it will be placed in the center of the screen and each formula can be dragged indavidually. This uses the formulaLib.kt file which contains all the formulas. The formulas as render as LaTeX on the notebook screen.
  - Users can also export the current version of their notebook screen by pressing the gear icon then clickin gon export. This will export their notebook as a PDF saving it within the Android Studio IDE. The PDF's can be access by by going to the top of the Android Studio IDE and press the hamburger icon, then click view, Tool Windows, then press Device Explorer. Afterwards you will be prompt with a file directory. To access the PDF from here you go to /storage/emulated/0/Android/data/com.thomasw.precision/files/Documents/PrecisionNotes. Afterwards a list of PDFs should display we could not figure out how to navigate to the PDFs on the emulator so for now they can be accesed that way.


  ![Screenshot 2024-11-25 165838](https://github.com/user-attachments/assets/3a5bf3ec-f41f-4c18-bc43-b757da558502)

b) FLASHCARD 
  IMPORTANT! FLASHCARD CODE IS NOT CONNECTED DIRECTLY TO MAIN HUB! YOU MUST LOAD THE FLASHCARD SEPERATELY! (Try loading as if you were opening another proejct in AndroidStudio's file directory system. Then run.)
  There is a certain way to get to the flash cards
  STEP 1: press the hamburger symbol at the top left then navigated to file, open, then go to the folder (Code Features (Flashcards)) located at this path Precision-Notes\src (Flashcard)\Code Features (Flashcards)
  STEP 2: sync the gradle by pressing the elephant symbol at the top right. (This may take a while as it did on some of our machines.)
  STEP 3: Now you can run the project by pressing the play button at the top.

![Screenshot 2024-11-27 121255](https://github.com/user-attachments/assets/9472509f-d9a1-4af5-8a48-00350e51b4e4)

   MANAGEMENT LIST

![Screenshot 2024-11-27 121333](https://github.com/user-attachments/assets/f55cf476-8a00-4146-989f-682dd0c4475e)

  
  The management list is a series of buttons of which each focusing on a different purpose.

  --"EDIT"
            
   Edit is self-explanitory, it opens up a display UI allowing users to edit the type of question presented on the flashcard and the answer. Users can also create several other flashcards here with the "NEXT" which opens up a new card while also allowing them to move between cards with the "PREVIOUS" button. The "DELETE" button deletes the current flashcard out of the current list.

  --"LOAD"
            
   The "LOAD" button allows users to load flashcards without making edits, this is to allow users to practice while still being presented with the answer that they provided inside of the "EDIT" section. This is different compared to the "RUN" function. (See Below)

  --"SAVE"
           
   Saves the current flashcard list present in the "EDIT" button. Users must hit SAVE before entering to practice otherwise, the flashcards will be displayed with the default inofrmation. 

   --"RUN"
           
   This runs the currently saved flashcards, putting them into a type-in-answer style practice session. The information present in the flashcard here is stored away and not present to the user. WHen inputting an answer to check if the response is correct, a Android Toast will appear at the bottom of the page. This Toast will confirm or deny whether the user inputted the answer saved to the flashcard correctly. Users may also hit the "NEXT" and "PREVIOUS" buttons to cycle between saved flashcards in order to maximize effective practice.
   
![Screenshot 2024-11-27 121349](https://github.com/user-attachments/assets/7b968837-3af4-4461-b243-afc64ecd25ff)
![Screenshot 2024-11-27 121406](https://github.com/user-attachments/assets/df90d740-ce13-441e-bcce-8ca1649b6add)

   --"QUIT"
            
   This quits the application bringing the user back to the "MAIN" of the application to allow them to access other functions of our application.

c) FOLDER
    Upon clicking the plus symbol and selecting FOLDER tab, users will be prompted to provide a name for the newly created folder. Upon selection a new folder will be created, users can click on the folder to access its contents. The folders are set up to where there can be infinite subfolders. In order to keep track of what folder has what items we used an ID counter that increments by one for each folder created. The folders also go to the next line when the width of the screen is reached.

  ![Screenshot 2024-11-25 170250](https://github.com/user-attachments/assets/48eb23b6-99c8-4501-a46d-9d0f4481d0ce)
  ![Screenshot 2024-11-25 170322](https://github.com/user-attachments/assets/6ef75ca5-abec-49b0-8107-e31636b08423)

D) PEN COLORS AND SIZE
Upon clicking the "Settings" button, users will see "Pens." Users can click that and will see a horizontal bar that is used to adjust size, and then a row of colorful circles to choose colors. To change the size of the pens, there is a vertical bar. Users are going to swipe it to change the size: to the left to decrease size and to the right to increase it. Users can click on a specific color circle to make their pen that color. When users are done picking the desired size and color, they can press the "Done" button to use their pen.

E) Calculator 
This calculator app is a simple yet functional tool created using Kotlin and Jetpack Compose. It allows users to perform various calculations, such as addition, subtraction, multiplication, division, and more complex operations like exponentiation and square roots. The app maintains a history of the last 10 calculations for easy reference. If a user inputs an incorrect expression, the app alerts them with a friendly error message. It’s designed to be intuitive and smooth, making it easy to handle everyday math tasks.  

Bug: Calculator buttons will overlap in the Landscape Version. Please keep in portrait will using the calculator.



    
    
    
