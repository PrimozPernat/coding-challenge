# android-codelab
Android project that serves as a base for code challenges implemented by applicants.
The base is written in kotlin. 

# Android Coding Challenges
Coding challenges are useful when the applicant does not provide a github repository or any work samples. Even if a github repository has been provided it is generally a good idea to give the applicant a task to solve and have him present his solution in a separate session. 

## General Instructions
The following instructions/conditions are valid independently of the actual coding challenge

- The code base has been tested with Android Studio 3.4.1 which is the recommended version, however feel free to try a higher version and adjust the configuration as needed
- The task should be implemented in kotlin
- Approach this task as if it was a real-world implementation - i.e. exactly how you would approach the task if you were working for a company
- 3rd party libraries may be used
- 3rd party libraries must be wrapped: They should be abstracted out, so any other library could be plugged into the solution
- The base project for this task will be provided by us
- Once completed, please send us your solution and presents it to us, followed by a discussion about the implementation and design decisions made
- The solution can be sent as a zip file or as a publicly accessible github/gitlub etc project link
- The solution sent to us must be complete, i.e. can be opened directly via Android Studio without additional configuration

## Feature Discovery
In this challenge the applicant has to implement feature discovery, the following conditions are given:

The official design guidelines for implementing Feature Discovery can be found here: https://material.io/guidelines/growth-communications/feature-discovery.html#feature-discovery-design-patterns
- The solution must be reusable, i.e. no hard-coding
- Pick the appropriate storage type to save the information on whether a Feature Discovery has already been shown
- Each Feature discovery should only be shown once
- If a Feature Discovery has been shown is not persisted beyond re-installation of the application (or clearing the data/cache)
- Implement the showing of the Following Feature Discovery sequences (played one after the other):
- Memo List - the first time the user opens this screen, below Feature Discoveries are shown
- Option menu item: Show All (Title: "Filter View" Description: "Click here to switch between showing all and showing open memos")
- FAB to create a new memo (Title: "Create new memos" Description: "Click here to create a new memo")
- Memo creation screen - the first time the user opens this screen, below Feature Discoveries are shown
- Option menu item: Save (Title: "Saving" Description: "Click here to save the memo")

## Voice Recognition
In this challenge the applicant has to implement voice recognition support, the following conditions are given:

- Implement voice recognition for the following two actions:
-- Create a new Memo > should open the "Create Memo" Activity
-- Switch the filter on the Homescreen to toggle between "Show All" and "Show Open" > toggling the filter should immediately update the UI accordingly
- Voice recognition should be implemented in a reusable way, i.e. with proper abstraction layers and the possibility to add further voice actions later on
- For now supporting English-language commands is sufficient

## Location Based Notifications
In this challenge the applicant has to implement location-based notifications/reminders, the following conditions are given:

- When creating a new memo, the user provides a location by selecting a point on a map (for instance: google maps or open street maps)
- The memo is then saved
- Once the user physically reaches that location, a notification should be displayed in the phone's status bar, that contains the title and the first 140 characters of the note text
- "Reaching the location" is defined as follows: The user is within 200 meters of the location he initially selected during the memo creation
- The notification should also contain an icon (the icon choice is up to you)
- Some form of location tracking will be required to achieve the desired result, i.e. to know when a user is close to the given location of a memo
- The feature must also work, when the app is running in the background (or possibly not running at all)
