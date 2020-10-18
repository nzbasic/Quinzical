# Quinzical

Quinzical is a javafx based quiz app about New Zealand (+bonus International questions). 

Menu:\
![alt text](https://cdn.discordapp.com/attachments/627267590862929961/767327293764009994/unknown.png "Quinzical Menu")

Question Answer:\
![alt text](https://cdn.discordapp.com/attachments/627267590862929961/767327457606238248/unknown.png "Question Selection Screen")

Find the current release [HERE](https://github.com/SOFTENG206-2020/assignment-3-and-project-team-05/releases)

Instructions on how to run the application:\
Option 1: Extract files from the [downloaded zip](https://github.com/SOFTENG206-2020/assignment-3-and-project-team-05/releases) and run the playQuinzical.sh script.\
Option 2: Run the jar file on your terminal/IDE.\
Option 3: Import source files and all other files as a project in your IDE.

Speech readouts will not work unless you have akl_nz_jdt_diphone installed for festival.

You must have javafx installed locally at /usr/share/java/lib for this script to work.

1) Extract files from the release zip into a folder.

2) Give yourself permission to run the play.sh script:\
    chmod +x play.sh

3) Run the script file:\
    ./play.sh
  
2) To run the application in Eclipse/Other IDE
   -Install javaFx inside Eclipse, https://marketplace.eclipse.org/content/efxclipse
   -Import Quinzical.jar or src files into your IDE. (Note: The categories folder should be inside the same folder as src)
   
NOTE:\
1)The categories folder must be placed in the same folder as where Quinzical.jar is placed (if you are to run the application via terminal)\
2)Make sure there isn't a folder named "attempt" in the folder which contains Quinzical.jar.\
3)Make sure that the festival command is available on your virturalbox with akl_nz_jdt_diphone installed.


