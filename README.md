# CoroutinesRoomMVVM - Android - Kotlin

This simple application demonstrates basic logic how we should apply the coroutines with Room database. 

Simply, The application containes three different screen, these are as follows:

1) SignUpFragment
2) LoginFragment
3) MainFragment

The Signup fragment retrieves the data provided by the user, such as username , password and information about the user. After
getting necessary information from the user, we are storing user details to user UserDatabase with User object.

If the signup procedure succesffuly completed we direct the user to MainFragment and the application displays the Username that
is entered by the user. 

At MainFragment user can logout or can delete their account from locally stored database. If they remove their account
they have to signup again. 

At Login fragment either user can login or sign up with provided details. 

In order to implement the application following technologies are used: 

1) Kotlin
2) Coroutines 
3) Navigation Components 
4) Single Activity structure 
5) MVVM
6) Lifecycle Extensions
7) Room 
