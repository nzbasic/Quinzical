# Quinzical

Menu:\
![alt text](https://media.discordapp.net/attachments/627267590862929961/772969678749827072/menu.png?width=1163&height=654 "Quinzical Menu")

Find the current release [HERE](https://github.com/SOFTENG206-2020/assignment-3-and-project-team-05/releases)

Instructions on how to run the application:\
Option 1: Extract files from the [downloaded zip](https://github.com/SOFTENG206-2020/assignment-3-and-project-team-05/releases) and run the playQuinzical.sh script.\
Option 2: Run the jar file on your terminal/IDE.\
Option 3: Import source files and all other files as a project in your IDE.

1) To run the script file from terminal:
  chmod +x playQuinzical.sh
  ./playQuinzical.sh
  
2) To run the application in Eclipse/Other IDE
   -Install javaFx inside Eclipse, https://marketplace.eclipse.org/content/efxclipse
   -Import Quinzical.jar or src files into your IDE. (Note: The categories folder should be inside the same folder as src)
   
NOTE:\
1)The categories folder must be placed in the same folder as where Quinzical.jar is placed (if you are to run the application via terminal)\
2)Make sure there isn't a folder named ".attempt" in the folder which contains Quinzical.jar.\
3)Make sure that the festival command is available on your virturalbox with akl_nz_jdt_diphone installed.

Adding Questions to database:\
To add questions browse to the categories folder.\

1)For NZ questions, double click on the file which matches the name of the category you would\
like to add a question to. On a new line, type your question in the followung structure :[question clue,answer,questiontype]\
The question clue is the question text in the form of an answer like Jeopardy. [Note that the question should not have any commas in them and\
there is no need to worry about upper/lower cases not being accepted as a correct answer]
The answer is the answer to the question, if there are multiple accepted answers separate the answers by slash /. [Note that individual answers\
should not contain any commas]\
The question type is one of the following: what is/who is/where is/when is.\
Example: qustion:These are the birds that New Zealanders are named after.\
         answer: Kiwis/kiwi\
         question type:What are\
         combine the sections by commas: These are the birds that New Zealanders are named after.,Kiwis/kiwi,What are\
Save the file and the question should be added successfully. If the game cannot run/behaves in an abnormal way it is likely because you didn't \
follow the format for adding questions. Double check the added question is in the correct format, check question,answser and question type are\
separated by a single comma and commas are only used to separate sectiions, should not exist any where inside question text,answer text or question\
type.\

2)For international Questions, you can add questions to the existing files and you are also permitted to create new files. The file name can be the name\
of a country or any customised name you prefer. Questions should be added in the same way following the instructions for adding a question in the NZ\
section above in 1).


         


