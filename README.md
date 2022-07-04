# SimpleKnowledgeBase

<br>

### Context

This is a Kotlin version on basis of the java project 'ITKnowledgeBase' (within this repo)
Additionally it tries to improve the user experience as well as the app's overall architecture.

### Description

This mobile application provides a simple Knowledge database. Information you don't want to forget can be saved and organised by the usage of four data fields: Title, Category, Description and Source.
You can save, edit and delete knowledge entries. Searching can be done by a general keyword or (TBD) within one of the above fields specifically. (TBD) All user created Categories can be shown in a overview list.

<br>

### Components
The app covers the following aspects of Android programming:

- #### Saving data in a local database
Persistence Room Library, Dao

- #### Adaptive Layout
Constraint Layout with Guidelines and Chains, Landscape Mode

- #### Displaying data
RecyclerView, Live Data, NavigationDrawer

<br>

### App Architecture

Fragments are included in one Activity. Navigation between those works via a NavController (Navigation Graph in res/layout/navigation_graph.xml)
The app uses the MVVM desgin approach: UI Views - ViewModel + LiveData - Repository - Data Source
(TBD) uml file to visualize the structure of the app

<br>