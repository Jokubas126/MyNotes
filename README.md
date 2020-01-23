# MyNotes
Android application built in MVP design pattern, which uses SQLite database, to store notes made by the user.
User can store any amount of notes, where one note can have a title, content text, recorded audio file with the title of it and an image.

# Restrictions 

All of the content cannot be have more than one instance of each component (content text, recording and image).
Images can only be loaded from phone's storage and not external drive such as google photos.

# Methods and functionalities used

* MVP design pattern (based on [Android Architecture Blueprints v2](https://github.com/android/architecture-samples) )
* SQLite database for storing the notes
* Fragment based views
* RecyclerView with ViewAdapter for List activity
* Usage of PopupWindow for the recorder 
* Usage of PopupMenu 
* Usage of checkbox for deleting items
* Implicit and explicit intents
* MediaPlayer and MediaRecorder to record sound and play it
* Requests for the dangerours permissions
* Async Tasks for playing and recording sound
* Retrieving images from gallery using their Uri
