# SpaceXFan
This project was all about the spaceX rockets and their upcoming launches.

# Project planning:
Divided the project into two parts
* Rockets - list and details
* Upcoming launches - list and details

# Tools used
Android studio, Kotlin, XML, SpaceX Api

# Android tools used
MVVM, ViewModel, LiveData, Databinding, ViewBinding, RXAndroid, Biomteric Verification, Fragments, Viewpager2, RecyclerView, Constraint layout, Firebase crashlytics and custom events

# App Flow explanation
* Opens with splash screen with a zoomIn animation.
* In home screen, created three bottom tabs for easy navigation b/w rockets, favorite rockets and upcoming launches.
* For favorite rockets, i have been maintaining the list in the activity which holds the three fragments. so if you close and open the favorite rockets list will be cleared.
* Added Biometric authentication in favorites list inorder view its details.
* In rocket details, we are displaying the respective rocket details and added favorite button to add/remove from favorites list.
* Added custom firebase event in rocket details and upcoming launch details.
* Centralized the favorite state from rocket details, favorites list and rockets list, so when ever u change the state it will be reflected in other respective places also.
* Created services library where we will connect with spaceX api and return data to the app module.
