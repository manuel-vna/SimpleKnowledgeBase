# SimpleKnowledgeBase

<br>
Project status: In Progress
<br>

### Context

This is a Kotlin version on basis of the Java project 'ITKnowledgeBase'. (another repo in this Github space)

### Description

This mobile application provides a simple knowledge database. Information you don't want to forget can be saved and organised by the usage of four data fields: Title, Category, Description and Source-URL.
You can save, edit and delete knowledge entries. Searching can be done by a general keyword, by a date timeframe or (TBD) within one of the above fields specifically.
All user created 'Categories' can be shown and searched in an overview list.

<br>

### Components
The app covers the following aspects of Android programming:

- #### Saving data in a local database
Persistence Room Library, DAO, High-Order functions, Lambda with SAM, Coroutines

- #### Adaptive Layout
Constraint layout with Guidelines and Chains, Landscape Mode

- #### Displaying data
RecyclerView, Live Data (Observer), AppBar with NavigationDrawer

<br>

### App Architecture

The fragments are included in one activity. Navigation between those works via a 'NavController' (Navigation Graph in res/layout/navigation_graph.xml)
The app uses the MVVM design approach: UI Views - ViewModel + LiveData - Repository - Data Source.

- #### MVVM Architecture Details
For the purpose of analysis more than one approaches are in use:
(1)
Fragment<->ViewModel: Live Data Observation ; ViewModel<->Repository: cross-class variable access ; Repository<->Dao: async coroutine with await
Used for: KeywordSearch*, CategoryOverview*
(2)
Fragment<->ViewModel: Live Data Observation ; ViewModel<->Repository: high-order function ; Repository<->Dao: coroutine with suspend
Used for: AdvancedSearch*

<br>