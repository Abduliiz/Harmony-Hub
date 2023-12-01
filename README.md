
<span style="color:darkorange">

# My Personal Project

</span>

Hello My name is **Abdulrahman Al Odat**, this will be my very first personal project ever!!
This project is part of Computer Science 210 Course that I am currently
taking at The University of British Columbia (UBC)



**Vision of Features:**
- play a song 
- pause 
- playlist - adding/removing songs
- artist 
- fast-forward/backward 
- skip song
- repeat songs
- shuffle 
- favourites
- volume 

<span style="color:darkorange">

## The Purpose of My Project

</span>


1. **What will the application do?**
2. **Who will use it?**
3. **Why is this project of interest to you?**



I have decided to create a music player application that can play music and read *MP3 files*.
This application can be used by anybody that loves music or has *MP3 files* like voice recordings or any other audio files that have the *MP3 format*. 
I am really interested in this project because I use a music player daily in my life and I think it would be really cool to create my own to show my friends and family.



<span style="color:darkorange">

## User Stories

</span>

- As a user, I want to be able to save my playlist to file (if I so choose)
- As a user, I want to be able to be able to load my playlist from file (if I so choose)
- As a user, I want to be able to add songs to my playlist. 
- As a user, I want to be able to remove songs from my playlist.
- As a user, I want to be able to view my list of songs in my playlist.
- As a user, I want to be able to be able to play, pause, and skip a song
- As a user, I want to be able to view the artist of the song
- As a user, I want to be able to shuffle between the songs
- As a user, I want to be able to repeat songs
- As a user, I want to be able to mark certain songs as favourite's

<span style="color:darkorange">

# Instructions for Grader
</span>

- You can generate the first required action related to the user story "adding multiple Xs to a Y" by filling in the 
required data in the text fields then pressing the "Add" button
- You can generate the second required action related to the user story
"I want to be able to be able to play, pause, and skip a song" by pressing on the song in the playlist and labelled
buttons for the other actions
- You can locate my visual component by looking at the frame
- You can save the state of my application by pressing the "Save" button
- You can reload the state of my application by pressing the "Load" button



### Phase 4: Task 2

_this is a sample of what the EventLogs may look like:_

```
---------------EVENT LOG---------------
Added song: Gesaffelstein, To: My PlayList   Thu Nov 30 22:37:04 PST 2023
Started Playing a Song   Thu Nov 30 22:37:07 PST 2023
Song Resumed   Thu Nov 30 22:37:12 PST 2023
Song Resumed   Thu Nov 30 22:37:13 PST 2023
Song Resumed   Thu Nov 30 22:37:14 PST 2023
Song Paused   Thu Nov 30 22:37:15 PST 2023
Song Resumed   Thu Nov 30 22:37:17 PST 2023
Started Playing a Song   Thu Nov 30 22:37:17 PST 2023
Song Paused   Thu Nov 30 22:37:18 PST 2023
Song Resumed   Thu Nov 30 22:37:19 PST 2023
Started Playing a Song   Thu Nov 30 22:37:19 PST 2023
Song Paused   Thu Nov 30 22:37:20 PST 2023
Removed: Gesaffelstein, From: My PlayList   Thu Nov 30 22:37:29 PST 2023
```


### Phase 4: Task 3

To improve my program design, I would refactor the PlayerApp and the AudioPlayer Classes into 
many subclasses because there are so much functionality crammed in those two classes unlike my PlayList or Song classes.
I Would also improve my exception handling and try to implement some of the new design patterns we have learned 
in the last three weeks of the course.