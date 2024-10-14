# SimpleKnowledgeBase

<br>
Project status: DEPRECATED
<br>

### Context

This is a Kotlin version on basis of the Java project 'ITKnowledgeBase'. (another repo in this Github space)

### Description

This mobile application provides a simple knowledge database. Information you don't want to forget can be saved and organised by the usage of four data fields: <br>
Title, Category, Description and Source-URL. <br>
You can save, edit and delete knowledge entries. Searching can be done by a general keyword, by a date timeframe or within one of the above fields specifically.
All user created 'Categories' can be shown and searched in an overview list.

<br>

### Areas
The app covers the following aspects of Android programming:

- #### Saving data in a local database
Persistence Room Library, DAO, High-Order functions, Lambda with SAM, Coroutines

- #### Adaptive Layout
Constraint layout with Guidelines and Chains, Landscape Mode

- #### Displaying data
RecyclerView, Live Data (Observer), AppBar with NavigationDrawer

<br>

### App Architecture

The fragments are included in one activity. Navigation between those works via a 'NavController' (Navigation Graph in res/layout/navigation_graph.xml) <br>
The app uses the MVVM design approach: UI Views - ViewModel + LiveData - Repository - Data Source.

- #### MVVM Architecture Details
For the purpose of analysis more than one approaches are in use: <br>
(1) <br>
Fragment<->ViewModel: Live Data Observation ; ViewModel<->Repository: cross-class variable access ; Repository<->Dao: async coroutine with await <br>
Used for: KeywordSearch*, CategoryOverview* <br>
(2) <br>
Fragment<->ViewModel: Live Data Observation ; ViewModel<->Repository: high-order function ; Repository<->Dao: coroutine with suspend <br>
Used for: AdvancedSearch*

<br>
