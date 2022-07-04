# SimpleKnowledgeBase

<br>
Project status: In Progress
<br>

### Context

This is a Kotlin version on basis of the Java project 'ITKnowledgeBase', which is another repo in the same Github space.
Additionally this app tries to improve the user experience as well as the other app's overall architecture.

### Description

This mobile application provides a simple knowledge database. Information you don't want to forget can be saved and organised by the usage of four data fields: Title, Category, Description and Source-URL.
You can save, edit and delete knowledge entries. Searching can be done by a general keyword or (TBD) within one of the above fields specifically or by a date timeframe. (TBD) All user created 'Categories' can be shown in an overview list.

<br>

### Components
The app covers the following aspects of Android programming:

- #### Saving data in a local database
Persistence Room Library, Dao

- #### Adaptive Layout
Constraint layout with Guidelines and Chains, Landscape Mode

- #### Displaying data
RecyclerView, Live Data (Observer), AppBar with NavigationDrawer

<br>

### App Architecture

The fragments are included in one activity. Navigation between those works via a 'NavController' (Navigation Graph in res/layout/navigation_graph.xml)
The app uses the MVVM desgin approach: UI Views - ViewModel + LiveData - Repository - Data Source.
(TBD) A visualisation (UML) to show the structure of the app.

<br>